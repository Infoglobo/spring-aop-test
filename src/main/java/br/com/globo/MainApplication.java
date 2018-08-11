package br.com.globo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    /**
     * Quando rodar via IDE use os seguintes parâmetros:
     * <ul>
     *     <li>--add-opens java.base/java.lang=spring.core</li>
     *     <li>--add-opens java.base/java.io=tomcat.embed.core</li>
     * </ul>
     */
    public static void main(String[] args) {

        SpringApplication.run(MainApplication.class, args);
    }
}