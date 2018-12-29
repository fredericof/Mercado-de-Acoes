# Mercado de Ações

Índice
========

   * [Descrição](#descrição)
   * [Utilização](#utilização)
      * [Empresa](#empresa)
        * [Cadastrar Empresa](#cadastrar-empresa)
        * [Consultar Empresa por Id](#consultar-empresa-id)
        * [Consultar lista de Empresas](#consultar-empresas)
      * [Ação](#ação)
        * [Cadastrar Ação](#cadastrar-ação)
        * [Consultar Ação por Id](#consultar-ação-id)
        * [Consultar lista de Ações](#consultar-ações)
        * [Comprar Ação](#comprar-ação)
        * [Vender Ação](#vender-ação)
      * [Comprador](#comprador)
        * [Cadastrar Comprador](#cadastrar-comprador)
        * [Consultar Comprador por Id](#consultar-comprador-id)
        * [Consultar lista de Compradores](#consultar-compradores) 
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
2. Consultar Empresa por Id
3. Consultar lista de Empresas

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

### Consultar Empresa por Id

| ENDPOINT                    | PARAMETROS         | TIPO          |
| -----------------------     | ---------------    | ------------- |
| /api/v1/empresas/{id}       | id = id da Empresa | GET           |

### Consultar lista de Empresas

| ENDPOINT                    | PARAMETROS         | TIPO          |
| -----------------------     | ---------------    | ------------- |
| /api/v1/empresas            |                    | GET           |


Ação
--------

Os recursos do Controller Ação realiza quatro operações:

1. Cadastrar Ação
2. Consultar Ação por Id
3. Consultar lista de Ações
4. Comprar Ação
5. Vender Ação

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

### Consultar Ação por Id

| ENDPOINT                                      | PARAMETROS       | TIPO          |
| ----------------------                        | -------------    | ------------- |
| /api/v1/acoes/{id}                            | id = id da Ação  | GET           |

### Consultar Lista de Ações

| ENDPOINT                                      | PARAMETROS       | TIPO          |
| ----------------------                        | -------------    | ------------- |
| /api/v1/acoes                                 |                  | GET           |

### Comprar Ação

| ENDPOINT                                                    | PARAMETROS                                                | TIPO          |
| ----------------------------------------------------------  | --------------------------------------------------------  | ------------- |
| /api/v1/acoes/{idAcao}/comprador/{idComprador}/comprar      | idAcao = id da Ação; idComprador = id do Comprador        | UPDATE        |

Body: Sem corpo

### Vender Ação

| ENDPOINT                                                    | PARAMETROS                                                | TIPO          |
| ----------------------------------------------------------  | --------------------------------------------------------  | ------------- |
| /api/v1/acoes/{idAcao}/valor/{valor}/vender                 | idAcao = id da Ação; valor = valor de compra da ação      | UPDATE        |

Body: Sem corpo

Comprador
--------

Os recursos do Controller Comprador realiza duas operações:

1. Cadastrar Comprador
2. Consultar Comprador por Id
3. Consultar lista de Compradores

### Cadastrar Comprador

| ENDPOINT                       | PARAMETROS    | TIPO          |
| --------------------------     | ------------- | ------------- |
| /api/v1/compradores            |               | POST          |

Body:

```
{
    "nome": "José",
    "email": "teste@teste.com.br"
}
```

### Consultar Comprador por Id

| ENDPOINT                       | PARAMETROS              | TIPO          |
| --------------------------     | --------------------    | ------------- |
| /api/v1/compradores/{id}       | id = id do Comprador    | GET           |

### Consultar lista de Compradores

| ENDPOINT                       | PARAMETROS              | TIPO          |
| --------------------------     | --------------------    | ------------- |
| /api/v1/compradores            |                         | GET           |

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
