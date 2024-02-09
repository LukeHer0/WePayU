package br.ufal.ic.p2.wepayu.XMLUse;

import br.ufal.ic.p2.wepayu.empregados.Empregado;
import br.ufal.ic.p2.wepayu.empregados.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.empregados.comissionado.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.empregados.horista.EmpregadoHorista;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    public static HashMap<String, Empregado> carregarEmpregadosXML(String arquivo){
        HashMap<String, Empregado> empregados = new HashMap<>();
        try (XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(arquivo)))) {
            Object obj;
            obj = decoder.readObject();
//                for(Map.Entry<?, ?> entry : ((HashMap<?, ?>) obj).entrySet()) {
//                    Empregado empregado = (Empregado) entry.getValue();
//                    if(empregado.getTipo().equals("horista")) {
//                        empregados.put((String) entry.getKey(), (EmpregadoHorista) entry.getValue());
//                    } else if(empregado.getTipo().equals("comissionado")){
//                            empregados.put((String) entry.getKey(), (EmpregadoComissionado) entry.getValue());
//                    }else if (empregado.getTipo().equals("assalariado")){
//                        empregados.put((String) entry.getKey(), (EmpregadoAssalariado) entry.getValue());
//                    }
//                }
                empregados = (HashMap<String, Empregado>) obj;

        } catch (Exception ignored) {
        }
        return empregados;
    }
}
