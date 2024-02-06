package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Aritmetica;
import br.ufal.ic.p2.wepayu.models.Erros;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GerenciaEmpregados {
    public static HashMap<String, Empregado> empregados = new HashMap<>();

    protected static Aritmetica aritmetica = new Aritmetica();
    protected static Erros verificarErros = new Erros();

    protected static int idCounter = 100000000;

    public static String getAtributoEmpregado(String emp, String atributo) throws
            IdNuloException, EmpregadoNaoExisteException, AtributoNExisteException{
        if (Objects.equals(emp, "")) {
            throw new IdNuloException();
        }
        if (!empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException();
        }
        if (!(Arrays.asList("nome", "endereco", "tipo", "salario", "sindicalizado", "comissao").contains(atributo))) {
            throw new AtributoNExisteException();
        }
        Empregado empregado = empregados.get(emp);
        return switch (atributo) {
            case "nome" -> empregado.getNome();
            case "endereco" -> empregado.getEndereco();
            case "tipo" -> empregado.getTipo();
            case "salario" -> empregado.getSalario();
            case "sindicalizado" -> String.valueOf(empregado.getSindicalizado());
            case "comissao" -> empregado.getComissao();
            default -> throw new AtributoNExisteException();
        };
    }

    public static String getEmpregadoPorNome(String nome, Integer indice) throws NomeInexistenteException, EmpregadoNaoExisteException {
        if(empregados.isEmpty()){
            System.out.println("\nEmpregados vazios\n");
            throw new EmpregadoNaoExisteException();
        }
        for (Map.Entry<String, Empregado> entry : empregados.entrySet()) {
            Empregado e = entry.getValue();
            String key = entry.getKey();
            if (nome.contains(e.getNome()))
                indice--;

            if (indice == 0)
                return key;
        }

        throw new NomeInexistenteException();
    }

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, ComissaoNulaException, ComissaoNumericaException,
            ComissaoNegativaException, TipoNAplicavelException{
        if(Objects.equals(tipo, "assalariado")){
            verificarErros.conferirErros(nome, endereco, tipo, salario);
            EmpregadoAssalariado empregado =
                    new EmpregadoAssalariado.EmpregadoAssalariadoBuilder()
                            .nome(nome)
                            .endereco(endereco)
                            .tipo(tipo)
                            .salario(salario)
                            .id(String.valueOf(idCounter++))
                            .build();
            empregados.put(empregado.getId(), empregado);
            return empregado.getId();
        }else{
            verificarErros.conferirErros(nome, endereco, tipo, salario);
            EmpregadoHorista empregado =
                    new EmpregadoHorista.EmpregadoHoristaBuilder()
                            .nome(nome)
                            .endereco(endereco)
                            .tipo(tipo)
                            .salario(salario)
                            .id(String.valueOf(idCounter++))
                            .build();
            empregados.put(empregado.getId(), empregado);
            return empregado.getId();
        }
    }

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, ComissaoNulaException, ComissaoNumericaException,
            ComissaoNegativaException, TipoNAplicavelException{
        if(Objects.equals(tipo, "comissionado")){
            verificarErros.conferirErros(nome, endereco, tipo, salario, comissao);
            EmpregadoComissionado empregado =
                    new EmpregadoComissionado.EmpregadoComissionadoBuilder()
                            .nome(nome)
                            .endereco(endereco)
                            .tipo(tipo)
                            .salario(salario)
                            .comissao(comissao)
                            .id(String.valueOf(idCounter++))
                            .build();
            empregados.put(empregado.getId(), empregado);
            return empregado.getId();
        } else{
            if(!comissao.isEmpty()){
                throw new TipoNAplicavelException();
            }
            throw new TipoInvalidoException();
        }
    }

    public static void alteraEmpregado(String emp, String atributo, String valor) throws EmpregadoNaoExisteException, NomeNuloException,
            EnderecoNuloException, SalarioNuloException, ComissaoNulaException, SalarioNumericoException, SalarioNegativoException,
            ValorTrueFalseException{
        if(emp == null){
            throw new EmpregadoNaoExisteException();
        }

        Empregado empregado = empregados.get(emp);

        switch (atributo){
            case "nome" ->{
                if(valor.isEmpty()){
                    throw new NomeNuloException();
                }
                empregado.setNome(valor);
            }
            case "endereco" ->{
                if(valor.isEmpty()){
                    throw new EnderecoNuloException();
                }
                empregado.setEndereco(valor);
            }
            case "salario" ->{
                if(valor.isEmpty()){
                    throw new SalarioNuloException();
                }

                try{
                    double salario = Double.parseDouble(valor.replace(',', '.'));

                    if(salario <= 0)
                    {
                        throw new SalarioNegativoException();
                    }
                }catch (Exception e){
                    throw new SalarioNumericoException();
                }
                empregado.setSalario(valor);
            }
            case "sindicalizado" ->{
                if(!valor.equals("false") && !valor.equals("true")){
                    throw new ValorTrueFalseException();
                }
                if(valor.equals("false")){
                    empregado.setSindicalizado(valor);
                }
            }
        }
    }

    public static void removerEmpregado (String emp) throws IdNuloException, EmpregadoNaoExisteException{
        if (emp.isEmpty())
            throw new IdNuloException();

        Empregado empregado = empregados.get(emp);

        if (empregado == null)
            throw new EmpregadoNaoExisteException();

        empregados.remove(emp);
    }

    public static String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoHoristaException,
            IdNuloException, DataIniPostFinException, DataInicialInvException, DataFinalInvException{
        if(emp == null) {
            throw new IdNuloException();
        }if(!(empregados.get(emp) instanceof EmpregadoHorista)){
            throw new EmpregadoNaoHoristaException();
        }
        EmpregadoHorista empregado = (EmpregadoHorista) empregados.get(emp);

        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate Inicial, Final;

        if(!(Erros.confereData(dataInicial))){
            throw new DataInicialInvException();
        }
        Inicial = LocalDate.parse(dataInicial, dataFormato);

        if(!(Erros.confereData(dataFinal))){
            throw new DataFinalInvException();
        }
        Final = LocalDate.parse(dataFinal,dataFormato);

        double acumulador = 0;
        if(Inicial.equals(Final))
        {
            return "0";
        }else if(Inicial.isAfter(Final)){
            throw new DataIniPostFinException();
        } else{
            if(empregado.cartaoPonto == null) {
                return "0";
            }
            for(CartaoPonto c : empregado.cartaoPonto){
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
        if(emp == null) {
            throw new IdNuloException();
        }if(!(empregados.get(emp) instanceof EmpregadoHorista)){
            throw new EmpregadoNaoHoristaException();
        }
        EmpregadoHorista empregado = (EmpregadoHorista) empregados.get(emp);


        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate Inicial, Final;

        if(!(Erros.confereData(dataInicial))){
            throw new DataInicialInvException();
        }
        Inicial = LocalDate.parse(dataInicial, dataFormato);

        if(!(Erros.confereData(dataFinal))){
            throw new DataFinalInvException();
        }
        Final = LocalDate.parse(dataFinal,dataFormato);

        double acumulador = 0;
        if(Inicial.equals(Final)) {
            return "0";
        }else if(Inicial.isAfter(Final)){
            throw new DataIniPostFinException();
        } else{
            if(empregado.cartaoPonto == null) {
                return "0";
            }
            for(CartaoPonto c : empregado.cartaoPonto){
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

    public static String getVendasRealizadas (String emp, String dataInicial, String dataFinal) throws IdNuloException, EmpregadoNaoComissionadoException,
            DataInicialInvException, DataFinalInvException, DataIniPostFinException{
        if(emp == null){
            throw new IdNuloException();
        }if(!(empregados.get(emp) instanceof  EmpregadoComissionado)){
            throw new EmpregadoNaoComissionadoException();
        }

        EmpregadoComissionado empregado = (EmpregadoComissionado) empregados.get(emp);

        DateTimeFormatter dataFormato = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate Inicial, Final;

        if(!(Erros.confereData(dataInicial))){
            throw new DataInicialInvException();
        }
        Inicial = LocalDate.parse(dataInicial, dataFormato);

        if(!(Erros.confereData(dataFinal))){
            throw new DataFinalInvException();
        }
        Final = LocalDate.parse(dataFinal,dataFormato);
        double acumulador = 0;
        if(Inicial.equals(Final)){
            return "0,00";
        }else if(Inicial.isAfter(Final)) {
            throw new DataIniPostFinException();
        } else{
            if(empregado.cartaoVenda == null){
                return "0";
            }
            for(CartaoVenda c : empregado.cartaoVenda){
                if(c.getData().isEqual(Inicial) || (c.getData().isAfter(Inicial) && c.getData().isBefore(Final))){
                    acumulador += c.getHoras();
                }
            }
        }

        return String.format("%.2f", acumulador).replace(".", ",");

    }
}
