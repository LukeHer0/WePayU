package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.Aritmetica;


import java.util.Hashtable;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;

public class Sistema {


    public void zerarSistema() {
        GerenciaEmpregados.empregados = new Hashtable<>();

        System.out.println("Sistema zerado \uD83D\uDC4D");
    }


    public void encerrarSistema(){
    }
}
