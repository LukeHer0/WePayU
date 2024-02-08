package br.ufal.ic.p2.wepayu.empregados;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoComissionadoException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoRecebeBancoException;
import br.ufal.ic.p2.wepayu.models.Aritmetica;
import br.ufal.ic.p2.wepayu.models.Erros;

import java.util.Objects;

public abstract class Empregado {

    protected String nome;
    protected String endereco;
    protected String tipo;
    protected String salario;
    protected boolean sindicalizado;
    //protected static int idCounter = 100000000;
    protected String id;
    protected String metodoPagamento;
    protected String banco;
    protected String agencia;
    protected String contaCorrente;
    protected String comissao;


    protected static Aritmetica aritmetica = new Aritmetica();
    protected static Erros verificarErros = new Erros();

    public void setComissao(String comissao){
        this.comissao = comissao;
    }

    public String getComissao(String tipo) throws EmpregadoNaoComissionadoException {
        if(Objects.equals(tipo, "comissionado")){
            return this.comissao;
        }
        throw new EmpregadoNaoComissionadoException();
    }

    public String getNome() {
        return this.nome;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getSalario() {
        return this.salario;
    }

    public boolean getSindicalizado() {
        return this.sindicalizado;
    }

    public String getId() {
        return this.id;
    }

    public String getMetodoPagamento(){
        return this.metodoPagamento;
    }

    public String getBanco(String metodoPagamento) throws EmpregadoNaoRecebeBancoException {
        if(Objects.equals(metodoPagamento, "banco")){
            return this.banco;
        }
        throw new EmpregadoNaoRecebeBancoException();
    }

    public String getAgencia(String metodoPagamento) throws EmpregadoNaoRecebeBancoException {
        if(Objects.equals(metodoPagamento, "banco")){
            return this.agencia;
        }
        throw new EmpregadoNaoRecebeBancoException();
    }

    public String getContaCorrente(String metodoPagamento) throws EmpregadoNaoRecebeBancoException {
        if(Objects.equals(metodoPagamento, "banco")){
            return this.contaCorrente;
        }
        throw new EmpregadoNaoRecebeBancoException();
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEndereco(String endereco){
        this.endereco = endereco;
    }

    public void setSalario(String salario){
        this.salario = aritmetica.doubleFormat(salario);
    }

    public void setSindicalizado(String sindicalizado){
            this.sindicalizado = sindicalizado.equals("true");
    }



    public void setBanco(String banco){
        this.banco = banco;
    }

    public void setAgencia(String agencia){
        this.agencia = agencia;
    }

    public void setContaCorrente(String contaCorrente){
        this.contaCorrente = contaCorrente;
    }

    public void setMetodoPagamento(String metodoPagamento){
        this.metodoPagamento = metodoPagamento;
    }

}
