package edu.usp.planex.model;

import edu.usp.planex.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by giulianoprado on 27/06/17.
 */

public class CambioTurismo implements Cambio {

    @Autowired
    Utils utils;

    double taxa;

    @Override
    public double calcularCotacao(double valor) {
        return valor * taxa;
    }

    @Override
    public boolean atualizaCambio() {
        try {
            String html = utils.getHTML("https://cotacoes.economia.uol.com.br/cambioJSONChart.html?callback=grafico.parseData&type=range&cod=BRLT&mt=off");
            int posValue = html.indexOf("\"ask\":",html.indexOf("{\"high\"")-33);
            String val = html.substring(posValue+6, posValue+12);
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            taxa = format.parse(val).doubleValue();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    @Override
    public double getTaxa() {
        return taxa;
    }

    @Override
    public String toString() {
        return "CambioCotacao{" +
                "utils=" + utils +
                ", taxa=" + taxa +
                '}';
    }
}
