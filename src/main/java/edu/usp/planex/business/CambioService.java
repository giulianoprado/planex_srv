package edu.usp.planex.business;

import edu.usp.planex.model.Quote;
import org.springframework.stereotype.Service;

@Service
public interface CambioService {
    public Quote calculateQuote(String providerId, String date);
}
