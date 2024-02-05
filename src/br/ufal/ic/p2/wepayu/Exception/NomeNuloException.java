package br.ufal.ic.p2.wepayu.Exception;

public class NomeNuloException extends Exception{
    public NomeNuloException() {
        super ("Nome nao pode ser nulo.");
    }

    @Override
    public String toString(){
        return "Nome nao pode ser nulo.";
    }
}
