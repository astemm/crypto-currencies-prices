package main.java.com.koblan.cryptoCurrencyTool.services;

import main.java.com.koblan.cryptoCurrencyTool.models.CryptoCurrencyRate;
import main.java.com.koblan.cryptoCurrencyTool.models.CurrCode;
import main.java.com.koblan.cryptoCurrencyTool.models.LastPriceDTO;
import main.java.com.koblan.cryptoCurrencyTool.repositories.PricesRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
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

    public CryptoCurrencyRate getByMaxPrice(String symbol) {
        CryptoCurrencyRate rate=null;
        CurrCode code=CurrCode.valueOf(symbol);
        rate=repository.findTopByCryptoSymbolOrderByLastPriceDesc(code);
        System.out.println(rate);
        return rate;
    }

    public Page<CryptoCurrencyRate> PageByCryptoSymbol(String cryptoSymbol, int page, int size) {
        Page<CryptoCurrencyRate> itemsPage=repository.findByCryptoSymbol(CurrCode.valueOf(cryptoSymbol),PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "lastPrice")));
        return itemsPage;
    }

    public void generateCsvReport (Writer writer) {

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord("Cryptocurrency Name", "Min Price", "Max Price");
            for (CurrCode name: CurrCode.values()) {
                if (name.equals(CurrCode.USD)) continue;
                csvPrinter.printRecord(name, repository.findTopByCryptoSymbolOrderByLastPriceAsc(name).getLastPrice(), repository.findTopByCryptoSymbolOrderByLastPriceDesc(name).getLastPrice());
            }
        }
        catch (IOException e) {
        }

    }
    
}
