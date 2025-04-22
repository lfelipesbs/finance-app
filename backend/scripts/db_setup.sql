CREATE DATABASE IF NOT EXISTS finance_app 
    CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;
USE finance_app;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS Transacao_FormaPagamento;
DROP TABLE IF EXISTS Log_Alteracao;
DROP TABLE IF EXISTS Pagamento;
DROP TABLE IF EXISTS Conta_Bancaria;
DROP TABLE IF EXISTS Transacao;
DROP TABLE IF EXISTS FormaPagamento;
DROP TABLE IF EXISTS Categoria;
DROP TABLE IF EXISTS Entidade_Comercial;
DROP TABLE IF EXISTS Funcionario;
DROP TABLE IF EXISTS Administrador;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS Empresa;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE Empresa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    razao_social VARCHAR(100) NOT NULL,
    cnpj CHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    empresa_id INT NOT NULL,
    role ENUM('admin','funcionario') NOT NULL,
    FOREIGN KEY (empresa_id) REFERENCES Empresa(id)
);

CREATE TABLE Administrador (
    usuario_id INT PRIMARY KEY,
    nivel_acesso ENUM('alto','medio','baixo') DEFAULT 'baixo',
    data_inicio_permissao DATE NOT NULL,
    setor_administrativo VARCHAR(50),
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

CREATE TABLE Funcionario (
    usuario_id INT PRIMARY KEY,
    cargo VARCHAR(50) NOT NULL,
    departamento VARCHAR(50),
    data_contratacao DATE NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

CREATE TABLE Entidade_Comercial (
    id INT AUTO_INCREMENT PRIMARY KEY,
    razao_social VARCHAR(100) NOT NULL,
    tipo ENUM('cliente','fornecedor') NOT NULL,
    rua VARCHAR(100),
    numero VARCHAR(20),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado CHAR(2),
    cep CHAR(9)
);

CREATE TABLE Categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Transacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data DATE NOT NULL,
    descricao TEXT,
    valor DECIMAL(10,2) NOT NULL CHECK (valor >= 0),
    tipo ENUM('receita','despesa') NOT NULL,
    categoria_id INT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES Categoria(id)
);

CREATE TABLE FormaPagamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL
);

CREATE TABLE Transacao_FormaPagamento (
    transacao_id INT NOT NULL,
    forma_pagamento_id INT NOT NULL,
    PRIMARY KEY (transacao_id, forma_pagamento_id),
    FOREIGN KEY (transacao_id) REFERENCES Transacao(id) ON DELETE CASCADE,
    FOREIGN KEY (forma_pagamento_id) REFERENCES FormaPagamento(id)
);

CREATE TABLE Pagamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    forma_pagamento VARCHAR(50) NOT NULL,
    status ENUM('pago','pendente','atrasado') NOT NULL DEFAULT 'pendente',
    data_pagamento DATE NOT NULL,
    usuario_id INT NOT NULL,
    transacao_id INT NOT NULL,
    entidade_comercial_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
    FOREIGN KEY (transacao_id) REFERENCES Transacao(id) ON DELETE CASCADE,
    FOREIGN KEY (entidade_comercial_id) REFERENCES Entidade_Comercial(id)
);

CREATE TABLE Log_Alteracao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao TEXT NOT NULL,
    usuario_id INT NOT NULL,
    transacao_id INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
    FOREIGN KEY (transacao_id) REFERENCES Transacao(id) ON DELETE CASCADE
);

CREATE TABLE Conta_Bancaria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    banco VARCHAR(50) NOT NULL,
    agencia VARCHAR(20),
    conta VARCHAR(20) NOT NULL,
    tipo_conta ENUM('corrente','poupanca') NOT NULL,
    saldo_inicial DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    empresa_id INT NOT NULL,
    FOREIGN KEY (empresa_id) REFERENCES Empresa(id)
);

INSERT INTO Empresa (razao_social, cnpj, telefone, email) VALUES
    ('Alpha Consultoria', '12345678000100', '(11)98765‑4321', 'contato@alpha.com'),
    ('Beta Tech',        '98765432000199', '(21)91234‑5678', 'suporte@beta.com'),
    ('Gamma Soluções',   '19283746000155', '(31)99876‑5432', 'vendas@gamma.com');

INSERT INTO Usuario (nome, email, senha, empresa_id, role) VALUES
    ('Ana Silva',    'ana.silva@alpha.com',    'senha123', 1, 'admin'),
    ('Bruno Costa',  'bruno.costa@alpha.com',  'senha123', 1, 'funcionario'),
    ('Carla Souza',  'carla.souza@beta.com',   'senha123', 2, 'admin'),
    ('Daniel Alves', 'daniel.alves@beta.com',  'senha123', 2, 'funcionario'),
    ('Eduardo Lima', 'eduardo.lima@gamma.com', 'senha123', 3, 'funcionario');

INSERT INTO Administrador (usuario_id, nivel_acesso, data_inicio_permissao, setor_administrativo) VALUES
    (1, 'alto',  '2024-01-01', 'Financeiro'),
    (3, 'medio', '2024-03-01', 'TI');

INSERT INTO Funcionario (usuario_id, cargo, departamento, data_contratacao) VALUES
    (2, 'Analista Financeiro',      'Financeiro',   '2024-02-15'),
    (4, 'Assistente Administrativo','Administração', '2024-04-01'),
    (5, 'Estagiário',               'Financeiro',   '2025-01-10');

INSERT INTO Entidade_Comercial (razao_social, tipo, rua, numero, bairro, cidade, estado, cep) VALUES
    ('Cliente A',    'cliente',    'Rua 1',      '100', 'Centro',     'São Paulo',      'SP', '01000‑000'),
    ('Fornecedor B', 'fornecedor', 'Av. B',      '200', 'Jardim',     'Rio de Janeiro', 'RJ', '20000‑000'),
    ('Cliente C',    'cliente',    'Travessa C', '50',  'Bela Vista', 'Belo Horizonte', 'MG', '30000‑000');

INSERT INTO Categoria (nome) VALUES
    ('venda'),
    ('manutencao'),
    ('salario'),
    ('imposto'),
    ('servicos');

INSERT INTO Transacao (data, descricao, valor, tipo, categoria_id) VALUES
    ('2025-04-01','Venda de produto X',    1500.00,'receita',1),
    ('2025-04-05','Manutenção equipamento',  300.00,'despesa',2),
    ('2025-04-07','Pagamento de salário',  2500.00,'despesa',3),
    ('2025-04-10','Receita de serviços',   2000.00,'receita',5),
    ('2025-04-12','Pagamento de imposto',   500.00,'despesa',4);

INSERT INTO FormaPagamento (descricao) VALUES
    ('Dinheiro'),
    ('Cartão de Crédito'),
    ('Transferência'),
    ('Boleto');

INSERT INTO Transacao_FormaPagamento (transacao_id, forma_pagamento_id) VALUES
    (1,1),(1,3),
    (2,3),
    (3,3),
    (4,2),(4,4),
    (5,4);

INSERT INTO Pagamento (forma_pagamento, status, data_pagamento, usuario_id, transacao_id, entidade_comercial_id) VALUES
    ('Dinheiro',        'pago',    '2025-04-01', 1, 1, 1),
    ('Transferência',   'pago',    '2025-04-06', 2, 2, 2),
    ('Transferência',   'pendente','2025-04-07', 4, 3, 1),
    ('Cartão de Crédito','pago',    '2025-04-10', 5, 4, 3),
    ('Boleto',          'atrasado','2025-04-15', 2, 5, 1);

INSERT INTO Log_Alteracao (data_hora, descricao, usuario_id, transacao_id) VALUES
    ('2025-04-02 10:00:00','Ajuste de valor da transacao 1',     1, 1),
    ('2025-04-06 14:30:00','Correção de categoria da transacao 2',2, 2),
    ('2025-04-11 09:15:00','Alteração status do pagamento 3',    4, 3);

INSERT INTO Conta_Bancaria (banco, agencia, conta, tipo_conta, saldo_inicial, empresa_id) VALUES
    ('Banco X','0001','12345-6','corrente', 10000.00, 1),
    ('Banco Y','0002','23456-7','poupanca',  5000.00,  2),
    ('Banco Z','0003','34567-8','corrente', 20000.00,  3);
