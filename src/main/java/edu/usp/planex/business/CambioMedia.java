package edu.usp.planex.business;

import edu.usp.planex.dao.BacenDAO;
import edu.usp.planex.dao.PriceDAO;
import edu.usp.planex.model.Price;
import edu.usp.planex.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by giulianoprado on 06/08/17.
 */
@Service
public class CambioMedia implements CambioService {

    @Autowired
    PriceDAO priceDAO = new PriceDAO();

    @Autowired
    BacenDAO bacenDAO = new BacenDAO();

    public Quote calculateQuote(String providerId, String date) {
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
        List<Price> pricesList = priceDAO.getLastPricesByProvider(providerId);
        double average = 0;
        for (Price price : pricesList) {
            double bacenQuote = bacenDAO.getQuote(in.format(price.getDate()));
            average += price.getValue()/bacenQuote;
        }
        average /= pricesList.size();
//        double averageIncrease = pricesList.parallelStream().mapToDouble(price -> {
//            double bacenQuote = bacenDAO.getQuote(in.format(price.getDate()));
//            return (price.getValue() - bacenQuote)/bacenQuote;
//        }).average().getAsDouble();
        Quote quote = new Quote();
        quote.setPtax(bacenDAO.getQuote(date));
        quote.setSpread(average);
        quote.setQuote(quote.getPtax() * quote.getSpread());
        return quote;
    }


}
