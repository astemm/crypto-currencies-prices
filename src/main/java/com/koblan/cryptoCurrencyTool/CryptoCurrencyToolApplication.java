package main.java.com.koblan.cryptoCurrencyTool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EnableScheduling
@SpringBootApplication
public class CryptoCurrencyToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoCurrencyToolApplication.class, args);
	}

}

