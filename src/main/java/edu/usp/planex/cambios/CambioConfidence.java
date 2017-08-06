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
    Utils utils;

    double taxa;

    @Override
    public double calcularCotacao(double valor) {
        return valor * taxa;
    }

    @Override
    public boolean atualizaCambio() {
        try {
            String html = utils.getHTML("https://www.confidencecambio.com.br/ecommerce/#/home");
            int posValue = html.indexOf("R$",html.indexOf("ESPÉCIE"));
            String val = html.substring(posValue+2, posValue+7);
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
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
