package br.ufal.ic.p2.wepayu.Exception;

public class NomeInexistenteException extends Exception{
    public NomeInexistenteException(){
        super ("Nao ha empregado com esse nome.");
    }

    @Override
    public String toString(){
        return "Nao ha empregado com esse nome.";
    }
}
