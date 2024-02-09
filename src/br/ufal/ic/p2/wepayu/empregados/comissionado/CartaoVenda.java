package br.ufal.ic.p2.wepayu.empregados.comissionado;

import java.io.Serializable;
import java.time.LocalDate;

public class CartaoVenda implements Serializable {

    private String data;
    private Double valor;

    public CartaoVenda() {
    }
    public CartaoVenda(String data, Double valor){
        this.data = data;
        this.valor = valor;
    }
    public String getData(){
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public Double getValor(){
        return this.valor;
    }
}
