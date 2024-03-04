package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.Exception.IdNuloException;
import br.ufal.ic.p2.wepayu.gerencia.*;
import br.ufal.ic.p2.wepayu.memento.Memento;
import br.ufal.ic.p2.wepayu.memento.MementoCommands;

import java.io.IOException;

public class Facade {

    private boolean sistemaEncerrado = false;
    MementoCommands mementoCommands = new MementoCommands();
    public void zerarSistema() throws IOException, CloneNotSupportedException, AgendaJaExisteException, AgendaInvalidaException {
        mementoCommands.backup();
        Sistema.zerarSistema();
    }

    public void encerrarSistema() throws CloneNotSupportedException {
        mementoCommands.backup();
        sistemaEncerrado = true;
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, ComissaoNulaException, ComissaoNumericaException,
            ComissaoNegativaException, TipoNAplicavelException, CloneNotSupportedException {
        mementoCommands.backup();
        String retorno = GerenciaEmpregados.criarEmpregado(nome, endereco, tipo, salario);
        return retorno;
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, ComissaoNulaException, ComissaoNumericaException,
            ComissaoNegativaException, TipoNAplicavelException, CloneNotSupportedException {
        mementoCommands.backup();
        String retorno = GerenciaEmpregados.criarEmpregado(nome, endereco, tipo, salario, comissao);
        return retorno;
    }

    public String getAtributoEmpregado(String emp, String atributo) throws
            IdNuloException, EmpregadoNaoExisteException, AtributoNExisteException,
            EmpregadoNaoComissionadoException, EmpregadoNaoRecebeBancoException,
            EmpregadoNaoSindicalizadoException, TipoInvalidoException {
        String retorno = GerenciaEmpregados.getAtributoEmpregado(emp, atributo);
        return retorno;
    }

    public String getEmpregadoPorNome(String nome, Integer indice) throws
            NomeInexistenteException, EmpregadoNaoExisteException {
        String retorno = GerenciaEmpregados.getEmpregadoPorNome(nome, indice);
        return retorno;
    }

    public String getTaxasServico(String emp, String dataInicial, String dataFinal) throws Exception {
        String retorno = GerenciaSindicato.getTaxasServico(emp, dataInicial, dataFinal);
        return retorno;
    }

    public void alteraEmpregado(String emp, String atributo, String valor, String idSindicato, String taxaSindical) throws
            TaxaSindicalNulaException, TaxaSindicalNumericaException, IdSindicatoNuloException, OutroEmpregadoSindicatoException,
            TaxaSindicalPositivaException, CloneNotSupportedException {
        mementoCommands.backup();
        GerenciaEmpregados.alteraEmpregado(emp, atributo, valor, idSindicato, taxaSindical);
    }

    public void alteraEmpregado(String emp, String atributo, String valor, String pagamento) throws IdNuloException, EmpregadoNaoRecebeBancoException, CloneNotSupportedException {
        mementoCommands.backup();
        GerenciaEmpregados.alteraEmpregado(emp, atributo, valor, pagamento);
    }

    public void alteraEmpregado(String emp, String atributo, String valor) throws
            EmpregadoNaoExisteException, NomeNuloException, EnderecoNuloException, SalarioNuloException,
            ComissaoNulaException, SalarioNumericoException, SalarioNegativoException, ValorTrueFalseException, EmpregadoNaoComissionadoException, AtributoNExisteException, TipoInvalidoException, MetodoPagInvalidoException, IdNuloException, ComissaoNegativaException, ComissaoNumericaException, EmpregadoNaoRecebeBancoException, CloneNotSupportedException, AgendaNaoDisponivelException {
        mementoCommands.backup();
        GerenciaEmpregados.alteraEmpregado(emp, atributo, valor);
    }

    public void alteraEmpregado (String emp, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws
            EmpregadoNaoExisteException, BancoNuloException, AgenciaNuloException, ContaCorrenteNuloException, AtributoNExisteException, MetodoPagInvalidoException, CloneNotSupportedException {
        mementoCommands.backup();
        GerenciaEmpregados.alteraEmpregado(emp, atributo, valor1, banco, agencia, contaCorrente);
    }

    public void removerEmpregado(String emp) throws
            IdNuloException, EmpregadoNaoExisteException, CloneNotSupportedException {
        mementoCommands.backup();
        GerenciaEmpregados.removerEmpregado(emp);
    }


    public String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws
            EmpregadoNaoHoristaException, IdNuloException, DataIniPostFinException, DataInicialInvException,
            DataFinalInvException {
        String retorno = GerenciaEmpregados.getHorasNormaisTrabalhadas(emp, dataInicial, dataFinal);
        return retorno;
    }

    public String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws
            EmpregadoNaoHoristaException, IdNuloException, DataIniPostFinException, DataInicialInvException,
            DataFinalInvException {
        String retorno = GerenciaEmpregados.getHorasExtrasTrabalhadas(emp, dataInicial, dataFinal);
        return retorno;
    }

    public String getVendasRealizadas(String emp, String dataInicial, String dataFinal) throws
            IdNuloException, EmpregadoNaoComissionadoException, DataInicialInvException, DataFinalInvException,
            DataIniPostFinException {
        String retorno = GerenciaVendas.getVendasRealizadas(emp, dataInicial, dataFinal);
        return retorno;
    }

    public void lancaCartao(String emp, String data, String horas) throws
            IdNuloException, EmpregadoNaoExisteException, EmpregadoNaoHoristaException,
            DataInvalidaException, HoraPositivaException, CloneNotSupportedException {
        mementoCommands.backup();
        GerenciaEmpregados.lancaCartao(emp, data, horas);
    }

    public void lancaVenda(String emp, String data, String valor) throws
            IdNuloException, EmpregadoNaoExisteException, EmpregadoNaoComissionadoException,
            ValorPositivoException, DataInvalidaException, CloneNotSupportedException {
        mementoCommands.backup();
        try {
            GerenciaVendas.lancaVenda(emp, data, valor);
        } catch (Exception e) {
            mementoCommands.getUndoMementos().pop();
            throw e;
        }

    }

    public void lancaTaxaServico(String membro, String data, String valor) throws
            IdMembroNuloException, MembroNaoExisteException, DataInvalidaException,
            ValorPositivoException, CloneNotSupportedException {
        mementoCommands.backup();
        GerenciaSindicato.lancaTaxaServico(membro, data, valor);
    }

    public String totalFolha(String data) throws Exception {
        String retorno = FolhadePagamento.totalFolha(data);
        return retorno;
    }

    public void rodaFolha(String data, String saida) throws CloneNotSupportedException,
            DataInicialInvException, EmpregadoNaoSindicalizadoException,
            EmpregadoNaoRecebeBancoException, DataIniPostFinException, IOException,
            EmpregadoNaoHoristaException, DataInvalidaException, IdNuloException,
            EmpregadoNaoComissionadoException, DataFinalInvException, AgendaInvalidaException {
        mementoCommands.backup();
        FolhadePagamento.rodaFolha(data, saida);
    }

    public Integer getNumeroDeEmpregados(){
        return (Integer) GerenciaEmpregados.getNumeroDeEmpregados();
    }

    public void undo() throws NaoComandoDesfazerException, ComandoAposEncerrarException {
        if(sistemaEncerrado) {
            throw new ComandoAposEncerrarException();
        }
        mementoCommands.undo();
    }

    public void redo() throws NaoComandoRefazerException {
        mementoCommands.redo();
    }

    public void criarAgendaDePagamentos(String descricao) throws AgendaJaExisteException, AgendaInvalidaException{
        GerenciaAgenda.criarAgendaDePagamentos(descricao);
    }
}