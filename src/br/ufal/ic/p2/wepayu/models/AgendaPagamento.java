package br.ufal.ic.p2.wepayu.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class AgendaPagamento implements Serializable {
    private String formula;
    private String tipo;
    private int semana;
    private int dia;

    public AgendaPagamento(){}

    public AgendaPagamento(String tipo, String dia){
        this.formula = tipo + " " + dia;
        this.tipo = tipo;
        if(dia.equals("$")){
            this.dia = -1;
        } else {
            this.dia = Integer.parseInt(dia);
        }
        if(tipo.equals("mensal")) {
            this.semana = 52/12;
        } else {
            this.semana = 1;
        }
    }

    public AgendaPagamento(String tipo, String semana, String dia){
        this.formula = tipo + " " + semana + " " + dia;
        this.tipo = tipo;
        this.dia = Integer.parseInt(dia);
        this.semana = Integer.parseInt(semana);
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSemana(int semana) {
        this.semana = semana;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public String getFormula() {
        return formula;
    }

    public String getTipo() {
        return tipo;
    }

    public int getSemana() {
        return semana;
    }

    public int getDia() {
        return dia;
    }


}
