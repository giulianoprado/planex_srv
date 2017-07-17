package edu.usp.planex.model;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

import java.io.IOException;

/**
 * Created by giulianoprado on 27/06/17.
 */
public class CambioYahoo implements Cambio{

    double taxa;

    @Override
    public double calcularCotacao(double valor) {
        return 0;
    }

    @Override
    public boolean atualizaCambio() {
        try {
            FxQuote usdbrl = YahooFinance.getFx("USDBRL=X");
            taxa = usdbrl.getPrice().doubleValue();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public void setTaxa(double taxa) {

    }

    @Override
    public double getTaxa() {
        return 0;
    }

    @Override
    public String toString() {
        return "CambioYahoo{" +
                "taxa=" + taxa +
                '}';
    }
}
