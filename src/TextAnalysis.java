
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

        RedditSearchScaper scraper = new RedditSearchScaper("Apple");

        List<LinkModel> list = scraper.getRedditLinksFromWeb();

        for (LinkModel link : list) {
                String keywords = getKeywords(link.getPermalink_url());
                double score = getSentScore(keywords);
                if (score > 0 || !keywords.isEmpty()) {
                    System.out.println(score);
                    System.out.println(keywords);
                }
            // Get sentiment Analysis

        }

    }
    public static double getSentScore(String keywords) {
        SentimentEval eval = new SentimentEval(keywords);
        return eval.evalScore();
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
