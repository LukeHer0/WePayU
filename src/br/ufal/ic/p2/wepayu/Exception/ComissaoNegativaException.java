package br.ufal.ic.p2.wepayu.Exception;

public class ComissaoNegativaException extends Exception{
    public ComissaoNegativaException(){
        super ("Comissao deve ser nao-negativa.");
    }

    @Override
    public String toString(){
        return "Comissao deve ser nao-negativa.";
    }
}
