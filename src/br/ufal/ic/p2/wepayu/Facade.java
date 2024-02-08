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
            IdNuloException, EmpregadoNaoExisteException, AtributoNExisteException,
            EmpregadoNaoComissionadoException, EmpregadoNaoRecebeBancoException,
            EmpregadoNaoSindicalizadoException {
        return GerenciaEmpregados.getAtributoEmpregado(emp, atributo);
    }

    public String getEmpregadoPorNome(String nome, Integer indice) throws
            NomeInexistenteException, EmpregadoNaoExisteException {
        return GerenciaEmpregados.getEmpregadoPorNome(nome, indice);
    }

    public String getTaxasServico(String emp, String dataInicial, String dataFinal) throws Exception {
        return GerenciaSindicato.getTaxasServico(emp, dataInicial, dataFinal);
    }

    public void alteraEmpregado(String emp, String atributo, String valor, String idSindicato, String taxaSindical) throws
            TaxaSindicalNulaException, TaxaSindicalNumericaException, IdSindicatoNuloException, OutroEmpregadoSindicatoException,
            TaxaSindicalPositivaException {
        GerenciaEmpregados.alteraEmpregado(emp, atributo, valor, idSindicato, taxaSindical);
    }

    public static void alteraEmpregado(String emp, String atributo, String valor, String pagamento) throws IdNuloException, EmpregadoNaoRecebeBancoException{
        GerenciaEmpregados.alteraEmpregado(emp, atributo, valor, pagamento);
    }

    public static void alteraEmpregado(String emp, String atributo, String valor) throws
            EmpregadoNaoExisteException, NomeNuloException, EnderecoNuloException, SalarioNuloException,
            ComissaoNulaException, SalarioNumericoException, SalarioNegativoException, ValorTrueFalseException, EmpregadoNaoComissionadoException, AtributoNExisteException, TipoInvalidoException, MetodoPagInvalidoException, IdNuloException, ComissaoNegativaException, ComissaoNumericaException, EmpregadoNaoRecebeBancoException {
        GerenciaEmpregados.alteraEmpregado(emp, atributo, valor);
    }

    public static void alteraEmpregado (String emp, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws
            EmpregadoNaoExisteException, BancoNuloException, AgenciaNuloException, ContaCorrenteNuloException, AtributoNExisteException, MetodoPagInvalidoException {
        GerenciaEmpregados.alteraEmpregado(emp, atributo, valor1, banco, agencia, contaCorrente);
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

    public void lancaTaxaServico(String membro, String data, String valor) throws
            IdMembroNuloException, MembroNaoExisteException, DataInvalidaException,
            ValorPositivoException {
        GerenciaSindicato.lancaTaxaServico(membro, data, valor);
    }
}