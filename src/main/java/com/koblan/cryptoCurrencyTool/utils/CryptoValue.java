package main.java.com.koblan.cryptoCurrencyTool.Utils;

import java.util.Arrays;
import java.util.HashSet;


public class CryptoValue {

    public static boolean isValidCrypto(String symbol) {
        boolean isValid=false;
        String[] codes=new String[] {"BTC","ETH","XRP"};
        if (new HashSet<String>(Arrays.asList(codes)).contains(symbol)) return true;
        return isValid;
    }
}
