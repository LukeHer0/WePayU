package br.ufal.ic.p2.wepayu.Exception;

public class DataFinalInvException extends Exception{
    public DataFinalInvException(){
        super("Data final invalida.");
    }

    @Override
    public String toString(){
        return "Data final invalida.";
    }
}
