package br.ufal.ic.p2.wepayu.Exception;

public class TaxaSindicalPositivaException extends Exception{
    public TaxaSindicalPositivaException(){
        super("Taxa sindical deve ser nao-negativa.");
    }

    @Override
    public String toString(){
        return "Taxa sindical deve ser nao-negativa.";
    }
}
