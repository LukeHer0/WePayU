package br.ufal.ic.p2.wepayu.empregados.horista;

import java.io.Serializable;
import java.time.LocalDate;
public class CartaoPonto implements Serializable {

    private LocalDate data;
    private Double horas;

    public CartaoPonto(LocalDate data, Double horas){
        this.data = data;
        this.horas = horas;
    }

    public LocalDate getData(){
        return this.data;
    }

    public Double getHoras(){
        return this.horas;
    }
}
