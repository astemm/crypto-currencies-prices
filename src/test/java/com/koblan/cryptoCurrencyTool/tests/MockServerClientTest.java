/*
package com.koblan.cryptoCurrencyTool.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koblan.cryptoCurrencyTool.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import main.java.com.koblan.cryptoCurrencyTool.client.CexIoClient;
import main.java.com.koblan.cryptoCurrencyTool.models.LastPriceDTO;
import main.java.com.koblan.cryptoCurrencyTool.CryptoCurrencyToolApplication;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
//@ContextConfiguration(classes = CryptoCurrencyToolApplication.class)
public class MockServerClientTest {

    //@Autowired
    //private CexIoClient client;
    private CexIoClient client=new CexIoClient();

    @Autowired
    private RestTemplate restTemplate;
    //private RestTemplate restTemplate=new RestTemplate();

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetLastPrice() throws URISyntaxException, JsonProcessingException {
        LastPriceDTO price=new LastPriceDTO(0.39f,"XRP","USD");
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("https://cex.io/api/last_price/XRP/USD")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(price))
                );

        LastPriceDTO actualPrice=client.getLastPrice("XRP").get();
        mockServer.verify();
        Assertions.assertEquals(price, actualPrice);
    }

}
*/
