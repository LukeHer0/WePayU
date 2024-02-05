package br.ufal.ic.p2.wepayu.Exception;

public class TipoInvalidoException extends Exception{
    public TipoInvalidoException(){
        super ("Tipo invalido.");
    }

    @Override
    public String toString(){
        return "Tipo invalido.";
    }
}
