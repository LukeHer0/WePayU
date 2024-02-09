package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
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

public class GerenciaSindicato implements Serializable {

    public static HashMap<String, MembroSindicato> empregadosSindicalizados = new HashMap<>();

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

        LocalDate date;
        try {
            DateTimeFormatter dataa = DateTimeFormatter.ofPattern("d/M/yyyy");
            date = LocalDate.parse(data, dataa);
        } catch (Exception e) {
            throw new DataInvalidaException();
        }
        TaxaServico taxa = new TaxaServico.TaxaServicoBuilder()
                .data(date)
                .valor(valorDouble)
                .build();
        membroSindicato.setTaxas(taxa);
    }

    public static String getTaxasServico(String emp, String dataIncial, String dataFinal) throws
            DataInvalidaException, EmpregadoNaoSindicalizadoException, DataIniPostFinException,
            DataFinalInvException, DataInicialInvException {
        LocalDate dateDataInicial, dateDataFinal;

        if(Erros.confereData(dataIncial)){
            dateDataInicial = Aritmetica.toData(dataIncial);
        } else {
            throw new DataInicialInvException();
        }


        if(Erros.confereData(dataFinal)){
            dateDataFinal = Aritmetica.toData(dataFinal);
        }else {
            throw new DataFinalInvException();
        }


        double taxas = 0;

        if(dateDataInicial.equals(dateDataFinal)){
            return "0,00";
        }else if(dateDataInicial.isAfter(dateDataFinal)){
            throw new DataIniPostFinException();
        }

        for(Map.Entry<String, MembroSindicato> entry: empregadosSindicalizados.entrySet()){
            MembroSindicato membro = entry.getValue();
            if(membro.getEmpregado().equals(emp)){
                if(membro.taxasServicos == null){
                    return "0,00";
                }
                else {
                    for(TaxaServico taxa: membro.taxasServicos){
                        LocalDate dataTaxa = taxa.getData();
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
            if(membro.getEmpregado().equals(emp)){
                return entry.getKey();
            }
        }
        throw new EmpregadoNaoSindicalizadoException();
    }

    public static String getTaxaSindical(String emp)throws
            EmpregadoNaoSindicalizadoException{
        for(Map.Entry<String, MembroSindicato> entry: empregadosSindicalizados.entrySet()){
            MembroSindicato membro = entry.getValue();
            if(membro.getEmpregado().equals(emp)){
                return Aritmetica.doubleFormat(membro.getTaxaSindical());
            }
        }
        throw new EmpregadoNaoSindicalizadoException();
    }
}
