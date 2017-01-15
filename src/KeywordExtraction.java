import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaheensharifian on 1/14/17.
 */
public class KeywordExtraction {

    private String input;
    private int count;
    private static final String TEXT_KEY = "604942f415f34bc8815031ff9685779e";
    private static final String BASE_URL = "https://westus.api.cognitive.microsoft.com/text/analytics/v2.0/keyPhrases";


    // Input is
    public KeywordExtraction(String input) {
        this.input = input;
    }

    public String getKeywords() {
        StringBuilder sb = new StringBuilder();
        String output = "";

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

<<<<<<< HEAD

//            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
//            }

=======
>>>>>>> f7dab03... Blah
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}
