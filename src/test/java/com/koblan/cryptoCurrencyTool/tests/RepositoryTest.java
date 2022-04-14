package com.koblan.cryptoCurrencyTool.tests;

//import com.koblan.cryptoCurrencyTool.MongoDbTestConfiguration;
import main.java.com.koblan.cryptoCurrencyTool.models.CurrCode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import main.java.com.koblan.cryptoCurrencyTool.repositories.PricesRepository;

import main.java.com.koblan.cryptoCurrencyTool.models.CryptoCurrencyRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import main.java.com.koblan.cryptoCurrencyTool.CryptoCurrencyToolApplication;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={CryptoCurrencyToolApplication.class})
@DataMongoTest(excludeAutoConfiguration =
       EmbeddedMongoAutoConfiguration.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RepositoryTest {

     @Autowired
     PricesRepository repository;

     @BeforeAll
     void setupInitialMongoDbData() {
        List<CryptoCurrencyRate> rates=new ArrayList<>();
        rates.add(new CryptoCurrencyRate(CurrCode.BTC,CurrCode.USD,45800.5f));
        rates.add(new CryptoCurrencyRate(CurrCode.ETH,CurrCode.USD,3250.5f));
        rates.add(new CryptoCurrencyRate(CurrCode.BTC,CurrCode.USD,45910.3f));
        rates.add(new CryptoCurrencyRate(CurrCode.BTC,CurrCode.USD,45730.0f));
        rates.add(new CryptoCurrencyRate(CurrCode.XRP,CurrCode.USD,0.38f));
        rates.add(new CryptoCurrencyRate(CurrCode.BTC,CurrCode.USD,45840.0f));
        repository.saveAll(rates);
    }

    @Test
    void assertSavedRates() {
        assertEquals(6,((ArrayList<CryptoCurrencyRate>)repository.findAll()).size());
    }

    @Test
    void checkCryptoCurrencyMinPrice() {
        System.out.println(repository.findTopByCryptoSymbolOrderByLastPriceAsc(CurrCode.BTC).getLastPrice());
        assertEquals(45730.0f,repository.findTopByCryptoSymbolOrderByLastPriceAsc(CurrCode.BTC).getLastPrice(),0.0f);
    }

    @Test
    void checkCryptoCurrencyMaxPrice() {
        assertEquals(45910.3f,repository.findTopByCryptoSymbolOrderByLastPriceDesc(CurrCode.BTC).getLastPrice(),0.0f);
    }

    @Test
    void checkCryptoCurrencyPaging() {
        Page<CryptoCurrencyRate> page=repository.findByCryptoSymbol(CurrCode.BTC, PageRequest.of(0,4, Sort.by(Sort.Direction.ASC, "lastPrice")));
        assertEquals(4,page.getSize());
        assertEquals(CurrCode.BTC,page.toList().get(0).getCryptoSymbol());
        assertEquals(45730.0f,page.toList().get(0).getLastPrice(),0.0f);

    }

    @AfterAll
    void RemoveMongoDbData() {
         repository.deleteAll();
    }


}
