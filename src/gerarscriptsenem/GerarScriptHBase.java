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
import java.util.Scanner;

/**
 *
 * @author Marcus Vinicius
 */
public class GerarScriptHBase {

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

            fileName = br.readLine();
            String[] cabecalho = fileName.split(",");
            while ((fileName = br.readLine()) != null) {
                StringBuilder insert = new StringBuilder();
                numeroLinha++;

                if (contador == 300000 || arquivo == null) {
                    if (arquivo != null) {
                        arquivo.close();
                    }
                    contador = 1;
                    arquivo = new FileWriter(new File("D:\\UNB\\HBASE\\ENEM_SCRIPT_" + prefFile + ".txt"));

                    prefFile++;
                }
                contador++;
                String[] result = fileName.split(",");

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

                        insert.append("put 'MICRODADOS_ENEM_2015' , '").append(result[0]).append("',");
                        insert.append("'DADOS_ENEM:").append(cabecalho[x]).append("' , '").append(result[x].replace("'", "")).append("' ");
                        insert.append("\n");
                    }

                }

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
