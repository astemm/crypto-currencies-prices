package com.koblan.cryptoCurrencyTool.tests;

import com.koblan.cryptoCurrencyTool.TestConfig;
import main.java.com.koblan.cryptoCurrencyTool.tasks.ScheduledPricesTasks;
import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import main.java.com.koblan.cryptoCurrencyTool.client.CexIoClient;
import main.java.com.koblan.cryptoCurrencyTool.services.LastPriceService;
import main.java.com.koblan.cryptoCurrencyTool.repositories.PricesRepository;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=TestConfig.class)
@EnableMongoRepositories(basePackageClasses = PricesRepository.class)
@ActiveProfiles("test")
public class ScheduledTest {

    @SpyBean
    ScheduledPricesTasks tasks;

    @SpyBean
    CexIoClient client;

    @SpyBean
    LastPriceService priceService;

    @SpyBean
    PricesRepository priceRepository;

    @Test
    public void checkScheduledTasksBTC() {
        await()
                .atMost(Durations.TWO_MINUTES)
                .untilAsserted(() -> verify(tasks, atLeast(2)).getBTCPrices());
    }

    @Test
    public void checkScheduledTasksETH() {
        await()
                .atMost(Durations.TWO_MINUTES)
                .untilAsserted(() -> verify(tasks, atLeast(2)).getETHPrices());
    }


}
