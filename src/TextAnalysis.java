
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by shaheensharifian on 1/14/17.
 */
public class TextAnalysis {
    public static void main(String[] args) throws IOException {
//        URL url = new URL("http://www.latimes.com/business/la-fi-uber-funding-20141205-story.html");
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        if(connection.getResponseCode() != 200) {
//            throw new IOException(connection.getResponseMessage());
//        }
//
//        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//        StringBuilder sb = new StringBuilder();
//        String line;
//
//        while((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//
//        rd.close();
//
//        connection.disconnect();
//        System.out.print(sb.toString());


        Document doc = Jsoup.connect("http://www.latimes.com/business/la-fi-uber-funding-20141205-story.html").get();
        System.out.println(doc.getAllElements().toString());
    }
}
