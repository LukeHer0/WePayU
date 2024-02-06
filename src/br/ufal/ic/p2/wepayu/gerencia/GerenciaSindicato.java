package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;
import br.ufal.ic.p2.wepayu.Exception.IdMembroNuloException;
import br.ufal.ic.p2.wepayu.Exception.MembroNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ValorPositivoException;
import br.ufal.ic.p2.wepayu.empregados.Empregado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados.empregados;

public class GerenciaSindicato {

    public static void lancaTaxasServico(String membro, String data, String valor) throws
            IdMembroNuloException, MembroNaoExisteException, DataInvalidaException,
            ValorPositivoException {
        double valorDouble = Double.parseDouble(valor.replace(",", "."));

        if (Objects.equals(membro, "")) {
            throw new IdMembroNuloException();
        } else if (!empregados.containsKey(membro)) {
            throw new MembroNaoExisteException();
        } else if (valorDouble <= 0) {
            throw new ValorPositivoException();
        }

        Empregado empregado = empregados.get(membro);

        LocalDate date;
        try {
            DateTimeFormatter dataa = DateTimeFormatter.ofPattern("d/M/yyyy");
            date = LocalDate.parse(data, dataa);
        } catch (Exception e) {
            throw new DataInvalidaException();
        }
//        MembroSindicato.setTaxas(new TaxaServico(date, valorDouble));
    }
}
