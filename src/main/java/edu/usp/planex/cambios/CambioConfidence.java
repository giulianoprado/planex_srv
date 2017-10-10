package edu.usp.planex.cambios;

import edu.usp.planex.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by giulianoprado on 27/06/17.
 */

public class CambioConfidence implements Cambio {

    @Autowired
    private Utils utils;

    private double IOF = 1.011;

    @Override
    public double calcularCotacao() {
        try {
            String html = utils.getHTML("https://www2.confidencecambio.com.br/api/v1/resumo-cotacao");
            int posValue = html.indexOf("\"cotacao\"", html.indexOf("ESPÉCIE"));
            String val = html.substring(posValue + 10, posValue + 16);
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            return format.parse(val).doubleValue() * IOF; //somar com IOF
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int getProviderId() {
        return 3;
    }
}