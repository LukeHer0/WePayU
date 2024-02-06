package br.ufal.ic.p2.wepayu.empregados;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.gerencia.CartaoPonto;
import br.ufal.ic.p2.wepayu.models.Erros;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados.empregados;

public class EmpregadoHorista extends Empregado{

//    public LinkedHashMap<LocalDate, String> horasNormais = new LinkedHashMap<>();
//    public LinkedHashMap<LocalDate, String> horasExtras = new LinkedHashMap<>();
public ArrayList<CartaoPonto> cartaoPonto;

    private EmpregadoHorista(String nome, String endereco, String tipo, String salario, String id) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.id = id;
        this.sindicalizado = false;
        this.cartaoPonto = new ArrayList<CartaoPonto>();
    }

    public void setPontodeHora(CartaoPonto cartao){
        this.cartaoPonto.add(cartao);
    }

    public static class EmpregadoHoristaBuilder {
        protected String nome;
        protected String endereco;
        protected String tipo;
        protected String salario;
        protected String id;

        public EmpregadoHoristaBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public EmpregadoHoristaBuilder endereco(String endereco) {
            this.endereco = endereco;
            return this;
        }

        public EmpregadoHoristaBuilder tipo(String tipo) {
            this.tipo = tipo;
            return this;
        }

        public EmpregadoHoristaBuilder salario(String salario) {
            this.salario = aritmetica.doubleFormat(salario);
            return this;
        }

        public EmpregadoHoristaBuilder id(String id) {
            this.id = id;
            return this;
        }

        public EmpregadoHorista build(){
            return new EmpregadoHorista(nome, endereco, tipo, salario, id);
        }


    }
}
