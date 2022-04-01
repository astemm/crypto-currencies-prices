package main.java.com.koblan.cryptoCurrencyTool.services;

import main.java.com.koblan.cryptoCurrencyTool.models.CryptoCurrencyRate;
import main.java.com.koblan.cryptoCurrencyTool.models.CurrCode;
import main.java.com.koblan.cryptoCurrencyTool.models.LastPriceDTO;
import main.java.com.koblan.cryptoCurrencyTool.repositories.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LastPriceService {

    @Autowired
    PricesRepository repository;
    public void saveLastPrice(LastPriceDTO lastPrice) {
         CryptoCurrencyRate curencyRate=
                 new CryptoCurrencyRate(CurrCode.valueOf(lastPrice.getCurr1()),CurrCode.valueOf(lastPrice.getCurr2()),lastPrice.getLprice());
         System.out.println(curencyRate);
         repository.save(curencyRate);
    }

    public CryptoCurrencyRate getByMinPrice(String symbol) {
        CryptoCurrencyRate rate=null;
        CurrCode code=CurrCode.valueOf(symbol);
        rate=repository.findTopByCryptoSymbolOrderByLastPriceAsc(code);
        System.out.println(rate);
        return rate;
    }

}
