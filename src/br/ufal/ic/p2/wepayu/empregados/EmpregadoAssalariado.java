package br.ufal.ic.p2.wepayu.empregados;

import java.io.Serial;
import java.io.Serializable;

public class EmpregadoAssalariado extends Empregado {

    public EmpregadoAssalariado(){}
    private EmpregadoAssalariado(String nome, String endereco, String tipo, String salario, String id){
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.id = id;
        this.sindicalizado = false;
        this.metodoPagamento = "emMaos";
    }

    @Override
    public EmpregadoAssalariado clone() throws CloneNotSupportedException{
        return (EmpregadoAssalariado) super.clone();
    }

    public static class EmpregadoAssalariadoBuilder{
        protected String nome;
        protected String endereco;
        protected String tipo;
        protected String salario;
        protected String id;
        protected String metodoPagamento;

        public EmpregadoAssalariadoBuilder nome(String nome){
            this.nome = nome;
            return this;
        }
        public EmpregadoAssalariadoBuilder endereco(String endereco){
            this.endereco = endereco;
            return this;
        }
        public EmpregadoAssalariadoBuilder tipo(String tipo){
            this.tipo = tipo;
            return this;
        }
        public EmpregadoAssalariadoBuilder salario(String salario){
            this.salario = aritmetica.doubleFormat(salario);
            return this;
        }
        public EmpregadoAssalariadoBuilder id(String id){
            this.id = id;
            return this;
        }
        public EmpregadoAssalariadoBuilder metodoPagamento(String metodoPagamento){
            this.metodoPagamento = metodoPagamento;
            return this;
        }
        public EmpregadoAssalariado build(){
            return new EmpregadoAssalariado(nome, endereco, tipo, salario, id);
        }
    }
}
