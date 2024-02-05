package br.ufal.ic.p2.wepayu.empregados;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.Aritmetica;
import br.ufal.ic.p2.wepayu.models.Erros;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Objects;

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

}
