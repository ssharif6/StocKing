package org.lasred;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Path("/hello")
public class TextAnalysisService {

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) throws IOException {
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


      Document doc = Jsoup.connect("http://www.latimes.com/business/la-fi-uber-funding-20141205-story.html").get();
      System.out.println(doc.getAllElements().toString());
		String output = "Jersey say : "  + msg + "" + doc.getAllElements().toString();

		return Response.status(200).entity(output).build();

	}

}