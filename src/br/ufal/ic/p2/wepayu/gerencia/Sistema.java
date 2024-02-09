package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.empregados.horista.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Aritmetica;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

public class Sistema {


    public static void zerarSistema() throws IOException {
        GerenciaEmpregados.empregados = new HashMap<>();
        GerenciaSindicato.empregadosSindicalizados = new HashMap<>();
    }

    public static void encerrarSistema(){
    }
}
