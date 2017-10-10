package edu.usp.planex.model;

/**
 * Created by giulianoprado on 24/07/17.
 */

//@Entity
//@Table(name="Provider")
public class Quote {
    double quote;
    double spread;
    double ptax;

    public double getQuote() {
        return quote;
    }

    public void setQuote(double quote) {
        this.quote = quote;
    }

    public double getSpread() {
        return spread;
    }

    public void setSpread(double spread) {
        this.spread = spread;
    }

    public double getPtax() {
        return ptax;
    }

    public void setPtax(double ptax) {
        this.ptax = ptax;
    }
}
