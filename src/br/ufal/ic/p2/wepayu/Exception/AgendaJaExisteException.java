package br.ufal.ic.p2.wepayu.Exception;

public class AgendaJaExisteException extends Exception{
    public AgendaJaExisteException(){
        super("Agenda de pagamentos ja existe");
    }

    @Override
    public String toString(){
        return "Agenda de pagamentos ja existe";
    }
}
