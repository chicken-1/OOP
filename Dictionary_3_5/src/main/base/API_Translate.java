package base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class API_Translate {

    public static String googleTranslate(String langFrom, String langTo, String text) throws IOException {
        String urlScript = "https://script.google.com/macros/s/AKfycbw1qSfs1Hvfnoi3FzGuoDWijwQW69eGcMM_iGDF7p5vu1oN_CaFqIDFmCGzBuuGCk_N/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlScript);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static void main(String[] args) throws IOException {
        String text = "hello";
        System.out.println(text.matches("\\w+"));
        System.out.println("Translated text: \n" + googleTranslate("", "vi", text));
    }
}


//********* using Wordnik API code
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WordnikAPI {

    public static String getWordInfo(String apiKey, String word) throws IOException {
        String urlScript = "https://api.wordnik.com/v4/word.json/" + URLEncoder.encode(word, "UTF-8") +
                           "/pronunciations?api_key=" + apiKey +
                           "&limit=1" +
                           "&includeRelated=false" +
                           "&useCanonical=false";
        URL url = new URL(urlScript);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Parse the response to get the word type, pronunciation, and definition
        JSONObject jsonObject = new JSONObject(response.toString());
        JSONArray pronunciations = jsonObject.getJSONArray("pronunciations");
        JSONObject pronunciation = pronunciations.getJSONObject(0);
        String wordType = pronunciation.getString("text");
        String pronunciationText = pronunciation.getString("raw");
        String definition = jsonObject.getJSONArray("definitions").getJSONObject(0).getString("text");

        // Return the word type, pronunciation, and definition as a string
        return "Word type: " + wordType + "\n" +
               "Pronunciation: " + pronunciationText + "\n" +
               "Definition: " + definition;
    }

    public static void main(String[] args) throws IOException {
        String apiKey = "YOUR_WORDNIK_API_KEY";
        String word = "simp";
        System.out.println(getWordInfo(apiKey, word));
    }

}
*/

