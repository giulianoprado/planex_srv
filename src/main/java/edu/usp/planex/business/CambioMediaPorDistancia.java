package edu.usp.planex.business;

import edu.usp.planex.dao.BacenDAO;
import edu.usp.planex.dao.PriceDAO;
import edu.usp.planex.model.Price;
import edu.usp.planex.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

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
        return quote;
    }


}
