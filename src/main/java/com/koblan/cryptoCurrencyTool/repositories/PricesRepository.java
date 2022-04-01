package main.java.com.koblan.cryptoCurrencyTool.repositories;

import main.java.com.koblan.cryptoCurrencyTool.models.CryptoCurrencyRate;
import main.java.com.koblan.cryptoCurrencyTool.models.CurrCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface PricesRepository extends PagingAndSortingRepository<CryptoCurrencyRate, Long> {

    CryptoCurrencyRate findTopByCryptoSymbolOrderByLastPriceAsc(CurrCode cryptoSymbol);
    CryptoCurrencyRate findTopByCryptoSymbolOrderByLastPriceDesc(CurrCode cryptoSymbol);
    Page<CryptoCurrencyRate> findByCryptoSymbol(CurrCode cryptoSymbol, PageRequest pageRequest);

}

