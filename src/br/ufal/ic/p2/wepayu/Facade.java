package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.Exception.IdNuloException;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados;
import br.ufal.ic.p2.wepayu.gerencia.Sistema;

public class Facade {
    Sistema sistema = new Sistema();
    GerenciaEmpregados gerenciaEmpregados = new GerenciaEmpregados();

    public void zerarSistema() {
        sistema = new Sistema();
        gerenciaEmpregados = new GerenciaEmpregados();
        //sistema.zerarSistema();
    }

    public void encerrarSistema() {
        sistema.encerrarSistema();
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, ComissaoNulaException, ComissaoNumericaException,
            ComissaoNegativaException, TipoNAplicavelException{
        return gerenciaEmpregados.criarEmpregado(nome, endereco, tipo, salario);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, ComissaoNulaException, ComissaoNumericaException,
            ComissaoNegativaException, TipoNAplicavelException{
        return gerenciaEmpregados.criarEmpregado(nome, endereco, tipo, salario, comissao);
    }

    public String getAtributoEmpregado(String emp, String atributo) throws
            IdNuloException, EmpregadoNaoExisteException, AtributoNExisteException {
        return gerenciaEmpregados.getAtributoEmpregado(emp, atributo);
    }

    public String getEmpregadoPorNome(String nome, Integer indice) throws NomeInexistenteException {
        return gerenciaEmpregados.getEmpregadoPorNome(nome, indice);
    }

    public void removerEmpregado (String emp) throws IdNuloException, EmpregadoNaoExisteException {
        gerenciaEmpregados.removerEmpregado(emp);
    }

}
