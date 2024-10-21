package com.example.demo321.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Autowired
    private RestClient restClient;

    @Value("${myapi.api.url}")
    private String urlFilmsById;
    @Value("${myapi.api.name}")
    private String name;
    @Value("${myapi.api.token}")
    private String token;


    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

    @Bean
    public RestClient restClient() {
        return new RestClient() {
            @Override
            public RequestHeadersUriSpec<?> get() {
                return null;
            }

            @Override
            public RequestHeadersUriSpec<?> head() {
                return null;
            }

            @Override
            public RequestBodyUriSpec post() {
                return null;
            }

            @Override
            public RequestBodyUriSpec put() {
                return null;
            }

            @Override
            public RequestBodyUriSpec patch() {
                return null;
            }

            @Override
            public RequestHeadersUriSpec<?> delete() {
                return null;
            }

            @Override
            public RequestHeadersUriSpec<?> options() {
                return null;
            }

            @Override
            public RequestBodyUriSpec method(HttpMethod method) {
                return null;
            }

            @Override
            public Builder mutate() {
                return null;
            }
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HttpEntity<String> getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add(name, token);
        return new HttpEntity<>(httpHeaders);
    }

    @Bean
    public ResponseEntity<String> enviroment(@PathVariable("id") int id){
        ResponseEntity<String> s = restClient.get().uri("https://kinopoiskapiunofficial.tech/api/v2.2/films/{id}", id)
                .header("X-API-KEY", "91ecfa0f-93c6-4204-b2b1-6cecf1d6e7a6")
                .header("Content-Type", "application/json").retrieve().toEntity(String.class);
        return s;
    }

}
