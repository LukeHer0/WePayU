package br.ufal.ic.p2.wepayu.Exception;

public class MetodoPagInvalidoException extends Exception{
    public MetodoPagInvalidoException(){
        super("Metodo de pagamento invalido.");
    }

    @Override
    public String toString(){
        return "Metodo de pagamento invalido.";
    }
}
