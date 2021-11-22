# Customer - API Rest
Aplicação desenvolvida com Java 8 e Spring Boot, para cadastro e autenticação de clientes;

# Como utilizar

Obs: De primeiro momento as requisições podem demorar alguns minutos até que o servidor se inicie, pois aplicação está rodando no Heroku, e após 20 minutos de inatividade, o servidor hiberna.

1º Passo: O cliente (usuario) precisa realizar seu cadastro através de um POST no end-point https://apicustomerchallenge.herokuapp.com/customers/sign-up, passando os campos obrigatórios:

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

### Exemplo JSON
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

2º Passo: Após realizar seu cadastro, o mesmo precisa realizar o login através de um POST no end-point https://apicustomerchallenge.herokuapp.com/customers/login, passando os campos "Email" e "Password" informados no cadastro. Se o login for realizado com sucesso, será retornado um Token no body da requisição para autorização aos outros end-points.

### Exemplo JSON
```
{
    "email":"teste@hotmail.com",
    "password": "123456"
}
```

<br>

3º Passo: Caso o cliente (usuario) queira realizar a alteração de suas informações cadastrais, o mesmo pode realizar um PUT no end-point https://apicustomerchallenge.herokuapp.com/customers/alteration, passando todos os campos e seu Id. Conforme o JSON abaixo, foi alterado o campo "name" e "street" se comparado ao momento do cadastro.

### Exemplo JSON
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




