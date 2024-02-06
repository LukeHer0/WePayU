package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.Exception.IdNuloException;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.empregados.comissionado.CartaoVenda;
import br.ufal.ic.p2.wepayu.empregados.comissionado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.gerencia.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados.empregados;

public class Facade {
    public void zerarSistema() {
        Sistema.zerarSistema();
    }

    public void encerrarSistema() {
        Sistema.encerrarSistema();
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

    public String getEmpregadoPorNome(String nome, Integer indice) throws
            NomeInexistenteException, EmpregadoNaoExisteException {
        return GerenciaEmpregados.getEmpregadoPorNome(nome, indice);
    }

    public void removerEmpregado(String emp) throws
            IdNuloException, EmpregadoNaoExisteException {
        GerenciaEmpregados.removerEmpregado(emp);
    }

    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws
            EmpregadoNaoHoristaException, IdNuloException, DataIniPostFinException, DataInicialInvException,
            DataFinalInvException {
        return GerenciaEmpregados.getHorasNormaisTrabalhadas(emp, dataInicial, dataFinal);
    }

    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws
            EmpregadoNaoHoristaException, IdNuloException, DataIniPostFinException, DataInicialInvException,
            DataFinalInvException {
        return GerenciaEmpregados.getHorasExtrasTrabalhadas(emp, dataInicial, dataFinal);
    }

    public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws
            IdNuloException, EmpregadoNaoComissionadoException, DataInicialInvException, DataFinalInvException,
            DataIniPostFinException {
        return GerenciaEmpregados.getVendasRealizadas(emp, dataInicial, dataFinal);
    }

    public void lancaCartao(String emp, String data, String horas) throws
            IdNuloException, EmpregadoNaoExisteException, EmpregadoNaoHoristaException,
            DataInvalidaException, HoraPositivaException {
        GerenciaEmpregados.lancaCartao(emp, data, horas);
    }

    public void lancaVenda(String emp, String data, String valor) throws
            IdNuloException, EmpregadoNaoExisteException, EmpregadoNaoComissionadoException,
            ValorPositivoException, DataInvalidaException {
        GerenciaVendas.lancaVenda(emp, data, valor);
    }

    public void lancaTaxasServico(String membro, String data, String valor) throws
            IdMembroNuloException, MembroNaoExisteException, DataInvalidaException,
            ValorPositivoException {
        GerenciaSindicato.lancaTaxasServico(membro, data, valor);
    }

    public static void alteraEmpregado(String emp, String atributo, String valor) throws
            EmpregadoNaoExisteException, NomeNuloException, EnderecoNuloException, SalarioNuloException,
            ComissaoNulaException, SalarioNumericoException, SalarioNegativoException, ValorTrueFalseException{
        GerenciaEmpregados.alteraEmpregado(emp, atributo, valor);
    }
}