package br.ufal.ic.p2.wepayu.empregados;

public class EmpregadoComissionado extends Empregado{
    private EmpregadoComissionado(String nome, String endereco, String tipo, String salario, String comissao, String id) {

        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.id = id;
        this.sindicalizado = false;
        this.comissao = comissao;
    }

    public static class EmpregadoComissionadoBuilder {
        protected String nome;
        protected String endereco;
        protected String tipo;
        protected String salario;
        protected String comissao;
        protected String id;

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

        public EmpregadoComissionado build(){
            return new EmpregadoComissionado(nome, endereco, tipo, salario, comissao, id);
        }
    }
}
