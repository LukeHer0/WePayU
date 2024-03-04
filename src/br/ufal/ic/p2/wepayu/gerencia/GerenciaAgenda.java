package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.AgendaInvalidaException;
import br.ufal.ic.p2.wepayu.Exception.AgendaJaExisteException;
import br.ufal.ic.p2.wepayu.Exception.AgendaNaoDisponivelException;
import br.ufal.ic.p2.wepayu.XMLUse.XMLUse;
import br.ufal.ic.p2.wepayu.models.AgendaPagamento;

import java.util.*;

public class GerenciaAgenda {
    //public static ArrayList <String> agendas = XMLUse.carregarAgendasXML("./listaAgendas.xml");
    public static ArrayList <AgendaPagamento> agendas;

    static {
        try {
            agendas = XMLUse.carregarAgendasXML("./listaAgendas.xml");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<AgendaPagamento> getAgendas() {
        return agendas;
    }

    public static void criarAgendaDePagamentos(String descricao) throws AgendaJaExisteException, AgendaInvalidaException{

        ArrayList<String> elementos = new ArrayList<>(Arrays.stream(descricao.split(" ")).toList());

        for (AgendaPagamento item: agendas) {
            if (item.getFormula().equals(descricao)){
                throw new AgendaJaExisteException();
            }
        }

        if(elementos.get(0).equals("semanal")){
            if(elementos.size() == 2) {
                if(Integer.parseInt(elementos.get(1)) >= 1 && Integer.parseInt(elementos.get(1)) <= 7){
                    AgendaPagamento agenda = new AgendaPagamento(elementos.get(0), elementos.get(1));
                    agendas.add(agenda);
                } else{
                    throw new AgendaInvalidaException();
                }
            } else if(elementos.size() == 3) {
                if(Integer.parseInt(elementos.get(1)) >= 1 && Integer.parseInt(elementos.get(1)) <= 52){
                    if(Integer.parseInt(elementos.get(2)) >= 1 && Integer.parseInt(elementos.get(2)) <= 7){
                       AgendaPagamento agenda = new AgendaPagamento(elementos.get(0), elementos.get(1), elementos.get(2));
                        for (AgendaPagamento item: agendas) {
                            if (item.getFormula().equals(descricao)){
                                throw new AgendaJaExisteException();
                            }
                        }
                        agendas.add(agenda);
                    }
                    else{
                        throw new AgendaInvalidaException();
                    }
                } else{
                    throw new AgendaInvalidaException();
                }
            } else {
                throw new AgendaInvalidaException();
            }

        }else if(elementos.get(0).equals("mensal")){
            if(elementos.get(1).equals("$")) {
                AgendaPagamento agenda = new AgendaPagamento("mensal", "$");
                agendas.add(agenda);
            } else if(Integer.parseInt(elementos.get(1)) >= 1 && Integer.parseInt(elementos.get(1)) <= 28){
                AgendaPagamento agenda = new AgendaPagamento(elementos.get(0), elementos.get(1));
                agendas.add(agenda);
            }else {
                throw new AgendaInvalidaException();
            }
        }else{
            throw new AgendaInvalidaException();
        }
        XMLUse.salvaAgendasXML(agendas, "./listaAgendas.xml");
    }
}
