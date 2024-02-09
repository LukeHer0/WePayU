package br.ufal.ic.p2.wepayu.empregados.comissionado;

import br.ufal.ic.p2.wepayu.empregados.Empregado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class EmpregadoComissionado extends Empregado implements Serializable {

    public ArrayList<CartaoVenda> cartaoVenda;
    public EmpregadoComissionado(){}
    private EmpregadoComissionado(String nome, String endereco, String tipo, String salario, String comissao, String id) {

        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.id = id;
        this.sindicalizado = false;
        this.comissao = comissao;
        this.cartaoVenda = new ArrayList<CartaoVenda>();
        this.metodoPagamento = "emMaos";
    }
    public void setCartaoVenda(CartaoVenda cartao){
        this.cartaoVenda.add(cartao);
    }
    public ArrayList<CartaoVenda> getCartaoVenda(){
        return this.cartaoVenda;
    }
    public static class EmpregadoComissionadoBuilder {
        protected String nome;
        protected String endereco;
        protected String tipo;
        protected String salario;
        protected String comissao;
        protected String id;
        protected String metodoPagamento;
        public EmpregadoComissionadoBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }
        public EmpregadoComissionadoBuilder endereco(String endereco) {
            this.endereco = endereco;
            return this;
        }
        public EmpregadoComissionadoBuilder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }
        public EmpregadoComissionadoBuilder salario(String salario) {
            this.salario = aritmetica.doubleFormat(salario);
            return this;
        }
        public EmpregadoComissionadoBuilder comissao(String comissao) {
            this.comissao = aritmetica.doubleFormat(comissao);
            return this;
        }
        public EmpregadoComissionadoBuilder id(String id) {
            this.id = id;
            return this;
        }
        public EmpregadoComissionadoBuilder metodoPagamento(String metodoPagamento){
            this.metodoPagamento = metodoPagamento;
            return this;
        }
        public EmpregadoComissionado build(){
            return new EmpregadoComissionado(nome, endereco, tipo, salario, comissao, id);
        }
    }
}
