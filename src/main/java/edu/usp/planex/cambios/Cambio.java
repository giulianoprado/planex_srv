package edu.usp.planex.cambios;

/**
 * Created by giulianoprado on 27/06/17.
 */
public interface Cambio {
    double taxa = 0;

    double calcularCotacao (double valor);

    boolean atualizaCambio();
    void setTaxa(double taxa);
    double getTaxa();

}
