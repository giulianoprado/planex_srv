package edu.usp.planex.support;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;

/**
 * Created by giulianoprado on 27/06/17.
 */

@Service
public class Utils {
    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("auth","ecommerce.confidence|ECommerce|null|2760|MCwCFHR9v54Zh3NSStFTH6qZQNcjRZbwAhRBcNjWyU7ufGJw62q/uN8acQL+vg==");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
