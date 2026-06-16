# Hexagonal Architecture Introduction Project

Projeto de introdução à **Arquitetura Hexagonal** (Ports & Adapters) com Spring Boot. O objetivo é demonstrar como isolar completamente o núcleo da aplicação (domínio e regras de negócio) de frameworks, bancos de dados e serviços externos.

---

## Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem |
| Spring Boot | 4.1.0 | Framework principal |
| Spring Web MVC | — | API REST |
| Spring Data MongoDB | — | Persistência |
| Spring Cloud OpenFeign | 2025.1.0 | Client HTTP declarativo |
| Spring Validation | — | Validação de entradas |
| MapStruct | 1.6.3 | Mapeamento entre objetos |
| Lombok | — | Redução de boilerplate |
| Maven | — | Build e gestão de dependências |

---

## Arquitetura Hexagonal

A arquitetura hexagonal (Ports & Adapters) divide a aplicação em três camadas:

```
┌─────────────────────────────────────────────────────────┐
│                       ADAPTERS                          │
│  ┌──────────────┐               ┌─────────────────────┐ │
│  │  IN (Driver) │               │   OUT (Driven)      │ │
│  │  Controller  │               │  MongoDB Adapter    │ │
│  │  REST API    │               │  Feign Client       │ │
│  └──────┬───────┘               └──────────┬──────────┘ │
│         │                                  │            │
│  ┌──────▼──────────────────────────────────▼──────────┐ │
│  │                  APPLICATION CORE                  │ │
│  │  ┌─────────────────────────────────────────────┐  │ │
│  │  │              INPUT PORTS (interfaces)       │  │ │
│  │  │           InsertCustomerInputPort           │  │ │
│  │  └───────────────────┬─────────────────────────┘  │ │
│  │                      │                             │ │
│  │  ┌───────────────────▼─────────────────────────┐  │ │
│  │  │                USE CASES                    │  │ │
│  │  │           InsertCustomerUseCase             │  │ │
│  │  └───────────────────┬─────────────────────────┘  │ │
│  │                      │                             │ │
│  │  ┌───────────────────▼─────────────────────────┐  │ │
│  │  │             OUTPUT PORTS (interfaces)       │  │ │
│  │  │  FindAddressByZipCodeOutputPort             │  │ │
│  │  │  InsertCustomerOutputPort                   │  │ │
│  │  └─────────────────────────────────────────────┘  │ │
│  │                                                    │ │
│  │  ┌─────────────────────────────────────────────┐  │ │
│  │  │                  DOMAIN                     │  │ │
│  │  │           Customer  |  Address              │  │ │
│  │  └─────────────────────────────────────────────┘  │ │
│  └────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────┘
```

**Princípio central:** o núcleo (`application/`) não importa nada do Spring, MongoDB ou qualquer framework. Ele é Java puro.

---

## Estrutura de Pacotes

```
src/main/java/.../
├── adapters/
│   ├── in/
│   │   └── controller/
│   │       ├── CustomerController.java          # REST Controller (adapter de entrada)
│   │       ├── request/
│   │       │   └── CustomerRequest.java         # DTO de entrada (record)
│   │       └── mapper/
│   │           └── CustomerMapper.java          # MapStruct: CustomerRequest → Customer
│   └── out/
│       ├── FindAddressByZipCodeAdapter.java     # Adapter: chama Feign Client
│       ├── InsertCustumerAdapter.java           # Adapter: persiste no MongoDB
│       ├── client/
│       │   ├── FindAddressByZipCodeClient.java  # Feign Client (busca endereço por CEP)
│       │   ├── response/
│       │   │   └── AddressResponse.java         # DTO de resposta do serviço externo
│       │   └── mapper/
│       │       └── AddressResponseMapper.java   # MapStruct: AddressResponse → Address
│       └── repository/
│           ├── CustomerRepository.java          # MongoRepository
│           ├── entity/
│           │   ├── CustomerEntity.java          # Documento MongoDB
│           │   └── AddressEntity.java           # Embedded no CustomerEntity
│           └── mapper/
│               └── CustomerEntityMapper.java    # MapStruct: Customer → CustomerEntity
├── application/
│   ├── core/
│   │   ├── domain/
│   │   │   ├── Customer.java                    # Entidade de domínio (Java puro)
│   │   │   └── Address.java                     # Value object de domínio (Java puro)
│   │   └── useCase/
│   │       └── InsertCustomerUseCase.java       # Regra de negócio (Java puro)
│   └── ports/
│       ├── in/
│       │   └── InsertCustomerInputPort.java     # Interface do caso de uso (entrada)
│       └── out/
│           ├── FindAddressByZipCodeOutputPort.java  # Interface para buscar endereço
│           └── InsertCustomerOutputPort.java        # Interface para persistir cliente
└── config/
    └── InsertCustomerConfig.java                # @Configuration: instancia o UseCase sem @Service
```

---

## Fluxo da Funcionalidade: Inserir Cliente

```
POST /api/v1/customers
        │
        ▼
CustomerController          (adapter IN — Spring)
        │  converte CustomerRequest → Customer via CustomerMapper (MapStruct)
        ▼
InsertCustomerInputPort     (interface — núcleo puro)
        │  implementada por:
        ▼
InsertCustomerUseCase       (regra de negócio — Java puro, sem Spring)
        │
        ├──▶ FindAddressByZipCodeOutputPort.find(zipCode)
        │           │ implementada por:
        │           ▼
        │    FindAddressByZipCodeAdapter  →  FindAddressByZipCodeClient (Feign)
        │                                         → GET http://localhost:8070/addresses/{zipCode}
        │    customer.setAddress(address)
        │
        └──▶ InsertCustomerOutputPort.insert(customer)
                    │ implementada por:
                    ▼
             InsertCustumerAdapter  →  CustomerRepository (MongoDB)
                                          → collection: customers
```

---

## Detalhe: Como o UseCase vira Bean Spring sem @Service

O `InsertCustomerUseCase` é Java puro — sem nenhuma anotação do Spring. Para que o Spring o gerencie, existe uma classe de configuração na camada de infraestrutura:

```java
// config/InsertCustomerConfig.java
@Configuration
public class InsertCustomerConfig {

    @Bean
    public InsertCustomerUseCase insertCustomerUseCase(
            FindAddressByZipCodeOutputPort findAddressByZipCodeOutputPort,
            InsertCustomerOutputPort insertCustomerOutputPort) {
        return new InsertCustomerUseCase(findAddressByZipCodeOutputPort, insertCustomerOutputPort);
    }
}
```

Dessa forma o domínio permanece **completamente desacoplado de frameworks**.

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

- Java 21
- Maven
- MongoDB rodando em `localhost:27017`
- Serviço de endereços disponível em `localhost:8070/addresses`

---

## Como executar

```bash
./mvnw spring-boot:run
```

---

## Endpoint

| Método | Path | Descrição |
|---|---|---|
| `POST` | `/api/v1/customers` | Cadastra um novo cliente |

**Body:**
```json
{
  "name": "João Silva",
  "cpf": "123.456.789-00",
  "zipCode": "01310-100"
}
```

Validações: todos os campos são obrigatórios (`@NotBlank`).
