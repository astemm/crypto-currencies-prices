The Spring Boot Project provides simple API for pulling, retrieving, and comparing 
the prices of BTC, ETH, XRP crypto currencies (in USD). The external server the 
data are received from is Cex.Io.
The Resttemplate Client provide real time ability to pull the prices
by schedule, and store them in MongoDb database.
The API provided:
1.CryptoCurrencyController:
-findWithMinPrice (currency_name)#/cryptocurrencies/minprice?name=[currency_name]
-findWithMaxPrice (currency_name)#/cryptocurrencies/maxprice?name=[currency_name]
-findPageOfCurrencies (currency_name, page_number, page_number)
#/cryptocurrencies?name=[currency_name]&page=[page_number]&size=[page_size]
2.CSVController:
-generateCSVReportFile()#/cryptocurrencies/csv - saved to cryptoReport.csv.
Fields provided in csv file: Cryptocurrency Name, Min Price, Max Price.

The project also provide junit5 and Spring Mocking testing of main methods.