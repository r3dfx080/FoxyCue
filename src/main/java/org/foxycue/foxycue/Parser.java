package org.foxycue.foxycue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.foxycue.foxycue.IO;

import CueSheetCore.*;
import com.google.gson.Gson;

public class Parser {

    private static final String USER_AGENT = "FoxyCue/0.1";
    private static final String DISCOGS_API_URL = "https://api.discogs.com/releases/";

    public static Release parse(String releaseId) {
        Release release = new Release();
        String jsonResponse = null;
        try {
            // Send the GET request to the Discogs API
            jsonResponse = sendGET(releaseId);
        } catch (SocketTimeoutException e) {
            //throw custom exception!
            System.out.println("socket timed out!");
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (jsonResponse != null) {
            Gson gson = new Gson();
            release = gson.fromJson(jsonResponse, Release.class);
        }
        return release;
    }

    private static HttpResponse<String> getJsonFromDiscogs(String releaseId) throws ProtocolException {
        String url = DISCOGS_API_URL + releaseId;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            //throw custom exceptions!
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    // The sendGET method is the same as the one I provided earlier
    private static String sendGET(String releaseId) throws Exception {
        String url = DISCOGS_API_URL + releaseId;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
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
