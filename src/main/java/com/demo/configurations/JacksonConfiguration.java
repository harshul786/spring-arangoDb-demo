package com.demo.configurations;

import com.arangodb.springframework.core.convert.resolver.LazyLoadingProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(LazyLoadingProxy.class, LazyLoadingProxyMixIn.class);
        return mapper;
    }
}
