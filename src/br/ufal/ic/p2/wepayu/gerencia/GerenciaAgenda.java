package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.AgendaInvalidaException;
import br.ufal.ic.p2.wepayu.Exception.AgendaJaExisteException;
import br.ufal.ic.p2.wepayu.Exception.AgendaNaoDisponivelException;
import br.ufal.ic.p2.wepayu.XMLUse.XMLUse;

import java.util.*;

public class GerenciaAgenda {
    //public static ArrayList <String> agendas = XMLUse.carregarAgendasXML("./listaAgendas.xml");
    public static ArrayList <String> agendas = XMLUse.carregarAgendasXML("./listaAgendas.xml");

    public static ArrayList<String> getAgendas() {
        return agendas;
    }

    public boolean verificaAgenda(String agenda)  throws AgendaNaoDisponivelException{
        if(agendas.contains(agenda)){
            return true;
        }throw new AgendaNaoDisponivelException();
    }

    public static void criarAgendaDePagamentos(String descricao) throws AgendaJaExisteException, AgendaInvalidaException{
        if(agendas.contains(descricao)){
            throw new AgendaJaExisteException();
        }

        ArrayList<String> elementos = new ArrayList<>(Arrays.stream(descricao.split(" ")).toList());

        if(Objects.equals(elementos.get(0), "semanal")){
            if(elementos.size() == 2) {
                if(Integer.parseInt(elementos.get(1)) >= 1 && Integer.parseInt(elementos.get(1)) <= 7){
                    agendas.add(descricao);
                } else{
                    throw new AgendaInvalidaException();
                }
            } else if(elementos.size() == 3) {
                if(Integer.parseInt(elementos.get(1)) >= 1 && Integer.parseInt(elementos.get(1)) <= 51){
                    if(Integer.parseInt(elementos.get(2)) >= 1 && Integer.parseInt(elementos.get(2)) <= 7){
                       agendas.add(descricao);
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

        }else if(Objects.equals(elementos.get(0), "mensal")){

            if(Integer.parseInt(elementos.get(1)) >= 1 && Integer.parseInt(elementos.get(1)) <= 28){
                agendas.add(descricao);
            }else {
                throw new AgendaInvalidaException();
            }
        }else{
            throw new AgendaInvalidaException();
        }

    }
}
