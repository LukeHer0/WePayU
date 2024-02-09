package br.ufal.ic.p2.wepayu.empregados.horista;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.XMLUse.XMLUse;
import br.ufal.ic.p2.wepayu.empregados.Empregado;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados.empregados;

public class EmpregadoHorista extends Empregado implements Serializable {

public ArrayList<CartaoPonto> cartaoPonto;

    public EmpregadoHorista(){}
    private EmpregadoHorista(String nome, String endereco, String tipo, String salario, String id) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.id = id;
        this.sindicalizado = false;
        this.metodoPagamento = "emMaos";
        this.cartaoPonto = new ArrayList<CartaoPonto>();
    }

    public void setCartaoPonto(CartaoPonto cartao){
        this.cartaoPonto.add(cartao);
    }
    public ArrayList<CartaoPonto> getCartaoPonto(){return this.cartaoPonto;}

    public static void lancaCartao(String emp, String data, String horas) throws IdNuloException, EmpregadoNaoExisteException, EmpregadoNaoHoristaException, DataInvalidaException, HoraPositivaException {
        double horasDouble = Double.parseDouble(horas.replace(",", "."));

        if (Objects.equals(emp, "")) {
            throw new IdNuloException();
        } else if (!empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException();
        } else if (!Objects.equals(empregados.get(emp).getTipo(), "horista")) {
            throw new EmpregadoNaoHoristaException();
        } else if (horasDouble <= 0) {
            throw new HoraPositivaException();
        }

        EmpregadoHorista empregado = (EmpregadoHorista) empregados.get(emp);
        XMLUse.salvaEmpregadosXML(empregados, "./listaEmpregados.xml");

        LocalDate date;
        try {
            DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
            date = LocalDate.parse(data, dataFormato);
        } catch (Exception e) {
            throw new DataInvalidaException();
        }

        empregado.setCartaoPonto(new CartaoPonto(data, horasDouble));
    }

    public static class EmpregadoHoristaBuilder {
        protected String nome;
        protected String endereco;
        protected String tipo;
        protected String salario;
        protected String id;
        protected String metodoPagamento;

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

        public EmpregadoHoristaBuilder metodoPagamento(String metodoPagamento){
            this.metodoPagamento = metodoPagamento;
            return this;
        }

        public EmpregadoHorista build(){
            return new EmpregadoHorista(nome, endereco, tipo, salario, id);
        }
    }
}
