package kaique;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WebScrapingTest {

    public static void main(String[] args) {

        try {

            String url = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
            Document doc = Jsoup.connect(url).get();


            Elements links = doc.select("a[href$=.pdf]");
            int count = 0;
            for (Element link : links) {
                String pdfUrl = link.attr("abs:href");
                String fileName = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);
                if (fileName.contains("Anexo_I") || fileName.contains("Anexo_II")) {
                    downloadFile(pdfUrl, fileName); // Download
                    count++;
                    if (count == 2) break; // Para após encontrar Anexo I e Anexo II
                }
            }

            // Compactar em ZIP
            zipFiles(new String[]{"Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf", "Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf"}, "Anexos.zip");

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
