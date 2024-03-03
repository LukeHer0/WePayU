package br.ufal.ic.p2.wepayu.Exception;

public class AgendaNaoDisponivelException extends Exception{
    public AgendaNaoDisponivelException(){
        super ("Agenda de pagamento nao esta disponivel");
    }

    @Override
    public String toString(){
        return "Agenda de pagamento nao esta disponivel";
    }
}
