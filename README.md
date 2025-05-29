# Finance App - Módulo 2

Este repositório contém o código completo do **Módulo 2** do Sistema de Gestão Financeira Simplificada:
- Modelagem lógica e script SQL de criação e povoamento
- Back‑end Java (Spring Boot + JDBC puro)
- Front‑end React.js (Create React App)

---

## 📋 Pré‑requisitos

Antes de começar, certifique‑se de ter instalado em sua máquina:

- **Git** (https://git-scm.com)
- **MySQL** (versão 8.x)
- **Java 17** e **Maven**
- **Node.js** (14.x ou superior) e **npm**

---

## 🗂️ Clonar este repositório

```bash
# no diretório onde deseja salvar o projeto
git clone https://github.com/<seu-usuario>/finance-app.git
cd finance-app
```

---

## 🛠️ Banco de Dados

1. Inicie o serviço MySQL:

   ```bash
   # macOS (Homebrew)
   brew services start mysql
   # ou no Linux
   sudo service mysql start
   ```

2. Rode o script de criação e povoamento:

   ```bash
   mysql -u root -p < backend/scripts/db_setup.sql
   ```

3. Verifique se o banco `finance_app` foi criado e as tabelas estão no lugar:

   ```bash
   mysql -u root -p -e "USE finance_app; SHOW TABLES;"
   ```

---

## ⚙️ Configurar Back‑end (Java/Spring Boot)

1. Abra `backend/src/main/resources/application.example.properties` (altere o nome do arquivo para application.properties) e ajuste:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/finance_app?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=<SUA_SENHA>
   server.port=8080
   ```

2. Compile e rode o back‑end:

   ```bash
   cd backend
   # para compilar e gerar o JAR\ m
   mvn clean package
   # executar o JAR
   java -jar target/finance-app-1.0.0.jar
   # ou, em desenvolvimento
   mvn spring-boot:run
   ```

3. Teste a API:

   ```bash
   curl http://localhost:8080/api/transacoes
   ```

---

## 🌐 Configurar Front‑end (React.js)

1. Instale dependências e rode:

   ```bash
   cd frontend
   npm install
   npm start
   ```

2. Abra no navegador: `http://localhost:3000`

---

## 🚀 Funcionalidades

### Transações
- **Inserir**: Modal de criação com campos para descrição, valor, data, tipo e categoria
- **Editar**: Modal de edição com campos pré-preenchidos
- **Excluir**: Exclusão com confirmação
- **Filtrar**: 
  - Por data (início e fim)
  - Por tipo (receita/despesa)
  - Por categoria
- **Ordenar**: 
  - Por data
  - Por valor
  - Por descrição
  - Por categoria
- **Formatação**: Valores em reais (R$) e datas no formato brasileiro

### Categorias
- **Inserir**: Modal de criação com nome e tipo
- **Editar**: Modal de edição com campos pré-preenchidos
- **Excluir**: 
  - Confirmação antes de excluir
  - Proteção contra exclusão de categorias com transações
- **Visualizar**: Tabela com todas as categorias
- **Filtrar**: Por tipo (receita/despesa)

### Dashboard
- **Resumo Financeiro**:
  - Total de receitas
  - Total de despesas
  - Saldo atual
- **Gráficos**:
  - Receitas vs Despesas
  - Distribuição por categoria
- **Filtros**: Por período (mês atual, mês anterior, etc.)

### Relatórios
- **Análise Financeira**:
  - Evolução de receitas e despesas
  - Distribuição por categoria
  - Tendências
- **Filtros**:
  - Por período
  - Por tipo de transação
  - Por categoria

### Interface
- **Responsiva**: Adaptável a diferentes tamanhos de tela
- **Modais**: Para criação e edição de registros
- **Validação**: Em tempo real dos campos
- **Feedback**: Mensagens de sucesso e erro
- **Navegação**: Menu lateral com acesso rápido às funcionalidades

### Segurança
- **Validação de Dados**: No frontend e backend
- **Proteção de Dados**: Contra exclusão acidental
- **Tratamento de Erros**: Mensagens amigáveis para o usuário

---
