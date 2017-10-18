package edu.usp.planex.model;

import java.util.List;

public class QuoteList {
    String provider;
    String providerName;
    List<Quote> data;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<Quote> getData() {
        return data;
    }

    public void setData(List<Quote> data) {
        this.data = data;
    }

    public QuoteList(String provider, String providerName, List<Quote> data) {
        this.provider = provider;
        this.providerName = providerName;
        this.data = data;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
}
