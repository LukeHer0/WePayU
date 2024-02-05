package br.ufal.ic.p2.wepayu.Exception;

public class DataInicialInvException extends Exception{
    public DataInicialInvException(){
        super("Data inicial invalida.");
    }

    @Override
    public String toString()
    {
        return "Data inicial invalida.";
    }
}
