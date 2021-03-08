package org.buraindo.sd.reactive.util;

import java.util.List;
import java.util.Map;

public class ParameterUtils {

    public static String getParameter(Map<String, List<String>> params, String key, String message) {
        var results = params.get(key);
        if (results == null || results.isEmpty()) {
            throw new RuntimeException(message);
        }
        return results.get(0);
    }

}
