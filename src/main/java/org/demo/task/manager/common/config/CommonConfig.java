package org.demo.task.manager.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class CommonConfig {

  @Bean
  ObjectMapper jsonObjectMapper() {
    String objectMapperLogBuilder =
        "Initializing the ObjectMapper instance with below mentioned configurations:"
            + "\n\t 1. Do not include null values."
            + "\n\t 2. Disable the feature to write ISO Date as timestamp."
            + "\n\t 3. Should include the stacktrace while error? " + true;
    System.out.println(objectMapperLogBuilder);
    return Jackson2ObjectMapperBuilder.json()
        .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
//        .deserializerByType(String.class, defaultJsonDeserializer) // Added to sanitize JSON payload to prevent XSS attack custom JsonDeserializer
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
        .indentOutput(true).modules(new JavaTimeModule())
        .build();
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setAmbiguityIgnored(true);
    return modelMapper;
  }

}
