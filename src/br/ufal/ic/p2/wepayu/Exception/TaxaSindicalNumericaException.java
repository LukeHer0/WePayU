package br.ufal.ic.p2.wepayu.Exception;

public class TaxaSindicalNumericaException extends Exception{
    public TaxaSindicalNumericaException(){
        super("Taxa sindical deve ser numerica.");
    }

    @Override
    public String toString(){
        return "Taxa sindical deve ser numerica.";
    }
}
