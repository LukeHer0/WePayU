package br.ufal.ic.p2.wepayu.sindicato;

import java.io.Serializable;
import java.time.LocalDate;

public class TaxaServico implements Serializable {
    private String data;
    private Double valor;

    public TaxaServico() {
    }

    private TaxaServico(String data, Double valor){
        this.data = data;
        this.valor = valor;
    }

    public String getData(){
        return this.data;
    }
    public Double getValor(){
        return this.valor;
    }


    public static class TaxaServicoBuilder{
        protected String data;
        protected Double valor;

        public TaxaServicoBuilder data(String data){
            this.data = data;
            return this;
        }

        public TaxaServicoBuilder valor(Double valor){
            this.valor = valor;
            return this;
        }

        public TaxaServico build() {
            return new TaxaServico(data, valor);
        }
    }
}
