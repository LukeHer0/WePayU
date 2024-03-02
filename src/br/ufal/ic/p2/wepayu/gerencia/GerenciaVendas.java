package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.XMLUse.XMLUse;
import br.ufal.ic.p2.wepayu.empregados.comissionado.CartaoVenda;
import br.ufal.ic.p2.wepayu.empregados.comissionado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Erros;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados.empregados;

public class GerenciaVendas implements Serializable {
    public static void lancaVenda(String emp, String data, String valor) throws
            IdNuloException, EmpregadoNaoExisteException, EmpregadoNaoComissionadoException,
            ValorPositivoException, DataInvalidaException {
        double valorDouble = Double.parseDouble(valor.replace(",", "."));
        if (Objects.equals(emp, "")) {
            throw new IdNuloException();
        } else if (!empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException();
        } else if (!Objects.equals(empregados.get(emp).getTipo(), "comissionado")) {
            throw new EmpregadoNaoComissionadoException();
        } else if (valorDouble <= 0) {
            throw new ValorPositivoException();
        }
        EmpregadoComissionado empregado = (EmpregadoComissionado) empregados.get(emp);
        LocalDate date;
        try {
            DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
            date = LocalDate.parse(data, dataFormato);
        } catch (Exception e) {
            throw new DataInvalidaException();
        }
        empregado.setCartaoVenda(new CartaoVenda(data, valorDouble));
        XMLUse.salvaEmpregadosXML(empregados, "./listaEmpregados.xml");
    }

    public static String getVendasRealizadas (String emp, String dataInicial, String dataFinal) throws IdNuloException, EmpregadoNaoComissionadoException, //calcula as vendas de um empregado comissionado
            DataInicialInvException, DataFinalInvException, DataIniPostFinException{
        if(emp == null){
            throw new IdNuloException();
        }if(!(empregados.get(emp).getTipo().equals("comissionado"))){
            throw new EmpregadoNaoComissionadoException();
        }
        EmpregadoComissionado empregado = (EmpregadoComissionado) empregados.get(emp);
        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate Inicial, Final;
        if(!(Erros.confereData(dataInicial))){
            throw new DataInicialInvException();
        }
        Inicial = LocalDate.parse(dataInicial, dataFormato);

        if(!(Erros.confereData(dataFinal))){
            throw new DataFinalInvException();
        }
        Final = LocalDate.parse(dataFinal,dataFormato);
        double acumulador = 0;
        if(Inicial.equals(Final)){
            return "0,00";
        }else if(Inicial.isAfter(Final)) {
            throw new DataIniPostFinException();
        } else{
            if(empregado.cartaoVenda == null){
                return "0";
            }
            for(CartaoVenda c : empregado.cartaoVenda){
                if(LocalDate.parse(c.getData(), dataFormato).isEqual(Inicial) || (LocalDate.parse(c.getData(), dataFormato).isAfter(Inicial) && LocalDate.parse(c.getData(), dataFormato).isBefore(Final))){
                    //System.out.println("Valor atual: " + acumulador + " | Próxima venda: " + c.getValor());
                    acumulador += c.getValor();
                }
            }
            //System.out.println("Valor total de vendas: " + acumulador + "\n\n");
        }
        XMLUse.salvaEmpregadosXML(empregados, "./listaEmpregados.xml");
        return String.format("%.2f", acumulador).replace(".", ",");
    }
}