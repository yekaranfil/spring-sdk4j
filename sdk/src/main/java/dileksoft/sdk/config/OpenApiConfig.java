package dileksoft.sdk.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Flying Food API")
                        .version("1.0")
                        .description("Flying Food uygulaması için REST API"))
                .servers(List.of(
                        new Server().url("http://localhost:9090").description("Local Development Server")
                ));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            openApi.getComponents().getSchemas().forEach((name, schema) -> {
                if (schema.getType() != null && schema.getType().equals("string")) {
                    if (schema.getFormat() != null && schema.getFormat().equals("date-time")) {
                        schema.setExample(OffsetDateTime.now().toString());
                    } else {
                        schema.setExample("Örnek " + name);
                    }
                }
            });
        };
    }
} 