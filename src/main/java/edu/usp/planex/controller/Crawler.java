package edu.usp.planex.controller;

import edu.usp.planex.cambios.Cambio;
import edu.usp.planex.cambios.CambioConfidence;
import edu.usp.planex.cambios.CambioCotacao;
import edu.usp.planex.dao.BacenDAO;
import edu.usp.planex.dao.PriceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Crawler {

    private final static Logger LOGGER = Logger.getLogger(Crawler.class.getName());

    @Autowired
    PriceDAO priceDAO;

    @Scheduled(fixedRate = 5000)
    public void autoPopulate() {
        List<Cambio> cambioProviders = new ArrayList<Cambio>();
        cambioProviders.add(new CambioConfidence());
        cambioProviders.add(new CambioCotacao());
        for (Cambio provider : cambioProviders) {
            double cotacao = provider.calcularCotacao();
            int providerId = provider.getProviderId();
            LOGGER.log(Level.INFO, "[CRAWLER] Adding value " + cotacao + " to provider id=" + providerId);
            //priceDAO.addPrice(cotacao,""+providerId,"today");
        }
    }
}
