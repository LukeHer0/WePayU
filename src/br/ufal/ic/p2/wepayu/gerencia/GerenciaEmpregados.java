package br.ufal.ic.p2.wepayu.gerencia;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Aritmetica;
import br.ufal.ic.p2.wepayu.models.Erros;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Enumeration;

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

    public static void removerEmpregado (String emp) throws IdNuloException, EmpregadoNaoExisteException{
        if (emp.equals(""))
            throw new IdNuloException();

        Empregado empregado = empregados.get(emp);

        if (empregado == null)
            throw new EmpregadoNaoExisteException();

        empregados.remove(emp);
    }
}
