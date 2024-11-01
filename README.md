# bookShop-java-spring
Esse simples projeto foi elaborado a título de começo de estudo e aprendizado, procurando praticar o início de um estudo em filas de messagens e por em prática outros conhecimentos um pouco mais consolidados.

## Sobre o projeto
O proejeto bem simples se trata da compra e processamento de livros.

Faz uso da arquitetura de microsserviços, docker, banco de dados relacional e fila de mensagens.

O serviço [book-service](https://github.com/ribYuri/bookShop-java-spring/tree/main/book-service) fica responsável por todo o CRUD de livros, calcular os valores de uma ordem de compra e enviar a compra para processamento via fila de mensagens.

O serviço [mq-book-broker](https://github.com/ribYuri/bookShop-java-spring/tree/main/mq-book-broker) fica responsável por receber a ordem de compra, processar o pagamento (cliente de pagamento mockado), salvar o pedido no banco de dados e, caso o pedido seja aprovado, enviar de volta uma mensagem para que o serviço **book-service** possa realizar o update de quantidades vendidas de cada livro no pedido.

## Executando o projeto
Para executar o projeto será necessário ter o docker instalado. Para Windows o [Docker Desktop](https://www.docker.com/products/docker-desktop/) deve estar em execução. 
Serão criados dois containers sendo o banco de dados PostgreSql e o RabbitMq para fila de mensagens. 

### Docker Compose
* A única configuração necessária será a da [fila de mensagens](#configurando-a-fila-de-mensagens).

Na pasta do projeto existe um arquivo docker compose que irá subir e criar as intâncias necessárias de banco de dados e fila de mensagens. 

Para iniciar, basta abrir o terminal dentro da pasta desse projeto (pasta que contém o arquivo "*docker-compose.yml*") e executar o comando:
```
docker compose up
```

### Docker manualmente
* Será necessário executar as duas configurações descritas [mais abaixo](#configurando-banco-de-dados).

Para criar e executar o banco PostregreSql basta executar o comando abaixo:
```
docker run --name postgres12 -p 5432:5432 -e POSTGRES_PASSWORD=secret -e POSTGRES_USER=root -d postgres:12-alpine
```

Para criar e executar o RabbitMq basta executar o comando abaixo:
```
docker run -d --hostname my-rabbit --name book-rabbit -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=secret -p 5672:5672 -p 15672:15672  rabbitmq:3-management
```
### Configurando banco de dados
Será necessário conectar ao banco de dados utilizando alguma plataforma como por exemplo o [DBeaver](https://dbeaver.io/download/). Depois será necessário criar dois bancos com os seguintes nomes:
* "book_database"
* "mq_book_database"

Ao executar os projetos o Flyway irá se encarregar de criar e gerenciar as tabelas.

### Configurando a Fila de mensagens
Para se conectar ao RabbitMq, basta acessar o [localhost](http://localhost:15672/). 
* Usuário: user
* Password: secret
  
Seguindo a ideia do [vídeo tutorial](https://www.youtube.com/watch?v=weAruTI623k&ab_channel=MasterDev) de exemplo, iremos criar e configurar as seguintes queues, exchanges e routing-key:
* Queue: "order-request-queue"  | Exchange: "order-request-exchange"  | Routing-key: "order-request-rout-key"
* Queue: "order-response-success-queue"  | Exchange: "order-response-success-exchange"  | Routing-key: "order-response-success-rout-key"

### Testando
Aqui no projeto se encontra uma collection que pode ser utilizada para testar a execução.

Então, basta rodar os dois projetos disponíveis ([book-service](https://github.com/ribYuri/bookShop-java-spring/tree/main/book-service) e [mq-book-broker](https://github.com/ribYuri/bookShop-java-spring/tree/main/mq-book-broker)) e utilizar a collection disponível para os testes.
