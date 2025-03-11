# Olá!

### Esse é um projeto proposto pela *DIO* no curso ***DECOLA TECH - 2025*** da Avanade, mais detalhes sobre esse projeto pode ser encontrado em: 

https://github.com/digitalinnovationone/exercicios-java-basico/blob/main/projetos/4%20-%20Técnicas%20Avançadas%2C%20Padrões%20e%20Persistência%20(Literalmente).md

---

### Resumo:

Ele consiste em um *Sistema de Boards* e persistência de dados, onde o usuário é capaz de criar boards, criar colunas para essas Boards e cards dentro dessas colunas.

para esse projeto, foram utilizado as seguintes tecnologias: 

* Java 23;
* PostgreSQL
* SpringBoot 3 com:
    - DataJPA;
    - PostgreSQL Driver;
    - Maven
    - Flyway;
    - Lombok;
  
---

Para acessar o projeto, basta seguir esses seguintes passos: 

### 1. Copiar o Repositório:
 - git clone https://github.com/hugoGAFM/MenuApp.git cd MenuApp

### 2. Criar um banco de dados com o seu nome, usuário e senha. Utilize-os para conectar os dados;
   
### 3. Atualizar o application.properties com os dados de login. O application properties estará assim:

- spring.datasource.url=jdbc:postgresql://localhost:5432/seuBancoDeDados **<-- Atualize aqui!**
- spring.datasource.username= # insira o seu nome do Usuario **<-- Atualize aqui também!**
- spring.datasource.password= # insira a sua password **<-- Para finalizar, atualize aqui.**
- spring.datasource.driver-class-name=org.postgresql.Driver
- spring.jpa.hibernate.ddl-auto=update
- spring.jpa.show-sql=true
- spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
- spring.flyway.enabled=true
- spring.flyway.locations=classpath:db/migration

Assim que essas mudanças forem concluídas, o aplicativo estará pronto. Rode a aplicação em sua IDE!

---

Nota: o aplicativo tem controllers já implementados. caso queira fazer os testes do banco de dados por um API client.