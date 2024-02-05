package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.Aritmetica;


import java.util.*;

public class Sistema {


    public static void zerarSistema() {
        GerenciaEmpregados.empregados = new HashMap<>();

        System.out.println("Sistema zerado \uD83D\uDC4D");
    }


    public void encerrarSistema(){
    }
}
