package br.ufal.ic.p2.wepayu.empregados.comissionado;

import java.io.Serializable;
import java.time.LocalDate;

public class CartaoVenda implements Serializable {
    private LocalDate data;
    private Double valor;

    public CartaoVenda(LocalDate data, Double valor){
        this.data = data;
        this.valor = valor;
    }

    public LocalDate getData(){
        return this.data;
    }

    public Double getHoras(){
        return this.valor;
    }
}
