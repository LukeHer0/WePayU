package br.ufal.ic.p2.wepayu.empregados;

import br.ufal.ic.p2.wepayu.models.Aritmetica;
import br.ufal.ic.p2.wepayu.models.Erros;

public abstract class Empregado {

    protected String nome;
    protected String endereco;
    protected String tipo;
    protected String salario;
    protected boolean sindicalizado;
    //protected static int idCounter = 100000000;
    protected String id;

    protected String comissao;

    protected static Aritmetica aritmetica = new Aritmetica();
    protected static Erros verificarErros = new Erros();

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

    public String getComissao() {
        return this.comissao;
    }

    public String getId() {
        return this.id;
    }

//    public void setTaxas(TaxaServico taxa){
//        this.taxaServicos.add(taxa);
//    }

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

}
