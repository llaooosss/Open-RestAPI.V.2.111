package com.example.demo321.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

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

}
