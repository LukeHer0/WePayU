package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.empregados.comissionado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.empregados.horista.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados;
import br.ufal.ic.p2.wepayu.gerencia.GerenciaSindicato;
import br.ufal.ic.p2.wepayu.gerencia.GerenciaVendas;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.*;
import java.util.*;
public class Aritmetica {


    public static boolean isNumeric(String str) { //testa se uma string eh numerica
        try {
            Double.parseDouble(str.replace(",", "."));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String doubleFormat(String str) { //converte para o formato de string requerido no projeto
        DecimalFormat dF = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        str = dF.format(Double.parseDouble(str.replace(',', '.')));

        return str.replace(".", "");
    }

    public static LocalDate toData(String data) throws
            DataInvalidaException {
        LocalDate date;

        DateTimeFormatter dataa = DateTimeFormatter.ofPattern("d/M/yyyy");
        date = LocalDate.parse(data, dataa);
        return date;
    }

    public static String calculaSalario(Empregado emp, String data) throws
            DataInvalidaException, EmpregadoNaoComissionadoException,
            DataInicialInvException, DataIniPostFinException, IdNuloException,
            DataFinalInvException, EmpregadoNaoHoristaException {
        LocalDate date = toData(data);
        double total = 0d;
        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");

        if (emp.getTipo().equals("horista")) {
            EmpregadoHorista empregadoHorista = (EmpregadoHorista) emp;
            String inData = date.minusDays(7).format(dataFormato);

            //Especifico Horista
            double horasNormaisAcumuladas = Double.parseDouble(GerenciaEmpregados.getHorasNormaisTrabalhadas(empregadoHorista.getId(), inData, data).replace(",", "."));
            double horasExtrasAcumuladas = Double.parseDouble(GerenciaEmpregados.getHorasExtrasTrabalhadas(empregadoHorista.getId(), inData, data).replace(",", "."));
            total = horasNormaisAcumuladas * Double.parseDouble(empregadoHorista.getSalario().replace(",", "."));
            total += (1.5 * horasExtrasAcumuladas * Double.parseDouble(empregadoHorista.getSalario().replace(",", ".")));

        } else if (emp.getTipo().equals("comissionado")) {
            EmpregadoComissionado empregadoComissionado = (EmpregadoComissionado) emp;
            String inData = date.minusDays(13).format(dataFormato);

            //Especifico Comissionado
            total += Math.floor((((Float.parseFloat(empregadoComissionado.getSalario().replace(",", ".")) * 12) / 52) * 2) * 100) / 100;
            total += Math.floor((Float.parseFloat(GerenciaVendas.getVendasRealizadas(emp.getId(), inData, data).replace(",", "."))) * Float.parseFloat(empregadoComissionado.getComissao().replace(",", ".")) * 100) / 100;

        } else if (emp.getTipo().equals("assalariado")) {
            EmpregadoAssalariado empregadoAssalariado = (EmpregadoAssalariado) emp;
            total += Double.parseDouble(empregadoAssalariado.getSalario().replace(",", "."));
        }
        return Double.toString(total).replace(".", ",");
    }

//    public static String calculaSalario(Empregado emp, String data, String inData) {
//
//    }

    public static String retornarMetodo(Empregado e) throws EmpregadoNaoRecebeBancoException {
        if(e.getMetodoPagamento().equals("banco")){
            return e.getBanco(e.getMetodoPagamento()) + ", Ag. " + e.getAgencia(e.getMetodoPagamento()) + " CC " + e.getContaCorrente(e.getMetodoPagamento());
        } else if(e.getMetodoPagamento().equals("correios")){
            return "Correios, " + e.getEndereco();
        } else{
            return "Em maos";
        }
    }

    public static String calculaDescontos(Empregado emp, String data) throws DataInvalidaException, EmpregadoNaoSindicalizadoException, DataInicialInvException, DataIniPostFinException, DataFinalInvException {
        double totalDescontos = 0d;
        LocalDate date = toData(data);
        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");

        if(emp.getSindicalizado()){
            if(emp.getTipo().equals("comissionado")){
                String inData = date.minusDays(13).format(dataFormato);
                totalDescontos += Float.parseFloat(GerenciaSindicato.getTaxasServico(emp.getId(), inData, data).replace(",", "."));
                totalDescontos += Float.parseFloat(GerenciaSindicato.getTaxaSindical(emp.getId()).replace(",", ".")) * 14;
            } else if(emp.getTipo().equals("horista")){
                String inData = date.minusDays(6).format(dataFormato);
                totalDescontos += Double.parseDouble(GerenciaSindicato.getTaxasServico(emp.getId(), inData, data).replace(",", "."));
                totalDescontos += Double.parseDouble(GerenciaSindicato.getTaxaSindical(emp.getId()).replace(",", ".")) * 7;
            } else{
                String inData = date.minusDays(30).format(dataFormato);
                totalDescontos += Double.parseDouble(GerenciaSindicato.getTaxasServico(emp.getId(), inData, data).replace(",", "."));
                totalDescontos += Double.parseDouble(GerenciaSindicato.getTaxaSindical(emp.getId()).replace(",", ".")) * date.lengthOfMonth();
            }
        } else{
            return "0,00";
        }
        return Double.toString(totalDescontos).replace(".", ",");
    }

    public static LinkedHashMap<String, Empregado> ordenaEmpregadosPorNome(LinkedHashMap<String, Empregado> empregados){
        LinkedHashMap<String, Empregado> ordenado = empregados.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(Empregado::getNome)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return ordenado;
    }
}