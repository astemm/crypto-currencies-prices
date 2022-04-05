package main.java.com.koblan.cryptoCurrencyTool.controllers;

import main.java.com.koblan.cryptoCurrencyTool.Utils.CryptoValue;
import main.java.com.koblan.cryptoCurrencyTool.exceptions.NoSuchCryptoSymbolException;
import main.java.com.koblan.cryptoCurrencyTool.models.CryptoCurrencyRate;
import main.java.com.koblan.cryptoCurrencyTool.models.CurrCode;
import main.java.com.koblan.cryptoCurrencyTool.services.LastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cryptocurrencies")
public class CryptoCurrencyController {

    @Autowired
    LastPriceService priceService;

    @GetMapping(value ="/minprice", params = { "name"})
    public CryptoCurrencyRate findWithMinPrice(@RequestParam("name") String name) {

        CryptoCurrencyRate curr=null;
        curr=priceService.getByMinPrice(name);
        return curr;
    }

    @GetMapping(value ="/maxprice", params = { "name"})
    public CryptoCurrencyRate findWithMaxPrice(@RequestParam("name") String name) {
        if (!CryptoValue.isValidCrypto(name))
            throw new NoSuchCryptoSymbolException("There is not such Crypto Currency");
        CryptoCurrencyRate curr=null;
        curr=priceService.getByMaxPrice(name);
        return curr;
    }

    @GetMapping(params = {"name","page","size"})
    public Page<CryptoCurrencyRate> findPageOfCurrencies(@RequestParam("name") String name,
                                                     @RequestParam(name="page", defaultValue="0", required = false)
                                                     int page,
                                                     @RequestParam(name="size", defaultValue="10", required=false)
                                                                 int size) {

        Page<CryptoCurrencyRate> itemsPage=priceService.PageByCryptoSymbol(name,page,size);
        return itemsPage;
    }
}
