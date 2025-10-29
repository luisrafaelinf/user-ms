package com.nisum.user.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;

@Configuration
public class Config {

    @Bean
    public MappingJackson2HttpMessageConverter responseBodyConverterJackson(ObjectMapper objectMapper) {

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));

        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(dateFormat);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new SimpleModule());
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

}
