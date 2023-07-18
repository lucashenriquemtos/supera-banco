# Banco Supera

## Requisitos de Instalação

- Docker
- Postman

Certifique-se de ter o Docker e o Postman instalados em seu sistema antes de prosseguir.

## Como Rodar a Aplicação

1. Clone este repositório em sua máquina local.
2. Navegue até o diretório raiz da aplicação.

### Obtendo a Imagem do Docker Hub

Você pode obter a imagem da aplicação diretamente do Docker Hub. Basta executar o seguinte comando no terminal:

```
docker pull lucashenriquemtos/banco:latest
```


### Rodando a Aplicação com a Imagem do Docker Hub

Após ter obtido a imagem do Docker Hub, você pode executar a aplicação utilizando o seguinte comando:

```
docker run --name banco -d -p 8080:8080 lucashenriquemtos/banco:latest
```


Isso iniciará um contêiner Docker com a aplicação Banco Supera.

Agora você pode acessar a aplicação pelo navegador usando a URL: [http://localhost:8080](http://localhost:8080). 