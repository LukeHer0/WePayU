package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.empregados.comissionado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.empregados.horista.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Aritmetica;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class FolhadePagamento {
    private static HashMap<String, EmpregadoHorista> empregadosHoristas;
    private static HashMap<String, EmpregadoComissionado> empregadosComissionados;
    private static HashMap<String, EmpregadoAssalariado> empregadosAssalariados;

    public FolhadePagamento(HashMap<String, EmpregadoHorista> empregadosHoristas,
                                        HashMap<String, EmpregadoComissionado> empregadosComissionados,
                                        HashMap<String, EmpregadoAssalariado> empregadosAssalariados) {
        this.empregadosHoristas = empregadosHoristas;
        this.empregadosComissionados = empregadosComissionados;
        this.empregadosAssalariados = empregadosAssalariados;
    }

//    public static void rodaFolha(String data, String saida) throws Exception {
//
//    }

    public static String totalSalario(String data) throws DataInvalidaException, DataInicialInvException, //retorna o quanto a empresa ira pagar numa determinada data
            DataIniPostFinException, EmpregadoNaoHoristaException, IdNuloException, DataFinalInvException, EmpregadoNaoComissionadoException {
        LocalDate date = Aritmetica.toData(data);
        double acumulado = 0d;
        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
        for (Map.Entry<String, EmpregadoHorista> e : empregadosHoristas.entrySet()) {
            EmpregadoHorista empregadoHorista = e.getValue();
            if (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
                break;
            } else {
                String inData = date.minusDays(7).format(dataFormato);
                double horasNormaisAcumuladas = Double.parseDouble(GerenciaEmpregados.getHorasNormaisTrabalhadas(e.getKey(), inData, data).replace("," , "."));
                double horasExtrasAcumuladas = Double.parseDouble(GerenciaEmpregados.getHorasExtrasTrabalhadas(e.getKey(), inData, data ).replace(",", "."));
                double total = horasNormaisAcumuladas * Double.parseDouble(empregadoHorista.getSalario().replace(",", "."));
                total += (1.5 * horasExtrasAcumuladas * Double.parseDouble(empregadoHorista.getSalario().replace(",", ".")));
                acumulado += total;
            }
        }

        for (Map.Entry<String, EmpregadoComissionado> e : empregadosComissionados.entrySet()){
            EmpregadoComissionado empregadoComissionado = e.getValue();

            if(date.getDayOfWeek() != DayOfWeek.FRIDAY){
                break;
            }
            LocalDate primeiroDia = LocalDate.of(2005,1,1);
            String inData = date.minusDays(13).format(dataFormato);
            long diferenca = ChronoUnit.DAYS.between(primeiroDia, date) + 1;
            if(diferenca % 14 == 0){
                acumulado += Math.floor((((Double.parseDouble(empregadoComissionado.getSalario().replace(",",".")) * 12)/ 52) * 2) * 100)/100d;
                acumulado += Math.floor((Double.parseDouble(GerenciaVendas.getVendasRealizadas(e.getKey(), inData, data).replace(",","."))) * Double.parseDouble(e.getValue().getComissao().replace(",", "."))*100)/100d;
            }else{
                break;
            }
        }
        for (Map.Entry<String, EmpregadoAssalariado> e : empregadosAssalariados.entrySet()){

            if(date.getDayOfMonth() == date.lengthOfMonth()){
                EmpregadoAssalariado empregadoAssalariado = e.getValue();
                acumulado += Double.parseDouble(empregadoAssalariado.getSalario().replace(",", "."));
            }else {
                break;
            }
        }
        return Double.toString(acumulado);
    }

    public static String totalFolha(String data) throws Exception {
        empregadosHoristas = GerenciaEmpregados.getEmpregadosHoristas();
        empregadosComissionados = GerenciaEmpregados.getEmpregadosComissionados();
        empregadosAssalariados = GerenciaEmpregados.getEmpregadosAssalariados();

        return Aritmetica.doubleFormat(totalSalario(data));
    }
}
