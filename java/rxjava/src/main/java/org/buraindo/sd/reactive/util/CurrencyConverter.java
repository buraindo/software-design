package org.buraindo.sd.reactive.util;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {

    private static final Map<String, Double> coefficients = new HashMap<>() {{
       put("usd", 1.0);
       put("rub", 75.0);
       put("eur", 90.0);
    }};

    public static Double convert(String currency, Double value) {
        return coefficients.get(currency) * value;
    }

}
