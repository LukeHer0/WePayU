package br.ufal.ic.p2.wepayu.sindicato;

import java.time.LocalDate;

public class TaxaServico {
    private LocalDate data;
    private Double valor;

    public TaxaServico(LocalDate data, Double valor){
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
