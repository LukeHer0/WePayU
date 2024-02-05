package br.ufal.ic.p2.wepayu.Exception;

public class SalarioNumericoException extends Exception{
    public SalarioNumericoException() {
        super("Salario deve ser numerico.");
    }

    @Override
    public String toString(){
        return "Salario deve ser numerico.";
    }
}
