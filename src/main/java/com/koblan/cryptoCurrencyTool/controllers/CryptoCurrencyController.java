package main.java.com.koblan.cryptoCurrencyTool.controllers;

import main.java.com.koblan.cryptoCurrencyTool.models.CryptoCurrencyRate;
import main.java.com.koblan.cryptoCurrencyTool.services.LastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("e2"+curr.getCryptoSymbol().getName());
        System.out.println("e3"+curr.getCryptoSymbol().getClass().isEnum());
        return curr;
    }
}
