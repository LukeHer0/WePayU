package br.ufal.ic.p2.wepayu.Exception;

public class MembroNaoExisteException extends  Exception{
    public MembroNaoExisteException(){
        super("Membro nao existe.");
    }

    @Override
    public String toString(){
        return "Membro nao existe.";
    }
}
