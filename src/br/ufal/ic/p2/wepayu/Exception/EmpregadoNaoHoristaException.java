package br.ufal.ic.p2.wepayu.Exception;

public class EmpregadoNaoHoristaException extends Exception{
    public EmpregadoNaoHoristaException(){
        super("Empregado nao eh horista.");
    }

    @Override
    public String toString(){
        return "Empregado nao eh horista.";
    }
}
