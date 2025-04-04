# Sistema de Login - Teste para Estágio

## 📋 Descrição do Projeto
Este projeto é uma expansão do teste prático para vaga de estágio, transformando um simples formulário em um sistema completo de autenticação e login de usuários. O sistema foi construído utilizando:

- **Backend**: Java 21 + Spring Boot + MongoDB
- **Frontend**: Next.js
- **Infraestrutura**: Docker e Docker Compose

## 🚀 Como Executar o Projeto

### Pré-requisitos
- [Docker](https://www.docker.com/get-started/) (v20+)
> Caso esteja no windows não é necessário baixar o compose. pois ele ja vem com a instalação do Docker Desktop.
- [Docker Compose](https://docs.docker.com/compose/install/) (v2+)
- [GIT](https://git-scm.com/downloads)
- Portas disponíveis:
  - **3000** - Frontend Next.js
  - **8081** - Backend Spring Boot
  - **27018** - MongoDB

> ⚠️ **Importante**: Verifique se estas portas não estão sendo usadas por outros serviços em sua máquina. Caso alguma porta esteja em uso, é necessário ajustar o arquivo `docker-compose.yml` antes da execução.

### Passos para Execução

1. **Clone o repositório**
   ```bash
   git clone https://github.com/Gabriel-M-Coutinho/processo-seletivo.git
   cd processo-seletivo
   ```

2. **Execute com Docker Compose Windows**
   ```bash
    docker-compose up --build -d
   ```
  **Execute com Docker Compose Linux**
   ```bash
    docker compose up --build -d
   ```
   
   Este comando irá:
  - Iniciar um container com MongoDB
  - Construir e iniciar o backend Spring Boot
  - Construir e iniciar o frontend Next.js

3. **Acesse a aplicação**
  - Frontend: [http://localhost:3000](http://localhost:3000)
  - API Backend: [http://localhost:8081](http://localhost:8081)
  - MongoDB (para inspeção): disponível na porta 27018

## 🧰 Arquitetura do Sistema

### Backend (Java + Spring Boot)
- **Porta**: 8081
- **Tecnologias**: Java 21, Spring Boot, Spring Security, JWT
- **Banco de Dados**: MongoDB
- **Padrão Arquitetural**: MSC (Model-Service-Controller)
  - **Model**: Entidades e representação dos dados
  - **Service**: Lógica de negócio e processamento
  - **Controller**: Endpoints da API e comunicação com o cliente
- **Endpoints Principais**:
  - `POST /user/signin` - Cadastro de novos usuários
  - `POST /user/login` - Autenticação e geração de token JWT

### Frontend (Next.js)
- **Porta**: 3000
- **Tecnologias**: Next.js, React, CSS moderno
- **Funcionalidades**:
  - Formulário de cadastro
  - Tela de login


### Banco de Dados (MongoDB)
- **Porta**: 27018 (mapeada externamente) / 27017 (interna ao Docker)
- **Coleções**:
  - Users - Armazena informações dos usuários cadastrados

## 🔐 Funcionalidades Implementadas

- Cadastro de usuários com validação de dados
- Autenticação segura com tokens JWT
- Criptografia de senhas usando BCrypt
- Proteção de rotas utilizando Spring Security
- Interface responsiva com Next.js
- Comunicação segura entre frontend e backend
- Containerização completa do ambiente para facilitar execução

## 🔍 Notas de Implementação

- O projeto segue o padrão arquitetural MSC (Model-Service-Controller) no backend:
  - **Model**: Define a estrutura dos dados e interação com o MongoDB
  - **Service**: Implementa a lógica de negócio, validações e processamento
  - **Controller**: Gerencia as requisições HTTP e respostas da API

- O projeto expande a proposta original de um simples formulário para uma aplicação mais completa
- A senha do usuário é armazenada de forma segura utilizando algoritmos de hash
- O token JWT contém informações do usuário e expira após um determinado período
- O frontend faz comunicação com o backend através de requisições HTTP
- Todas as configurações de conexão com o banco de dados são gerenciadas via variáveis de ambiente no Docker Compose

## 💡 Melhorias Futuras

- Implementação de recuperação de senha
- Adição de níveis de acesso (usuário comum/administrador)
- Adição de testes automatizados
- Implementação de logs de auditoria
- Melhorias na UI/UX do frontend

## ⚠️ Solução de Problemas

- **Erro de conexão com o MongoDB**: Verifique se o container do MongoDB está rodando corretamente com `docker ps`
- **Problema nas portas**: Se algum serviço não iniciar, verifique se as portas necessárias (3000, 8081, 27018) estão disponíveis
- **Reiniciar os containers**: Em caso de problemas, tente `docker-compose down` seguido de `docker-compose up`