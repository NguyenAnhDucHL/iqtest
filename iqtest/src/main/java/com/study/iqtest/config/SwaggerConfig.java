package com.study.iqtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springdoc.core.GroupedOpenApi;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("IQ Test API")
                        .version("1.0")
                        .description("API documentation for IQ Test Application")
                        .contact(new Contact()
                                .name("IQ Test")
                                .url("https://yourwebsite.com")
                                .email("ducna27@fpt.com")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/iqtest/**")
                .build();
    }
}
