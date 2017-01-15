package org.lasred;
/**
 * Created by taehyunkwon on 1/15/17.
 */
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SentimentEval {
    private String input;
    private int count;
    private static final String TEXT_KEY = "604942f415f34bc8815031ff9685779e";
    private static final String BASE_URL = "https://westus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment";

    public SentimentEval(String input) {
        this.input = input;
    }

    public double evalScore() {
        StringBuilder sb = new StringBuilder();
        String output = "";
        double score = 0.0;
        StringBuilder c = new StringBuilder();

        try {
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Ocp-Apim-Subscription-Key", TEXT_KEY);
            conn.setDoOutput(true);


            List<JSONObject> list = new ArrayList<>();

            JSONObject obj = new JSONObject();
            JSONObject innerObj = new JSONObject();
            innerObj.put("language", "en");
            innerObj.put("id", count);
            innerObj.put("text", this.input);
            list.add(innerObj);
            obj.put("documents", list);

            String requestString = obj.toString();

            OutputStream os = conn.getOutputStream();
            os.write(requestString.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();
            try {
                String blah = sb.toString();
                org.json.JSONObject jObj = new org.json.JSONObject(sb.toString());


                org.json.JSONArray arr = jObj.getJSONArray("documents");
                org.json.JSONObject obj2 = arr.getJSONObject(0);
                score = obj2.getDouble("score");


            } catch (JSONException e) {
                System.out.println("ERROR");
            }

        } catch (java.io.IOException e) {
            return 0.0;
        }

        return score;
    }
}