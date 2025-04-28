# Relatório de Desenvolvimento - Projeto SciConnect

## 1. Visão Geral do Projeto

- **Nome:** SciConnect
- **Descrição:** Plataforma para publicação e divulgação de artigos científicos, voltada a estudantes de mestrado, doutorado e pesquisadores.
- **Objetivo do projeto:** Aprender e aplicar conceitos modernos de desenvolvimento de software: microserviços, mensageria (Kafka), containers (Docker), orquestração (Kubernetes), monitoramento (Prometheus/Grafana), segurança (OAuth2/JWT).
- **Início do projeto:** [Data]
- **Participantes:** [Seu nome] e [Nome do colega]

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

## 5. Roadmap de Desenvolvimento

| Sprint | Objetivo Principal                             | Status         | Link Documentação |
|:------:|:----------------------------------------------:|:--------------:|:-----------------:|
| 1      | Setup básico, auth e discovery                 | Em andamento / Concluído | [link] |
| 2      | CRUD artigos, feed e notificações via Kafka    | Em andamento / Planejado | [link] |
| 3      | Melhorias: uploads reais, cache, resiliência   | Planejado | [link] |

---

## 6. Sprints - Relatórios Individuais

### Sprint 1: Setup Inicial

- **Data de Início:** [Data]
- **Data de Término:** [Data]
- **Objetivos:**
  - Setup Docker
  - Auth-Service funcional
  - Descoberta de serviços via Eureka
  - API Gateway configurado
- **Atividades realizadas:**
  - [Descreva aqui]
- **Problemas encontrados:**
  - [Descreva aqui]
- **Soluções adotadas:**
  - [Descreva aqui]
- **Aprendizados:**
  - [Descreva aqui]
- **Melhorias sugeridas:**
  - [Descreva aqui]

---

### Sprint 2: CRUD de Artigos + Kafka

- **Data de Início:** [Data]
- **Data de Término:** [Data]
- **Objetivos:**
  - Article-Service completo
  - Kafka produzindo e consumindo eventos
  - Feed e Notificações atualizadas
- **Atividades realizadas:**
  - [Descreva aqui]
- **Problemas encontrados:**
  - [Descreva aqui]
- **Soluções adotadas:**
  - [Descreva aqui]
- **Aprendizados:**
  - [Descreva aqui]
- **Melhorias sugeridas:**
  - [Descreva aqui]

---

### Sprint 3: Melhorias de Produção

- **Data de Início:** [Data]
- **Data de Término:** [Data]
- **Objetivos:**
  - Upload real de PDF
  - Cache com Redis
  - Implementar Circuit Breaker (Resiliência)
- **Atividades realizadas:**
  - [Descreva aqui]
- **Problemas encontrados:**
  - [Descreva aqui]
- **Soluções adotadas:**
  - [Descreva aqui]
- **Aprendizados:**
  - [Descreva aqui]
- **Melhorias sugeridas:**
  - [Descreva aqui]

---

## 7. Decisões de Arquitetura

- **Padrões adotados:**
  - API Gateway + Service Discovery
  - Event-Driven Architecture (Kafka)
  - OAuth2 + JWT para segurança
- **Motivações:**
  - Aprendizado de tecnologias relevantes de mercado
  - Facilidade de escalar ou adaptar novos módulos futuramente

---

## 8. Documentação Técnica dos Serviços

> Preencher para cada serviço:

### Exemplo: Auth-Service

- **Descrição:** Serviço de autenticação de usuários.
- **Tecnologias:** Spring Security, JWT, PostgreSQL.
- **Principais endpoints:**
  - `POST /signup` - Cadastro de novo usuário
  - `POST /login` - Geração de token JWT
- **Configurações importantes:**
  - [link para configs application.yml]
  - [link para Dockerfile]

---

## 9. Observabilidade e Monitoramento

- **Métricas monitoradas:**
  - Health checks de todos os serviços
  - Número de artigos publicados
  - Quantidade de eventos Kafka processados
- **Ferramentas usadas:**
  - Prometheus para coleta de métricas
  - Grafana para dashboards

---

## 10. Lições Aprendidas (Geral)

- **Tecnologias mais desafiadoras:**
  - [Descreva aqui]
- **O que funcionou bem:**
  - [Descreva aqui]
- **O que melhoraria em futuros projetos:**
  - [Descreva aqui]

---

## 11. Anexos

- **Links úteis:**
  - [Documentações externas, tutoriais, etc.]
- **Prints de telas / APIs testadas:**
  - [Inserir prints relevantes]

---
