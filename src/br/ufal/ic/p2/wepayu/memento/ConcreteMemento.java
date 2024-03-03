package br.ufal.ic.p2.wepayu.memento;

import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.sindicato.MembroSindicato;

import java.util.LinkedHashMap;

public class ConcreteMemento implements Memento{
    private LinkedHashMap<String, Empregado> empregados;
    private LinkedHashMap<String, MembroSindicato> membrosSindicato;

    public ConcreteMemento(LinkedHashMap<String, Empregado> empregados, LinkedHashMap<String, MembroSindicato> membrosSindicato) {
        this.empregados = empregados;
        this.membrosSindicato = membrosSindicato;
    }

    public LinkedHashMap<String, Empregado> getEmpregados() {
        return this.empregados;
    }

    public LinkedHashMap<String, MembroSindicato> getMembrosSindicato() {
        return this.membrosSindicato;
    }
}
