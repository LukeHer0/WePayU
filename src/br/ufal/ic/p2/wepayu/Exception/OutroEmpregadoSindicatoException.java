package br.ufal.ic.p2.wepayu.Exception;

public class OutroEmpregadoSindicatoException extends Exception{
    public OutroEmpregadoSindicatoException(){
        super("Ha outro empregado com esta identificacao de sindicato");
    }

    @Override
    public String toString(){
        return "Ha outro empregado com esta identificacao de sindicato";
    }
}
