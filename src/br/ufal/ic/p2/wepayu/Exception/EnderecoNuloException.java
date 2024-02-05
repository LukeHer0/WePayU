package br.ufal.ic.p2.wepayu.Exception;

public class EnderecoNuloException extends Exception{
    public EnderecoNuloException() {
        super ("Endereco nao pode ser nulo.");
    }

    @Override
    public String toString() {
        return "Endereco nao pode ser nulo.";
    }
}