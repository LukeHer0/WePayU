package br.ufal.ic.p2.wepayu.Exception;

public class EmpregadoNaoSindicalizadoException extends Exception{
    public EmpregadoNaoSindicalizadoException(){
        super("Empregado nao eh sindicalizado.");
    }

    @Override
    public String toString(){
        return "Empregado nao eh sindicalizado.";
    }
}
