/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerarscriptsenem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.StringTokenizer;

/**
 *
 * @author Marcus Vinicius
 */
public class GerarScriptCassandra {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        String fileName = "C:\\Users\\Marcus Vinicius\\Documents\\UNB\\microdados_enem_2015\\DADOS\\MICRODADOS_ENEM_2015.csv";
        PrintStream out = new PrintStream(System.out, true, "UTF-8");

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "ISO-8859-1"));
            String strLine = null;
            StringTokenizer st = null;
            int numeroLinha = 0;
            int contador = 0;
            int prefFile = 1;
            FileWriter arquivo = null;

            br.readLine();
            while ((fileName = br.readLine()) != null) {
                StringBuilder insert = new StringBuilder();
                numeroLinha++;
                insert.append("INSERT INTO enem.microdados_enem_2015(NU_INSCRICAO ,NU_ANO, NO_MUNICIPIO_RESIDENCIA, SG_UF_RESIDENCIA, NU_NOTA_COMP1, NU_NOTA_COMP2, NU_NOTA_COMP3, NU_NOTA_COMP4, NU_NOTA_COMP5, NU_NOTA_REDACAO) VALUES(");

                if (contador == 300000 || arquivo == null) {
                    if (arquivo != null) {
                        arquivo.close();
                    }
                    contador = 1;
                    arquivo = new FileWriter(new File("D:\\UNB\\ENEM_SCRIPT_" + prefFile + ".cql"));

                    prefFile++;
                }
                contador++;
                String[] result = fileName.split(",");

                insert.append("").append(result[0].replace("'", "")).append(", ");

                for (int x = 1; x < result.length; x++) {
                    if (x == 2 || x == 4) {
                        x++;
                    }
                    if (x == 6) {
                        x = 110;
                    }
                    if (x == 116) {
                        break;
                    }
                    if (x < (result.length - 1)) {

                        if (x != 115) {
                            insert.append("'").append(result[x].replace("'", "")).append("', ");

                        } else {
                            insert.append("'").append(result[x].replace("'", "")).append("'");
                        }
                    }
                }
                insert.append(");\n");
                arquivo.write(insert.toString());
            }
            System.out.println((numeroLinha - 1) + " inserções realizadas ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
