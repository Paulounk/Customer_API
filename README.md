# Customer - API Rest
Aplicação desenvolvida para cadastro e autenticação de clientes.

### Tecnologias 
- Java 8
- Spring Boot (JPA, Security, H2)
- JUNIT5
- PostgreSQL
- Eclipse IDE
- Swagger (Documentação)

### Deploy

O deploy foi realizado na nuvem utilizando o serviço do Heroku. De primeiro momento as requisições podem demorar alguns minutos até que o servidor se inicie, pois o servidor no Heroku hiberna após 20 minutos de inatividade.

# Como utilizar

### Cadastro de Cliente
Para realizar o cadastro de um cliente, será necessário realizar uma requisição do tipo POST no end-point https://apicustomerchallenge.herokuapp.com/customers/sign-up, passando os campos obrigatórios:

- Email (necessario ser um e-mail válido) 
- Password (necessario ter no minimo 6 caracteres)
- Name
- CPF (necessario ser um CPF valido)
- Address:
  - Street
  - Number
  - Complement
  - Neighborhood
  - City
  - State

#### Exemplo JSON
```
{
    "email":"teste@hotmail.com",
    "password": "123456",
    "name": "Fulano Teste",
    "cpf": "43510385004",
    "address":
        {
            "street": "Rua Santo Antonio",
            "number": "86",
            "complement": "02",
            "neighborhood": "Vila Centro",
            "city": "Campinas",
            "state": "São Paulo"
        }
}
```

<br>

### Login
Para o cliente realizar o login será necessario realizar uma requisição do tipo POST no end-point https://apicustomerchallenge.herokuapp.com/customers/login, passando os campos "Email" e "Password" informados no cadastro. Se o login for realizado com sucesso, será retornado um Token no body da requisição para autorização aos outros end-points.

#### Exemplo JSON
```
{
    "email":"teste@hotmail.com",
    "password": "123456"
}
```
#### Exemplo de Retorno
```
{
    "email": "teste@hotmail.com",
    "password": "$2a$10$.cfAnu6HDrZ/FSMUXM1AE.GZbRCWxB.5Uqmvv7qmECncA7fU4fqjC",
    "token": "Basic cGF1bG9AaG90bWFpbC5jb206MTIzNDU2"
}
```

<br>

### Alteração de Cadastro 
Caso o cliente (usuario) queira realizar a alteração de suas informações cadastrais, o mesmo pode realizar um PUT no end-point https://apicustomerchallenge.herokuapp.com/customers/alteration, passando o Token de autorização no Header da requisição, e no body todos os campos e seu Id. Conforme o JSON abaixo, foi alterado o campo "name" e "street" se comparado ao momento do cadastro.

#### Exemplo JSON

```
{
    "id": 1,
    "email":"teste@hotmail.com",
    "password": "123456",
    "name": "Fulano Teste",
    "cpf": "43510385004",
    "address":
        {
            "id": 1,
            "street": "Rua Santo Antonio",
            "number": "86",
            "complement": "02",
            "neighborhood": "Vila Centro",
            "city": "Campinas",
            "state": "São Paulo"
        }
}
```

<br>

### Exclusão de Cadastro 
Para excluir um cliente na base de dados, será necessario realizar um DELETE no end-point https://apicustomerchallenge.herokuapp.com/delete/{id}, passando o ID do cliente. Necessario passar o Token de Autorização recebido no momento do Login.

<br>

### Obter Todos os Clientes
Para retornar todos os clientes cadastrados na base de dados, será necessario realizar um GET no end-point https://apicustomerchallenge.herokuapp.com/customers. Necessario passar o Token de Autorização recebido no momento do Login.

<br>

### Obter Cliente
Para retornar apenas um cliente, será necessario realizar um GET no end-point https://apicustomerchallenge.herokuapp.com/customers/{id}, passando o ID do cliente. Necessario passar o Token de Autorização recebido no momento do Login.

