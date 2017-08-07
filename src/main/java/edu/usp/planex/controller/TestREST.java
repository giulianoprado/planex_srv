package edu.usp.planex.controller;

/**
 * Created by giulianoprado on 27/06/17.
 */

import edu.usp.planex.business.CambioService;
import edu.usp.planex.cambios.Cambio;
import edu.usp.planex.cambios.CambioCotacao;
import edu.usp.planex.cambios.CambioTurismo;
import edu.usp.planex.cambios.CambioYahoo;
import edu.usp.planex.dao.BacenDAO;
import edu.usp.planex.dao.PriceDAO;
import edu.usp.planex.model.Price;
import edu.usp.planex.model.Provider;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;



@RestController
public class TestREST
{

    //@Autowired
    //PriceDAO priceDAO;


    @RequestMapping(value = "/api", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<String> test() {
        double aaaa = new CambioService().calculateQuote("NUBANK","2017-08-08");
        new BacenDAO().getQuote("2017-08-06");
        List<Provider> test = new PriceDAO().getProviderList();

        List<Price> testt = new PriceDAO().getPricesByProvider("BB","2017-08-06");
        List<Cambio> cambios = new ArrayList<>();
        cambios.add(new CambioTurismo());
        cambios.add(new CambioYahoo());
        cambios.add(new CambioCotacao());
        for (Cambio cambio : cambios) {
            cambio.atualizaCambio();
        }
        List<String> list = new ArrayList<>();
        for (Cambio cambio : cambios) {
            String s = cambio.toString();
            list.add(s);
        }
        return list;
    }

    @RequestMapping(value = "/addValue", method = RequestMethod.GET)
    public String addValue(@RequestParam(value = "value") double value, @RequestParam(value = "provider") String provider) {
        new PriceDAO().addPrice(value, provider);
        return "ok";
    }

    @RequestMapping(value = "/getQuote", method = RequestMethod.GET)
    public double getQuote(@RequestParam(value = "date") String date, @RequestParam(value = "provider") String provider) {
        return new CambioService().calculateQuote(provider, date);
    }


}
