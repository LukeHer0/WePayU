package br.ufal.ic.p2.wepayu.Exception;

public class DataIniPostFinException extends Exception{
    DataIniPostFinException(){
        super("Data inicial nao pode ser posterior aa data final.");
    }

    @Override
    public String toString(){
        return "Data inicial nao pode ser posterior aa data final";
    }
}
