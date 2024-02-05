package br.ufal.ic.p2.wepayu.Exception;public class SalarioNuloException extends Exception{
    public SalarioNuloException(){
        super ("Salario nao pode ser nulo.");
    }

    @Override
    public String toString(){
        return "Salario nao pode ser nulo.";
    }
}
