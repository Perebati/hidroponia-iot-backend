# Sistema de Hidroponia - Backend

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.1-green)
![MQTT](https://img.shields.io/badge/MQTT-Eclipse%20Paho-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)

Sistema backend para monitoramento e controle de sistemas de hidroponia, desenvolvido com Spring Boot e comunicação MQTT.

## 📋 Funcionalidades

- **Monitoramento em Tempo Real**: Coleta de dados de sensores via MQTT
- **Autenticação de Usuários**: Sistema de login seguro
- **Controle de Ambiente**: Monitoramento de temperatura, pH e luminosidade
- **Sistema de Alertas**: Notificações automáticas para condições críticas
- **Relatórios**: Geração de relatórios históricos de dados
- **Logs de Atividades**: Registro de todas as alterações do sistema

## 🏗️ Arquitetura

O sistema utiliza uma arquitetura moderna baseada em Spring Boot com as seguintes camadas:

```
├── Config          # Configurações MQTT e propriedades
├── Entity          # Entidades JPA do banco de dados
├── Repository      # Repositórios Spring Data JPA
├── Service         # Lógica de negócio
├── Listener        # Listeners MQTT para pub/sub
└── Events          # Sistema de eventos e alertas
```

## 🔧 Tecnologias Utilizadas

- **Java 17**: Linguagem de programação
- **Spring Boot 3.1.1**: Framework principal
- **Spring Data JPA**: Persistência de dados
- **Eclipse Paho MQTT**: Comunicação pub/sub
- **PostgreSQL**: Banco de dados
- **Maven**: Gerenciamento de dependências
- **SLF4J**: Sistema de logging

## 📦 Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- PostgreSQL 12+
- Broker MQTT (AWS IoT Core ou similar)

## ⚙️ Configuração

### 1. Banco de Dados

Execute o script SQL para criar as tabelas:

```bash
psql -U postgres -d trabSD -f tabelas.sql
```

### 2. Variáveis de Ambiente

Copie o arquivo `.env` e configure suas credenciais:

```bash
cp .env.example .env
```

Edite o arquivo `.env` com suas configurações:

```properties
# MQTT Configuration
MQTT_BROKER=ssl://seu-broker-mqtt.com:8883
MQTT_USERNAME=seu_usuario
MQTT_PASSWORD=sua_senha

# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/trabSD
DB_USERNAME=postgres
DB_PASSWORD=sua_senha_db
```

### 3. Configuração do application.properties

O arquivo `application.properties` já está configurado para usar as variáveis de ambiente.

## 🚀 Execução

### Desenvolvimento

```bash
# Compilar o projeto
./mvnw clean compile

# Executar em modo desenvolvimento
./mvnw spring-boot:run
```

### Produção

```bash
# Gerar JAR
./mvnw clean package

# Executar JAR
java -jar target/hidroponia-backend-1.0.0.jar
```

## 📡 Tópicos MQTT

O sistema escuta e publica nos seguintes tópicos:

| Tópico | Tipo | Descrição |
|--------|------|-----------|
| `login_request` | Subscribe | Solicitações de autenticação |
| `login_reply` | Publish | Respostas de autenticação |
| `esp8266_01/var_stream` | Subscribe | Dados dos sensores |
| `10/ph_set` | Subscribe | Configuração de pH |
| `10/temp_set` | Subscribe | Configuração de temperatura |
| `relatorio_request` | Subscribe | Solicitações de relatório |
| `relatorio_reply` | Publish | Dados do relatório |
| `10/alerta` | Publish | Alertas do sistema |

## 📊 Estrutura do Banco de Dados

### Principais Tabelas

- **usuario**: Dados dos usuários do sistema
- **cliente**: Informações dos clientes
- **equipamento**: Equipamentos de hidroponia
- **ambiente**: Dados ambientais coletados
- **ambiente_cli**: Ambientes dos clientes
- **ph_log**: Log de alterações de pH
- **temp_log**: Log de alterações de temperatura
- **relatorio_log**: Log de relatórios gerados

## 🔍 Logs e Monitoramento

O sistema utiliza SLF4J para logging estruturado:

- **INFO**: Operações normais do sistema
- **WARN**: Situações de atenção
- **ERROR**: Erros que precisam de investigação

Logs são configuráveis via `application.properties`.

## 🚨 Sistema de Alertas

O sistema monitora automaticamente:

- **Temperatura**: Alertas quando fora dos limites configurados
- **pH**: Notificações para valores críticos de acidez
- **Conectividade**: Alertas de perda de conexão MQTT
- **Autenticação**: Logs de tentativas de login

## 🧪 Testes

```bash
# Executar testes
./mvnw test

# Executar testes com coverage
./mvnw test jacoco:report
```

## 📈 Melhorias Futuras

- [ ] API REST para interface web
- [ ] Dashboard em tempo real
- [ ] Integração com IA para predições
- [ ] Suporte a múltiplos protocolos
- [ ] Containerização com Docker
- [ ] Métricas e observabilidade


## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.


---

**Sistema de Hidroponia Backend** - Monitoramento inteligente para agricultura moderna 🌱
