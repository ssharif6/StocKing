
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;


/**
 * Created by shaheensharifian on 1/14/17.
 */
public class TextAnalysis {



    public static void main(String[] args) throws IOException, JSONException, MalformedURLException {

        double x = getFinalScore("Uber");
        System.out.println(x);

    }

    // API METHOD
    public static double getFinalScore(String query) throws JSONException, ProtocolException {
        int totalRedditScore = 0;
        double totalEvalScore = 0.0;
        double resultFinal = 0.0;
        double totalWeight = 0.0;

        RedditSearchScaper scraper = new RedditSearchScaper("Delta");

        List<LinkModel> list = scraper.getRedditLinksFromWeb();

        for (LinkModel link : list) {
            String keywords = getKeywords(link.getPermalink_url());
            double score = getSentScore(keywords);
            if (score > 0 || !keywords.isEmpty()) {
                link.setEval_score(score);
                totalEvalScore += score;
                totalRedditScore += link.getScore();
            } else {
                link.setBroken(true);
            }


        }

        for (LinkModel link : list) {
            if(link.getEval_score() > 0 || !link.isBroken()) {
                double weight = ((double)link.getScore() / (double)totalRedditScore) * 100;
                double m1 = (link.getEval_score() * 100) * weight;
                resultFinal += m1;
                totalWeight += weight;
            }
        }
        System.out.println("ResultFinal : " + resultFinal);
        return (resultFinal / totalWeight);
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
