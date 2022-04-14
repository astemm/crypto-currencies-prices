package com.koblan.cryptoCurrencyTool;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
@ComponentScan(value="main.java.com.koblan.cryptoCurrencyTool.services")
public class TestConfig {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(MongoClients.create(), "test");
    }


}
