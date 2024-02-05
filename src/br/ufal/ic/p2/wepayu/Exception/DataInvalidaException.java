package br.ufal.ic.p2.wepayu.Exception;

public class DataInvalidaException extends Exception{
    public DataInvalidaException(){
        super("Data invalida.");
    }

    @Override
    public String toString(){
        return "Data invalida.";
    }
}
