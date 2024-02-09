package br.ufal.ic.p2.wepayu.sindicato;

import br.ufal.ic.p2.wepayu.empregados.Empregado;

import java.io.Serializable;
import java.util.ArrayList;

public class MembroSindicato implements Serializable {
    private String idMembro;
    private String taxaSindical;
    private Empregado empregado;
    public ArrayList<TaxaServico> taxasServicos;

    public MembroSindicato() {
    }

    private MembroSindicato(String idMembro, String taxaSindical, Empregado empregado){
        this.idMembro = idMembro;
        this.taxaSindical = taxaSindical;
        this.empregado = empregado;
        this.taxasServicos = new ArrayList<>();
    }

    public static class MembroSindicatoBuilder{
        protected String idMembro;
        protected String taxaSindical;
        protected Empregado empregado;

        public MembroSindicatoBuilder idMembro(String idMembro){
            this.idMembro = idMembro;
            return this;
        }
        public MembroSindicatoBuilder taxaSindical(String taxaSindical){
            this.taxaSindical = taxaSindical;
            return this;
        }

        public MembroSindicatoBuilder empregado(Empregado empregado){
            this.empregado = empregado;
            return this;
        }

        public MembroSindicato build(){
            return new MembroSindicato(idMembro, taxaSindical, empregado);
        }
    }

    public String getIdMembro(){
        return this.idMembro;
    }

    public String getTaxaSindical(){
        return this.taxaSindical;
    }

    public String getEmpregado() {
        return this.empregado.getId();
    }

    public void setTaxas(TaxaServico taxa){
        this.taxasServicos.add(taxa);
    }

}
