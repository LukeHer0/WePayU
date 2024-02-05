package br.ufal.ic.p2.wepayu.Exception;

public class SalarioNegativoException extends Exception{
    public SalarioNegativoException(){
        super ("Salario deve ser nao-negativo.");
    }

    @Override
    public String toString(){
        return "Salario deve ser nao-negativo.";
    }
}
