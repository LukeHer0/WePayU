package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.empregados.horista.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Aritmetica;

import java.time.LocalDate;
import java.util.*;

public class Sistema {


    public static void zerarSistema() {
        GerenciaEmpregados.empregados = new HashMap<>();
        GerenciaSindicato.empregadosSindicalizados = new HashMap<>();
//        System.out.println("Sistema zerado \uD83D\uDC4D");
    }

//    public static String totalFolha(String data) throws DataInvalidaException {
//        HashMap<String, EmpregadoHorista> empregadohorista;
//        private
//    }

//    public static void rodaFolha(String data, String saida) throws DataInvalidaException {
//        LocalDate date = Aritmetica.toData(data);
//    }

    public static void encerrarSistema(){
    }

}
