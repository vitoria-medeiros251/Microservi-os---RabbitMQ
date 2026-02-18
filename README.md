# Microserviços com RabbitMQ

Sistema de microserviços desenvolvido em Java com Spring Boot que utiliza RabbitMQ para comunicação assíncrona entre serviços.

## 📋 Sobre o Projeto

Este projeto implementa uma arquitetura de microserviços composta por dois serviços independentes que se comunicam através de mensageria usando RabbitMQ:

- **User Service**: Responsável pelo cadastro de usuários
- **Email Service**: Responsável pelo envio de emails de boas-vindas

Quando um usuário é cadastrado, o User Service publica uma mensagem na fila do RabbitMQ, e o Email Service consome essa mensagem para enviar um email de confirmação automaticamente.

## 🏗️ Arquitetura

```
┌─────────────────┐         ┌──────────────┐         ┌─────────────────┐
│  User Service   │────────▶│   RabbitMQ   │────────▶│  Email Service  │
│   (Producer)    │         │    (Broker)  │         │   (Consumer)    │
│   Port: 8081    │         │              │         │   Port: 8083    │
└─────────────────┘         └──────────────┘         └─────────────────┘
        │                                                      │
        ▼                                                      ▼
┌─────────────────┐                                  ┌─────────────────┐
│  PostgreSQL     │                                  │  PostgreSQL     │
│   ms-user       │                                  │   ms-email      │
└─────────────────┘                                  └─────────────────┘
```

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 21**: Linguagem de programação
- **Spring Boot 2.7.18**: Framework principal
- **Spring AMQP**: Integração com RabbitMQ
- **Spring Data JPA**: Persistência de dados
- **Spring Validation**: Validação de dados
- **Spring Mail**: Envio de emails

### Mensageria
- **RabbitMQ (CloudAMQP)**: Message broker para comunicação assíncrona entre microserviços
- **Jackson**: Serialização/deserialização de mensagens JSON

### Banco de Dados
- **PostgreSQL**: Banco de dados relacional para ambos os serviços

### Build
- **Maven**: Gerenciamento de dependências e build

### Outras Bibliotecas
- **Lombok**: Redução de código boilerplate (User Service)

## 📦 Estrutura dos Microserviços

### User Service
```
user/
├── configs/          # Configurações do RabbitMQ
├── controllers/      # Endpoints REST
├── dto/              # Data Transfer Objects
├── models/           # Entidades JPA
├── producers/        # Publicadores de mensagens
├── repositories/     # Repositórios JPA
└── services/         # Lógica de negócio
```

### Email Service
```
email/
├── configs/          # Configurações do RabbitMQ
├── consumers/        # Consumidores de mensagens
├── dtos/             # Data Transfer Objects
├── enums/            # Enumerações (StatusEmail)
├── models/           # Entidades JPA
├── repositories/     # Repositórios JPA
└── services/         # Lógica de envio de email
```

## ⚙️ Configuração

### Pré-requisitos
- Java 21
- Maven 3.x
- PostgreSQL
- Conta CloudAMQP (ou RabbitMQ local)
- Conta Gmail (para envio de emails)

### Configuração do Banco de Dados

Crie dois bancos de dados no PostgreSQL:
```sql
CREATE DATABASE "ms-user";
CREATE DATABASE "ms-email";
```

### Configuração do RabbitMQ

O projeto está configurado para usar CloudAMQP (RabbitMQ na nuvem). As configurações estão em `application.properties` de cada serviço.

**Fila utilizada**: `default.email`

### Variáveis de Ambiente

Atualize os arquivos `application.properties` com suas credenciais:

**User Service** (`user/user/src/main/resources/application.properties`):
- Credenciais do PostgreSQL
- Credenciais do RabbitMQ/CloudAMQP

**Email Service** (`email/email/src/main/resources/application.properties`):
- Credenciais do PostgreSQL
- Credenciais do RabbitMQ/CloudAMQP
- Credenciais do Gmail (SMTP)

## 🔧 Como Executar

### 1. Compilar os projetos
```bash
# User Service
cd user/user
mvn clean install

# Email Service
cd email/email
mvn clean install
```

### 2. Executar os serviços

**User Service**:
```bash
cd user/user
mvn spring-boot:run
```

**Email Service**:
```bash
cd email/email
mvn spring-boot:run
```

### 3. Testar o sistema

Cadastre um usuário via POST:
```bash
POST http://localhost:8081/users
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@example.com"
}
```

O fluxo será:
1. User Service salva o usuário no banco
2. User Service publica mensagem no RabbitMQ
3. Email Service consome a mensagem
4. Email Service envia email de boas-vindas
5. Email Service salva registro do email enviado

## 📨 Fluxo de Mensageria

### Producer (User Service)
```java
// Publica mensagem na fila após cadastro do usuário
rabbitTemplate.convertAndSend("", queueName, emailDto);
```

### Consumer (Email Service)
```java
// Escuta a fila e processa mensagens
@RabbitListener(queues = "${broker.queue.email.name}")
public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto)
```

### Formato da Mensagem
```json
{
  "userId": "uuid",
  "emailTo": "usuario@example.com",
  "subject": "cadastro realizado com sucesso",
  "text": "Nome, seja bem vindo(a)! ..."
}
```

## 🔐 Segurança

- Conexão SSL/TLS habilitada com RabbitMQ
- Autenticação SMTP para envio de emails
- Senhas devem ser armazenadas em variáveis de ambiente (não commitar credenciais)

## 📊 Portas dos Serviços

| Serviço       | Porta |
|---------------|-------|
| User Service  | 8081  |
| Email Service | 8083  |

## 🎯 Benefícios da Arquitetura

- **Desacoplamento**: Serviços independentes e autônomos
- **Escalabilidade**: Cada serviço pode escalar independentemente
- **Resiliência**: Falhas em um serviço não afetam diretamente o outro
- **Assincronicidade**: Processamento não-bloqueante
- **Manutenibilidade**: Código organizado e separado por responsabilidade

## 📝 Melhorias Futuras

- Implementar Dead Letter Queue (DLQ) para mensagens com falha
- Adicionar retry policy para envio de emails
- Implementar circuit breaker
- Adicionar monitoramento e observabilidade (Prometheus, Grafana)
- Containerizar com Docker
- Implementar testes unitários e de integração
- Adicionar autenticação e autorização (Spring Security)

## 👥 Autor

Vitória Letícia da Silva
