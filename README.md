# Relatório de Desenvolvimento - Projeto TCCnnect

## 1. Visão Geral do Projeto

- **Nome:** TCCnnect
- **Descrição:** Plataforma para publicação e divulgação de artigos científicos, voltada a estudantes de mestrado, doutorado e pesquisadores.
- **Objetivo do projeto:** Aprender e aplicar conceitos modernos de desenvolvimento de software: microserviços, mensageria (Kafka), containers (Docker), orquestração (Kubernetes), monitoramento (Prometheus/Grafana), segurança (OAuth2/JWT).
- **Início do projeto:** 28/04/2025
- **Participantes:** Adriano Barbosa e Douglas Porto

---

## 2. Tecnologias e Ferramentas Utilizadas

| Categoria             | Tecnologia             | Observação |
|-----------------------|-------------------------|------------|
| Linguagem             | Java 17+                |            |
| Framework             | Spring Boot 3.x         |            |
| API Gateway           | Spring Cloud Gateway    |            |
| Descoberta de Serviços| Eureka Server           |            |
| Mensageria            | Apache Kafka            |            |
| Banco de Dados SQL    | PostgreSQL              |            |
| Banco de Dados NoSQL  | MongoDB                 |            |
| Cache                 | Redis                   | Opcional (Feed) |
| Containers            | Docker, Docker-Compose  |            |
| Orquestração          | Kubernetes (Minikube)   | Em evolução |
| Monitoramento         | Prometheus, Grafana     |            |
| Autenticação          | OAuth2, JWT             |            |

---

## 3. Organização dos Serviços

- **Auth-Service:** Responsável pela autenticação e geração de tokens.
- **User-Service:** Gerenciamento de perfis e relações entre usuários.
- **Article-Service:** Cadastro, consulta e gerenciamento de artigos.
- **Feed-Service:** Atualização do feed de usuários baseados em artigos novos.
- **Notification-Service:** Envio de notificações sobre novas publicações.
- **API-Gateway:** Ponto único de entrada da aplicação.
- **Discovery-Service:** Registro e descoberta de microserviços.

---

## 4. Estrutura de Comunicação

- **Comunicação interna:** REST via API Gateway e Eureka.
- **Eventos assíncronos:** Apache Kafka para publicação e consumo de eventos (`new-article-published`).

---



## 6. Decisões de Arquitetura

- **Padrões adotados:**
  - API Gateway + Service Discovery
  - Event-Driven Architecture (Kafka)
  - OAuth2 + JWT para segurança

---

## 7. Observabilidade e Monitoramento

- **Métricas monitoradas:**
  - Health checks de todos os serviços
  - Número de artigos publicados
  - Quantidade de eventos Kafka processados
- **Ferramentas usadas:**
  - Prometheus para coleta de métricas
  - Grafana para dashboards

---

