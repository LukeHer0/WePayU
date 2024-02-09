package br.ufal.ic.p2.wepayu.empregados.horista;

import java.io.Serializable;
public class CartaoPonto implements Serializable {

    private String data;
    private Double horas;

    public CartaoPonto() {
    }

    public CartaoPonto(String data, Double horas){
        this.data = data;
        this.horas = horas;
    }
    public String getData(){
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    public Double getHoras(){
        return this.horas;
    }
}
