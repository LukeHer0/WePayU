package br.ufal.ic.p2.wepayu.empregados;

import br.ufal.ic.p2.wepayu.Exception.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados.empregados;

public class EmpregadoHorista extends Empregado{

    public LinkedHashMap<String, String> horasNormais = new LinkedHashMap<>();
    public LinkedHashMap<String, String> horasExtras = new LinkedHashMap<>();

    private EmpregadoHorista(String nome, String endereco, String tipo, String salario, String id) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.id = id;
        this.sindicalizado = false;
    }


    public static String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoHoristaException, IdNuloException{
        EmpregadoHorista empregado = (EmpregadoHorista) empregados.get(emp);
        if(emp == null)
        {
            throw new IdNuloException();
        }
        if(!Objects.equals(empregado.getTipo(), "horista")){
            throw new EmpregadoNaoHoristaException();
        }
        return empregado.getNome();
    }

    public static String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoHoristaException, IdNuloException{
        EmpregadoHorista empregado = (EmpregadoHorista) empregados.get(emp);
        if(emp == null)
        {
            throw new IdNuloException();
        }
        if(!Objects.equals(empregado.getTipo(), "horista")){
            throw new EmpregadoNaoHoristaException();
        }
        return empregado.getNome();
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
