package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.empregados.comissionado.CartaoVenda;
import br.ufal.ic.p2.wepayu.empregados.comissionado.EmpregadoComissionado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados.empregados;

public class GerenciaVendas {
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
            DateTimeFormatter dataa = DateTimeFormatter.ofPattern("d/M/yyyy");
            date = LocalDate.parse(data, dataa);
        } catch (Exception e) {
            throw new DataInvalidaException();
        }
        empregado.setVenda(new CartaoVenda(date, valorDouble));
    }
}
