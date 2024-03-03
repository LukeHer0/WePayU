package br.ufal.ic.p2.wepayu.Exception;

public class NaoComandoDesfazerException extends Exception{
    public NaoComandoDesfazerException(){
        super("Nao ha comando a desfazer.");
    }

    @Override
    public String toString(){
        return "Nao ha comando a desfazer.";
    }
}
