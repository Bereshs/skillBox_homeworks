import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;

public class ImagesDownloader {
    public static void main(String[] args) {
        String url = "https://skillbox.ru/";
        HashSet<String> links = new HashSet<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements images = (Elements) doc.select("img");
            for (Element image : images) {
                links.add(image.attr("abs:src"));
            }
            int number = 0;
            for (String link : links) {
                String fileExtension = link.replaceAll(
                                "^.+\\.", "")
                        .replace("?.+$", "");
                String filePath = "data/" + number++ + "." + fileExtension;
                try {
                    download(link, filePath);
                } catch (IOException ex) {
                    System.out.println("bad link " + link);
                }
            }
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void download(String link, String pathToWrite) throws IOException {
        URLConnection connection = new URL(link).openConnection();
        InputStream inStream = connection.getInputStream();
        FileOutputStream outStream = new FileOutputStream(pathToWrite);
        int element;
        while ((element = inStream.read()) != -1) {
            outStream.write(element);
        }
        outStream.flush();
        outStream.close();
        inStream.close();
    }


}