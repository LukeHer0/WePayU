package br.ufal.ic.p2.wepayu.models;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Aritmetica {
    private final DecimalFormat dF = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str.replace(",", "."));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String doubleFormat(String str) {
        str =  dF.format(Double.parseDouble(str.replace(',', '.')));

        return str.replace(".", "");
    }
}
