package main.java.com.koblan.cryptoCurrencyTool.models;

import java.util.stream.Stream;

public enum CurrCode {
    BTC("Bitcoin"), ETH("Ethereum"), XRP("Ripple"), USD("Dollar");

    private final String name;

    CurrCode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static CurrCode getCode(String name) {
        return Stream.of(CurrCode.values())
                .filter(code -> name.equals(code.getName()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
