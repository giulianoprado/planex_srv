package edu.usp.planex.dao;

import edu.usp.planex.support.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by giulianoprado on 06/08/17.
 */
@Service
public class BacenDAO {

    @Autowired
    Utils utils;

    private static Map<String, Double> ptaxCache = new HashMap<String, Double>();

    private final static Logger LOGGER = Logger.getLogger(BacenDAO.class.getName());

    public double getQuote(String date) {
        try {
            // improvements: usar uma cache e buscar varios dados de uma vez
            SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat csv = new SimpleDateFormat("1,0000ddMMyyyy");

            if (ptaxCache.get(date) != null) {
                LOGGER.log(Level.INFO, "Quote received from cache: date = " + date  + ", value: " + ptaxCache.get(date));
                return ptaxCache.get(date);
            }
            // busca pelos ultimos 6 dias, e utiliza apenas o ultimo (solucao para evitar feriados)
            String queryURL = "https://ptax.bcb.gov.br/ptax_internet/consultaBoletim.do?method=gerarCSVFechamentoMoedaNoPeriodo&ChkMoeda=61&DATAINI=" + out.format(new Date(in.parse(date).getTime() - (6 * 1000 * 60 * 60 * 24))) + "&DATAFIM=" + out.format(in.parse(date));
            LOGGER.log(Level.INFO, "Requesting BACEN quote for day " + date + " - CSV request: " + queryURL);
            String quote = utils.getHTML(queryURL);
            String[] result = quote.split(";");
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
            double quoteValue = format.parse(result[result.length-3]).doubleValue();
            Date quoteDate = csv.parse(result[result.length-8]);
            LOGGER.log(Level.INFO, "Quote received from BACEN: date = " + in.format(quoteDate)  + ", value: " + quoteValue + ", request result:  " + quote);
            ptaxCache.put(date,quoteValue);
            return quoteValue;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
