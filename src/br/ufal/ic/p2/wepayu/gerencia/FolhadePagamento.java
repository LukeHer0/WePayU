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
        String horas, extra, salarioBruto, descontos, salarioLiquido, metodo, fixo, vendas, comissao;
        Integer acumuladoHora = 0, acumuladoExtra = 0;
        Double acumuladoBruto = 0d, acumuladoDescontos = 0d, acumuladoLiquido = 0d, acumuladoFixo = 0d, acumuladoVendas = 0d, acumuladoComissao = 0d;
        String space = "";
        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
        ArrayList<String> horistas = new ArrayList<String>();
        ArrayList<String> comissionados = new ArrayList<String>();
        ArrayList<String> assalariados = new ArrayList<String>();
        LocalDate datalocal = Aritmetica.toData(data);
        LocalDate primeiroDia = LocalDate.of(2005,1,1);
        long diferenca = ChronoUnit.DAYS.between(primeiroDia, datalocal) + 1;

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

        if(datalocal.getDayOfWeek() == DayOfWeek.FRIDAY) {
            for (Map.Entry<String, EmpregadoHorista> entry : empregadosHoristas.entrySet()) { //for que percorre a lista de empregados
                Empregado e = entry.getValue();
                String key = entry.getKey();

                horas = GerenciaEmpregados.getHorasNormaisTrabalhadas(key, Aritmetica.toData(data).minusDays(6).format(dataFormato), data);
                //System.out.println(horas);
                extra = GerenciaEmpregados.getHorasExtrasTrabalhadas(key, Aritmetica.toData(data).minusDays(6).format(dataFormato), data);
                salarioBruto = Aritmetica.calculaSalario(e, data);
                descontos = Aritmetica.calculaDescontos(e, data);
                salarioLiquido = Double.toString(Double.parseDouble(salarioBruto.replace(',', '.')) - Double.parseDouble(descontos.replace(',', '.'))).replace(".", ",");
                metodo = Aritmetica.retornarMetodo(e);

                acumuladoHora += Integer.parseInt(horas);
                acumuladoExtra += Integer.parseInt(extra);
                acumuladoBruto += Double.parseDouble(salarioBruto.replace(",", "."));
                acumuladoDescontos += Double.parseDouble(descontos.replace(",", "."));
                acumuladoLiquido += Double.parseDouble(salarioLiquido.replace(",", "."));
                String printnome = e.getNome();

                while(printnome.length() < 37){
                    printnome += " ";
                }bufferedWriter.write(printnome);
                while((space.length() + horas.length()) < 5){
                    space += " ";
                }space += horas + " ";
                bufferedWriter.write(space);
                space = "";
                while((space.length() + extra.length()) < 5){
                    space += " ";
                }space += extra + " ";
                bufferedWriter.write(space);
                space = "";
                while((space.length() + salarioBruto.length()) < 13){
                    space += " ";
                }space += salarioBruto + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + descontos.length()) < 9){
                    space += " ";
                }space += descontos + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + salarioLiquido.length()) < 15){
                    space += " ";
                }space += salarioLiquido + " ";
                bufferedWriter.write(space + metodo);
                bufferedWriter.newLine();
                space = "";
                horistas.add(e.getNome() + "\t\t" + horas + "\t\t" + extra + "\t\t" + salarioBruto + "\t\t" + descontos + "\t\t" + salarioLiquido + "\t\t" + metodo);
            }
        }
        bufferedWriter.newLine();
        bufferedWriter.write("TOTAL HORISTAS                       ");
        while ((space.length() + acumuladoHora.toString().length()) < 5){
            space += " ";
        }space += acumuladoHora.toString() + " ";
        bufferedWriter.write(space);
        space = "";
        while ((space.length() + acumuladoExtra.toString().length()) < 5){
            space += " ";
        }space += acumuladoExtra.toString() + " ";
        bufferedWriter.write(space);
        space = "";
        while ((space.length() + acumuladoBruto.toString().length()) < 13){
            space += " ";
        }space += acumuladoBruto.toString().replace(".", ",") + " ";
        bufferedWriter.write(space);
        space = "";
        while ((space.length() + acumuladoDescontos.toString().length()) < 9){
            space += " ";
        }space += acumuladoDescontos.toString().replace(".", ",") + " ";
        bufferedWriter.write(space);
        space = "";
        while ((space.length() + acumuladoLiquido.toString().length()) < 15){
            space += " ";
        }space += acumuladoLiquido.toString().replace(".", ",") + " ";
        bufferedWriter.write(space);
        space = "";
        bufferedWriter.write("\n");

        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===================== ASSALARIADOS ============================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("Nome                                             Salario Bruto Descontos Salario Liquido Metodo");
        bufferedWriter.newLine();
        bufferedWriter.write("================================================ ============= ========= =============== ======================================\n");

        if(datalocal.getDayOfMonth() == datalocal.lengthOfMonth()) {
            for (Map.Entry<String, EmpregadoAssalariado> entry : empregadosAssalariados.entrySet()) { //for que percorre a lista de empregados
                Empregado e = entry.getValue();
                String key = entry.getKey();

                salarioBruto = Aritmetica.calculaSalario(e, data);
                descontos = Aritmetica.calculaDescontos(e, data);
                salarioLiquido = Double.toString(Double.parseDouble(salarioBruto.replace(',', '.')) - Double.parseDouble(descontos.replace(',', '.'))).replace(",", ".");
                metodo = Aritmetica.retornarMetodo(e);

                String printnome = e.getNome();
//                String space = "";
                while(printnome.length() < 49){
                    printnome += " ";
                }bufferedWriter.write(printnome);
                while((space.length() + salarioBruto.length()) < 13){
                    space += " ";
                }space += salarioBruto + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + descontos.length()) < 9){
                    space += " ";
                }space += descontos + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + salarioLiquido.length()) < 15){
                    space += " ";
                }space += salarioLiquido + " ";
                bufferedWriter.write(space + metodo);
                bufferedWriter.newLine();
                assalariados.add(e.getNome() + "\t\t" + salarioBruto + "\t\t" + descontos + "\t\t" + salarioLiquido + "\t\t" + metodo);
            }
        }
        bufferedWriter.newLine();
        bufferedWriter.write("TOTAL ASSALARIADOS                                        0,00      0,00            0,00\n");
        bufferedWriter.newLine();

        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===================== COMISSIONADOS ===========================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("Nome                  Fixo     Vendas   Comissao Salario Bruto Descontos Salario Liquido Metodo");
        bufferedWriter.newLine();
        bufferedWriter.write("===================== ======== ======== ======== ============= ========= =============== ======================================\n");

        if (diferenca % 14 == 0) {
            for (Map.Entry<String, EmpregadoComissionado> entry : empregadosComissionados.entrySet()) { //for que percorre a lista de empregados
                Empregado e = entry.getValue();
                String key = entry.getKey();

                fixo = Aritmetica.calculaSalario(e, data);
                vendas = Aritmetica.calculaSalario(e, data);
                comissao = e.getComissao();
                salarioBruto = Aritmetica.calculaSalario(e, data);
                descontos = Aritmetica.calculaDescontos(e, data);
                salarioLiquido = Double.toString(Double.parseDouble(salarioBruto.replace(',', '.')) - Double.parseDouble(descontos.replace(',', '.'))).replace(",", ".");
                metodo = Aritmetica.retornarMetodo(e);

                String printnome = e.getNome();
//                String space = "";
                while(printnome.length() < 22){
                    printnome += " ";
                }bufferedWriter.write(printnome);
                while((space.length() + fixo.length()) < 8){
                    space += " ";
                }space += fixo + " ";
                bufferedWriter.write(space);
                space = "";
                while((space.length() + vendas.length()) < 8){
                    space += " ";
                }space += vendas + " ";
                bufferedWriter.write(space);
                space = "";
                while((space.length() + comissao.length()) < 8){
                    space += " ";
                }space += comissao + " ";
                bufferedWriter.write(space);
                space = "";
                while((space.length() + salarioBruto.length()) < 13){
                    space += " ";
                }space += salarioBruto + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + descontos.length()) < 9){
                    space += " ";
                }space += descontos + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + salarioLiquido.length()) < 15){
                    space += " ";
                }space += salarioLiquido + " ";
                bufferedWriter.write(space + metodo);
                bufferedWriter.newLine();

                comissionados.add(e.getNome() + "\t\t" + fixo + "\t\t" + vendas + "\t\t" + comissao + "\t\t" + salarioBruto + "\t\t" + descontos + "\t\t" + salarioLiquido + "\t\t" + metodo);
            }
        }
        bufferedWriter.newLine();
        bufferedWriter.write("TOTAL COMISSIONADOS       0,00     0,00     0,00          0,00      0,00            0,00\n");
        bufferedWriter.newLine();

        bufferedWriter.write("TOTAL FOLHA: 0,00");
        bufferedWriter.newLine();
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
