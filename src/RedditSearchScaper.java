import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


import java.util.List;

/**
 * Created by shaheensharifian on 1/14/17.
 */
public class RedditSearchScaper {
    private String query;
    private List<LinkModel> links;
    private static final String BASE_URL = "https://www.reddit.com/r/business/search.json?q=";

    public RedditSearchScaper(String query) {
        this.query = query;
        this.links = new ArrayList<>();
    }

    public List<LinkModel> getRedditLinksFromWeb() throws ProtocolException, JSONException {

        StringBuilder sb = new StringBuilder();

        try {
            URL url = new URL(BASE_URL + this.query + "&restrict_sr=on");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-agent", "your bot 0.1");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            JSONParser parser = new JSONParser();
            JSONObject jobj = new org.json.JSONObject(sb.toString());

            JSONArray jArray = jobj.getJSONObject("data").getJSONArray("children");
            // parse JArray
            for(int i = 0; i < jArray.length(); i++) {

                JSONObject obj = jArray.getJSONObject(i);
                String kind = obj.getString("kind");
                if(kind.equals("t3")) {
                    JSONObject innerObj = obj.getJSONObject("data");
                    LinkModel linkModel = new LinkModel();
                    int score = innerObj.getInt("score");
                    String subreddit = innerObj.getString("subreddit");
                    String id = innerObj.getString("id");
                    String url1 = innerObj.getString("url");
                    
                }



            }


            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
