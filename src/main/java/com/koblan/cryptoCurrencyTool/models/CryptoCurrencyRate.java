package main.java.com.koblan.cryptoCurrencyTool.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "rates")
public class CryptoCurrencyRate {

    private String id;
    private CurrCode cryptoSymbol;
    private String cryptoName;
    private CurrCode secondSymbol;
    private float lastPrice;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date createdAt;

    public CryptoCurrencyRate() {}

    public CryptoCurrencyRate( CurrCode cryptoSymbol, CurrCode secondSymbol, float lastPrice) {
        this.cryptoSymbol = cryptoSymbol;
        this.cryptoName = cryptoSymbol.getName();
        this.secondSymbol = secondSymbol;
        this.lastPrice = lastPrice;
        this.createdAt=new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CurrCode getCryptoSymbol() {
        return cryptoSymbol;
    }

    public void setCryptoSymbol(CurrCode cryptoSymbol) {
        this.cryptoSymbol = cryptoSymbol;
    }

    public String getCryptoName() {
        return cryptoName;
    }

    public void setCryptoName(String cryptoName) {
        this.cryptoName = cryptoName;
    }

    public CurrCode getSecondSymbol() {
        return secondSymbol;
    }

    public void setSecondSymbol(CurrCode secondSymbol) {
        this.secondSymbol = secondSymbol;
    }

    public float getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(float lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CryptoCurrencyRate{" +
                "id='" + id + '\'' +
                ", cryptoSymbol=" + cryptoSymbol +
                ", cryptoName='" + cryptoName + '\'' +
                ", secondSymbol=" + secondSymbol +
                ", lastPrice=" + lastPrice +
                ", createdAt=" + createdAt +
                '}';
    }
}
