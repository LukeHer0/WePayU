package br.ufal.ic.p2.wepayu.Exception;

public class ContaCorrenteNuloException extends Exception{
    public ContaCorrenteNuloException(){
        super("Conta corrente nao pode ser nulo.");
    }

    @Override
    public String toString(){
        return "Conta corrente nao pode ser nulo.";
    }
}
