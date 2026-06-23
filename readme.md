# Hexagonal Architecture Introduction Project

Projeto de introdução à **Arquitetura Hexagonal** (Ports & Adapters) com Spring Boot, implementando um CRUD completo de clientes com integração a serviço externo de endereços via **OpenFeign** e validação assíncrona de CPF via **Apache Kafka**.

O objetivo é demonstrar como isolar completamente o núcleo da aplicação (domínio e regras de negócio) de frameworks, bancos de dados e serviços externos — mantendo o core em **Java puro**.

---

## Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem |
| Spring Boot | 4.0.0 | Framework principal |
| Spring Web MVC | — | API REST |
| Spring Data MongoDB | — | Persistência |
| Spring Kafka | — | Mensageria assíncrona (produção e consumo) |
| Spring Cloud OpenFeign | 2025.1.0 | Client HTTP declarativo |
| Spring Validation | — | Validação de entradas (`@NotBlank`) |
| MapStruct | 1.6.3 | Mapeamento entre objetos (domain ↔ entity/DTO) |
| Lombok | — | Redução de boilerplate |
| ArchUnit | 1.4.1 | Testes automatizados de regras arquiteturais |
| Maven | — | Build e gestão de dependências |

---

## O que é Arquitetura Hexagonal?

A **Arquitetura Hexagonal**, também chamada de **Ports & Adapters**, foi proposta por Alistair Cockburn. A ideia central é que a **lógica de negócio** (o "hexágono") seja completamente independente de tecnologias externas.

### Conceitos-chave

- **Domínio (Domain):** entidades e objetos de valor que representam o negócio. São classes Java puras, sem nenhuma anotação de framework.
- **Portas de Entrada (Input Ports):** interfaces que definem **o que** a aplicação faz (os casos de uso expostos). Os adapters de entrada chamam essas interfaces.
- **Portas de Saída (Output Ports):** interfaces que definem **o que** a aplicação precisa do mundo externo (banco de dados, APIs, mensageria). Os use cases dependem dessas interfaces, nunca de implementações concretas.
- **Casos de Uso (Use Cases):** implementam as portas de entrada. Contêm a regra de negócio pura — orquestram chamadas às portas de saída. São classes Java puras, sem `@Service`, sem `@Component`.
- **Adapters de Entrada (Driving Adapters):** recebem requisições do mundo externo (REST Controller, Kafka Consumer) e delegam para as Input Ports.
- **Adapters de Saída (Driven Adapters):** implementam as Output Ports usando tecnologias concretas (MongoDB, Feign, KafkaTemplate).
- **Configuração (Config):** classes `@Configuration` que instanciam os Use Cases como beans Spring, fazendo a "cola" entre portas e adapters via injeção de dependência — sem poluir o domínio com anotações.

### Diagrama da Arquitetura

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              ADAPTERS                                      │
│                                                                             │
│  ┌────────────────────┐                      ┌────────────────────────────┐ │
│  │    IN (Driving)     │                      │      OUT (Driven)          │ │
│  │                     │                      │                            │ │
│  │  CustomerController │                      │  InsertCustomerAdapter     │ │
│  │  (REST API)         │                      │  FindCustomerByIdAdapter   │ │
│  │                     │                      │  UpdateCustomerAdapter     │ │
│  │  ReceiveValidated   │                      │  DeleteCustomerAdapter     │ │
│  │  CpfConsumer        │                      │  FindAddressByZipCodeAdapt │ │
│  │  (Kafka Consumer)   │                      │  SendCpfValidationAdapter  │ │
│  └─────────┬──────────┘                      └──────────┬─────────────────┘ │
│             │                                           │                   │
│  ┌──────────▼───────────────────────────────────────────▼─────────────────┐ │
│  │                        APPLICATION CORE                                │ │
│  │                                                                        │ │
│  │  ┌──────────────────────────────────────────────────────────────────┐  │ │
│  │  │                    INPUT PORTS (interfaces)                      │  │ │
│  │  │  InsertCustomerInputPort    FindCustomerByIdInputPort           │  │ │
│  │  │  UpdateCustomerInputPort    DeleteCustomerInputPort             │  │ │
│  │  └──────────────────────────────┬───────────────────────────────────┘  │ │
│  │                                 │                                      │ │
│  │  ┌──────────────────────────────▼───────────────────────────────────┐  │ │
│  │  │                      USE CASES (Java puro)                       │  │ │
│  │  │  InsertCustomerUseCase     FindCustomerByIdUseCase              │  │ │
│  │  │  UpdateCustomerUseCase     DeleteCustomerUseCase                │  │ │
│  │  └──────────────────────────────┬───────────────────────────────────┘  │ │
│  │                                 │                                      │ │
│  │  ┌──────────────────────────────▼───────────────────────────────────┐  │ │
│  │  │                   OUTPUT PORTS (interfaces)                      │  │ │
│  │  │  InsertCustomerOutputPort       FindCustomerByIdOutputPort      │  │ │
│  │  │  UpdateCustomerOutputPort       DeleteCustomerOutputPort        │  │ │
│  │  │  FindAddressByZipCodeOutputPort SendCpfForValidationOutputPort  │  │ │
│  │  └──────────────────────────────────────────────────────────────────┘  │ │
│  │                                                                        │ │
│  │  ┌──────────────────────────────────────────────────────────────────┐  │ │
│  │  │                        DOMAIN (Java puro)                        │  │ │
│  │  │                    Customer   |   Address                        │  │ │
│  │  └──────────────────────────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                                                             │
│  ┌─────────────────────────────────────────────────────────────────────────┐│
│  │                        CONFIG (@Configuration)                         ││
│  │  InsertCustomerConfig  FindByIdCustomerConfig  UpdateCustomerConfig    ││
│  │  DeleteCustomerConfig                                                  ││
│  │  → Instancia os UseCases como beans Spring (a "cola" entre as camadas) ││
│  └─────────────────────────────────────────────────────────────────────────┘│
└─────────────────────────────────────────────────────────────────────────────┘
```

### Regra de ouro

> **O núcleo (`application/`) não importa NADA do Spring, MongoDB, Kafka ou qualquer framework. Ele é 100% Java puro.**

Isso garante que:
- A lógica de negócio pode ser testada unitariamente sem dependências externas.
- Trocar MongoDB por PostgreSQL, ou REST por gRPC, requer apenas criar novos adapters — sem tocar no domínio.
- O código de negócio é portável e imune a mudanças de infraestrutura.

---

## Estrutura de Pacotes

```
src/main/java/com/joao/hexagonal_architecture_introduction_project/
│
├── adapters/
│   ├── in/                                        ← ADAPTERS DE ENTRADA (Driving)
│   │   ├── controller/
│   │   │   ├── CustomerController.java            # REST Controller — CRUD completo
│   │   │   ├── request/
│   │   │   │   └── CustomerRequest.java           # DTO de entrada (record com @NotBlank)
│   │   │   ├── response/
│   │   │   │   ├── CustomerResponse.java          # DTO de resposta
│   │   │   │   └── AddressResponse.java           # DTO de endereço na resposta
│   │   │   └── mapper/
│   │   │       └── CustomerMapper.java            # MapStruct: Request ↔ Domain ↔ Response
│   │   └── consumer/
│   │       ├── ReceiveValidatedCpfConsumer.java    # Kafka Consumer — recebe CPF validado
│   │       ├── message/
│   │       │   └── CustomerMessage.java           # DTO da mensagem Kafka
│   │       └── mapper/
│   │           └── CustomerMessageMapper.java     # MapStruct: Message → Customer
│   │
│   └── out/                                       ← ADAPTERS DE SAÍDA (Driven)
│       ├── InsertCustomerAdapter.java             # Persiste no MongoDB
│       ├── FindCustomerByIdAdapter.java           # Busca no MongoDB
│       ├── UpdateCustomerAdapter.java             # Atualiza no MongoDB
│       ├── DeleteCustomerAdapter.java             # Remove do MongoDB
│       ├── FindAddressByZipCodeAdapter.java       # Busca endereço via Feign Client
│       ├── SendCpfValidationAdapter.java          # Envia CPF para validação via Kafka
│       ├── client/
│       │   ├── FindAddressByZipCodeClient.java    # Feign Client (HTTP)
│       │   ├── response/
│       │   │   └── AddressResponse.java           # DTO do serviço externo
│       │   └── mapper/
│       │       └── AddressResponseMapper.java     # MapStruct: Response → Address
│       └── repository/
│           ├── CustomerRepository.java            # MongoRepository
│           ├── entity/
│           │   ├── CustomerEntity.java            # @Document MongoDB
│           │   └── AddressEntity.java             # Embedded no CustomerEntity
│           └── mapper/
│               └── CustomerEntityMapper.java      # MapStruct: Customer ↔ CustomerEntity
│
├── application/                                   ← NÚCLEO DA APLICAÇÃO (Java puro)
│   ├── core/
│   │   ├── domain/
│   │   │   ├── Customer.java                      # Entidade de domínio
│   │   │   └── Address.java                       # Value object
│   │   └── useCase/
│   │       ├── InsertCustomerUseCase.java         # Inserir cliente
│   │       ├── FindCustomerByIdUseCase.java       # Buscar cliente por ID
│   │       ├── UpdateCustomerUseCase.java         # Atualizar cliente
│   │       ├── DeleteCustomerUseCase.java         # Deletar cliente
│   │       └── exception/
│   │           └── NotFoundException.java         # Exceção de domínio
│   └── ports/
│       ├── in/                                    # Portas de Entrada (interfaces)
│       │   ├── InsertCustomerInputPort.java
│       │   ├── FindCustomerByIdInputPort.java
│       │   ├── UpdateCustomerInputPort.java
│       │   └── DeleteCustomerInputPort.java
│       └── out/                                   # Portas de Saída (interfaces)
│           ├── InsertCustomerOutputPort.java
│           ├── FindCustomerByIdOutputPort.java
│           ├── UpdateCustomerOutputPort.java
│           ├── DeleteCustomerOutputPort.java
│           ├── FindAddressByZipCodeOutputPort.java
│           └── SendCpfForValidationOutputPort.java
│
├── config/                                        ← CONFIGURAÇÃO (cola entre camadas)
│   ├── InsertCustomerConfig.java
│   ├── FindByIdCustomerConfig.java
│   ├── UpdateCustomerConfig.java
│   └── DeleteCustomerConfig.java
│
└── HexagonalArchitectureIntroductionProjectApplication.java  ← @SpringBootApplication

src/test/java/.../architecture/
├── LayeredArchitectureTest.java                   # Valida regras de dependência entre camadas
└── NamingConventionTest.java                      # Valida convenções de nomenclatura
```

---

## Fluxos Detalhados

### 1. Inserir Cliente (`POST /api/v1/customers`)

```
HTTP POST /api/v1/customers { name, cpf, zipCode }
        │
        ▼
CustomerController                         (Adapter IN — Spring REST)
        │  CustomerMapper.toCustomer(request)
        ▼
InsertCustomerInputPort.insert(customer, zipCode)
        │  implementada por ↓
        ▼
InsertCustomerUseCase                      (Java puro — regra de negócio)
        │
        ├──▶ FindAddressByZipCodeOutputPort.find(zipCode)
        │         │  implementada por ↓
        │         ▼
        │    FindAddressByZipCodeAdapter → FeignClient → GET /addresses/{zipCode}
        │    customer.setAddress(address)
        │
        ├──▶ InsertCustomerOutputPort.insert(customer)
        │         │  implementada por ↓
        │         ▼
        │    InsertCustomerAdapter → CustomerRepository.save() → MongoDB
        │
        └──▶ SendCpfForValidationOutputPort.send(cpf)
                  │  implementada por ↓
                  ▼
             SendCpfValidationAdapter → KafkaTemplate.send("tp_cpf_validation", cpf)
```

Ao inserir, o sistema **envia o CPF para validação assíncrona** via Kafka no tópico `tp_cpf_validation`. O campo `isCpfValid` começa como `false`.

### 2. Buscar Cliente por ID (`GET /api/v1/customers/{id}`)

```
HTTP GET /api/v1/customers/{id}
        │
        ▼
CustomerController
        │
        ▼
FindCustomerByIdInputPort.find(id)
        │  implementada por ↓
        ▼
FindCustomerByIdUseCase                    (Java puro)
        │
        └──▶ FindCustomerByIdOutputPort.find(id) → Optional<Customer>
                  │  implementada por ↓
                  ▼
             FindCustomerByIdAdapter → CustomerRepository.findById() → MongoDB
             Se não encontrado → lança NotFoundException
```

### 3. Atualizar Cliente (`PUT /api/v1/customers/{id}`)

```
HTTP PUT /api/v1/customers/{id} { name, cpf, zipCode }
        │
        ▼
CustomerController
        │  CustomerMapper.toCustomer(request) → customer.setId(id)
        ▼
UpdateCustomerInputPort.update(customer, zipCode)
        │  implementada por ↓
        ▼
UpdateCustomerUseCase                      (Java puro)
        │
        ├──▶ FindCustomerByIdInputPort.find(id)     ← verifica se existe (lança NotFoundException se não)
        ├──▶ FindAddressByZipCodeOutputPort.find(zipCode) ← busca novo endereço
        │    customer.setAddress(address)
        └──▶ UpdateCustomerOutputPort.update(customer)
                  │  implementada por ↓
                  ▼
             UpdateCustomerAdapter → CustomerRepository.save() → MongoDB
```

> **Nota:** o `UpdateCustomerUseCase` depende de `FindCustomerByIdInputPort` (porta de entrada), reutilizando o caso de uso de busca. Isso demonstra **composição de use cases**.

### 4. Deletar Cliente (`DELETE /api/v1/customers/{id}`)

```
HTTP DELETE /api/v1/customers/{id}
        │
        ▼
CustomerController
        │
        ▼
DeleteCustomerInputPort.delete(id)
        │  implementada por ↓
        ▼
DeleteCustomerUseCase                      (Java puro)
        │
        └──▶ DeleteCustomerOutputPort.delete(id)
                  │  implementada por ↓
                  ▼
             DeleteCustomerAdapter → CustomerRepository.deleteById() → MongoDB
```

### 5. Validação Assíncrona de CPF (Kafka Consumer)

```
Kafka Topic: "tp_cpf_validated"
        │  mensagem com { id, name, zipCode, cpf, isCpfValid: true }
        ▼
ReceiveValidatedCpfConsumer                (Adapter IN — Kafka)
        │  CustomerMessageMapper.toCustomer(message)
        ▼
UpdateCustomerInputPort.update(customer, zipCode)
        │  reutiliza o mesmo fluxo de atualização
        ▼
UpdateCustomerUseCase → busca endereço → persiste com isCpfValid = true
```

**Fluxo completo de validação de CPF:**
1. `InsertCustomerUseCase` insere o cliente e envia CPF para o tópico `tp_cpf_validation`
2. Um serviço externo (fora deste projeto) consome, valida e publica no tópico `tp_cpf_validated`
3. `ReceiveValidatedCpfConsumer` consome e atualiza o cliente com `isCpfValid = true`

---

## Como o UseCase Vira Bean Spring sem @Service

Os Use Cases são **Java puro** — sem `@Service`, `@Component` ou qualquer anotação do Spring. Para registrá-los como beans, usamos classes `@Configuration`:

```java
@Configuration
public class InsertCustomerConfig {

    @Bean
    public InsertCustomerUseCase insertCustomerUseCase(
            FindAddressByZipCodeAdapter findAddressByZipCodeAdapter,
            InsertCustomerAdapter insertCustomerAdapter,
            SendCpfValidationAdapter sendCpfValidationAdapter) {
        return new InsertCustomerUseCase(
            findAddressByZipCodeAdapter,
            insertCustomerAdapter,
            sendCpfValidationAdapter
        );
    }
}
```

**Por que fazer assim?**
- O UseCase não sabe que o Spring existe.
- O `@Configuration` é a "cola" que conecta portas a adapters.
- Se amanhã você trocar Spring por Quarkus, basta trocar essas configurações — o domínio permanece intacto.

---

## Testes de Arquitetura (ArchUnit)

O projeto utiliza **ArchUnit** para garantir automaticamente que as regras da arquitetura hexagonal não sejam violadas:

### `LayeredArchitectureTest` — Regras de dependência entre camadas

| Regra | Significado |
|---|---|
| `AdaptersIn` pode ser acessado apenas por `Config` | Ninguém do core referencia controllers/consumers |
| `AdaptersOut` pode ser acessado apenas por `Config` | Ninguém do core referencia adapters de saída |
| `UseCase` pode ser acessado apenas por `Config` | Use cases são instanciados via configuração |
| `PortsIn` pode ser acessado por `UseCase` e `AdaptersIn` | Adapters IN chamam Input Ports; Use Cases implementam |
| `PortsOut` pode ser acessado por `UseCase` e `AdaptersOut` | Use Cases usam Output Ports; Adapters OUT implementam |
| `Config` não pode ser acessado por nenhuma camada | Configuração é ponto terminal |

### `NamingConventionTest` — Convenções de nomenclatura

Garante que cada classe:
- Resida no pacote correto (ex: `*Controller` só em `..adapters.in.controller`)
- Tenha o sufixo correto (ex: classes em `..useCase` terminam com `UseCase`)

Exemplos de regras:

| Classe | Deve estar em | Sufixo obrigatório |
|---|---|---|
| `*Controller` | `..adapters.in.controller` | `Controller` |
| `*Adapter` | `..adapters.out` | `Adapter` |
| `*UseCase` | `..application.core.useCase` | `UseCase` |
| `*InputPort` | `..application.ports.in` | `InputPort` |
| `*OutputPort` | `..application.ports.out` | `OutputPort` |
| `*Config` | `..config` | `Config` |
| `*Repository` | `..adapters.out.repository` | `Repository` |
| `*Entity` | `..adapters.out.repository.entity` | `Entity` |
| `*Client` | `..adapters.out.client` | `Client` |
| `*Mapper` | pacotes `mapper` específicos | `Mapper` |
| `*Consumer` | `..adapters.in.consumer` | `Consumer` |
| `*Message` | `..adapters.in.consumer.message` | `Message` |
| `*Request` | `..adapters.in.controller.request` | `Request` |
| `*Response` | pacotes `response` específicos | `Response` |
| `*Exception` | `..application.core.useCase.exception` | `Exception` |

Executar os testes:

```bash
./mvnw test
```

---

## Modelo de Domínio

### `Customer` (Entidade de Domínio)

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | `String` | Identificador único (gerado pelo MongoDB) |
| `name` | `String` | Nome do cliente |
| `address` | `Address` | Endereço (preenchido via CEP) |
| `cpf` | `String` | CPF do cliente |
| `isCpfValid` | `Boolean` | Indica se o CPF foi validado (inicia `false`) |

### `Address` (Value Object)

| Campo | Tipo | Descrição |
|---|---|---|
| `street` | `String` | Rua |
| `city` | `String` | Cidade |
| `state` | `String` | Estado |

---

## Mapeamento Porta → Adapter

| Porta (Interface) | Adapter (Implementação) | Tecnologia |
|---|---|---|
| `InsertCustomerOutputPort` | `InsertCustomerAdapter` | MongoDB |
| `FindCustomerByIdOutputPort` | `FindCustomerByIdAdapter` | MongoDB |
| `UpdateCustomerOutputPort` | `UpdateCustomerAdapter` | MongoDB |
| `DeleteCustomerOutputPort` | `DeleteCustomerAdapter` | MongoDB |
| `FindAddressByZipCodeOutputPort` | `FindAddressByZipCodeAdapter` | OpenFeign (HTTP) |
| `SendCpfForValidationOutputPort` | `SendCpfValidationAdapter` | Kafka Producer |

---

## Configuração

`src/main/resources/application.yaml`:

```yaml
spring:
  application:
    name: hexagonal-architecture-introduction-project
  mongodb:
    uri: "mongodb://localhost:27017/hexagonal-introduction-project"
client:
  address:
    url: "http://localhost:8070/addresses"
```

| Propriedade | Descrição |
|---|---|
| `spring.mongodb.uri` | URI do MongoDB local |
| `client.address.url` | URL base do serviço externo de busca de endereço por CEP |

---

## Pré-requisitos

- **Java 21**
- **Maven 3.8+**
- **MongoDB** rodando em `localhost:27017`
- **Apache Kafka** rodando localmente (para produção/consumo de mensagens)
- **Serviço de endereços** disponível em `localhost:8070/addresses` (endpoint que retorna `{ street, city, state }` dado um CEP)

---

## Como executar

```bash
# Compilar
./mvnw clean compile

# Rodar os testes (incluindo ArchUnit)
./mvnw test

# Executar a aplicação
./mvnw spring-boot:run
```

---

## Endpoints da API

Base URL: `http://localhost:8080/api/v1/customers`

| Método | Path | Descrição | Response |
|---|---|---|---|
| `POST` | `/api/v1/customers` | Cadastra um novo cliente | `200 OK` |
| `GET` | `/api/v1/customers/{id}` | Busca cliente por ID | `200 OK` + body |
| `PUT` | `/api/v1/customers/{id}` | Atualiza cliente | `204 No Content` |
| `DELETE` | `/api/v1/customers/{id}` | Remove cliente | `204 No Content` |

### Request Body (POST / PUT)

```json
{
  "name": "João Silva",
  "cpf": "123.456.789-00",
  "zipCode": "01310-100"
}
```

Todos os campos são obrigatórios (`@NotBlank`).

### Response Body (GET)

```json
{
  "id": "665a1b2c3d4e5f6789012345",
  "name": "João Silva",
  "address": {
    "street": "Av. Paulista",
    "city": "São Paulo",
    "state": "SP"
  },
  "cpf": "123.456.789-00",
  "isCpfValid": false
}
```

---

## Tópicos Kafka

| Tópico | Direção | Descrição |
|---|---|---|
| `tp_cpf_validation` | **Produção** (este projeto envia) | CPF enviado para validação externa |
| `tp_cpf_validated` | **Consumo** (este projeto recebe) | Resultado da validação, atualiza `isCpfValid` |

---

## Resumo: Por que Arquitetura Hexagonal?

| Benefício | Como este projeto demonstra |
|---|---|
| **Isolamento do domínio** | `application/` não importa Spring, MongoDB nem Kafka |
| **Testabilidade** | Use Cases podem ser testados com mocks simples das Output Ports |
| **Troca de tecnologia** | Para trocar MongoDB por PostgreSQL, basta criar novos adapters OUT |
| **Múltiplas entradas** | REST Controller e Kafka Consumer usam as mesmas Input Ports |
| **Regras protegidas** | ArchUnit impede violações de dependência em tempo de teste |
| **Convenções claras** | NamingConventionTest garante nomenclatura consistente |
