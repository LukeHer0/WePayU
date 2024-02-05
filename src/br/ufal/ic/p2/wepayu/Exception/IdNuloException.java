package br.ufal.ic.p2.wepayu.Exception;

public class IdNuloException extends Exception{
    public IdNuloException(){
        super ("Identificacao do empregado nao pode ser nula.");
    }

    @Override
    public String toString(){
        return "Identificacao do empregado nao pode ser nula.";
    }
}
