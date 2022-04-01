package main.java.com.koblan.cryptoCurrencyTool.repositories;

import main.java.com.koblan.cryptoCurrencyTool.models.CryptoCurrencyRate;
import main.java.com.koblan.cryptoCurrencyTool.models.CurrCode;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface PricesRepository extends PagingAndSortingRepository<CryptoCurrencyRate, Long> {
    //findFirstByImportTypeOrderByTimestampDesc(String importType);
    CryptoCurrencyRate findTopByCryptoSymbolOrderByLastPriceAsc(CurrCode cryptoSymbol);
    //List<CryptoCurrencyRate>
}
