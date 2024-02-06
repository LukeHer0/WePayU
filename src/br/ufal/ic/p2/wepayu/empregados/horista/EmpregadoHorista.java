package br.ufal.ic.p2.wepayu.empregados.horista;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.empregados.Empregado;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados.empregados;

public class EmpregadoHorista extends Empregado {

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

        LocalDate date;
        try {
            DateTimeFormatter dataa = DateTimeFormatter.ofPattern("d/M/yyyy");
            date = LocalDate.parse(data, dataa);
        } catch (Exception e) {
            throw new DataInvalidaException();
        }

        empregado.setPontodeHora(new CartaoPonto(date, horasDouble));
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
