package kaique;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class DatabasePreparation {

    public static void main(String[] args) {

        try {

            String demo2023Url1 = "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2023/1T2023.zip";
            downloadAndExtract(demo2023Url1, "1T2023.zip");

            String demo2023Url2 = "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2023/2T2023.zip";
            downloadAndExtract(demo2023Url2, "2T2023.zip");

            String demo2023Url3 = "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2023/3T2023.zip";
            downloadAndExtract(demo2023Url3, "3T2023.zip");

            String demo2023Url4 = "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2023/4T2023.zip";
            downloadAndExtract(demo2023Url4, "4T2023.zip");

            String demo2024Url1 = "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2024/1T2024.zip";
            downloadAndExtract(demo2024Url1, "1T2024.zip");

            String demo2024Url2 = "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2024/2T2024.zip";
            downloadAndExtract(demo2024Url2, "2T2024.zip");

            String demo2024Url3 = "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2024/3T2024.zip";
            downloadAndExtract(demo2024Url3, "3T2024.zip");

            String demo2024Url4 = "https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/2024/4T2024.zip";
            downloadAndExtract(demo2024Url4, "4T2024.zip");

            String csvUrl2 = "https://dadosabertos.ans.gov.br/FTP/PDA/operadoras_de_plano_de_saude_ativas/Relatorio_cadop.csv";
            downloadFile(csvUrl2, "operadoras_ativas.csv");



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadFile(String fileUrl, String fileName) throws IOException {
        URL url = new URL(fileUrl);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
        System.out.println("Baixado: " + fileName);
    }

    private static void downloadAndExtract(String fileUrl, String fileName) throws IOException {
        // Primeiro, faz o download do arquivo ZIP
        downloadFile(fileUrl, fileName);

        // Agora, descompacta o arquivo ZIP
        extractZip(fileName);
    }

    private static void extractZip(String zipFileName) throws IOException {
        File zipFile = new File(zipFileName);

        // Abre o arquivo ZIP
        try (ZipFile zip = new ZipFile(zipFile)) {
            zip.stream().forEach(entry -> {
                try {
                    String fileName = entry.getName();
                    // Extrai diretamente no diretório atual, mantendo a estrutura de subdiretórios, se houver
                    File newFile = new File(fileName);

                    // Cria subdiretórios, se houver
                    if (entry.isDirectory()) {
                        newFile.mkdirs();
                    } else {
                        // Verifica se há um diretório pai; se não houver, não tenta criar
                        File parentDir = newFile.getParentFile();
                        if (parentDir != null) {
                            parentDir.mkdirs();
                        }

                        // Extrai o arquivo
                        try (InputStream is = zip.getInputStream(entry);
                             FileOutputStream fos = new FileOutputStream(newFile)) {
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = is.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                        }
                    }
                    System.out.println("Descompactado: " + newFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


         zipFile.delete();
    }




}
