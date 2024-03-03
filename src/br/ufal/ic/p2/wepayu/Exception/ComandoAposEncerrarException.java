package br.ufal.ic.p2.wepayu.Exception;

public class ComandoAposEncerrarException extends Exception{
    public ComandoAposEncerrarException(){
        super("Nao pode dar comandos depois de encerrarSistema.");
    }

    @Override
    public String toString(){
        return "Nao pode dar comandos depois de encerrarSistema.";
    }
}
