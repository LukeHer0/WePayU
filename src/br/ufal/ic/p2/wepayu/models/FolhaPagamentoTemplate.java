package br.ufal.ic.p2.wepayu.models;

import java.io.BufferedWriter;
import java.io.IOException;

public class FolhaPagamentoTemplate {
    public static void templateInicioHorista(BufferedWriter bufferedWriter, String data) throws IOException {
        bufferedWriter.write("FOLHA DE PAGAMENTO DO DIA " + data);
        bufferedWriter.newLine();
        bufferedWriter.write("====================================");
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===================== HORISTAS ================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("===============================================================================================================================");
        bufferedWriter.newLine();
        bufferedWriter.write("Nome                                 Horas Extra Salario Bruto Descontos Salario Liquido Metodo");
        bufferedWriter.newLine();
        bufferedWriter.write("==================================== ===== ===== ============= ========= =============== ======================================\n");
        bufferedWriter.newLine();
    }
}
