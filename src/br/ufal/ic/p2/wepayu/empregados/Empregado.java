package br.ufal.ic.p2.wepayu.empregados;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoComissionadoException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoRecebeBancoException;
import br.ufal.ic.p2.wepayu.Exception.TipoInvalidoException;
import br.ufal.ic.p2.wepayu.models.AgendaPagamento;
import br.ufal.ic.p2.wepayu.models.Aritmetica;
import br.ufal.ic.p2.wepayu.models.Erros;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public abstract class Empregado implements Serializable, Cloneable {

    protected String nome;
    protected String endereco;
    protected String tipo;
    protected String salario;
    protected boolean sindicalizado;
    protected String id;
    protected String metodoPagamento;
    protected String banco;
    protected String agencia;
    protected String contaCorrente;
    protected String comissao;
    protected AgendaPagamento agendaPagamento;
    protected static Aritmetica aritmetica = new Aritmetica();
    protected static Erros verificarErros = new Erros();
    public Empregado(){}

    public String getComissao() throws EmpregadoNaoComissionadoException {
            return this.comissao;
    }
    public void setComissao(String comissao){
        this.comissao = comissao;
    }

    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getEndereco() {
        return this.endereco;
    }
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    public String getTipo() {
        return this.tipo;
    }
    public void setTipo(String tipo) throws TipoInvalidoException {
        if(!(Arrays.asList("horista", "comissionado", "assalariado").contains(tipo))){
            throw new TipoInvalidoException();
        }
        else {
            this.tipo = tipo;
        }
    }
    public String getSalario() {
        return this.salario;
    }
    public void setSalario(String salario){
        this.salario = aritmetica.doubleFormat(salario);
    }
    public boolean getSindicalizado() {
        return this.sindicalizado;
    }
    public void setSindicalizado(String sindicalizado){
        this.sindicalizado = sindicalizado.equals("true");
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getMetodoPagamento(){
        return this.metodoPagamento;
    }
    public void setMetodoPagamento(String metodoPagamento){
        this.metodoPagamento = metodoPagamento;
    }
    public String getBanco(String metodoPagamento) throws EmpregadoNaoRecebeBancoException {
        if(Objects.equals(metodoPagamento, "banco")){
            return this.banco;
        }
        throw new EmpregadoNaoRecebeBancoException();
    }
    public void setBanco(String banco){
        this.banco = banco;
    }
    public String getAgencia(String metodoPagamento) throws EmpregadoNaoRecebeBancoException {
        if(Objects.equals(metodoPagamento, "banco")){
            return this.agencia;
        }
        throw new EmpregadoNaoRecebeBancoException();
    }
    public void setAgencia(String agencia){
        this.agencia = agencia;
    }
    public String getContaCorrente(String metodoPagamento) throws EmpregadoNaoRecebeBancoException {
        if(Objects.equals(metodoPagamento, "banco")){
            return this.contaCorrente;
        }
        throw new EmpregadoNaoRecebeBancoException();
    }
    public void setContaCorrente(String contaCorrente){
        this.contaCorrente = contaCorrente;
    }

    public AgendaPagamento getAgendaPagamento() {
        return this.agendaPagamento;
    }

    public void setAgendaPagamento(AgendaPagamento agendaPagamento) {
        this.agendaPagamento = agendaPagamento;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return (Empregado) super.clone();
    }
}
