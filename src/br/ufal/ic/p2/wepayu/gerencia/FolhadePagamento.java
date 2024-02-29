package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.empregados.comissionado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.empregados.horista.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Aritmetica;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

//    private static String dadosFolha(Empregado e, String data) throws DataInicialInvException, DataIniPostFinException, EmpregadoNaoHoristaException, DataInvalidaException, IdNuloException, EmpregadoNaoComissionadoException, DataFinalInvException, EmpregadoNaoSindicalizadoException, EmpregadoNaoRecebeBancoException {
//        
//
//        if(e.getTipo().equals("horista")){
//            horas = GerenciaEmpregados.getHorasNormaisTrabalhadas(e.getId(), Aritmetica.toData(data).minusDays(7).toString(), data);
//            extra = GerenciaEmpregados.getHorasExtrasTrabalhadas(e.getId(), Aritmetica.toData(data).minusDays(7).toString(), data);
//            salarioBruto = Aritmetica.calculaSalario(e, data);
//            descontos = Aritmetica.calculaDescontos(e, data);
//            salarioLiquido = Double.toString(Double.parseDouble(salarioBruto) - Double.parseDouble(descontos)).replace(",", ".");
//            metodo = Aritmetica.retornarMetodo(e);
//
//            return e.getNome() + horas  + extra + salarioBruto + descontos + salarioLiquido + metodo;
//        } else if(e.getTipo().equals("comissionado")){
//
//        } else{
//
//        }
//        return "";
//    }

    public static void rodaFolha(String data, String saida) throws
            IOException, DataInvalidaException, DataInicialInvException,
            DataIniPostFinException, EmpregadoNaoHoristaException,
            IdNuloException, DataFinalInvException, EmpregadoNaoComissionadoException,
            EmpregadoNaoRecebeBancoException, EmpregadoNaoSindicalizadoException {
        //System.out.println("\n" + data + "\n");
        String horas, extra, salarioBruto, descontos, salarioLiquido, metodo;
        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
        ArrayList<String> horistas = new ArrayList<String>();
        ArrayList<String> comissionados = new ArrayList<String>();
        ArrayList<String> assalariados = new ArrayList<String>();


        
        OutputStream outputStream = new FileOutputStream(saida);
        Writer writer = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        bufferedWriter.write("FOLHA DE PAGAMENTO DO DIA " + Aritmetica.toData(data));
        bufferedWriter.newLine();
        bufferedWriter.write("====================================");
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===================== HORISTAS ================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("Nome                                 Horas Extra Salario Bruto Descontos Salario Liquido Metodo");
        bufferedWriter.newLine();
        bufferedWriter.write("==================================== ===== ===== ============= ========= =============== ======================================\n");
        bufferedWriter.newLine();

        for (Map.Entry<String, EmpregadoHorista> entry : empregadosHoristas.entrySet()) { //for que percorre a lista de empregados
            Empregado e = entry.getValue();
            String key = entry.getKey();


            horas = GerenciaEmpregados.getHorasNormaisTrabalhadas(key, Aritmetica.toData(data).minusDays(6).format(dataFormato), data);
            //System.out.println(horas);
            extra = GerenciaEmpregados.getHorasExtrasTrabalhadas(key, Aritmetica.toData(data).minusDays(6).format(dataFormato), data);
            salarioBruto = Aritmetica.calculaSalario(e, data);
            descontos = Aritmetica.calculaDescontos(e, data);
            salarioLiquido = Double.toString(Double.parseDouble(salarioBruto.replace(',', '.')) - Double.parseDouble(descontos.replace(',', '.'))).replace(",", ".");
            metodo = Aritmetica.retornarMetodo(e);
            System.out.println("aaaaaa");
            bufferedWriter.write(e.getNome() + "\t\t" + horas + "\t\t" + extra + "\t\t" + salarioBruto + "\t\t" + descontos + "\t\t" + salarioLiquido + "\t\t" + metodo);
            //bufferedWriter.newLine();
            //horistas.add(e.getNome() + "\t\t" + horas + "\t\t" + extra + "\t\t" + salarioBruto + "\t\t" + descontos + "\t\t" + salarioLiquido + "\t\t" + metodo);
        }

//        for (String linha: horistas) {
//            bufferedWriter.write(linha);
//            bufferedWriter.newLine();
//        }

        //Libera a escrita no arquivo e fecha o mesmo
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static String totalSalario(String data) throws DataInvalidaException, DataInicialInvException, //retorna o quanto a empresa ira pagar numa determinada data
            DataIniPostFinException, EmpregadoNaoHoristaException, IdNuloException, DataFinalInvException, EmpregadoNaoComissionadoException {
        LocalDate date = Aritmetica.toData(data);
        double acumulado = 0d;
        for (Map.Entry<String, EmpregadoHorista> e : empregadosHoristas.entrySet()) {
            if (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
                break;
            } else {
//                System.out.println(Aritmetica.calculaSalario(e.getValue(), data).replace(",", "."));
                acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data).replace(",", "."));
            }
        }

        for (Map.Entry<String, EmpregadoComissionado> e : empregadosComissionados.entrySet()){

            if(date.getDayOfWeek() != DayOfWeek.FRIDAY){
                break;
            }

            LocalDate primeiroDia = LocalDate.of(2005,1,1);
            long diferenca = ChronoUnit.DAYS.between(primeiroDia, date) + 1;

            if(diferenca % 14 == 0){
                acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data).replace(",", "."));
            }else{
                break;
            }
        }
        for (Map.Entry<String, EmpregadoAssalariado> e : empregadosAssalariados.entrySet()){

            if(date.getDayOfMonth() == date.lengthOfMonth()){
                acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data).replace(",", "."));
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
