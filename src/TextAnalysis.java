
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;


/**
 * Created by shaheensharifian on 1/14/17.
 */
public class TextAnalysis {
    public static void main(String[] args) throws IOException, JSONException {
//



        RedditSearchScaper scraper = new RedditSearchScaper("Delta");
        scraper.getRedditLinksFromWeb();
        //System.out.println(getKeywords(""));

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
                textString = textString.replaceAll("<a ", "");
                textString = textString.replaceAll("/a>", "");
                sb.append(textString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String getKeywords(String query) {
        String sanitized = sanitizePage("http://www.latimes.com/business/la-fi-uber-funding-20141205-story.html");
        KeywordExtraction extractor = new KeywordExtraction(sanitized);
        String keywords = extractor.getKeywords();
        return keywords;
    }

}
