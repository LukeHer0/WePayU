package br.ufal.ic.p2.wepayu.Exception;

public class BancoNuloException extends Exception{
    public BancoNuloException(){
        super("Banco nao pode ser nulo.");
    }

    @Override
    public String toString(){
        return "Banco nao pode ser nulo.";
    }
}
