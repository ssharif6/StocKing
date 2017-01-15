package org.lasred;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

@Path("/getScore")
public class TextAnalysisService {

    // API METHOD
	@GET
	@Path("/{param}")
    public static Response getFinalScore(String query) throws JSONException, ProtocolException {
        int totalRedditScore = 0;
        double resultFinal = 0.0;
        double totalWeight = 0.0;

        RedditSearchScaper scraper = new RedditSearchScaper(query);

        List<LinkModel> list = scraper.getRedditLinksFromWeb();

        for (LinkModel link : list) {
            String keywords = getKeywords(link.getPermalink_url());
            double score = getSentScore(keywords);
            if (score > 0 || !keywords.isEmpty()) {
                link.setEval_score(score);
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
        double value = (resultFinal / totalWeight);
		return Response.status(200).entity(value+"").build();

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
            KeywordExtractionService extractor = new KeywordExtractionService(sanitized.substring(0, 500));
            keywords = extractor.getKeywords();
        } else {
            KeywordExtractionService extractor = new KeywordExtractionService(sanitized);
            keywords = extractor.getKeywords();
        }
        return keywords;
    }
}