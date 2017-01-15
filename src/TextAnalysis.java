
import org.apache.commons.validator.routines.UrlValidator;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;


/**
 * Created by shaheensharifian on 1/14/17.
 */
public class TextAnalysis {
    public static void main(String[] args) throws IOException, JSONException, MalformedURLException {

        RedditSearchScaper scraper = new RedditSearchScaper("Delta");

        List<LinkModel> list = scraper.getRedditLinksFromWeb();

        for (LinkModel link : list) {
            if(isValidURL(link.getPermalink_url())) {
                String keywords = getKeywords(link.getPermalink_url());
                System.out.println(keywords);
            }
            // Get sentiment Analysis

        }

    }

    private static String sanitizePage(String url) {

        Document doc = null;
        StringBuilder sb = new StringBuilder();
        try {
            doc = Jsoup.connect(url).get();
            URL url1 = new URL(url);

            Elements texts = doc.getElementsByTag("p");

            for (Element text : texts) {
                String textString = text.toString();
                textString = textString.replaceAll("<p>", "");
                textString = textString.replaceAll("</p>", "");
                textString = textString.replaceAll("\"", "");
                textString = textString.replaceAll("<em>", "");
                textString = textString.replaceAll("</em>", "");
                textString = Jsoup.clean(textString, Whitelist.simpleText());
                sb.append(textString);
            }
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }
        return sb.toString();
    }

    private static boolean isValidURL(String url) {

        String[] schemes = {"http","https"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);
        return (urlValidator.isValid(url));
    }

    public static String getKeywords(String url) {
        String sanitized = sanitizePage(url);
        int length = sanitized.length();
        String keywords = "";
        if (length > 500) {
            KeywordExtraction extractor = new KeywordExtraction(sanitized.substring(0, 500));
            keywords = extractor.getKeywords();
        } else {
            KeywordExtraction extractor = new KeywordExtraction(sanitized);
            keywords = extractor.getKeywords();
        }
        return keywords;
    }

}
