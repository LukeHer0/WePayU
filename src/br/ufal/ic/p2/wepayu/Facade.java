package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.Exception.IdNuloException;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.gerencia.*;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados.empregados;

public class Facade {
    Sistema sistema = new Sistema();
    GerenciaEmpregados gerenciaEmpregados = new GerenciaEmpregados();

    public void zerarSistema() {
        Sistema.zerarSistema();
        //sistema.zerarSistema();
    }

    public void encerrarSistema() {
        sistema.encerrarSistema();
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, ComissaoNulaException, ComissaoNumericaException,
            ComissaoNegativaException, TipoNAplicavelException {
        return GerenciaEmpregados.criarEmpregado(nome, endereco, tipo, salario);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, ComissaoNulaException, ComissaoNumericaException,
            ComissaoNegativaException, TipoNAplicavelException {
        return GerenciaEmpregados.criarEmpregado(nome, endereco, tipo, salario, comissao);
    }

    public String getAtributoEmpregado(String emp, String atributo) throws
            IdNuloException, EmpregadoNaoExisteException, AtributoNExisteException {
        return GerenciaEmpregados.getAtributoEmpregado(emp, atributo);
    }

    public String getEmpregadoPorNome(String nome, Integer indice) throws NomeInexistenteException, EmpregadoNaoExisteException {
        return GerenciaEmpregados.getEmpregadoPorNome(nome, indice);
    }

    public void removerEmpregado(String emp) throws IdNuloException, EmpregadoNaoExisteException {
        GerenciaEmpregados.removerEmpregado(emp);
    }

    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoHoristaException, IdNuloException, DataIniPostFinException, DataInicialInvException, DataFinalInvException {
        return GerenciaEmpregados.getHorasNormaisTrabalhadas(emp, dataInicial, dataFinal);
    }

    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoHoristaException, IdNuloException, DataIniPostFinException, DataInicialInvException, DataFinalInvException {
        return GerenciaEmpregados.getHorasExtrasTrabalhadas(emp, dataInicial, dataFinal);
    }

    public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws IdNuloException, EmpregadoNaoComissionadoException,
            DataInicialInvException, DataFinalInvException, DataIniPostFinException {
        return GerenciaEmpregados.getVendasRealizadas(emp, dataInicial, dataFinal);
    }

    public void lancaCartao(String emp, String data, String horas) throws IdNuloException, EmpregadoNaoExisteException, EmpregadoNaoHoristaException, DataInvalidaException, HoraPositivaException {
        Double horasDouble = Double.parseDouble(horas.replace(",", "."));

        if (Objects.equals(emp, "")) {
            throw new IdNuloException();
        } else if (!empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException();
        } else if (!Objects.equals(empregados.get(emp).getTipo(), "horista")) {
            throw new EmpregadoNaoHoristaException();
        } else if (horasDouble <= 0) {
            throw new HoraPositivaException();
        }

        EmpregadoHorista empregado = (EmpregadoHorista) empregados.get(emp);

        LocalDate date;
        try {
            DateTimeFormatter dataa = DateTimeFormatter.ofPattern("d/M/yyyy");
            date = LocalDate.parse(data, dataa);
        } catch (Exception e) {
            throw new DataInvalidaException();
        }

        empregado.setPontodeHora(new CartaoPonto(date, horasDouble));
    }

    public void lancaVenda(String emp, String data, String valor) throws IdNuloException, EmpregadoNaoExisteException,
            EmpregadoNaoComissionadoException, ValorPositivoException, DataInvalidaException {
        Double valorDouble = Double.parseDouble(valor.replace(",", "."));

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

    public void lancaTaxasServico(String membro, String data, String valor) throws IdMembroNuloException, MembroNaoExisteException,
            DataInvalidaException, ValorPositivoException {
        Double valorDouble = Double.parseDouble(valor.replace(",", "."));

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
        empregado.setTaxas(new TaxaServico(date, valorDouble));
    }

    public static void alteraEmpregado(String emp, String atributo, String valor) throws EmpregadoNaoExisteException, NomeNuloException,
            EnderecoNuloException, SalarioNuloException, ComissaoNulaException, SalarioNumericoException, SalarioNegativoException,
            ValorTrueFalseException{
        GerenciaEmpregados.alteraEmpregado(emp, atributo, valor);
    }
}