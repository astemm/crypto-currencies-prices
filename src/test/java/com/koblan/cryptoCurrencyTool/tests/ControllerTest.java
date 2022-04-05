package com.koblan.cryptoCurrencyTool.tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.koblan.cryptoCurrencyTool.models.CurrCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import main.java.com.koblan.cryptoCurrencyTool.services.LastPriceService;
import main.java.com.koblan.cryptoCurrencyTool.models.CryptoCurrencyRate;
import org.springframework.test.web.servlet.MvcResult;
import main.java.com.koblan.cryptoCurrencyTool.controllers.CryptoCurrencyController;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import main.java.com.koblan.cryptoCurrencyTool.CryptoCurrencyToolApplication;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
@WebMvcTest(CryptoCurrencyController.class)
//@WebAppConfiguration
@ContextConfiguration(classes = CryptoCurrencyToolApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LastPriceService priceService;

    ObjectMapper objectMapper;

    @BeforeAll
    public void setUp() {
        objectMapper = new ObjectMapper();
       // mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    void checkCryptoCurrencyMinPrice() throws Exception {

        CryptoCurrencyRate currency=
                new CryptoCurrencyRate(CurrCode.ETH,CurrCode.USD,3250.5f);
        when(priceService.getByMinPrice("ETH")).thenReturn(currency);

        String expected  = objectMapper.writeValueAsString(currency);

        MvcResult mvcResult = mockMvc.perform(get("/cryptocurrencies/minprice")
                .param("name","ETH"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(expected, content);
        verify(priceService, times(1)).getByMinPrice(anyString());
    }

    @Test
    void checkCryptoCurrencyMaxPrice() throws Exception {

        CryptoCurrencyRate currency=
                new CryptoCurrencyRate(CurrCode.BTC,CurrCode.USD,45500.5f);
        when(priceService.getByMaxPrice("BTC")).thenReturn(currency);
        String expected  = objectMapper.writeValueAsString(currency);

        MvcResult mvcResult = mockMvc.perform(get("/cryptocurrencies/maxprice")
                .param("name","BTC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(expected, content);
        verify(priceService, times(1)).getByMaxPrice(anyString());
    }

    @Test
    void checkCryptoCurrencyPaging() throws Exception {
        CryptoCurrencyRate [] currs=new CryptoCurrencyRate[] {new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45800.5f),
                new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45910.3f),
                new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45730.0f),
                new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45840.0f)
        };
        List<CryptoCurrencyRate> currencies=new ArrayList<CryptoCurrencyRate>(Arrays.asList(currs))
                .stream().sorted(Comparator.comparingDouble(CryptoCurrencyRate::getLastPrice)).collect(Collectors.toList());
        Page<CryptoCurrencyRate> page=new PageImpl<CryptoCurrencyRate>(currencies,PageRequest.of(0,4, Sort.by(Sort.Direction.ASC, "lastPrice")),4);
        when(priceService.PageByCryptoSymbol("BTC",0,4)).
                thenReturn(page);
        String expected  = objectMapper.writeValueAsString(page);

        MvcResult mvcResult = mockMvc.perform(get("/cryptocurrencies")
                .param("name","BTC")
                .param("page","0")
                .param("size","4"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(expected, content);
        verify(priceService, times(1)).PageByCryptoSymbol(anyString(),anyInt(), anyInt());

    }

}
