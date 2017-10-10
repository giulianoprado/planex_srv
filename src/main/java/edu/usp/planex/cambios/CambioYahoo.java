package edu.usp.planex.cambios;

import edu.usp.planex.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by giulianoprado on 27/06/17.
 */
public class CambioYahoo implements Cambio{

    @Autowired
    private Utils utils;

    @Override
    public double calcularCotacao() {
        try {
            FxQuote usdbrl = YahooFinance.getFx("USDBRL=X");
            return usdbrl.getPrice().doubleValue();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int getProviderId() {
        return 3;
    }
}
