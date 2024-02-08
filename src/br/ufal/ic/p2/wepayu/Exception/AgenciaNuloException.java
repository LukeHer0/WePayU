package br.ufal.ic.p2.wepayu.Exception;

public class AgenciaNuloException extends Exception{
    public AgenciaNuloException(){
        super("Agencia nao pode ser nulo.");
    }

    @Override
    public String toString(){
        return "Agencia nao pode ser nulo.";
    }
}
