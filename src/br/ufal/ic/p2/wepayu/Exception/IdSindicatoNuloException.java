package br.ufal.ic.p2.wepayu.Exception;

public class IdSindicatoNuloException extends Exception {
    public IdSindicatoNuloException(){
        super("Identificacao do sindicato nao pode ser nula.");
    }

    @Override
    public String toString(){
        return "Identificacao do sindicato nao pode ser nula";
    }
}
