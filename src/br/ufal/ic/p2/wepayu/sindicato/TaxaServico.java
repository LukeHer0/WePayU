package br.ufal.ic.p2.wepayu.sindicato;

import java.time.LocalDate;

public class TaxaServico {
    private LocalDate data;
    private Double valor;

    public TaxaServico() {
    }

    private TaxaServico(LocalDate data, Double valor){
        this.data = data;
        this.valor = valor;
    }

    public LocalDate getData(){
        return this.data;
    }
    public Double getValor(){
        return this.valor;
    }


    public static class TaxaServicoBuilder{
        protected LocalDate data;
        protected Double valor;

        public TaxaServicoBuilder data(LocalDate data){
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
