package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.XMLUse.XMLUse;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.models.Aritmetica;
import br.ufal.ic.p2.wepayu.models.Erros;
import br.ufal.ic.p2.wepayu.sindicato.MembroSindicato;
import br.ufal.ic.p2.wepayu.sindicato.TaxaServico;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;
import java.util.Map;

public class GerenciaSindicato {

    public static HashMap<String, MembroSindicato> empregadosSindicalizados = XMLUse.carregarMembroSindicatoXML("./listaMembrosSindicato.xml");

    public static void lancaTaxaServico(String membro, String data, String valor) throws
            IdMembroNuloException, MembroNaoExisteException, DataInvalidaException,
            ValorPositivoException {
        double valorDouble = Double.parseDouble(valor.replace(",", "."));
        //System.out.println("Valor:" + valorDouble);

        if (Objects.equals(membro, "")) {
            throw new IdMembroNuloException();
        } else if (!empregadosSindicalizados.containsKey(membro)) {
            throw new MembroNaoExisteException();
        } else if (valorDouble <= 0) {
            throw new ValorPositivoException();
        }

        MembroSindicato membroSindicato = empregadosSindicalizados.get(membro);
        XMLUse.salvaMembroSindicatoXML(empregadosSindicalizados, "./listaMembrosSindicato.xml");

        LocalDate date;
        try {
            DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
            date = LocalDate.parse(data, dataFormato);
        } catch (Exception e) {
            throw new DataInvalidaException();
        }
        TaxaServico taxa = new TaxaServico.TaxaServicoBuilder()
                .data(data)
                .valor(valorDouble)
                .build();
        membroSindicato.setTaxasServicos(taxa);
    }

    public static String getTaxasServico(String emp, String dataInicial, String dataFinal) throws
            DataInvalidaException, EmpregadoNaoSindicalizadoException, DataIniPostFinException,
            DataFinalInvException, DataInicialInvException {

        LocalDate dateDataInicial, dateDataFinal;

        if(Erros.confereData(dataInicial)){
            dateDataInicial = Aritmetica.toData(dataInicial);
        } else {
            throw new DataInicialInvException();
        }

        if(Erros.confereData(dataFinal)){
            dateDataFinal = Aritmetica.toData(dataFinal);
        }else {
            throw new DataFinalInvException();
        }

        if(dateDataInicial.equals(dateDataFinal)){
            return "0,00";
        }else if(dateDataInicial.isAfter(dateDataFinal)){
            throw new DataIniPostFinException();
        }
        double taxas = 0d;

        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");

        for(Map.Entry<String, MembroSindicato> entry: empregadosSindicalizados.entrySet()){
            MembroSindicato membro = entry.getValue();

            if(membro.getEmpregadoId().equals(emp)){

                if(membro.taxasServicos.isEmpty()){
                    return "0,00";
                }
                else {
                    for(TaxaServico taxa: membro.taxasServicos){
                        LocalDate dataTaxa = LocalDate.parse(taxa.getData(), dataFormato);

                        if((dataTaxa.isAfter(dateDataInicial) || dataTaxa.isEqual(dateDataInicial))
                                && dataTaxa.isBefore(dateDataFinal)) {
                            taxas += taxa.getValor();

                        }
                    }
                    return String.format("%.2f", taxas).replace(".", ",");
                }
            }
        }
        throw new EmpregadoNaoSindicalizadoException();
    }

    public static String getIdSindicato(String emp)throws
            EmpregadoNaoSindicalizadoException{
        for(Map.Entry<String, MembroSindicato> entry: empregadosSindicalizados.entrySet()){
            MembroSindicato membro = entry.getValue();
            if(membro.getEmpregadoId().equals(emp)){
                return entry.getKey();
            }
        }throw new EmpregadoNaoSindicalizadoException();
    }

    public static String getTaxaSindical(String emp)throws
            EmpregadoNaoSindicalizadoException{
        for(Map.Entry<String, MembroSindicato> entry: empregadosSindicalizados.entrySet()){
            MembroSindicato membro = entry.getValue();
            if(membro.getEmpregadoId().equals(emp)){
                return Aritmetica.doubleFormat(membro.getTaxaSindical());
            }
        }
        throw new EmpregadoNaoSindicalizadoException();
    }
}
