package br.ufal.ic.p2.wepayu.Exception;

public class ValorPositivoException extends Exception{
    public ValorPositivoException(){
        super("Valor deve ser positivo.");
    }

    @Override
    public String toString(){
        return "Valor deve ser positivo.";
    }
}
