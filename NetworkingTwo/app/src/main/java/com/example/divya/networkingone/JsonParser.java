package com.example.divya.networkingone;

import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by divya on 28/6/16.
 */
public class JsonParser {

    // TODO : make this static final
    String userPassword = "563492ad6f91700001000001d73171e95ed742c3582b9f773d830297";

    //TODO : is this constructor necessary? java already provides a default constructor when no other is defined
    public JsonParser() {

    }

    public JSONObject getJsonFromUrl(String url) {
        //TODO: is this initialisation necessary?
        String response = new String();
        JSONObject jsonObject = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", userPassword);

            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    //TODO : it would be better to use StringBuilder here
                    /**
                     * Strings are immutable and every time a new String is created due to this statement
                     * It would be a performance boost to use StringBuilder in cases like these.
                     */
                    response = response + inputLine;
                }
                jsonObject = new JSONObject(response);
                //TODO : always close() in finally block
                br.close();
            } else {
                Log.d("Exception", "GET request not worked");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
