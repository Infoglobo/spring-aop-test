# SPRING-AOP-TEST

Quer aprender um pouco sobre Spring AOP? Leia nosso artigo no Medium e teste com este projeto!

- [Altere argumentos recebidos na controladora: Proxy nunca foi tão simples com Spring AOP](https://medium.com/editora-globo/altere-argumentos-recebidos-na-controladora-cf0c7fb7f6ad)

Além disso, fizemos um outro artigo explicando como aplicar teste unitário em JavaScript em seu projeto Java. Saiba mais no link abaixo:

- [Testes unitários para JavaScript em seu projeto Spring Boot](https://medium.com/editora-globo/testes-unit%C3%A1rios-para-javascript-em-seu-projeto-spring-boot-da1707d703)

# Stack utilizada

- Java 10 com module path
- Spring Boot 2.1.0.M1
- JUnit 5 com AssertJ e Mockito
- Jasmine para o front-end com [jasmine-maven-plugin](https://github.com/searls/jasmine-maven-plugin)
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

### Rode como um módulo

Para rodar usando 100% [JPMS](https://en.wikipedia.org/wiki/Java_Platform_Module_System) execute o comando abaixo:

    java --add-modules java.xml.bind --upgrade-module-path=target/modules --module globo.editoraglobo.springaoptest

Se você parar o serviço provavelmente receberá o seguinte erro:

    Caused by: java.lang.reflect.InaccessibleObjectException: Unable to make field static final java.util.concurrent.ConcurrentMap java.io.ObjectStreamClass$Caches.localDescs accessible: module java.base does not "opens java.io" to module tomcat.embed.core

Uma maneira de resolver isso (detalhes [aqui](https://stackoverflow.com/a/41265267)) é rodando com o comando abaixo:

    java --add-modules java.xml.bind --add-opens java.base/java.io=tomcat.embed.core --upgrade-module-path=target/modules --module globo.editoraglobo.springaoptest

## Referências

Saiba mais nos links:

- [Upgrade to Java 10 now! Why not?](https://medium.com/criciumadev/java-10-migration-5d853f5b5f7e)
- [Spring Boot with Java 9 and above](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-with-Java-9-and-above)