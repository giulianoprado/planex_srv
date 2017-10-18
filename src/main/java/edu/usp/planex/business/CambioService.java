package edu.usp.planex.business;

import edu.usp.planex.model.Quote;
import edu.usp.planex.model.QuoteList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CambioService {
    public Quote calculateQuote(String providerId, String date);

    List<QuoteList> getQuoteList(List<String> providerId, String date);
}
