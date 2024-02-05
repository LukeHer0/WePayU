package br.ufal.ic.p2.wepayu.Exception;

public class ComissaoNumericaException extends Exception{
    public ComissaoNumericaException(){
        super ("Comissao deve ser numerica.");
    }

    @Override
    public String toString(){
        return "Comissao deve ser numerica.";
    }
}
