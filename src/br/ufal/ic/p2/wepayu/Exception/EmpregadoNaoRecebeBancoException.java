package br.ufal.ic.p2.wepayu.Exception;

public class EmpregadoNaoRecebeBancoException extends Exception{
    public EmpregadoNaoRecebeBancoException(){
        super("Empregado nao recebe em banco.");
    }

    @Override
    public String toString(){
        return "Empregado nao recebe em banco.";
    }
}
