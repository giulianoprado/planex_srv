package edu.usp.planex.business;

import edu.usp.planex.dao.BacenDAO;
import edu.usp.planex.dao.PriceDAO;
import edu.usp.planex.model.Price;
import edu.usp.planex.model.Quote;
import edu.usp.planex.model.QuoteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by giulianoprado on 06/08/17.
 */
@Service
@Primary
public class CambioMediaPorDistancia implements CambioService {

    @Autowired
    PriceDAO priceDAO;

    @Autowired
    BacenDAO bacenDAO;

    public Quote calculateQuote(String providerId, String date) {
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
        List<Price> pricesList = priceDAO.getAggregatedPricesByProviderAndDate(providerId, date);
        double average = 0;
        double pesos = 0;
        for (Price price : pricesList) {
            double bacenQuote = bacenDAO.getQuote(in.format(price.getDate()));
            average += (1/(price.getPriceDistance()+0.1)) * price.getValue()/bacenQuote;
            pesos += (1/(price.getPriceDistance()+0.1));
        }
        average /= pesos;
        Quote quote = new Quote();
        quote.setPtax(bacenDAO.getQuote(date));
        quote.setSpread(average);
        quote.setQuote(quote.getPtax() * quote.getSpread());
        quote.setDate(date);
        return quote;
    }

    @Override
    public List<QuoteList> getQuoteList(List<String> providerId, String date) {
        return providerId.parallelStream().map(provider -> {
            try {
                List<Quote> listQuote = new ArrayList<Quote>();
                SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
                Date end = in.parse(date);
                Calendar cEnd = Calendar.getInstance(); cEnd.setTime(end);
                for (int i=0;i<15;i++) {
                    cEnd.add(Calendar.DAY_OF_MONTH, -1);
                    listQuote.add(calculateQuote(provider, in.format(cEnd.getTime())));
                }
                return new QuoteList(provider, priceDAO.getProviderName(provider), listQuote);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

    }


}
