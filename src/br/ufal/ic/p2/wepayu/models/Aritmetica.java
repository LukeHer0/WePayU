package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Aritmetica {


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str.replace(",", "."));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String doubleFormat(String str) {
        DecimalFormat dF = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        str =  dF.format(Double.parseDouble(str.replace(',', '.')));

        return str.replace(".", "");
    }

    public static LocalDate toData(String data) throws
            DataInvalidaException{
        LocalDate date;

        DateTimeFormatter dataa = DateTimeFormatter.ofPattern("d/M/yyyy");
        date = LocalDate.parse(data, dataa);
        return date;

    }

//    public String
}
