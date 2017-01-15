
import org.jsoup.Jsoup;
<<<<<<< HEAD
<<<<<<< HEAD

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
=======
=======
>>>>>>> f7dab03... Blah
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

<<<<<<< HEAD
>>>>>>> f7dab03... Blah
=======
>>>>>>> f7dab03... Blah

/**
 * Created by shaheensharifian on 1/14/17.
 */
public class TextAnalysis {
    public static void main(String[] args) throws IOException {
<<<<<<< HEAD
<<<<<<< HEAD
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




        getSentimentResult("");
=======
=======
>>>>>>> f7dab03... Blah
//



        RedditSearchScaper scraper = new RedditSearchScaper("Delta");
        scraper.getRedditLinksFromWeb();
        //getSentimentResult("");
<<<<<<< HEAD
>>>>>>> f7dab03... Blah
=======
>>>>>>> f7dab03... Blah

    }

    private static String sanitizePage(String url) {
        Document doc = null;
        StringBuilder sb = new StringBuilder();
        try {
            doc = Jsoup.connect(url).get();
            Elements texts = doc.getElementsByTag("p");

            for (Element text : texts) {
                String textString = text.toString();
                textString = textString.replaceAll("<p>", "");
                textString = textString.replaceAll("</p>", "");
<<<<<<< HEAD
<<<<<<< HEAD
=======
                textString = textString.replaceAll("<a ", "");
                textString = textString.replaceAll("/a>", "");
>>>>>>> f7dab03... Blah
=======
                textString = textString.replaceAll("<a ", "");
                textString = textString.replaceAll("/a>", "");
>>>>>>> f7dab03... Blah
                sb.append(textString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getSentimentResult(String query) {
<<<<<<< HEAD
<<<<<<< HEAD
        String sanitized = sanitizePage("http://www.latimes.com/business/la-fi-uber-funding-20141205-story.html").substring(0, 550);
=======
        String sanitized = sanitizePage("http://www.latimes.com/business/la-fi-uber-funding-20141205-story.html");
>>>>>>> f7dab03... Blah
=======
        String sanitized = sanitizePage("http://www.latimes.com/business/la-fi-uber-funding-20141205-story.html");
>>>>>>> f7dab03... Blah
        KeywordExtraction extractor = new KeywordExtraction(sanitized);
        String keywords = extractor.getKeywords();
        return keywords;
    }

}
