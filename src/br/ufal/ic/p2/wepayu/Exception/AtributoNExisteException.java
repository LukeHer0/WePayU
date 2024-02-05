package br.ufal.ic.p2.wepayu.Exception;

public class AtributoNExisteException extends Exception{
    public AtributoNExisteException(){
        super ("Atributo nao existe.");
    }

    @Override
    public String toString(){
        return "Atributo nao existe.";
    }
}
