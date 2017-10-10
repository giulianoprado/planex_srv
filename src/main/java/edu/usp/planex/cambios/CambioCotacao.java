package edu.usp.planex.cambios;

import edu.usp.planex.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by giulianoprado on 27/06/17.
 */

public class CambioCotacao implements Cambio {

    @Autowired
    private Utils utils;

    @Override
    public double calcularCotacao() {
        try {
            String html = utils.getHTML("https://www.cotacao.com.br/produtos/cartoes-de-viagem-rendimento/rendimento-visa-travelmoney.html");
            int posValue = html.indexOf("R$",html.indexOf("<i class=\"flag USD\"></i>Dólar Americano</td>"));
            String val = html.substring(posValue+2, posValue+7);
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            return format.parse(val).doubleValue();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int getProviderId() {
        return 5;
    }
}
