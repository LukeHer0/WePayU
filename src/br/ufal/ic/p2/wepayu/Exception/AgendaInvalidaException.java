package br.ufal.ic.p2.wepayu.Exception;

public class AgendaInvalidaException extends Exception{
    public AgendaInvalidaException(){
        super("Descricao de agenda invalida");
    }

    @Override
    public String toString(){
        return "Descricao de agenda invalida";
    }
}
