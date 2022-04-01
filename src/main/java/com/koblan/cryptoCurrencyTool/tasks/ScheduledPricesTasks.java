package main.java.com.koblan.cryptoCurrencyTool.tasks;

import main.java.com.koblan.cryptoCurrencyTool.client.CexIoClient;
import main.java.com.koblan.cryptoCurrencyTool.models.LastPriceDTO;
import main.java.com.koblan.cryptoCurrencyTool.services.LastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledPricesTasks {

    @Autowired
    private CexIoClient client;
    @Autowired
    private LastPriceService priceService;

    //@Scheduled(cron = "${application.scheduling.update-finnhub-transcripts-data}", zone = NEW_YORK_ZONE_NAME)
    //@Scheduled(initialDelay = 30000, fixedRate = 61000)
    public void getBTCPrices() {
        try {
            LastPriceDTO BTCprice=client.getLastPrice("BTC").get();
            System.out.println(BTCprice);
            priceService.saveLastPrice(BTCprice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Scheduled(initialDelay = 60000, fixedRate = 61000)
    public void getETHPrices() {
        try {
            LastPriceDTO ETHprice=client.getLastPrice("ETH").get();
            System.out.println(ETHprice);
            priceService.saveLastPrice(ETHprice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //@Scheduled(initialDelay = 90000, fixedRate = 61000)
    public void getXRPPrices() {
        try {
            LastPriceDTO XRPprice=client.getLastPrice("XRP").get();
            priceService.saveLastPrice(XRPprice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
