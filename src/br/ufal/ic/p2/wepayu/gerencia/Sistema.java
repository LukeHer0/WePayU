package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.empregados.horista.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.memento.ConcreteMemento;
import br.ufal.ic.p2.wepayu.memento.Memento;
import br.ufal.ic.p2.wepayu.memento.MementoCommands;
import br.ufal.ic.p2.wepayu.models.Aritmetica;
import br.ufal.ic.p2.wepayu.sindicato.MembroSindicato;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class Sistema {


    public static void zerarSistema() throws IOException {
        GerenciaEmpregados.empregados = new LinkedHashMap<>();
        GerenciaSindicato.empregadosSindicalizados = new LinkedHashMap<>();
    }

    public static void encerrarSistema(){

    }

    public static Memento save() throws CloneNotSupportedException{
        LinkedHashMap<String, Empregado> empregadosClone = new LinkedHashMap<String, Empregado>();
        LinkedHashMap<String, MembroSindicato> sindicatoClone = new LinkedHashMap<String, MembroSindicato>();

        GerenciaEmpregados.empregados.forEach((id, emp) -> {
            try {
                empregadosClone.put(id, (Empregado) emp.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        });

        GerenciaSindicato.empregadosSindicalizados.forEach((id, sind) -> {
            try {
                MembroSindicato membro = (MembroSindicato) sind.clone();
                String idEmp = membro.getEmpregadoId();
                Empregado empregado = empregadosClone.get(idEmp);
                membro.setEmpregado(empregado);

                sindicatoClone.put(id, membro);
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        });

        return new ConcreteMemento(empregadosClone, sindicatoClone);
    }

    public static void restore(Memento memento) {
        ConcreteMemento concreteMemento = (ConcreteMemento) memento;
        GerenciaEmpregados.empregados = concreteMemento.getEmpregados();
        GerenciaSindicato.empregadosSindicalizados = concreteMemento.getMembrosSindicato();
    }
}
