package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.XMLUse.XMLUse;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.empregados.comissionado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.empregados.comissionado.CartaoVenda;
import br.ufal.ic.p2.wepayu.empregados.horista.CartaoPonto;
import br.ufal.ic.p2.wepayu.empregados.horista.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Aritmetica;
import br.ufal.ic.p2.wepayu.models.Erros;
import br.ufal.ic.p2.wepayu.sindicato.MembroSindicato;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static br.ufal.ic.p2.wepayu.gerencia.GerenciaSindicato.empregadosSindicalizados;

public class GerenciaEmpregados{
    public static HashMap<String, Empregado> empregados = XMLUse.carregarEmpregadosXML("./listaEmpregados.xml");
    protected static Erros verificarErros = new Erros();
    protected static int idCounter = 100000000;

    public static HashMap<String, EmpregadoHorista> getEmpregadosHoristas(){ //retorna o conjunto de empregados horistas da empresa em uma hash map
        HashMap <String, EmpregadoHorista> empregadosHoristas = new HashMap<String,EmpregadoHorista>();
        for(Map.Entry<String, Empregado> e : empregados.entrySet()){
            Empregado empregado = e.getValue();
            if(empregado.getTipo().equals("horista")){
                empregadosHoristas.put(e.getKey(), (EmpregadoHorista) empregado);
            }
        }
        return empregadosHoristas;
    }
    public static HashMap<String, EmpregadoComissionado> getEmpregadosComissionados(){ //retorna o conjunto de empregados comissionados da empresa em uma hash map
        HashMap <String, EmpregadoComissionado> empregadosComissionados = new HashMap<String,EmpregadoComissionado>();
        for(Map.Entry<String, Empregado> e : empregados.entrySet()){
            Empregado empregado = e.getValue();
            if(empregado.getTipo().equals("comissionado")){
                empregadosComissionados.put(e.getKey(), (EmpregadoComissionado) empregado);
            }
        }
        return empregadosComissionados;
    }

    public static HashMap<String, EmpregadoAssalariado> getEmpregadosAssalariados(){ //retorna o conjunto de empregados assalariados da empresa em uma hashmap
        HashMap <String, EmpregadoAssalariado> empregadosAssalariados = new HashMap<String,EmpregadoAssalariado>();
        for(Map.Entry<String, Empregado> e : empregados.entrySet()){
            Empregado empregado = e.getValue();
            if(empregado.getTipo().equals("assalariado")){
                empregadosAssalariados.put(e.getKey(), (EmpregadoAssalariado) empregado);
            }
        }
        return empregadosAssalariados;
    }

    public static String getAtributoEmpregado(String emp, String atributo) throws
            IdNuloException, EmpregadoNaoExisteException, AtributoNExisteException,
            EmpregadoNaoComissionadoException, EmpregadoNaoRecebeBancoException,
            EmpregadoNaoSindicalizadoException, TipoInvalidoException {
        //Método que retorna um atributo requerido pelo usuário
        if (Objects.equals(emp, "")) {
            throw new IdNuloException(); //caso o id seja nulo
        }
        if (!empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException(); //caso o id não seja nulo, mas não há nenhum empregado com o id requerido
        }
        if (!(Arrays.asList("nome", "endereco", "tipo", "salario", "sindicalizado", "comissao", "metodoPagamento",
                "banco", "agencia", "contaCorrente", "idSindicato", "taxaSindical").contains(atributo))) {
            throw new AtributoNExisteException(); //o atributo deve ser um desses da lista acima
        }
        Empregado empregado = empregados.get(emp);

        return switch (atributo) {
            case "nome" -> empregado.getNome();
            case "endereco" -> empregado.getEndereco();
            case "tipo" -> empregado.getTipo();
            case "salario" -> empregado.getSalario();
            case "sindicalizado" -> String.valueOf(empregado.getSindicalizado());
            case "comissao" -> {
                    if (empregado.getTipo().equals("comissionado")){
                    yield empregado.getComissao();
                    }else{
                    throw new EmpregadoNaoComissionadoException();
                }
            }
            case "metodoPagamento" -> empregado.getMetodoPagamento();
            case "banco" -> empregado.getBanco(empregado.getMetodoPagamento());
            case "agencia" -> empregado.getAgencia(empregado.getMetodoPagamento());
            case "contaCorrente" -> empregado.getContaCorrente(empregado.getMetodoPagamento());
            case "idSindicato" -> GerenciaSindicato.getIdSindicato(emp);
            case "taxaSindical" -> GerenciaSindicato.getTaxaSindical(emp);
            default -> throw new AtributoNExisteException(); //redundante com o if acima, porém o switch case precisa de um default para funcionar
        };
    }

    public static String getEmpregadoPorNome(String nome, Integer indice) throws NomeInexistenteException, EmpregadoNaoExisteException {
        //Método para obter um empregado a partir do nome
        if(empregados.isEmpty()){
            System.out.println("\nEmpregados vazios\n");
            throw new EmpregadoNaoExisteException();
        }
        for (Map.Entry<String, Empregado> entry : empregados.entrySet()) { //for que percorre a lista de empregados
            Empregado e = entry.getValue();
            String key = entry.getKey();
            if (nome.contains(e.getNome())) //caso haja um empregado com o nome requerido, é decrementado 1 do indice para garantir que o empregado é necessariamente o que o usuário quer
                indice--;

            if (indice == 0) //indice necessario para os caros em que há mais de um empregado com o mesmo nome
                return key;
        }
        throw new NomeInexistenteException(); //nao ha empregado com esse nome
    }

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, ComissaoNulaException, ComissaoNumericaException,
            ComissaoNegativaException, TipoNAplicavelException{ //Método para criar empregados assalariados e horistas (4 parametros)
        if(Objects.equals(tipo, "assalariado")){
            verificarErros.conferirErros(nome, endereco, tipo, salario);
            EmpregadoAssalariado empregado =
                    new EmpregadoAssalariado.EmpregadoAssalariadoBuilder()
                            .nome(nome)
                            .endereco(endereco)
                            .tipo(tipo)
                            .salario(salario)
                            .id(String.valueOf(idCounter++))
                            .metodoPagamento("emMaos")
                            .build();
            empregados.put(empregado.getId(), empregado);
            XMLUse.salvaEmpregadosXML(empregados, "./listaEmpregados.xml");
            return empregado.getId(); //empregado criado e o id sera gerado na classe empregado
        }else{
            verificarErros.conferirErros(nome, endereco, tipo, salario);
            EmpregadoHorista empregado =
                    new EmpregadoHorista.EmpregadoHoristaBuilder()
                            .nome(nome)
                            .endereco(endereco)
                            .tipo(tipo)
                            .salario(salario)
                            .id(String.valueOf(idCounter++))
                            .metodoPagamento("emMaos")
                            .build();
            empregados.put(empregado.getId(), empregado);
            return empregado.getId();
        }
    }

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao) throws
            SalarioNuloException, SalarioNumericoException, NomeNuloException, EnderecoNuloException,
            TipoInvalidoException, SalarioNegativoException, ComissaoNulaException, ComissaoNumericaException,
            ComissaoNegativaException, TipoNAplicavelException{ //Método para criar empregados comissionados (5 parâmetros)
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
                            .metodoPagamento("emMaos")
                            .build();
            empregados.put(empregado.getId(), empregado);
            XMLUse.salvaEmpregadosXML(empregados, "./listaEmpregados.xml");
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
            ValorTrueFalseException, EmpregadoNaoComissionadoException, AtributoNExisteException, TipoInvalidoException, MetodoPagInvalidoException,
            IdNuloException, ComissaoNumericaException, ComissaoNegativaException, EmpregadoNaoRecebeBancoException { //Método para alterar algum atributo de um empregado (3 parâmetros)
        if(emp.isEmpty()){ //qualquer outro atributo que queira ser alterado sera possível em outro metodo de alteraEmpregado
            throw new IdNuloException();
        }

        Empregado empregado = empregados.get(emp);

        if(empregados.isEmpty()){
            throw new EmpregadoNaoExisteException();
        }

        switch (atributo){
            case "nome" ->{
                if(valor.isEmpty()){
                    throw new NomeNuloException();
                }if(empregado == null){
                    throw new EmpregadoNaoExisteException();
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
                } else if(!Aritmetica.isNumeric(valor)) {
                    throw new SalarioNumericoException();
                } else if (Double.parseDouble(valor.replace(',', '.')) <= 0) {
                    throw new SalarioNegativoException();
                }
                empregado.setSalario(valor);
            }
            case "sindicalizado" ->{
                if(!valor.equals("false") && !valor.equals("true")){
                    throw new ValorTrueFalseException();
                }
                if(valor.equals("false")){
                    empregado.setSindicalizado(valor);
                    String key = null;
                    for(Map.Entry<String, MembroSindicato> entry : empregadosSindicalizados.entrySet()){
                        MembroSindicato membro = entry.getValue();
                        if(membro.getEmpregadoId().equals(emp)){
                            key = entry.getKey();
                        }
                    }
                    empregadosSindicalizados.remove(key);
                    XMLUse.salvaMembroSindicatoXML(empregadosSindicalizados, "./listaMembrosSindicato.xml");
                }
            }
            case "tipo" -> {
                if(!valor.equals("assalariado")){
                    throw new TipoInvalidoException();
                } else {
                    EmpregadoAssalariado novoEmpregado =
                        new EmpregadoAssalariado.EmpregadoAssalariadoBuilder()
                            .nome(empregado.getNome())
                            .endereco(empregado.getEndereco())
                            .tipo("assalariado")
                            .salario(empregado.getSalario())
                            .id(empregado.getId())
                            .build();
                    if(Objects.equals(empregado.getMetodoPagamento(), "banco")){
                        empregado.setBanco(empregado.getBanco(empregado.getMetodoPagamento()));
                        empregado.setAgencia(empregado.getAgencia(empregado.getMetodoPagamento()));
                        empregado.setContaCorrente(empregado.getContaCorrente(empregado.getMetodoPagamento()));
                    }
                    empregados.put(novoEmpregado.getId(), novoEmpregado);
                }

            }
            case "comissao" ->{
                if(!(Objects.equals(empregado.getTipo(), "comissionado"))){
                    throw new EmpregadoNaoComissionadoException();
                }else if(valor.isEmpty()){
                    throw new ComissaoNulaException();
                }else if(!(Aritmetica.isNumeric(valor))){
                    throw new ComissaoNumericaException();
                }else if (Double.parseDouble(valor.replace(',', '.')) <= 0) {
                    throw new ComissaoNegativaException();
                }
                EmpregadoComissionado empregadoComissionado = (EmpregadoComissionado) empregado;
                empregadoComissionado.setComissao(valor);
            }
            case "metodoPagamento" ->{
                if(!(Arrays.asList("emMaos", "correios").contains(valor))){
                    throw new MetodoPagInvalidoException();
                }
                empregado.setMetodoPagamento(valor);
            }
            default -> throw new AtributoNExisteException();
        }
    }

    public static void alteraEmpregado(String emp, String atributo, String valor, String pagamento) throws IdNuloException, EmpregadoNaoRecebeBancoException {//altera o atributo de um empregado (4 parametros)
        Empregado empregado = empregados.get(emp);

        if(emp == null){
            throw new IdNuloException();
        }
        if(valor.equals("horista")) {
            EmpregadoHorista novoEmpregado =
                new EmpregadoHorista.EmpregadoHoristaBuilder()
                        .nome(empregado.getNome())
                        .endereco(empregado.getEndereco())
                        .tipo("horista")
                        .salario(pagamento)
                        .id(empregado.getId())
                        .metodoPagamento(empregado.getMetodoPagamento())
                        .build();
            if(Objects.equals(empregado.getMetodoPagamento(), "banco")){
                empregado.setBanco(empregado.getBanco(empregado.getMetodoPagamento()));
                empregado.setAgencia(empregado.getAgencia(empregado.getMetodoPagamento()));
                empregado.setContaCorrente(empregado.getContaCorrente(empregado.getMetodoPagamento()));
            }
            empregados.put(novoEmpregado.getId(), novoEmpregado);
        } else if(valor.equals("comissionado")){
            EmpregadoComissionado novoEmpregado =
                    new EmpregadoComissionado.EmpregadoComissionadoBuilder()
                            .nome(empregado.getNome())
                            .endereco(empregado.getEndereco())
                            .tipo("comissionado")
                            .salario(empregado.getSalario())
                            .id(empregado.getId())
                            .metodoPagamento(empregado.getMetodoPagamento())
                            .build();
            novoEmpregado.setComissao(pagamento);
            if(Objects.equals(empregado.getMetodoPagamento(), "banco")){
                empregado.setBanco(empregado.getBanco(empregado.getMetodoPagamento()));
                empregado.setAgencia(empregado.getAgencia(empregado.getMetodoPagamento()));
                empregado.setContaCorrente(empregado.getContaCorrente(empregado.getMetodoPagamento()));
            }
            empregados.put(novoEmpregado.getId(), novoEmpregado);
            XMLUse.salvaEmpregadosXML(empregados, "./listaEmpregados.xml");
        }
    }

    public static void alteraEmpregado(String emp, String atributo, String valor, String idSindicato, String taxaSindical) throws //altera o atributo de um empregado (5 parametros)
            IdSindicatoNuloException, TaxaSindicalNulaException, TaxaSindicalNumericaException, OutroEmpregadoSindicatoException, TaxaSindicalPositivaException {
        if(idSindicato.isEmpty()){
            throw new IdSindicatoNuloException();
        }else if(taxaSindical.isEmpty()){
            throw new TaxaSindicalNulaException();
        } else if(!Aritmetica.isNumeric((taxaSindical))) {
            throw new TaxaSindicalNumericaException();
        }
        else if(Double.parseDouble(taxaSindical.replace(',','.'))<= 0){
            throw new TaxaSindicalPositivaException();
        }  else if(!Erros.verificarIdSindicato(idSindicato)) {
            throw new OutroEmpregadoSindicatoException();
        }
        empregados.get(emp).setSindicalizado("true");
        MembroSindicato novoMembroSindicato = new MembroSindicato.MembroSindicatoBuilder()
                .idMembro(idSindicato)
                .taxaSindical(taxaSindical)
                .empregado(empregados.get(emp))
                .build();
        empregadosSindicalizados.put(idSindicato, novoMembroSindicato);
        XMLUse.salvaEmpregadosXML(empregados, "./listaEmpregados.xml");
    }

    public static void alteraEmpregado (String emp, String atributo, String valor1, String banco, String agencia, String contaCorrente) throws //altera o atributo de um empregado (6 parametros)
            EmpregadoNaoExisteException, BancoNuloException, AgenciaNuloException, ContaCorrenteNuloException, AtributoNExisteException, MetodoPagInvalidoException {
        if(emp == null){
            throw new EmpregadoNaoExisteException();
        }
        Empregado empregado = empregados.get(emp);
        switch (atributo){
            case "metodoPagamento" ->{
                if(!(Arrays.asList("emMaos", "banco", "correios").contains(valor1))){
                    throw new MetodoPagInvalidoException();
                } else if(banco.isEmpty()){
                    throw new BancoNuloException();
                } else if (agencia.isEmpty()){
                    throw new AgenciaNuloException();
                } else if (contaCorrente.isEmpty()) {
                    throw new ContaCorrenteNuloException();
                }
                empregado.setMetodoPagamento(valor1);
                empregado.setBanco(banco);
                empregado.setAgencia(agencia);
                empregado.setContaCorrente(contaCorrente);
            }
            case "banco" ->{
                if (valor1.isEmpty()){
                    throw new BancoNuloException();
                }
                empregado.setMetodoPagamento(valor1);
            }
            case "agencia" ->{
                if (valor1.isEmpty()){
                    throw new AgenciaNuloException();
                }
            }
            case "contaCorrente" ->{
                if(valor1.isEmpty()){
                    throw new ContaCorrenteNuloException();
                }
            }
            default -> throw new AtributoNExisteException();
        }
        XMLUse.salvaEmpregadosXML(empregados, "./listaEmpregados.xml");
    }

    public static void removerEmpregado (String emp) throws IdNuloException, EmpregadoNaoExisteException{ //remove um empregado do hashmap
        if (emp.isEmpty())
            throw new IdNuloException();
        Empregado empregado = empregados.get(emp);
        if (empregado == null)
            throw new EmpregadoNaoExisteException();
        empregados.remove(emp);
        XMLUse.salvaEmpregadosXML(empregados, "./listaEmpregados.xml");
    }

    public static String getHorasNormaisTrabalhadas(String emp, String dataInicial, String dataFinal) throws EmpregadoNaoHoristaException, //calcula as horas normais de um empregado horista
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
                if(LocalDate.parse(c.getData(), dataFormato).isEqual(Inicial) || (LocalDate.parse(c.getData(), dataFormato).isAfter(Inicial) && LocalDate.parse(c.getData(), dataFormato).isBefore(Final))){
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

    public static String getHorasExtrasTrabalhadas(String emp, String dataInicial, String dataFinal) throws IdNuloException, EmpregadoNaoHoristaException, //calcula as horas extras de um empregado horista
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
                if(LocalDate.parse(c.getData(), dataFormato).isEqual(Inicial) || (LocalDate.parse(c.getData(), dataFormato).isAfter(Inicial) && LocalDate.parse(c.getData(), dataFormato).isBefore(Final))){
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

    public static void lancaCartao(String emp, String data, String horas) throws IdNuloException, EmpregadoNaoExisteException, EmpregadoNaoHoristaException, DataInvalidaException, HoraPositivaException{ //referencia para um metodo de EmpregadoHorista
        EmpregadoHorista.lancaCartao(emp, data, horas);
    }
}