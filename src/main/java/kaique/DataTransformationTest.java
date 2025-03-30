package kaique;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataTransformationTest {

    static class Procedimento {
        String procedimento;
        String rn;
        String vigencia;
        String segOdontologica;
        String segAmbulatorial;
        String hco;
        String hso;
        String ref;
        String pac;
        String dut;
        String subgrupo;
        String grupo;
        String capitulo;

        Procedimento(String procedimento, String rn, String vigencia, String segOdontologica, String segAmbulatorial,
                     String hco, String hso, String ref, String pac, String dut, String subgrupo, String grupo, String capitulo) {
            this.procedimento = procedimento != null ? procedimento : "";
            this.rn = rn != null ? rn : "";
            this.vigencia = vigencia != null ? vigencia : "";
            this.segOdontologica = segOdontologica != null ? segOdontologica : "";
            this.segAmbulatorial = segAmbulatorial != null ? segAmbulatorial : "";
            this.hco = hco != null ? hco : "";
            this.hso = hso != null ? hso : "";
            this.ref = ref != null ? ref : "";
            this.pac = pac != null ? pac : "";
            this.dut = dut != null ? dut : "";
            this.subgrupo = subgrupo != null ? subgrupo : "PROCEDIMENTOS GERAIS";
            this.grupo = grupo != null ? grupo : "PROCEDIMENTOS GERAIS";
            this.capitulo = capitulo != null ? capitulo : "PROCEDIMENTOS GERAIS";
        }

        String toCSVRow() {
            return String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n",
                    procedimento, rn, vigencia, segOdontologica, segAmbulatorial, hco, hso, ref, pac, dut, subgrupo, grupo, capitulo);
        }
    }

    public static void main(String[] args) {
        String pdfPath = "Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf";
        String csvPath = "rol_procedimentos.csv";

        try {

            File pdfFile = new File(pdfPath);
            PDDocument document = PDDocument.load(pdfFile);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();


            List<Procedimento> procedimentos = parsePDFText(text);


            gerarCSV(procedimentos, csvPath);

        } catch (IOException e) {
            System.err.println("Erro ao processar o PDF: " + e.getMessage());
        }
    }

    private static List<Procedimento> parsePDFText(String text) {
        List<Procedimento> procedimentos = new ArrayList<>();
        String[] linhas = text.split("\n");

        for (String linha : linhas) {
            linha = linha.trim();
            if (linha.isEmpty() || linha.startsWith("ROL DE PROCEDIMENTOS") || linha.startsWith("(") ||
                    linha.startsWith("|") || linha.contains("Legenda:") || linha.contains("OD:") || linha.contains("AMB:")) {
                continue;
            }

            String[] partes = linha.split("\\s+");
            int idx = 0;


            StringBuilder nome = new StringBuilder();
            String rn = "", vigencia = "", segOdontologica = "", segAmbulatorial = "", hco = "", hso = "", ref = "", pac = "", dut = "";
            while (idx < partes.length && !partes[idx].matches("\\d{3,7}/\\d{4}") && !partes[idx].matches("\\d{2}/\\d{2}/\\d{4}") &&
                    !partes[idx].equals("OD") && !partes[idx].equals("AMB") && !partes[idx].equals("HCO") &&
                    !partes[idx].equals("HSO") && !partes[idx].equals("REF") && !partes[idx].equals("PAC") &&
                    !partes[idx].matches("\\d+")) {
                nome.append(partes[idx]).append(" ");
                idx++;
            }
            String procedimento = nome.toString().trim();


            if (idx < partes.length && partes[idx].matches("\\d{3,7}/\\d{4}")) {
                rn = partes[idx];
                idx++;
                if (idx < partes.length && partes[idx].matches("\\d{2}/\\d{2}/\\d{4}")) {
                    vigencia = partes[idx];
                    idx++;
                }
            }


            while (idx < partes.length) {
                switch (partes[idx]) {
                    case "OD": segOdontologica = "X"; idx++; break;
                    case "AMB": segAmbulatorial = "X"; idx++; break;
                    case "HCO": hco = "X"; idx++; break;
                    case "HSO": hso = "X"; idx++; break;
                    case "REF": ref = "X"; idx++; break;
                    case "PAC": pac = "X"; idx++; break;
                    default:
                        if (partes[idx].matches("\\d+")) {
                            dut = partes[idx];
                            idx++;
                        } else if (linha.contains("COM DIRETRIZ DE UTILIZAÇÃO")) {
                            dut = "X";
                        }
                        idx++;
                        break;
                }
            }

            procedimentos.add(new Procedimento(procedimento, rn, vigencia, segOdontologica, segAmbulatorial, hco, hso, ref, pac, dut, null, null, null));
        }
        return procedimentos;
    }

    private static void gerarCSV(List<Procedimento> procedimentos, String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {

            writer.write("\"Procedimento\",\"RN\",\"VIGENCIA\",\"Seg. Odontológica\",\"Seg. Ambulatorial\",\"HCO\",\"HSO\",\"REF\",\"PAC\",\"DUT\",\"Subgrupo\",\"Grupo\",\"Capítulo\"\n");
            for (Procedimento p : procedimentos) {
                writer.write(p.toCSVRow());
            }
            System.out.println("Arquivo CSV gerado com sucesso: " + nomeArquivo);
            zipFiles(new String[]{nomeArquivo}, "Teste_Kaique.zip");
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo CSV: " + e.getMessage());
        }
    }

    private static void zipFiles(String[] files, String zipFileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipFileName);
        ZipOutputStream zos = new ZipOutputStream(fos);
        byte[] buffer = new byte[1024];
        for (String file : files) {
            FileInputStream fis = new FileInputStream(file);
            zos.putNextEntry(new ZipEntry(file));
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            fis.close();
            zos.closeEntry();
        }
        zos.close();
        fos.close();
        System.out.println("Compactado: " + zipFileName);
    }


}
