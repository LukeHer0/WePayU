package br.ufal.ic.p2.wepayu.empregados.comissionado;

import br.ufal.ic.p2.wepayu.empregados.Empregado;

import java.util.ArrayList;

public class EmpregadoComissionado extends Empregado {

    public ArrayList<CartaoVenda> cartaoVenda;
    private EmpregadoComissionado(String nome, String endereco, String tipo, String salario, String comissao, String id) {

        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.salario = salario;
        this.id = id;
        this.sindicalizado = false;
        this.comissao = comissao;
        this.cartaoVenda = new ArrayList<CartaoVenda>();
    }

//    public static String getVendasRealizadas (String emp, String dataInicial, String dataFinal) throws IdNuloException, EmpregadoNaoComissionadoException,
//            DataInicialInvException, DataFinalInvException, DataIniPostFinException{
//        if(emp == null){
//            throw new IdNuloException();
//        }
//        EmpregadoComissionado empregado = (EmpregadoComissionado) empregados.get(emp);
//        if(!Objects.equals(empregados.get(emp).getTipo(), "comissionado")){
//            throw new EmpregadoNaoComissionadoException();
//        }
//
//        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
//        LocalDate Inicial, Final;
//
//        if(!(Erros.confereData(dataInicial))){
//            throw new DataInicialInvException();
//        }
//        Inicial = LocalDate.parse(dataInicial, dataFormato);
//
//        if(!(Erros.confereData(dataFinal))){
//            throw new DataFinalInvException();
//        }
//        Final = LocalDate.parse(dataFinal,dataFormato);
//        double acumulador = 0;
//        if(Inicial.equals(Final)){
//            return "0,00";
//        }else if(Inicial.isAfter(Final)) {
//            throw new DataIniPostFinException();
//        } else{
//            if(empregado.cartao == null){
//                return "0";
//            }
//            for(CartaoVenda c : empregado.cartao){
//                if(c.getData().isEqual(Inicial) || (c.getData().isAfter(Inicial) && c.getData().isBefore(Final))){
//                    acumulador += c.getHoras();
//                }
//            }
//        }
//
//        return String.format("%.2f", acumulador).replace(".", ",");
//
//    }

    public void setVenda(CartaoVenda cartao){
        this.cartaoVenda.add(cartao);
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
