package br.ufal.ic.p2.wepayu.XMLUse;

import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.empregados.comissionado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.empregados.horista.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.sindicato.MembroSindicato;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.*;

public class XMLUse implements Serializable{
    public XMLUse(){
    }
    public static void salvaEmpregadosXML(HashMap<String, Empregado> empregados, String arquivo) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(arquivo)))) {
            encoder.writeObject(empregados);
            encoder.flush();
        } catch (Exception ignored) {
        }
    }

    public static LinkedHashMap<String, Empregado> carregarEmpregadosXML(String arquivo){
        LinkedHashMap<String, Empregado> empregados = new LinkedHashMap<>();
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)))) {
            Object obj;
            obj = decoder.readObject();
            empregados = (LinkedHashMap<String, Empregado>) obj;
        } catch (Exception ignored) {

        }
        return empregados;
    }

    public static void salvaMembroSindicatoXML(HashMap<String, MembroSindicato> membro, String arquivo) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(arquivo)))) {
            encoder.writeObject(membro);
            encoder.flush();
        } catch (Exception ignored) {
        }
    }

    public static LinkedHashMap<String, MembroSindicato> carregarMembroSindicatoXML(String arquivo){
        LinkedHashMap<String, MembroSindicato> membros = new LinkedHashMap<>();
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)))) {
            Object obj;
            obj = decoder.readObject();
            membros = (LinkedHashMap<String, MembroSindicato>) obj;

        } catch (Exception ignored) {
        }
        return membros;
    }

    public static void salvaAgendasXML(ArrayList<String> agendas, String arquivo){
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(arquivo)))) {
            encoder.writeObject(agendas);
            encoder.flush();
        } catch (Exception ignored) {
        }
    }

    public static ArrayList<String> carregarAgendasXML(String arquivo){
        ArrayList<String> agendas = new ArrayList<>();
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)))){
            Object obj;
            obj = decoder.readObject();
            agendas = (ArrayList<String>) obj;

            return agendas;
        } catch (Exception ignored) {

        }

        if (agendas.isEmpty()) {
            agendas.addAll(Arrays.asList("mensal $", "semanal 5", "semanal 2 5"));
        }
        return agendas;
    }
}