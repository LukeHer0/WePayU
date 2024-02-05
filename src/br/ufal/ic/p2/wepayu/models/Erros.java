package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.*;

import java.util.Arrays;
import java.util.Objects;

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
}