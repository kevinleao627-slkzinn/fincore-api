# 🏦 FinCore API

**Financial Core Banking Simulation**

A REST API de simulação bancária desenvolvida com **Java 21, Spring Boot e PostgreSQL**, com o objetivo de reproduzir funcionalidades essenciais de um sistema bancário, aplicando boas práticas de desenvolvimento backend, segurança e integridade de dados.

O projeto está sendo desenvolvido como iniciativa de estudo e portfólio, com foco em arquitetura de software, APIs REST e desenvolvimento de sistemas financeiros.

---

## 🎯 Objetivo do projeto

Construir uma API bancária que permita gerenciar usuários, contas e movimentações financeiras, simulando operações comuns de um sistema bancário.

O desenvolvimento busca aplicar conceitos importantes da engenharia de software, como separação de responsabilidades, validação de dados, tratamento de exceções, autenticação e controle de transações.

## 🚀 Funcionalidades planejadas

- [ ] Cadastro de usuários.
- [ ] Autenticação e autorização.
- [ ] Criação e gerenciamento de contas bancárias.
- [ ] Consulta de saldo.
- [ ] Transferências entre contas.
- [ ] Histórico de transações e extrato bancário.
- [ ] Validação de operações financeiras.
- [ ] Prevenção de saldo negativo.
- [ ] Controle de concorrência em transferências.
- [ ] Tratamento global de exceções.

## 🛠️ Tecnologias utilizadas

| Tecnologia | Finalidade |
|---|---|
| Java 21 | Linguagem de programação |
| Spring Boot 4 | Framework backend |
| Spring Data JPA | Persistência e acesso a dados |
| Spring Security | Segurança e autenticação |
| PostgreSQL 18 | Banco de dados relacional |
| Maven | Gerenciamento de dependências |
| Docker | Containerização do banco de dados |
| Spring Boot Actuator | Monitoramento da aplicação |

## 🏗️ Arquitetura do projeto

O projeto utiliza uma organização modular por funcionalidades (*Package by Feature*), separando responsabilidades para facilitar a manutenção e evolução da aplicação.

```text
fincore-api/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/kevin/fincore/
│   │   │       ├── config/
│   │   │       ├── security/
│   │   │       ├── user/
│   │   │       │   ├── controller/
│   │   │       │   ├── service/
│   │   │       │   ├── repository/
│   │   │       │   ├── entity/
│   │   │       │   └── dto/
│   │   │       ├── account/
│   │   │       ├── transaction/
│   │   │       ├── common/
│   │   │       │   └── exception/
│   │   │       └── FincoreApiApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── docker-compose.yml
├── .env
├── .gitignore
├── pom.xml
└── README.md
```

## ⚙️ Como executar o projeto

### Pré-requisitos

Antes de começar, você precisará ter instalado:

- Java 21 ou superior compatível.
- Docker Desktop.
- Docker Compose.

### 1. Clone o repositório

```bash
git clone URL_DO_REPOSITORIO
```

Entre na pasta do projeto:

```bash
cd fincore-api
```

### 2. Configure as variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
DB_PASSWORD=sua_senha
```

**Importante:** não compartilhe o arquivo `.env` nem envie suas credenciais para o GitHub.

### 3. Inicie o PostgreSQL

Execute:

```bash
docker compose up -d
```

Verifique se o container está em execução:

```bash
docker ps
```

### 4. Configure a conexão com o banco

No arquivo `src/main/resources/application.properties`, configure:

```properties
spring.application.name=fincore-api

spring.datasource.url=jdbc:postgresql://localhost:5432/fincore_db
spring.datasource.username=fincore
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.jpa.open-in-view=false

server.port=8080
```

### 5. Execute a aplicação

No PowerShell, defina a variável de ambiente:

```powershell
$env:DB_PASSWORD="sua_senha"
```

Execute o projeto utilizando o Maven Wrapper:

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

## 📡 Monitoramento

O projeto utiliza Spring Boot Actuator para disponibilizar informações básicas sobre a saúde da aplicação.

Endpoint de verificação:

```text
http://localhost:8080/actuator/health
```

## 🔒 Segurança e integridade financeira

Um dos principais objetivos do FinCore é estudar e aplicar mecanismos que garantam a consistência das operações financeiras.

Entre os pontos que serão trabalhados:

- Proteção de dados e credenciais dos usuários.
- Controle de acesso aos recursos.
- Validação de transferências.
- Atomicidade das operações financeiras.
- Controle de concorrência para evitar inconsistências de saldo.
- Prevenção de transferências duplicadas.

## 📚 Objetivos de aprendizado

O desenvolvimento do FinCore busca aprofundar conhecimentos em:

- Desenvolvimento de APIs REST com Java e Spring Boot.
- Modelagem de bancos de dados relacionais.
- Arquitetura backend e separação de responsabilidades.
- Autenticação e autorização.
- Gerenciamento de transações com Spring.
- Testes automatizados.
- Docker e integração com PostgreSQL.

## ⚠️ Aviso

Este projeto possui finalidade exclusivamente educacional e de portfólio. Não é um sistema bancário real e não deve ser utilizado para operações financeiras verdadeiras.

## 👨‍💻 Autor

**Kevin Leão**

Estudante de Engenharia de Computação com foco em Inteligência Artificial, interessado em desenvolvimento backend, arquitetura de software e construção de APIs.

- GitHub: [@kevinleao627-slkzinn](https://github.com/kevinleao627-slkzinn)
- LinkedIn: [Kevin Leão](https://www.linkedin.com/in/kevin-le%C3%A3o-5033382ab/)

---

*FinCore API — Building a financial backend from the ground up.*
