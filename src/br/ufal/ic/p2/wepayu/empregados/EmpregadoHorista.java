package br.ufal.ic.p2.wepayu.empregados;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.gerencia.CartaoPonto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static br.ufal.ic.p2.wepayu.gerencia.GerenciaEmpregados.empregados;

public class EmpregadoHorista extends Empregado{

//    public LinkedHashMap<LocalDate, String> horasNormais = new LinkedHashMap<>();
//    public LinkedHashMap<LocalDate, String> horasExtras = new LinkedHashMap<>();
private ArrayList<CartaoPonto> cartao;

    private EmpregadoHorista(String nome, String endereco, String tipo, String salario, String id) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.id = id;
        this.sindicalizado = false;
        this.cartao = new ArrayList<CartaoPonto>();
    }
    public static String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoHoristaException,
            IdNuloException, DataIniPostFinException, DataInicialInvException, DataFinalInvException{
        EmpregadoHorista empregado = (EmpregadoHorista) empregados.get(emp);
        if(emp == null)
        {
            throw new IdNuloException();
        }if(!Objects.equals(empregado.getTipo(), "horista")){
            throw new EmpregadoNaoHoristaException();
        }

        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate Inicial;
        try {
            Inicial = LocalDate.parse(dataInicial, dataFormato);
        }catch (Exception e){
            throw new DataInicialInvException();
        }
        LocalDate Final;
        try{
            Final = LocalDate.parse(dataFinal, dataFormato);
        }catch (Exception e){
            throw new DataFinalInvException();
        }
        double acumulador = 0;

        if(Inicial.equals(Final))
        {
            return "0";
        }else if(Inicial.isAfter(Final)){
            throw new DataIniPostFinException();
        } else{
            if(empregado.cartao == null) {
                return "0";
            }
            for(CartaoPonto c : empregado.cartao){
                if(c.getData().isEqual(Inicial) || (c.getData().isAfter(Inicial) && c.getData().isBefore(Final))){
                    if(c.getHoras() > 8){
                        acumulador += 8;
                    }else{
                         acumulador += c.getHoras();
                    }
                }
            }
        }
        if(acumulador != (int) acumulador) {

            return String.format("%.1f", acumulador).replace(".", ",");
        }
        return Integer.toString((int)acumulador);
    }

    public static String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws IdNuloException, EmpregadoNaoHoristaException,
            DataIniPostFinException, DataInicialInvException, DataFinalInvException{
        EmpregadoHorista empregado = (EmpregadoHorista) empregados.get(emp);
        if(emp == null)
        {
            throw new IdNuloException();
        }
        if(!Objects.equals(empregado.getTipo(), "horista")){
            throw new EmpregadoNaoHoristaException();
        }


        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate Inicial;
        try {
            Inicial = LocalDate.parse(dataInicial, dataFormato);
        }catch (Exception e){
            throw new DataInicialInvException();
        }
        LocalDate Final;
        try{
            Final = LocalDate.parse(dataFinal, dataFormato);
        }catch (Exception e){
            throw new DataFinalInvException();
        }

        double acumulador = 0;
        if(Inicial.equals(Final))
        {
            return "0";
        }else if(Inicial.isAfter(Final)){
            throw new DataIniPostFinException();
        } else{
            if(empregado.cartao == null) {
                return "0";
            }
            for(CartaoPonto c : empregado.cartao){
                if(c.getData().isEqual(Inicial) || (c.getData().isAfter(Inicial) && c.getData().isBefore(Final))){
                    if(c.getHoras() > 8){
                        acumulador += (c.getHoras() - 8);
                    }
                }
            }
        }
        if(acumulador != (int) acumulador) {

            return String.format("%.1f", acumulador).replace(".", ",");
        }
        return Integer.toString((int)acumulador);
    }

    public void setPontodeHora(CartaoPonto cartao){
        this.cartao.add(cartao);
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
