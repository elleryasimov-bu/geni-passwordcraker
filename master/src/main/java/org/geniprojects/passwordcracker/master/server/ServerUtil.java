package org.geniprojects.passwordcracker.master.server;

import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class ServerUtil {

    public static String DEFAULT_PAGE_URL = "/client.html";
    public static String FIELD_NAME = "encryptedString";

    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(pair.substring(0, idx), pair.substring(idx + 1));
        }
        return query_pairs;
    }

}
