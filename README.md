# Arquitetura Hexagonal com Microsserviços

Projeto que demonstra a implementação de uma arquitetura hexagonal em microsserviços, com comunicação assíncrona via Kafka, validação de CPF descentralizada e consulta de endereços por CEP.

---

## Tecnologias

1. Java 17+
2. Spring Boot
3. Mockito & JUnit
4. MongoDB
5. Docker & Docker-Compose
6. Kafka

## 📋 Índice

1. [Visão Geral](#visão-geral)
2. [Estrutura do Projeto](#estrutura-do-projeto)
3. [Microsserviços](#microsserviços)
4. [Arquitetura Hexagonal](#arquitetura-hexagonal)
5. [Fluxo de Dados](#fluxo-de-dados)
6. [Pré-requisitos](#pré-requisitos)
7. [Instalação e Setup](#instalação-e-setup)
8. [Executar Aplicações](#executar-aplicações)
9. [Criar Tópicos Kafka](#criar-tópicos-kafka)
10. [API Endpoints](#api-endpoints)
11. [Troubleshooting](#troubleshooting)

---

## 🎯 Visão Geral

Este projeto implementa uma **arquitetura hexagonal** com dois microsserviços que se comunicam via **Kafka**:

- **Hexagonal**: Serviço principal que gerencia clientes, endereços e coordena validações
- **ms-cpf-validation**: Serviço especializado em validação de CPF

### Tecnologias Stack

```
┌─────────────────────────────────────────────┐
│  Java 17, Spring Boot 4.1.1, Maven          │
├─────────────────────────────────────────────┤
│  MongoDB (Persistência de Dados)            │
│  Kafka + Zookeeper (Mensageria)             │
│  (API HTTP - ViaCEP)                        │
│  Docker + Docker Compose (Orquestração)     │
└─────────────────────────────────────────────┘
```

---

## 📁 Estrutura do Projeto

```
ArquiteturaHexagonal/
├── hexagonal/                          # Microsserviço Principal
│   ├── src/main/java/
│   │   └── io/github/Maike2961/hexagonal/
│   │       ├── adapters/               # Camada de Adapters
│   │       │   ├── in/                 # Entrada (Controller, Consumer)
│   │       │   │   ├── controller/     # REST API
│   │       │   │   └── consumer/       # Kafka Consumer
│   │       │   └── out/                # Saída (Repository, HTTP, Kafka Producer)
│   │       │       ├── repository/     # MongoDB
│   │       │       ├── client/         # Client (ViaCEP)
│   │       │       └── SendCpfValidationAdapter
│   │       ├── application/            # Camada de Aplicação
│   │       │   ├── core/
│   │       │   │   ├── domain/         # Entidades de Domínio
│   │       │   │   ├── usecase/        # Lógica de Negócio
│   │       │   │   └── exception/      # Exceções de Negócio
│   │       │   └── ports/              # Interfaces (InputPort/OutputPort)
│   │       └── config/                 # Configurações Spring
│   ├── docker-compose.yaml             # Orquestração de containers
│   ├── Dockerfile
│   └── pom.xml
│
├── ms-cpf-validation-topic/            # Microsserviço de Validação
│   ├── src/main/java/
│   │   └── io/github/Maike2961/validation_cpf/
│   │       ├── adapters/
│   │       │   ├── in/consumer/        # Kafka Consumer
│   │       │   └── out/publisher/      # Kafka Producer
│   │       ├── application/
│   │       │   ├── core/usecase/       # ValidateCpfUseCase
│   │       │   └── ports/
│   │       └── config/
│   ├── Dockerfile
│   └── pom.xml
│
└── README.md                           # Este arquivo
```

---

## 🏗️ Microsserviços

### 1. **Hexagonal** (Serviço Principal)

#### Responsabilidades:
- ✅ Gerenciar cadastro de clientes
- ✅ Validar CPF (delegando ao ms-cpf-validation)
- ✅ Buscar endereço por CEP (via ViaCEP API)
- ✅ Atualizar status de validação dos clientes

#### Porta: **8080**
#### Banco de Dados: **MongoDB** (porta 27017)

#### Use Cases Principais:
- `InsertCustomerUseCase` - Criar novo cliente
- `FindCustomerByIdUseCase` - Buscar cliente por ID
- `UpdateCustomerUseCase` - Atualizar dados do cliente
- `DeleteCustomerByIdUseCase` - Deletar cliente
- `FindAllCustomersUseCase` - Listar clientes com paginação

---

### 2. **ms-cpf-validation** (Serviço de Validação)

#### Responsabilidades:
- ✅ Validar CPF usando algoritmo específico
- ✅ Responder se CPF é válido ou inválido
- ✅ Enviar resultado de volta para hexagonal via Kafka

#### Porta: **8081**
#### Banco de Dados: **Nenhum** (stateless)

#### Use Cases Principais:
- `ValidateCpfUseCase` - Validar CPF e retornar resultado

---

## 🔷 Arquitetura Hexagonal

A arquitetura hexagonal (também chamada de Portas e Adaptadores) segue este padrão:

```
                        ┌─────────────────────────────────┐
                        │  CAMADA DE APRESENTAÇÃO         │
                        │  (HTTP Controllers, CLI)        │
                        └────────────┬────────────────────┘
                                     │
                        ┌────────────▼────────────┐
                        │   INPUT PORTS           │
                        │  (Interfaces de Entrada)│
                        └────────────┬────────────┘
                                     │
        ┌────────────────────────────┼───────────────────────────┐
        │                            │                           │
    ┌───▼────────┐         ┌─────────▼────────┐         ┌────────▼──┐
    │ Controllers│         │  CORE DOMAIN     │         │ Consumers │
    │  (HTTP)    │         │  (UseCase Logic) │         │ (Kafka)   │
    │            │         │                  │         │           │
    └────────────┘         └─────────┬────────┘         └───────────┘
                                     │
                        ┌────────────▼────────────┐
                        │   OUTPUT PORTS          │
                        │  (Interfaces de Saída)  │
                        └────────────┬────────────┘
                                     │
        ┌────────────────────────────┼───────────────────────────┐
        │                            │                           │
    ┌───▼────────┐         ┌─────────▼────────┐         ┌────────▼──┐
    │ Repository │         │  HTTP Clients    │         │ Producers │
    │ (MongoDB)  │         │                  │         │ (Kafka)   │
    │            │         │                  │         │           │
    └────────────┘         └──────────────────┘         └───────────┘

CAMADAS:
- INPUT: Como dados chegam ao sistema (HTTP, Kafka, CLI)
- CORE: Lógica de negócio pura (sem dependências)
- OUTPUT: Como dados saem do sistema (BD, APIs, Mensagens)
```

### Vantagens:
✅ **Independência de Frameworks** - Core domain não depende de Spring  
✅ **Testabilidade** - Fácil mockar dependências  
✅ **Manutenibilidade** - Mudanças em uma camada não afetam outras  
✅ **Escalabilidade** - Adicionar novos adapters sem refatorar domain  

---

## 🔄 Fluxo de Dados

### Fluxo Completo de Criação e Validação de Cliente

```
1. ENTRADA (HTTP POST)
   ┌──────────────────────────────────────┐
   │ Usuário: POST /api/customers         │
   │ Body: { name, cpf, zipCode }         │
   └────────────┬─────────────────────────┘
                │
2. HEXAGONAL (Controller → UseCase)
   ┌────────────▼─────────────────────────┐
   │ CustomerController                    │
   │ → InsertCustomerUseCase               │
   │   - Validar dados                     │
   │   - Inserir no MongoDB                │
   │   - Gerar ID (MongoDB _id)            │
   └────────────┬─────────────────────────┘
                │
3. ENVIO VIA KAFKA
   ┌────────────▼─────────────────────────┐
   │ SendCpfValidationAdapter              │
   │ → Topic: tp-cpf-validation            │
   │ Message: {id, name, cpf, zipCode}    │
   └────────────┬─────────────────────────┘
                │
4. MS-CPF-VALIDATION (Valida CPF)
   ┌────────────▼─────────────────────────┐
   │ ReceiveValidationCpfConsumer          │
   │ → ValidateCpfUseCase                  │
   │   - Executa algoritmo de validação    │
   │   - Determina se é válido/inválido    │
   └────────────┬─────────────────────────┘
                │
5. RESPOSTA VIA KAFKA
   ┌────────────▼─────────────────────────┐
   │ SendValidatedCpfAdapter               │
   │ → Topic: tp-cpf-validated             │
   │ Message: {id, name, cpf, isValid}    │
   └────────────┬─────────────────────────┘
                │
6. HEXAGONAL (Recebe Resposta)
   ┌────────────▼─────────────────────────┐
   │ ReceiveValidatedCpfConsumer           │
   │ → UpdateCustomerUseCase               │
   │   - Buscar endereço (ViaCEP)          │
   │   - Atualizar cliente com resultado   │
   │   - Salvar no MongoDB                 │
   └────────────┬─────────────────────────┘
                │
7. RESPOSTA PARA USUÁRIO
   ┌────────────▼─────────────────────────┐
   │ Customer criado com:                  │
   │ - ID gerado                           │
   │ - CPF validado (sim/não)              │
   │ - Endereço preenchido (ViaCEP)        │
   └──────────────────────────────────────┘
```

### Topics Kafka

| Topic | Origem | Destino | Mensagem | Exemplo |
|-------|--------|---------|----------|---------|
| `tp-cpf-validation` | hexagonal | ms-cpf | Dados para validar | `{id, name, cpf, zipCode}` |
| `tp-cpf-validated` | ms-cpf | hexagonal | Resultado validação | `{id, cpf, isValidCpf}` |

---

## 📋 Pré-requisitos

Certifique-se de ter instalado:

- **Java 17+** - [Download](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Maven 3.8+** - [Download](https://maven.apache.org/download.cgi)
- **Docker 20.10+** - [Download](https://www.docker.com/)
- **Docker Compose 2.0+** - Incluído com Docker Desktop
- **Git** - [Download](https://git-scm.com/)

### Verificar Instalação

```bash
java -version          # Deve ser 17 ou superior
mvn -version          # Deve ser 3.8 ou superior
docker --version      # Deve ser 20.10 ou superior
docker-compose --version  # Deve ser 2.0 ou superior
```

---

## 🚀 Instalação e Setup

### 1. Clonar Repositório

```bash
git clone <seu-repositorio>
cd ArquiteturaHexagonal
```

### 2. Estrutura de Diretórios

```bash
# Ambos os microsserviços estão na raiz
ls -la
# hexagonal/
# ms-cpf-validation-topic/
# docker-compose.yaml (na pasta hexagonal/)
```

### 3. Compilar Ambos Microsserviços

```bash
# Compilar hexagonal
cd hexagonal
mvn clean install -DskipTests

# Compilar ms-cpf-validation
cd ../ms-cpf-validation-topic
mvn clean install -DskipTests

# Voltar para raiz
cd ..
```

### 4. Verificar Docker

```bash
docker ps          # Listar containers rodando
docker volume ls   # Listar volumes
```

---

## 🏃 Executar Aplicações

### Opção 1: Docker Compose (Recomendado)

Este método inicia todos os serviços automaticamente: Kafka, Zookeeper, MongoDB, Mongo Express, e as aplicações.

```bash
# Ir para pasta do hexagonal (onde está docker-compose.yaml)
cd hexagonal

# Iniciar todos os containers
docker compose up -d

# Verificar status
docker compose ps

# Ver logs (opcional)
docker compose logs -f

# Parar tudo
docker compose down

# Limpar tudo (volumes também)
docker compose down -v
```

### Opção 2: Executar Localmente com Docker Services

Se preferir rodar os microsserviços localmente enquanto os serviços rodamem em Docker:

```bash
# Terminal 1: Iniciar apenas os serviços (sem as aplicações)
cd hexagonal
docker compose up kafka zookeeper mongo mongo-express -d

# Terminal 2: Rodar hexagonal localmente
cd hexagonal
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=docker"

# Terminal 3: Rodar ms-cpf localmente
cd ms-cpf-validation-topic
mvn spring-boot:run
```

### Opção 3: Modo Interativo (Ver Logs)

```bash
# Iniciar sem detach (vê logs em tempo real)
cd hexagonal
docker compose up

# Para parar: Ctrl+C
# Para detach: Ctrl+P + Ctrl+Q
```

---

## 🌐 Acessar Serviços

Após iniciar com Docker Compose:

| Serviço | URL | Descrição |
|---------|-----|-----------|
| **Hexagonal API** | `http://localhost:8080` | REST API principal |
| **Mongo Express** | `http://localhost:8081` | Interface web MongoDB |
| **Kafka** | `localhost:9092` (interno) | Broker Kafka |
| **Zookeeper** | `localhost:2181` | Coordenação Kafka |

### Testar Hexagonal API

```bash
# Criar cliente
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Orlando Silva",
    "cpf": "506.458.458-45",
    "zipCode": "08141180"
  }'

# Buscar cliente por ID
curl http://localhost:8080/api/customers/{id}

# Listar todos (paginado)
curl "http://localhost:8080/api/customers?page=0&size=10"
```

---

## 🎯 Criar Tópicos Kafka

Os tópicos são criados **automaticamente** quando as aplicações iniciam, mas pode criar manualmente se necessário:

### Opção 1: CLI do Kafka (Dentro do Container)

```bash
# Entrar no container do Kafka
docker exec -it hexagonal-kafka-1 bash

# Criar tópico de validação
kafka-topics --create \
  --topic tp-cpf-validation \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1

# Criar tópico de resposta
kafka-topics --create \
  --topic tp-cpf-validated \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1

# Listar todos os tópicos
kafka-topics --list --bootstrap-server localhost:9092

# Ver detalhes de um tópico
kafka-topics --describe --topic tp-cpf-validation --bootstrap-server localhost:9092

# Deletar tópico (se necessário)
kafka-topics --delete --topic tp-cpf-validation --bootstrap-server localhost:9092

# Sair do container
exit
```

### Opção 2: Docker exec (One-liner)

```bash
# Criar ambos os tópicos
docker exec hexagonal-kafka-1 kafka-topics --create --topic tp-cpf-validation --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

docker exec hexagonal-kafka-1 kafka-topics --create --topic tp-cpf-validated --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

# Listar tópicos
docker exec hexagonal-kafka-1 kafka-topics --list --bootstrap-server localhost:9092
```

### Opção 3: Script Automatizado

```bash
#!/bin/bash
# save as: create-topics.sh

KAFKA_CONTAINER="hexagonal-kafka-1"
BOOTSTRAP_SERVER="localhost:9092"

echo "Criando tópicos Kafka..."

docker exec $KAFKA_CONTAINER kafka-topics \
  --create \
  --if-not-exists \
  --topic tp-cpf-validation \
  --bootstrap-server $BOOTSTRAP_SERVER \
  --partitions 1 \
  --replication-factor 1

docker exec $KAFKA_CONTAINER kafka-topics \
  --create \
  --if-not-exists \
  --topic tp-cpf-validated \
  --bootstrap-server $BOOTSTRAP_SERVER \
  --partitions 1 \
  --replication-factor 1

echo "✅ Tópicos criados com sucesso!"

# Listar tópicos
docker exec $KAFKA_CONTAINER kafka-topics --list --bootstrap-server $BOOTSTRAP_SERVER
```

Usar o script:
```bash
chmod +x create-topics.sh
./create-topics.sh
```

## Subir Imagem Docker Com Testes Unitários Testados:

- Comando para permitir o script:
```bash
chmod +x build-tag-push.sh
```

- Comando para executar os testes unitários e subir a imagem para o Docker Hub:
```bash
./build-tag-push v.1.1.1
```

---

## 📡 Monitorar Mensagens Kafka

### Ver Mensagens em Tempo Real

```bash
# Consumir mensagens de tp-cpf-validation (desde o início)
docker exec hexagonal-kafka-1 kafka-console-consumer \
  --topic tp-cpf-validation \
  --from-beginning \
  --bootstrap-server localhost:9092

# Consumir mensagens de tp-cpf-validated
docker exec hexagonal-kafka-1 kafka-console-consumer \
  --topic tp-cpf-validated \
  --from-beginning \
  --bootstrap-server localhost:9092

# Ver apenas últimas N mensagens (últimas 10)
docker exec hexagonal-kafka-1 kafka-console-consumer \
  --topic tp-cpf-validation \
  --bootstrap-server localhost:9092 \
  --max-messages 10
```

---

## 📚 API Endpoints

### Clientes

#### Criar Cliente
```
POST /api/customers
Content-Type: application/json

{
  "name": "Orlando Silva",
  "cpf": "506.458.458-45",
  "zipCode": "08141180"
}

Response (201 Created):
{
  "id": "6adeb41e98e15217187e2ac9",
  "name": "Orlando Silva",
  "cpf": "506.458.458-45",
  "zipCode": "08141180",
  "street": "Rua Teste",
  "city": "São Paulo",
  "state": "SP"
}
```

#### Buscar Cliente por ID
```
GET /api/customers/{id}

Response (200 OK):
{
  "id": "6adeb41e98e15217187e2ac9",
  "name": "Orlando Silva",
  "cpf": "506.458.458-45",
  "zipCode": "08141180",
  ...
}
```

#### Listar Todos (Paginado)
```
GET /api/customers?page=0&size=10&sort=name,asc

Response (200 OK):
{
  "content": [...],
  "pageable": {...},
  "totalPages": 1,
  "totalElements": 2,
  "size": 10,
  "number": 0,
  "first": true,
  "last": true,
  "empty": false
}
```

#### Atualizar Cliente
```
PUT /api/customers/{id}
Content-Type: application/json

{
  "name": "Orlando Silva Atualizado",
  "cpf": "506.458.458-45",
  "zipCode": "01234567"
}

Response (200 OK):
{ ... }
```

#### Deletar Cliente
```
DELETE /api/customers/{id}

Response (204 No Content)
```

---

## 📊 Monitorar Dados

### MongoDB (Mongo Express)

1. Abrir: `http://localhost:8081`
2. Clicar em `hexagonal` database
3. Clicar em `customers` collection
4. Ver todos os clientes cadastrados

### Verificar Logs

```bash
# Logs do hexagonal
docker compose logs hexagonal

# Logs do ms-cpf-validation
docker compose logs ms-cpf-validation

# Logs do Kafka
docker compose logs kafka

# Ver logs em tempo real (últimas 100 linhas)
docker compose logs -f --tail=100 hexagonal
```

---

## 🧪 Testar Fluxo Completo

### 1. Criar Cliente

```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "cpf": "123.456.789-09",
    "zipCode": "08141180"
  }'

# Guarde o ID retornado
```

### 2. Verificar Mensagens Kafka

```bash
# Terminal 1: Monitorar tp-cpf-validation
docker exec hexagonal-kafka-1 kafka-console-consumer \
  --topic tp-cpf-validation \
  --from-beginning \
  --bootstrap-server localhost:9092

# Terminal 2: Monitorar tp-cpf-validated
docker exec hexagonal-kafka-1 kafka-console-consumer \
  --topic tp-cpf-validated \
  --from-beginning \
  --bootstrap-server localhost:9092
```

### 3. Buscar Cliente Atualizado

```bash
# Após alguns segundos
curl http://localhost:8080/api/customers/{id}

# Deve ver: zipCode, city, state (preenchidos pelo ViaCEP)
```

---

## 🐛 Troubleshooting

### ❌ Erro: "Connection refused" ao conectar Kafka

**Solução:**
```bash
# Verificar se containers estão rodando
docker compose ps

# Se não estiverem, iniciar novamente
docker compose up -d

# Se persistir, limpar e recomeçar
docker compose down -v
docker compose up -d
```

### ❌ Erro: "Topics are not created"

**Solução:**
```bash
# Criar tópicos manualmente
docker exec hexagonal-kafka-1 kafka-topics --create --if-not-exists \
  --topic tp-cpf-validation --bootstrap-server localhost:9092 \
  --partitions 1 --replication-factor 1

docker exec hexagonal-kafka-1 kafka-topics --create --if-not-exists \
  --topic tp-cpf-validated --bootstrap-server localhost:9092 \
  --partitions 1 --replication-factor 1
```

### ❌ Erro: "MongoDB connection refused"

**Solução:**
```bash
# Verificar se MongoDB está rodando
docker compose ps mongo

# Se não está, iniciar
docker compose up mongo -d

# Dar tempo para inicializar (10 segundos)
sleep 10

# Tente novamente
```

### ❌ Erro: "Deserialization error on CustomerMessage"

**Solução:**
```bash
# Deletar tópicos e recriar
docker exec hexagonal-kafka-1 kafka-topics --delete --topic tp-cpf-validation --bootstrap-server localhost:9092

docker exec hexagonal-kafka-1 kafka-topics --delete --topic tp-cpf-validated --bootstrap-server localhost:9092

# Recriar tópicos
docker exec hexagonal-kafka-1 kafka-topics --create --if-not-exists \
  --topic tp-cpf-validation --bootstrap-server localhost:9092 \
  --partitions 1 --replication-factor 1

docker exec hexagonal-kafka-1 kafka-topics --create --if-not-exists \
  --topic tp-cpf-validated --bootstrap-server localhost:9092 \
  --partitions 1 --replication-factor 1

# Reiniciar aplicações
docker compose restart hexagonal ms-cpf-validation
```

### ❌ Erro: "Port already in use"

**Solução:**
```bash
# Liberar porta (exemplo: 8080)
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>

# Ou mudar porta no docker-compose.yaml
```

### ❌ Erro: "OutOfMemory"

**Solução:**
```bash
# Limpar volumes e cache
docker compose down -v
docker system prune -a

# Aumentar memória do Docker se necessário
# Settings > Resources > Memory > 4GB+
```

---

## 📖 Documentação Adicional

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [Apache Kafka](https://kafka.apache.org/documentation/)
- [Docker Documentation](https://docs.docker.com/)
- [Arquitetura Hexagonal](https://herbertograca.com/2017/09/28/the-software-architecture-chronicles/)

---

## 👥 Contribuidores

- Maike (Desenvolvimento)

---

## 📝 Licença

Este projeto é de propósito educacional.

---

## 📞 Suporte

Para dúvidas ou problemas:
1. Verifique a seção de Troubleshooting
2. Consulte os logs: `docker compose logs -f`
3. Teste endpoints com curl ou Postman

---

**Última atualização**: Outubro 2026
