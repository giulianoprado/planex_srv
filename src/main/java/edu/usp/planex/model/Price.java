package edu.usp.planex.model;

import java.sql.Date;

/**
 * Created by giulianoprado on 06/08/17.
 */
public class Price {
    private Provider provider;
    private double value;
    private Date date;
    private int count;
    private int priceDistance;

    public int getPriceDistance() {
        return priceDistance;
    }

    public void setPriceDistance(int priceDistance) {
        this.priceDistance = priceDistance;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
