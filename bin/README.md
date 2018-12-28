# Mercado de Ações

Índice
========

   * [Descrição](#descrição)
   * [Utilização](#utilização)
      * [Empresa](#empresa)
        * [Cadastrar Empresa](#cadastrar-empresa)
        * [Consultar Empresa](#consultar-empresa)
      * [Ação](#ação)
        * [Cadastrar Ação](#cadastrar-ação)
        * [Consultar Ação](#consultar-ação)
        * [Comprar Ação](#comprar-ação)
        * [Vender Ação](#vender-ação)
      * [Comprador](#comprador)
        * [Cadastrar Comprador](#cadastrar-comprador)
        * [Consultar Comprador](#consultar-comprador)
   * [Tecnologias](#tecnologias)

Descrição
========

O objetivo do sistema é realizar, de forma simples, a compra e vendar de ações de acordo com o negócio abaixo:

O sistema deverá tratar da compra de ações para pessoas físicas.

- Uma Empresa possui um número limitado de ações para serem vendidas;
- As Empresas podem emitir novas ações porém não podemos diminuir o número de ações atuais;
- Cada ação pode pertencer a somente um Comprador;
- Uma Ação deve possuir a informação de quando foi comprada e de qual seu valor inicial e atual, juntamente das informações do seu Comprador;
- Um Comprador pode possuir várias Ações;
- O sistema precisa tratar de forma assíncrona a compra e venda das Ações;
- Durante uma compra ou venda, seu Comprador antigo e o novo precisam receber um email com a informação adequada sobre a operação;

Utilização
========

Empresa
--------

Os recursos do Controller Empresa realiza duas operações:

1. Cadastrar Empresa
2. Consultar Empresa

### Cadastrar Empresa

| ENDPOINT                       | PARAMETROS    | TIPO          |
| --------------------------     | ------------- | ------------- |
| /api/v1/conta/cadastrar        |               | POST          |

Body:

```
{
	"nome": "Coca Cola",
	"numMaxAcoes": 2
}
```

### Consultar Empresa

| ENDPOINT                    | PARAMETROS         | TIPO          |
| -----------------------     | ---------------    | ------------- |
| /api/v1/empresas/{id}       | id = id da empresa | GET           |

Ação
--------

Os recursos do Controller Ação realiza quatro operações:

1. Cadastrar Ação
2. Consultar Ação
3. Comprar Ação
4. Vender Ação

### Cadastrar Ação

| ENDPOINT                                 | PARAMETROS    | TIPO          |
| -----------------                        | ------------- | ------------- |
| /api/v1/acoes                            |               | POST          |

Body:

```
{
	"valorInicial": 54.69,
	"valorAtual": 77,
	"empresa": {
		"id": 1
	}
}
```

### Consultar Ação

| ENDPOINT                                      | PARAMETROS       | TIPO          |
| ----------------------                        | -------------    | ------------- |
| /api/v1/acoes/{id}                            | id = id da Ação  | GET           |

### Comprar Ação

| ENDPOINT                                             | PARAMETROS       | TIPO          |
| -----------------------------                        | -------------    | ------------- |
| /api/v1/acoes/comprar/{id}                           | id = id da Ação  | UPDATE        |

Body:

```
{
    "comprador": {
        "id": 1
    }
}
```

### Vender Ação

| ENDPOINT                                             | PARAMETROS       | TIPO          |
| -----------------------------                        | -------------    | ------------- |
| /api/v1/acoes/vender/{id}                            | id = id da Ação  | UPDATE        |

Body: Sem corpo

Comprador
--------

Os recursos do Controller Comprador realiza duas operações:

1. Cadastrar Comprador
2. Consultar Comprador

### Cadastrar Comprador

| ENDPOINT                       | PARAMETROS    | TIPO          |
| --------------------------     | ------------- | ------------- |
| /api/v1/compradores            |               | POST          |

Body:

```
{
    "nome": "José"
}
```

### Consultar Comprador

| ENDPOINT                       | PARAMETROS              | TIPO          |
| --------------------------     | --------------------    | ------------- |
| /api/v1/compradores/{id}       | id = id do Comprador    | GET           |

Tecnologias
========

Neste sistema foram utilizadas as seguintes tecnologias:

```
* Spring Boot
* Spring Boot Starter Data Jpa
* Spring Boot Starter Mail
* Lombok
* H2 Database
* Spring Boot Starter AMQP
* Maven
```

**SpringBoot**: Framework responsável por tornar fácil a criação de aplicações stand-alone, pois ele já possui
um container de apliação embarcado.

**Spring Boot Starter Data Jpa**: Framework que nos fornece interfaces para criarmos repositórios facilmente e também
funcionalidades pré-implementadas para buscar resgistros no banco de dados.

**Spring Boot Starter Mail**: Framework utilizado para "conversar" com protocolo SMTP e enviar emails. 

**Lombok**: Framework que fornece anotações que gera código automaticamente e consequentemente tornando o código menos verboso.

**H2 Database**: Banco de dados em memória

**Spring Boot Starter AMQP**: Servidor de mesageria para tratar transações assíncronas

**Maven**: Ferramenta de automação de compilação que gerencia dependências.
