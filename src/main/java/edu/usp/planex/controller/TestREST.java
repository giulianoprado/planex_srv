package edu.usp.planex.controller;

/**
 * Created by giulianoprado on 27/06/17.
 */

import edu.usp.planex.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping ("/api")
@RestController
public class TestREST
{
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<String> test() {
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


}
