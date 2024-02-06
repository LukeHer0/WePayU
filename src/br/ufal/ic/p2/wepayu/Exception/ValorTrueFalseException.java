package br.ufal.ic.p2.wepayu.Exception;

public class ValorTrueFalseException extends Exception{
    public ValorTrueFalseException(){
        super("Valor deve ser true ou false.");
    }

    @Override
    public String toString(){
        return "Valor deve ser true ou false.";
    }
}
