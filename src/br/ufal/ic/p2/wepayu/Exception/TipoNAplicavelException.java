package br.ufal.ic.p2.wepayu.Exception;

public class TipoNAplicavelException extends Exception{
    public TipoNAplicavelException() {
        super ("Tipo nao aplicavel.");
    }

    @Override
    public String toString() {
        return "Tipo nao aplicavel.";
    }
}