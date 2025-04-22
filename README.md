# Finance App - Módulo 1

Este repositório contém o código completo do **Módulo 1** do Sistema de Gestão Financeira Simplificada:
- Modelagem lógica e script SQL de criação e povoamento
- Back‑end Java (Spring Boot + JDBC puro)
- Front‑end React.js (Create React App)

---

## 📋 Pré‑requisitos

Antes de começar, certifique‑se de ter instalado em sua máquina:

- **Git** (https://git-scm.com)
- **MySQL** (versão 8.x)
- **Java 17** e **Maven**
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

## ⚙️ Configurar Back‑end (Java/Spring Boot)

1. Abra `backend/src/main/resources/application.properties` e ajuste:

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

## 🚀 Testando o CRUD

- **Inserir**: use o formulário no topo para adicionar transações
- **Editar**: clique em ✏️ e edite via modal
- **Excluir**: clique em 🗑️
- A lista atualiza automaticamente

---
