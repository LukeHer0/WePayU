package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.empregados.comissionado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.empregados.horista.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.AgendaPagamento;
import br.ufal.ic.p2.wepayu.models.Aritmetica;

import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.*;

public class FolhadePagamento {
    private static LinkedHashMap<String, EmpregadoHorista> empregadosHoristas;
    private static LinkedHashMap<String, EmpregadoComissionado> empregadosComissionados;
    private static LinkedHashMap<String, EmpregadoAssalariado> empregadosAssalariados;


    public FolhadePagamento(LinkedHashMap<String, EmpregadoHorista> empregadosHoristas,
                                        LinkedHashMap<String, EmpregadoComissionado> empregadosComissionados,
                                        LinkedHashMap<String, EmpregadoAssalariado> empregadosAssalariados) {
        this.empregadosHoristas = empregadosHoristas;
        this.empregadosComissionados = empregadosComissionados;
        this.empregadosAssalariados = empregadosAssalariados;
    }


    public static void rodaFolha(String data, String saida) throws
            IOException, DataInvalidaException, DataInicialInvException,
            DataIniPostFinException, EmpregadoNaoHoristaException,
            IdNuloException, DataFinalInvException, EmpregadoNaoComissionadoException,
            EmpregadoNaoRecebeBancoException, EmpregadoNaoSindicalizadoException, AgendaInvalidaException {
        String horas, extra, salarioBruto, descontos, salarioLiquido, metodo, fixo, vendas, comissao;
        Integer acumuladoHora = 0, acumuladoExtra = 0;
        Double acumuladoTotal = 0d, acumuladoBruto = 0d, acumuladoDescontos = 0d, acumuladoLiquido = 0d, acumuladoFixo = 0d, acumuladoVendas = 0d, acumuladoComissao = 0d;
        String space = "";
        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
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

        for (Map.Entry<String, EmpregadoHorista> entry : empregadosHoristas.entrySet()) { //for que percorre a lista de empregados
            EmpregadoHorista e = entry.getValue();
            String key = entry.getKey();

            AgendaPagamento pagamento = e.getAgendaPagamento();



            if(pagamento.getTipo().equals("mensal")){
                String inData = datalocal.minusMonths(1).format(dataFormato);
                if(pagamento.getDia() == -1){
                    if(datalocal == datalocal.with(TemporalAdjusters.lastDayOfMonth())){
                        salarioBruto = Aritmetica.calculaSalario(e, data, inData);
                    }
                }
                if(datalocal.getDayOfMonth() == pagamento.getDia()) {
                    salarioBruto = Aritmetica.calculaSalario(e, data, inData);
                }else{
                    continue;
                }
            }else if(pagamento.getTipo().equals("semanal")){
                if(pagamento.getDia() == datalocal.getDayOfWeek().getValue()){
                    if(((ChronoUnit.WEEKS.between(LocalDate.of(2005, 1, 1), datalocal) + 1) % pagamento.getSemana() == 0)){
                        String inData = datalocal.minusDays((7 * pagamento.getSemana()) - 1).format(dataFormato);
                        salarioBruto = Aritmetica.calculaSalario(e, data, inData);
                    }else{
                        continue;
                    }
                }
                else{
                    continue;
                }
            }else{
                throw new AgendaInvalidaException();
            }

            horas = GerenciaEmpregados.getHorasNormaisTrabalhadas(key, Aritmetica.toData(data).minusDays(6).format(dataFormato), data);

            extra = GerenciaEmpregados.getHorasExtrasTrabalhadas(key, Aritmetica.toData(data).minusDays(6).format(dataFormato), data);
//            salarioBruto = Aritmetica.calculaSalario(e, data);
            descontos = Aritmetica.calculaDescontos(e, data);

            salarioLiquido = Double.toString(Double.parseDouble(salarioBruto.replace(',', '.')) - Double.parseDouble(descontos.replace(',', '.')) - e.getDescontoHorista());

            if(Double.parseDouble(salarioLiquido) < 0){
                //as proximas 2 linhas definem descontoHorista = o quanto o salario deste mês ficaria negativado
                e.setDescontoHorista(-e.getDescontoHorista());
                e.setDescontoHorista(Math.abs(Double.parseDouble(salarioLiquido.replace(',', '.'))));
                descontos = salarioBruto;
                salarioLiquido = "0,00";
            } else if(Double.parseDouble(salarioLiquido.replace(',', '.')) == 0) {
                e.setDescontoHorista(-e.getDescontoHorista());
                descontos = salarioLiquido;
            } else {
                descontos = (Double.toString((Double.parseDouble(descontos.replace(',', '.')) + e.getDescontoHorista())));
                e.setDescontoHorista(-e.getDescontoHorista());
            }

            metodo = Aritmetica.retornarMetodo(e);

            acumuladoHora += Integer.parseInt(horas);
            acumuladoExtra += Integer.parseInt(extra);
            acumuladoBruto += Double.parseDouble(salarioBruto.replace(",", "."));
            acumuladoDescontos += Double.parseDouble(descontos.replace(',', '.'));
            acumuladoLiquido += Double.parseDouble(salarioLiquido.replace(',', '.'));

            String printnome = e.getNome();
            //Printa o nome no espaçamento correto.
            while(printnome.length() < 37){
                printnome += " ";
            }bufferedWriter.write(printnome);
            //Printa as horas trabalhadas com o espaçamento correto.
            while((space.length() + horas.length()) < 5){
                space += " ";
            }space += horas + " ";
            bufferedWriter.write(space);
            space = "";
            //Printa as horas extras com o espaçamento correto.
            while((space.length() + extra.length()) < 5){
                space += " ";
            }space += extra + " ";
            bufferedWriter.write(space);
            space = "";
            //Printa o salario bruto formatado corretamente (X,00) e com o espaçamento correto.
            while((space.length() + Aritmetica.doubleFormat(salarioBruto).length()) < 13){
                space += " ";
            }space += Aritmetica.doubleFormat(salarioBruto) + " ";
            bufferedWriter.write(space);
            space = "";
            //Printa os descontos formatados corretamente (X,00) e com o espaçamento correto.
            while ((space.length() + Aritmetica.doubleFormat(descontos).length()) < 9){
                space += " ";
            }space += Aritmetica.doubleFormat(descontos) + " ";
            bufferedWriter.write(space);
            space = "";
            //Printa o salário líquido formatado corretamente (X,00) e com o espaçamento correto.
            while ((space.length() + Aritmetica.doubleFormat(salarioLiquido).length()) < 15){
                space += " ";
            }space += Aritmetica.doubleFormat(salarioLiquido) + " ";
            bufferedWriter.write(space + metodo);
            bufferedWriter.newLine();
            space = "";
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
        while ((space.length() + Aritmetica.doubleFormat(acumuladoBruto.toString()).length()) < 13){
            space += " ";
        }space += Aritmetica.doubleFormat(acumuladoBruto.toString()) + " ";
        bufferedWriter.write(space);
        space = "";
        while ((space.length() + Aritmetica.doubleFormat(acumuladoDescontos.toString()).length()) < 9){
            space += " ";
        }space += Aritmetica.doubleFormat(acumuladoDescontos.toString()) + " ";
        bufferedWriter.write(space);
        space = "";
        while ((space.length() + Aritmetica.doubleFormat(acumuladoLiquido.toString()).length()) < 15){
            space += " ";
        }space += Aritmetica.doubleFormat(acumuladoLiquido.toString()) + " ";
        bufferedWriter.write(space);
        space = "";
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        acumuladoTotal += acumuladoBruto;

        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===================== ASSALARIADOS ============================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("Nome                                             Salario Bruto Descontos Salario Liquido Metodo");
        bufferedWriter.newLine();
        bufferedWriter.write("================================================ ============= ========= =============== ======================================\n");

        acumuladoBruto = 0d;
        acumuladoDescontos = 0d;
        acumuladoLiquido = 0d;

        for (Map.Entry<String, EmpregadoAssalariado> entry : empregadosAssalariados.entrySet()) { //for que percorre a lista de empregados
            Empregado e = entry.getValue();
            String key = entry.getKey();
            AgendaPagamento pagamento = e.getAgendaPagamento();

            if(pagamento.getTipo().equals("mensal")){
                String inData = datalocal.minusMonths(1).format(dataFormato);
                if(pagamento.getDia() == -1){

                    if(datalocal == datalocal.with(TemporalAdjusters.lastDayOfMonth())){
                        salarioBruto = e.getSalario();

                        //System.out.println(salarioBruto);
                    }
                    else
                        continue;
                }
                else {
                    if(datalocal.getDayOfMonth() == pagamento.getDia()) {
                        salarioBruto = e.getSalario();
                    }else{
                        continue;
                    }
                }
            }else if(pagamento.getTipo().equals("semanal")){
                if(pagamento.getDia() == datalocal.getDayOfWeek().getValue()){
                    if(((ChronoUnit.WEEKS.between(LocalDate.of(2005, 1, 1), datalocal) + 1) % pagamento.getSemana() == 0)){
                        String inData = datalocal.minusDays((7 *  pagamento.getSemana()) - 1).format(dataFormato);
                        salarioBruto = Aritmetica.calculaSalario(e, data, inData);
                    }else{
                        continue;
                    }
                }
                else{
                    continue;
                }
            }else{
                throw new AgendaInvalidaException();
            }

            //salarioBruto = Aritmetica.calculaSalario(e, data, inData);
            descontos = Aritmetica.calculaDescontos(e, data);
            salarioLiquido = Double.toString(Double.parseDouble(salarioBruto.replace(',', '.')) - Double.parseDouble(descontos.replace(',', '.'))).replace(".", ",");
            if(Double.parseDouble(salarioLiquido.replace(",", ".")) < 0){
                salarioLiquido = "0,00";
            }metodo = Aritmetica.retornarMetodo(e);

            acumuladoBruto += Double.parseDouble(salarioBruto.replace(",", "."));
            if(acumuladoBruto == 0d){
                acumuladoDescontos = 0d;
            }else{
            acumuladoDescontos += Double.parseDouble(descontos.replace(",", "."));
            }
            acumuladoLiquido += Double.parseDouble(salarioLiquido.replace(",", "."));

//            if(!(salarioBruto.equals("0,00"))){
                String printnome = e.getNome();
                while(printnome.length() < 49){
                    printnome += " ";
                }bufferedWriter.write(printnome);
                while((space.length() + salarioBruto.length()) < 13){
                    space += " ";
                }space += Aritmetica.doubleFormat(salarioBruto) + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + Aritmetica.doubleFormat(descontos).length()) < 9){
                    space += " ";
                }space += Aritmetica.doubleFormat(descontos) + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + salarioLiquido.length()) < 14){
                    space += " ";
                }space += Aritmetica.doubleFormat(salarioLiquido) + " ";
                bufferedWriter.write(space + metodo);
                bufferedWriter.newLine();
                space = "";
//            }
        }
        bufferedWriter.newLine();
        bufferedWriter.write("TOTAL ASSALARIADOS                               ");
        while (space.length() + acumuladoBruto.toString().length() < 12){
            space += " ";
        }space += Aritmetica.doubleFormat(acumuladoBruto.toString()) + " ";
        bufferedWriter.write(space);
        space = "";
        while (space.length() + acumuladoDescontos.toString().length() < 8){
            space += " ";
        }space += Aritmetica.doubleFormat(acumuladoDescontos.toString()) + " ";
        bufferedWriter.write(space);
        space = "";
        while (space.length() + acumuladoLiquido.toString().length() < 14){
            space += " ";
        }space += Aritmetica.doubleFormat(acumuladoLiquido.toString()) + " ";
        bufferedWriter.write(space);
        space = "";
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        acumuladoTotal += acumuladoBruto;

        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===================== COMISSIONADOS ===========================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("Nome                  Fixo     Vendas   Comissao Salario Bruto Descontos Salario Liquido Metodo");
        bufferedWriter.newLine();
        bufferedWriter.write("===================== ======== ======== ======== ============= ========= =============== ======================================\n");

        acumuladoBruto = 0d;
        acumuladoDescontos = 0d;
        acumuladoLiquido = 0d;

        String inData = datalocal.minusDays(13).format(dataFormato);

        for (Map.Entry<String, EmpregadoComissionado> entry : empregadosComissionados.entrySet()) { //for que percorre a lista de empregados
            EmpregadoComissionado e = entry.getValue();
            String key = entry.getKey();
            AgendaPagamento pagamento = e.getAgendaPagamento();
//            boolean vaiSerPago

            if(pagamento.getTipo().equals("mensal")) {
                inData = datalocal.minusMonths(1).format(dataFormato);
                if (pagamento.getDia() == -1) {
                    if (datalocal == datalocal.with(TemporalAdjusters.lastDayOfMonth())) {
                        salarioBruto = Aritmetica.calculaSalario(e, data, inData);
                    }
                    else
                        continue;
                }
                if (pagamento.getDia() == datalocal.getDayOfWeek().getValue())
                    salarioBruto = Aritmetica.calculaSalario(e, data, inData);
                else
                    continue;
            } else if(pagamento.getTipo().equals("semanal")) {
                if (pagamento.getDia() == datalocal.getDayOfWeek().getValue()) {
                    if (((ChronoUnit.WEEKS.between(LocalDate.of(2005, 1, 1), datalocal) + 1) % pagamento.getSemana() == 0)) {
                        inData = datalocal.minusDays((7 * pagamento.getSemana()) - 1).format(dataFormato);
                        salarioBruto = Aritmetica.calculaSalario(e, data, inData);
                    }
                    else
                        continue;
                }
                else
                    continue;
            } else {
                throw new AgendaInvalidaException();
            }


            Double fSalario = Math.floor((((Float.parseFloat(e.getSalario().replace(",", ".")) * 12) / 52) * e.getAgendaPagamento().getSemana()) * 100) / 100;

            fixo = Double.toString(fSalario);
            vendas = GerenciaVendas.getVendasRealizadas(key, inData, data);
            comissao = Double.toString(Math.floor(Double.parseDouble(e.getComissao().replace(',', '.')) * Double.parseDouble(vendas.replace(',', '.'))*100)/100);

            salarioBruto = Aritmetica.calculaSalario(e, data, inData);
            descontos = Aritmetica.calculaDescontos(e, data);
            salarioLiquido = Float.toString(Float.parseFloat(salarioBruto.replace(',', '.')) - Float.parseFloat(descontos.replace(',', '.'))).replace(".", ",");
            if(Double.parseDouble(salarioLiquido.replace(",", ".")) < 0){
                salarioLiquido = "0,00";
            }metodo = Aritmetica.retornarMetodo(e);

            acumuladoFixo += Float.parseFloat((fixo.replace(",", ".")));
            acumuladoVendas += Float.parseFloat(vendas.replace(",", "."));
            acumuladoComissao += Float.parseFloat(comissao.replace(",", "."));
            acumuladoBruto += Float.parseFloat(salarioBruto.replace(",", "."));
            acumuladoDescontos += Float.parseFloat(descontos.replace(",", "."));
            acumuladoLiquido += Float.parseFloat(salarioLiquido.replace(",", "."));

            String printnome = e.getNome();

//            if(!(salarioBruto.equals("0,00"))) {
                while (printnome.length() < 22) {
                    printnome += " ";
                }
                bufferedWriter.write(printnome);
                while ((space.length() + Aritmetica.doubleFormat(fixo).length()) < 8) {
                    space += " ";
                }
                space += Aritmetica.doubleFormat(fixo) + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + Aritmetica.doubleFormat(vendas).length()) < 8) {
                    space += " ";
                }
                space += Aritmetica.doubleFormat(vendas) + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + Aritmetica.doubleFormat(comissao).length()) < 8) {
                    space += " ";
                }
                space += Aritmetica.doubleFormat(comissao) + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + Aritmetica.doubleFormat(salarioBruto).length()) < 13) {
                    space += " ";
                }
                space += Aritmetica.doubleFormat(salarioBruto) + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + Aritmetica.doubleFormat(descontos).length()) < 9) {
                    space += " ";
                }
                space += Aritmetica.doubleFormat(descontos) + " ";
                bufferedWriter.write(space);
                space = "";
                while ((space.length() + Aritmetica.doubleFormat(salarioLiquido).length()) < 15) {
                    space += " ";
                }
                space += Aritmetica.doubleFormat(salarioLiquido) + " ";
                bufferedWriter.write(space + metodo);
                space = "";
                bufferedWriter.newLine();
//            }
        }

        bufferedWriter.newLine();
        bufferedWriter.write("TOTAL COMISSIONADOS   ");
        while ((space.length() + Aritmetica.doubleFormat(acumuladoFixo.toString()).length()) < 8){
            space += " ";
        }space+= Aritmetica.doubleFormat(acumuladoFixo.toString()) + " ";
        bufferedWriter.write(space);
        space= "";
        while (space.length() + Aritmetica.doubleFormat(acumuladoVendas.toString()).length() < 8){
            space += " ";
        }space += Aritmetica.doubleFormat(acumuladoVendas.toString()) + " ";
        bufferedWriter.write(space);
        space = "";
        while (space.length() + Aritmetica.doubleFormat(acumuladoComissao.toString()).length() < 8){
            space += " ";
        }space += Aritmetica.doubleFormat(acumuladoComissao.toString()) + " ";
        bufferedWriter.write(space);
        space = "";
        while (space.length() + Aritmetica.doubleFormat(acumuladoBruto.toString()).length() < 13){
            space += " ";
        }space += Aritmetica.doubleFormat(acumuladoBruto.toString()) + " ";
        bufferedWriter.write(space);
        space = "";
        while (space.length() + Aritmetica.doubleFormat(acumuladoDescontos.toString()).length() < 9){
            space += " ";
        }space += Aritmetica.doubleFormat(acumuladoDescontos.toString()) + " ";
        bufferedWriter.write(space);
        space = "";
        while (space.length() + Aritmetica.doubleFormat(acumuladoLiquido.toString()).length() < 15){
            space += " ";
        }space += Aritmetica.doubleFormat(acumuladoLiquido.toString()) + " ";
        bufferedWriter.write(space);
        space = "";
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        acumuladoTotal += acumuladoBruto;

        bufferedWriter.write("TOTAL FOLHA: ");
        bufferedWriter.write(Aritmetica.doubleFormat(totalSalario(data)));
        //bufferedWriter.write(Aritmetica.doubleFormat(acumuladoTotal.toString()));
        bufferedWriter.newLine();

        //Libera a escrita no arquivo e fecha o mesmo
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static String totalSalario(String data) throws DataInvalidaException, DataInicialInvException, //retorna o quanto a empresa ira pagar numa determinada data
            DataIniPostFinException, EmpregadoNaoHoristaException, IdNuloException, DataFinalInvException, EmpregadoNaoComissionadoException {
        LocalDate date = Aritmetica.toData(data);
        double acumulado = 0d;
        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");

        for (Map.Entry<String, EmpregadoHorista> e : empregadosHoristas.entrySet()) {
            AgendaPagamento pagamento = e.getValue().getAgendaPagamento();
            //ArrayList<String> elementos = new ArrayList<>(Arrays.stream(pagamento.split(" ")).toList());

            if(pagamento.getTipo().equals("mensal")){
                String inData = date.minusMonths(1).format(dataFormato);
                if(pagamento.getDia() == -1){
                    if(date == date.with(TemporalAdjusters.lastDayOfMonth())){
                        acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data, inData).replace(",", "."));
                    }
                }
                if(date.getDayOfMonth() == pagamento.getDia()) {
                    acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data, inData).replace(",", "."));
                }
            }else if(pagamento.getTipo().equals("semanal")){
                if(pagamento.getDia() == date.getDayOfWeek().getValue()){
                    if(((ChronoUnit.WEEKS.between(LocalDate.of(2005, 1, 1), date) + 1) % pagamento.getSemana() == 0)){
                            String inData = date.minusDays((7 * pagamento.getSemana()) - 1).format(dataFormato);
                            acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data, inData).replace(",", "."));
                    }
                }
            }

//            if (date.getDayOfWeek() != DayOfWeek.FRIDAY) {
////                break;
//            } else {
//                acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data).replace(",", "."));
//            }
        }

        for (Map.Entry<String, EmpregadoComissionado> e : empregadosComissionados.entrySet()){
            AgendaPagamento pagamento = e.getValue().getAgendaPagamento();

            if(pagamento.getTipo().equals("mensal")){
                String inData = date.minusMonths(1).format(dataFormato);
                if(pagamento.getDia() == -1){
                    if(date == date.with(TemporalAdjusters.lastDayOfMonth())){
                        acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data, inData).replace(",", "."));
                    }
                }
                if(date.getDayOfMonth() == pagamento.getDia()) {
                    acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data, inData).replace(",", "."));
                }
            }else if(pagamento.getTipo().equals("semanal")){
                if(pagamento.getDia() == date.getDayOfWeek().getValue()){
                    if(((ChronoUnit.WEEKS.between(LocalDate.of(2005, 1, 1), date) + 1) % pagamento.getSemana() == 0)){
                        String inData = date.minusDays((7 * pagamento.getSemana()) - 1).format(dataFormato);
                        acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data, inData).replace(",","."));
                    }
                }
            }

//            if(date.getDayOfWeek() != DayOfWeek.FRIDAY){
////                break;
//            }
//            LocalDate primeiroDia = LocalDate.of(2005,1,1);
//            long diferenca = ChronoUnit.DAYS.between(primeiroDia, date) + 1;
//
//            if(diferenca % 14 == 0){
//                acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data).replace(",", "."));
//            }else{
////                break;
//            }
        }
        for (Map.Entry<String, EmpregadoAssalariado> e : empregadosAssalariados.entrySet()){
            AgendaPagamento pagamento = e.getValue().getAgendaPagamento();

            if(pagamento.getTipo().equals("mensal")){
                String inData = date.minusMonths(1).format(dataFormato);
                if(pagamento.getDia() == -1){
                    if(date == date.with(TemporalAdjusters.lastDayOfMonth())){
                        acumulado += Double.parseDouble(e.getValue().getSalario().replace(',', '.'));
                    }
                }
                if(date.getDayOfMonth() == pagamento.getDia()) {
                    acumulado += Double.parseDouble(e.getValue().getSalario().replace(',', '.'));
                }
            }else if(pagamento.getTipo().equals("semanal")){
                if(pagamento.getDia() == date.getDayOfWeek().getValue()){
                    if(((ChronoUnit.WEEKS.between(LocalDate.of(2005, 1, 1), date) + 1) % pagamento.getSemana() == 0)){
                        String inData = date.minusDays((7 * pagamento.getSemana()) - 1).format(dataFormato);
                        acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data, inData).replace(",","."));
                    }
                }
            }

//            if(date.getDayOfMonth() == date.lengthOfMonth()){
//                acumulado += Double.parseDouble(Aritmetica.calculaSalario(e.getValue(), data).replace(",", "."));
//            }else {
////                break;
//            }
        }return Double.toString(acumulado);
    }

    public static String totalFolha (String data) throws Exception {
        GerenciaEmpregados.empregados = Aritmetica.ordenaEmpregadosPorNome(GerenciaEmpregados.empregados);
        empregadosHoristas = GerenciaEmpregados.getEmpregadosHoristas();
        empregadosComissionados = GerenciaEmpregados.getEmpregadosComissionados();
        empregadosAssalariados = GerenciaEmpregados.getEmpregadosAssalariados();

        return Aritmetica.doubleFormat(totalSalario(data));
    }
}
