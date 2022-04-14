package com.koblan.cryptoCurrencyTool.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import main.java.com.koblan.cryptoCurrencyTool.client.CexIoClient;
import main.java.com.koblan.cryptoCurrencyTool.models.LastPriceDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CexIoClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CexIoClient  client = new CexIoClient();

    @Test
    public void testGetLastPrice() {
        LastPriceDTO price=new LastPriceDTO(0.39f,"XRP","USD");
        HttpHeaders headers = Mockito.spy(HttpHeaders.class);
        headers.add("user-agent", "Application");
        HttpEntity<LastPriceDTO> httpEntity= new HttpEntity<LastPriceDTO>(headers);
        when(restTemplate.exchange("https://cex.io/api/last_price/XRP/USD", HttpMethod.GET, httpEntity, LastPriceDTO.class))
          .thenReturn(new ResponseEntity(price, HttpStatus.OK));
        LastPriceDTO actualPrice=client.getLastPrice("XRP").get();
        Assertions.assertEquals(price, actualPrice);

    }

}

