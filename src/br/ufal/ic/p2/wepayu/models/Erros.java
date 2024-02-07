package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados;
import br.ufal.ic.p2.wepayu.gerencia.GerenciaSindicato;
import br.ufal.ic.p2.wepayu.sindicato.MembroSindicato;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Erros {
    private static Aritmetica aritmetica = new Aritmetica();
    public void conferirErros(String nome, String endereco, String tipo, String salario) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, TipoNAplicavelException{
        if (salario.isEmpty()) {
            throw new SalarioNuloException();
        } else if (!aritmetica.isNumeric(salario.replace(',', '.'))) {
            throw new SalarioNumericoException();
        } else if (nome.isEmpty()) {
            throw new NomeNuloException();
        } else if (endereco.isEmpty()) {
            throw new EnderecoNuloException();
        }  else if (!(Arrays.asList("assalariado", "horista", "comissionado").contains(tipo))) {
            throw new TipoInvalidoException();
        } else if (Double.parseDouble(salario.replace(',', '.')) < 0) {
            throw new SalarioNegativoException();
        } else if(Objects.equals(tipo, "comissionado")) {
            throw new TipoNAplicavelException();
        }
    }

    public void conferirErros (String nome, String endereco, String tipo, String salario, String comissao) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, ComissaoNulaException, ComissaoNumericaException,
            ComissaoNegativaException{
        if (nome.isEmpty()) {
            throw new NomeNuloException();
        } else if(comissao.isEmpty()) {
            throw new ComissaoNulaException();
        } else if(!(aritmetica.isNumeric(comissao))){
            throw new ComissaoNumericaException();
        } else if (!aritmetica.isNumeric(salario)) {
            throw new SalarioNumericoException();
        }else if(Double.parseDouble(comissao.replace(",", ".")) < 0) {
            throw new ComissaoNegativaException();
        }  else if (endereco.isEmpty()) {
            throw new EnderecoNuloException();
        } else if (salario.isEmpty()) {
            throw new SalarioNuloException();
        }else if (!(Arrays.asList("assalariado", "horista", "comissionado").contains(tipo))) {
            throw new TipoInvalidoException();
        } else if (Double.parseDouble(salario.replace(',', '.')) < 0) {
            throw new SalarioNegativoException();
        }
    }

    public static boolean verificarIdSindicato(String idSindicato) {
        for(Map.Entry<String, MembroSindicato> entry : GerenciaSindicato.empregadosSindicalizados.entrySet()) {
            String membro = entry.getKey();
            if(membro.equals(idSindicato)){
                return false;
            }
        }
        return true;
    }

    public static boolean confereData(String data){
        int d = 0, m = 0, y, i = 0;

        for (String s : data.split("/")) {
            if (i == 0) {
                d = Integer.parseInt(s);
                i++;
            } else if (i == 1) {
                m = Integer.parseInt(s);
                i++;
            } else {
                y = Integer.parseInt(s);
            }
        }
        if(d > 31){
            return false;
        }else if (m == 2 && d > 29){
            return false;
        }else if (m > 12){
            return false;
        }else {
            return true;
        }
    }

    public static void conferirErrosGetAtributo(String emp, String atributo)
            throws IdNuloException, EmpregadoNaoExisteException, AtributoNExisteException,
            EmpregadoNaoComissionadoException {
        if (Objects.equals(emp,"")) {
            throw new IdNuloException();
        } else if (!GerenciaEmpregados.empregados.containsKey(emp)){
            throw new EmpregadoNaoExisteException();
        } else if (!(Arrays.asList("nome", "endereco", "tipo", "salario", "sindicalizado", "comissao").contains(atributo))){
            throw new AtributoNExisteException();
        } else if (!(GerenciaEmpregados.empregados.get(emp).getTipo().equals("comissionado")) && atributo.equals("comissao")){
            throw new EmpregadoNaoComissionadoException();
        }
    }
}