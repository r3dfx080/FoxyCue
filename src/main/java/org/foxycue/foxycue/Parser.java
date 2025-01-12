package org.foxycue.foxycue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import CueSheetCore.*;
import com.google.gson.Gson;

public class Parser {

    private static final String USER_AGENT = "FoxyCue/0.2";
    private static final String DISCOGS_API_URL = "https://api.discogs.com/releases/";

    public static Release parseReleaseViaId(String releaseId) {
        Release release = new Release();
        String jsonResponse;
        try {
            // send the GET request to the Discogs API
            jsonResponse = sendGET(releaseId);
        } catch (SocketTimeoutException e) {
            // TODO throw custom exception! socket timeout!
            System.out.println("socket timed out!");
            return null;
        } catch (Exception e) {
            // TODO throw generic exception!
            throw new RuntimeException(e);
        }
        if (jsonResponse != null) {
            Gson gson = new Gson();
            release = gson.fromJson(jsonResponse, Release.class);
        }
        return release;
    }

    private static String sendGET(String releaseId) throws Exception {

        URL url = new URI(DISCOGS_API_URL + releaseId).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);

        connection.setConnectTimeout(10000); // 10 seconds connection timeout
        connection.setReadTimeout(5000); // 5 seconds read timeout

        if (connection.getResponseCode() != 200){
            System.err.println("connection failed");
            return null;
        }
        StringBuilder response = new StringBuilder();
        try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))){
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return response.toString();
    }
}
