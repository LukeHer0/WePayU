package br.ufal.ic.p2.wepayu.Exception;

public class NaoComandoRefazerException extends Exception{
    public NaoComandoRefazerException(){
        super("Nao ha comando a refazer.");
    }

    @Override
    public String toString(){
        return "Nao ha comando a refazer.";
    }
}
