package br.ufal.ic.p2.wepayu.empregados;

import br.ufal.ic.p2.wepayu.gerencia.TaxaServico;

import java.util.ArrayList;

public class MembroSindicato {
    public String idMembro;
    public String taxaSindical;
    public ArrayList<TaxaServico> taxaServicos;

//    public MembroSindicatoBuilder(){
//        this.taxaServico = new ArrayList<TaxaServico>();
//    }

    public String getIdMembro(){
        return this.idMembro;
    }

    public String getTaxaSindical(){
        return this.taxaSindical;
    }

//    public static void setTaxas(TaxaServico taxa){
//        this.taxaServicos.add(taxa);
//    }

}
