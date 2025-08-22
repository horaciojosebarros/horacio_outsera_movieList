# horacio_outsera_movieList — Backend API

API RESTful para ler a lista de indicados/vencedores de **Pior Filme** do Golden Raspberry Awards e expor o endpoint que retorna o **produtor com menor e maior intervalo** entre vitórias consecutivas, conforme o formato exigido.

## Requisitos atendidos

- Leitura do CSV e carga em **H2** (em memória) ao iniciar a aplicação.
- API no **Nível 2 de Richardson**: recursos (`/produtores/intervalos`) com uso de HTTP e JSON.
- **Testes de integração** com `@SpringBootTest` + `MockMvc` asseguram a conformidade dos dados.
- Banco embarcado **H2** — nenhuma instalação externa necessária.

## Como rodar

### Pré-requisitos
- **Java 17+**
- **Maven 3.9+**

### Passos
```bash
mvn clean package
mvn spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

### Endpoint
`GET /horacio/api/produtores/intervalos`

**Exemplo de resposta** (pode haver empates, retornamos listas para `min` e `max`):
```json
{
  "min": [
    { "producer": "Joel Silver", "interval": 1, "previousWin": 1990, "followingWin": 1991 }
  ],
  "max": [
    { "producer": "Matthew Vaughn", "interval": 13, "previousWin": 2002, "followingWin": 2015 }
  ]
}
```

## Testes

Rode:
```bash
mvn -q -DskipTests=false test
```

Os testes sobem o contexto Spring, carregam o CSV real (`src/main/resources/movielist.csv`) em H2 e chamam o endpoint verificando os valores esperados.

## Observações de implementação

- O parser do CSV considera **separador `;`** e interpreta `winner=yes` (case-insensitive) como verdadeiro.
- Produtores múltiplos são divididos por vírgula e por `" and "`.
- O cálculo de intervalos considera **apenas** vitórias (linhas `winner=yes`) e, para cada produtor com ≥2 vitórias, mede as diferenças de anos consecutivos; em seguida obtém-se o **menor** e o **maior** intervalo (com empate).
