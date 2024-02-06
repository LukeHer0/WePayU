package br.ufal.ic.p2.wepayu.Exception;

import br.ufal.ic.p2.wepayu.gerencia.TaxaServico;

public class TaxaSindicalNulaException extends Exception{
    public TaxaSindicalNulaException(){
        super("Taxa sindical nao pode ser nula.");
    }

    @Override
    public String toString(){
        return "Taxa sindical nao pode ser nula.";
    }
}
