package com.practical.InternTask.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {

        Info info = new Info()
                .title("Task required for 1221System")
                .version("1.0.0")
                .description("Required API's for task")
                .contact(new Contact()
                        .name("Abduraxim")
                        .email("tursunboyevabdurahim2004@gmail.com")
                        .url("https://abduraxim.uz")
                );

        Server server1 = new Server()
                .description("Local")
                .url("http://localhost:9091");

        OpenAPI openAPI = new OpenAPI();
        openAPI.setInfo(info);
        openAPI.setServers(List.of(server1));

        return openAPI;
    }
}
