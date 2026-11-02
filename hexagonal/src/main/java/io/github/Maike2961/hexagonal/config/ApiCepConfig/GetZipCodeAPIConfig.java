package io.github.Maike2961.hexagonal.config.ApiCepConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class GetZipCodeAPIConfig {

    @Value("${spring.cep.api.url}")
    private String API_CEP_URL;

    @Bean
    public RestClient getRestClient() {
        return RestClient.builder()
                .baseUrl(API_CEP_URL)
                .build();
    }
}
