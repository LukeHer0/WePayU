package br.ufal.ic.p2.wepayu.Exception;

public class IdMembroNuloException extends Exception{
    public IdMembroNuloException(){
        super("Identificacao do membro nao pode ser nula.");
    }

    @Override
    public String toString(){
        return "Identificacao do membro nao pode ser nula.";
    }
}
