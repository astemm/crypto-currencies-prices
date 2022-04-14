package com.koblan.cryptoCurrencyTool.tests;

import main.java.com.koblan.cryptoCurrencyTool.models.CurrCode;
import main.java.com.koblan.cryptoCurrencyTool.services.LastPriceService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import main.java.com.koblan.cryptoCurrencyTool.repositories.PricesRepository;
import main.java.com.koblan.cryptoCurrencyTool.models.LastPriceDTO;
import main.java.com.koblan.cryptoCurrencyTool.models.CryptoCurrencyRate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServiceTest {

    @Mock
    PricesRepository repository;

    @InjectMocks
    LastPriceService priceService;

    @Test
    void checkSavingCryptoCurrency() {
        ArgumentCaptor<CryptoCurrencyRate> captor = ArgumentCaptor.forClass(CryptoCurrencyRate.class);
        priceService.saveLastPrice(new LastPriceDTO(45800.5f,"BTC","USD"));
        verify(repository, times(1)).save(captor.capture());
        CryptoCurrencyRate price = captor.getAllValues().get(0);
        assertEquals(45800.5f, price.getLastPrice(),0.0f);
    }

    @Test
    void checkCryptoCurrencyMinPrice() {
        when(repository.findTopByCryptoSymbolOrderByLastPriceAsc(CurrCode.BTC)).thenReturn(new main.java.com.koblan.cryptoCurrencyTool.models.CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45730.0f));
        main.java.com.koblan.cryptoCurrencyTool.models.CryptoCurrencyRate withMinrice=priceService.getByMinPrice("BTC");
        assertEquals(45730.0f,priceService.getByMinPrice("BTC").getLastPrice(),0.0f);

    }

    @Test
    void checkCryptoCurrencyMaxPrice() {
        when(repository.findTopByCryptoSymbolOrderByLastPriceDesc(CurrCode.BTC)).thenReturn(new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45910.3f));
        CryptoCurrencyRate withMinrice=priceService.getByMaxPrice("BTC");
        assertEquals(45910.3f,priceService.getByMaxPrice("BTC").getLastPrice(),0.0f);

    }

    @Test
    void checkForInequalityforCryptoCurrencyMaxPrice() {
        when(repository.findTopByCryptoSymbolOrderByLastPriceDesc(CurrCode.BTC)).thenReturn(new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45910.3f));
        CryptoCurrencyRate withMinrice=priceService.getByMaxPrice("BTC");
        assertNotEquals(45911.3f,priceService.getByMaxPrice("BTC").getLastPrice(),0.0f);
    }

    @Test
    void checkCryptoCurrencyPaging() {
        CryptoCurrencyRate [] currs=new CryptoCurrencyRate[] {new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45800.5f),
                new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45910.3f),
                new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45730.0f),
                new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45840.0f)
        };
        List<CryptoCurrencyRate> currencies=new ArrayList<CryptoCurrencyRate>(Arrays.asList(currs))
                .stream().sorted(Comparator.comparingDouble(CryptoCurrencyRate::getLastPrice)).collect(Collectors.toList());
        when(repository.findByCryptoSymbol(CurrCode.BTC, PageRequest.of(0,4, Sort.by(Sort.Direction.ASC, "lastPrice")))).
                thenReturn(new PageImpl<CryptoCurrencyRate>(currencies,PageRequest.of(0,4, Sort.by(Sort.Direction.ASC, "lastPrice")),4));
        Page<CryptoCurrencyRate> page=priceService.PageByCryptoSymbol("BTC",0,4);
        assertEquals(4,page.toList().size());
        assertEquals(CurrCode.BTC,page.toList().get(1).getCryptoSymbol());
        assertEquals(45800.5f,page.toList().get(1).getLastPrice(),0.0f);
    }


    @Test
    void checkCSVreportgeneration() throws IOException {
        when(repository.findTopByCryptoSymbolOrderByLastPriceDesc(CurrCode.BTC)).thenReturn(new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45910.3f));
        when(repository.findTopByCryptoSymbolOrderByLastPriceAsc(CurrCode.BTC)).thenReturn(new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,45730.0f));
        when(repository.findTopByCryptoSymbolOrderByLastPriceDesc(CurrCode.ETH)).thenReturn(new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,3450.5f));
        when(repository.findTopByCryptoSymbolOrderByLastPriceAsc(CurrCode.ETH)).thenReturn(new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,3250.5f));
        when(repository.findTopByCryptoSymbolOrderByLastPriceDesc(CurrCode.XRP)).thenReturn(new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,0.38f));
        when(repository.findTopByCryptoSymbolOrderByLastPriceAsc(CurrCode.XRP)).thenReturn(new CryptoCurrencyRate(CurrCode.BTC, CurrCode.USD,0.35f));

        File f=new File("C:\\test.csv");
        FileWriter  writer=new FileWriter(f);
        priceService.generateCsvReport(writer);
        writer.close();
    }
}