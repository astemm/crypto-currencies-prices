package main.java.com.koblan.cryptoCurrencyTool.client;

import main.java.com.koblan.cryptoCurrencyTool.models.LastPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class CexIoClient {

    //@Autowired
    private RestTemplate restTemplate=new RestTemplate();

    public Optional<LastPriceDTO> getLastPrice(String cryptoSymbol) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        this.restTemplate.setMessageConverters(messageConverters);
        String lastPriceUrl = getLastPriceUrl(cryptoSymbol);
        System.out.println("uuu..."+lastPriceUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Application");
        //headers.add("Content-Type", "text/json");
        //headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<LastPriceDTO> entity = new HttpEntity<>(headers);
        //ResponseEntity<LastPriceDTO> cexIoResponse = restTemplate.getForEntity(lastPriceUrl, LastPriceDTO.class);
        ResponseEntity<LastPriceDTO> cexIoResponse=restTemplate.exchange(lastPriceUrl, HttpMethod.GET, entity, LastPriceDTO.class);
        System.out.println(cexIoResponse.getHeaders().getContentType());
        return Optional.of(cexIoResponse)
                .filter(response -> response.getStatusCode() == HttpStatus.OK)
                .map(ResponseEntity::getBody);
    }

    private String getLastPriceUrl(String symbol) {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("cex.io")
                .pathSegment("api/last_price",symbol,"USD")
                .build().toString();
    }

}
