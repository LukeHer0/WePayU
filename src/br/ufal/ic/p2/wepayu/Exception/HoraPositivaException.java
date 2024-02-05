package br.ufal.ic.p2.wepayu.Exception;

public class HoraPositivaException extends Exception{
    public HoraPositivaException(){
        super("Horas devem ser positivas.");
    }

    @Override
    public String toString(){
        return "Horas devem ser positivas.";
    }
}
