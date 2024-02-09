package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.empregados.comissionado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.empregados.horista.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Aritmetica;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FolhadePagamento {
    private HashMap<String, EmpregadoHorista> empregadoHorista;
    private HashMap<String, EmpregadoComissionado> empregadosComissionados;
    private HashMap<String, EmpregadoAssalariado> empregadosAssalariados;

    private static HashMap<String, Double> salarioBrutoHoristas;
    private HashMap<String, Double> salarioBrutoComissionados;
    private HashMap<String, Double> salarioBrutoAssalariados;
    private static HashMap<String, EmpregadoHorista> empregadosHoristas;
//    private HashMap<String, EmpregadoHorista> empregadosHoristas;

    public void FolhadePagamento(HashMap<String, EmpregadoHorista> empregadosHoristas,
                                 HashMap<String, EmpregadoComissionado> empregadosComissionados,
                                 HashMap<String, EmpregadoAssalariado> empregadosAssalariados) {

        this.empregadosHoristas = empregadosHoristas;
        this.empregadosComissionados = empregadosComissionados;
        this.empregadosAssalariados = empregadosAssalariados;

        this.salarioBrutoHoristas = new HashMap<String, Double>();
        this.salarioBrutoComissionados = new HashMap<String, Double>();
        this.salarioBrutoAssalariados = new HashMap<String, Double>();
    }

    public static void rodaFolha(String data, String saida) throws Exception {
        LocalDate date = Aritmetica.toData(data);
        for (Map.Entry<String, EmpregadoHorista> e : empregadosHoristas.entrySet()) {

            EmpregadoHorista empregadoHorista = e.getValue();
            if (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
                salarioBrutoHoristas.put(e.getKey(), 0.0);
            } else {
                LocalDate dataInicial = date.minusDays(6);

                double horasNormaisAcumuladas = Double.parseDouble(GerenciaEmpregados.getHorasNormaisTrabalhadas(e.getKey(), data, data).replace("," , "."));
                double horasExtrasAcumuladas = Double.parseDouble(GerenciaEmpregados.getHorasExtrasTrabalhadas(e.getKey(), data, data).replace(",", "."));

                double total = horasNormaisAcumuladas * Double.parseDouble(empregadoHorista.getSalario().replace(",", "."));
                total += (1.5 * horasExtrasAcumuladas * Double.parseDouble(empregadoHorista.getSalario().replace(",", ".")));

                salarioBrutoHoristas.put(e.getKey(), total);
            }
        }
    }

//    public static String totalFolha(String data) throws DataInvalidaException {
//
//    }
}
