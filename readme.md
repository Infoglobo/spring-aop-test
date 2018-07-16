# SPRING-AOP-TEST

Quer aprender um pouco sobre Spring AOP? Leia nosso artigo no Medium e teste com este projeto!

- [Altere argumentos recebidos na controladora: Proxy nunca foi tão simples com Spring AOP](https://medium.com/editora-globo/altere-argumentos-recebidos-na-controladora-cf0c7fb7f6ad)

# Stack utilizada

- Java 10 com module path
- Spring Boot 2.1.0.BUILD-SNAPSHOT
- JUnit 5 com AssertJ e Mockito
- Maven

## Testando o projeto

Infelizmente existe um problema para rodar os testes com Java 10 (até a data do último commit deste projeto). Maiores detalhes:

- https://github.com/spring-projects/spring-boot/issues/13581
- https://jira.spring.io/browse/SPR-16977

Podemos contornar usando um build customizado para rodar tudo com *classpath* em vez de *modulepath*! Então, para testar execute o seguinte comando:

    mvn clean test -Pjdk10-classpath

## Rode e teste em seu navegador

Você pode usar o `ModHeader 2.2.3` para Chrome por exemplo. Crie um REQUEST HEADER da seguinte maneira:

- Chave: `h-sample-header`
- Valor: `{  "honest-parameter": "Por quê? É por que o porquê das coisas são estudadas. Estudo porque é importante" }`

Construa e rode o projeto:

    mvn clean package && java -jar target/spring-aop-test.jar

Depois só acessar:

    http://localhost:8080/sample

## Referências

Saiba mais nos links:

- [Upgrade to Java 10 now! Why not?](https://medium.com/criciumadev/java-10-migration-5d853f5b5f7e)
- [Spring Boot with Java 9 and above](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-with-Java-9-and-above)