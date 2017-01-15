package org.lasred;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Path("/analyzeText")
public class TextAnalysisService {


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
                sb.append(textString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String source) throws IOException {
//      URL url = new URL("http://www.latimes.com/business/la-fi-uber-funding-20141205-story.html");
//      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//      if(connection.getResponseCode() != 200) {
//          throw new IOException(connection.getResponseMessage());
//      }
//
//      BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//
//      StringBuilder sb = new StringBuilder();
//      String line;
//
//      while((line = rd.readLine()) != null) {
//          sb.append(line);
//      }
//
//      rd.close();
//
//      connection.disconnect();
//      System.out.print(sb.toString());
      String sanitized = null;
	  if(source.equals("business")) {
    	  sanitized = sanitizePage("http://www.latimes.com/business/la-fi-uber-funding-20141205-story.html").substring(0, 550);
      }  
            
      KeywordExtractionService extractor = new KeywordExtractionService(sanitized);
      String keywords = extractor.getKeywords();
      System.out.println(extractor.getKeywords());
	  String output = "Jess says : "  + source + " the words are" + sanitized;
      output += "\n" + " keywords: " + keywords;
	  
		return Response.status(200).entity(output).build();

	}

}