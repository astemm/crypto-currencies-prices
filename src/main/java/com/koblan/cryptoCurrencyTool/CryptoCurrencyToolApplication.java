package main.java.com.koblan.cryptoCurrencyTool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CryptoCurrencyToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoCurrencyToolApplication.class, args);
	}
}

