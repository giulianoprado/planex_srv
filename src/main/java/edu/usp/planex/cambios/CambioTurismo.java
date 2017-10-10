package edu.usp.planex.cambios;

import edu.usp.planex.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by giulianoprado on 27/06/17.
 */

public class CambioTurismo implements Cambio {

    @Autowired
    private Utils utils;

    @Override
    public double calcularCotacao() {
        try {
            String html = utils.getHTML("https://cotacoes.economia.uol.com.br/cambioJSONChart.html?callback=grafico.parseData&type=range&cod=BRLT&mt=off");
            int posValue = html.indexOf("\"ask\":",html.indexOf("{\"high\"")-33);
            String val = html.substring(posValue+6, posValue+12);
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            return format.parse(val).doubleValue();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int getProviderId() {
        return 3;
    }
}
