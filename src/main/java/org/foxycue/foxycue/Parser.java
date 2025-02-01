package org.foxycue.foxycue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import CueSheetCore.*;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Parser {

    private static final String USER_AGENT = "FoxyCue/0.3";
    private static final String DISCOGS_API_URL = "https://api.discogs.com/releases/";

    private static final Logger logger = LogManager.getLogger(Parser.class);

    public static Release parseReleaseViaId(String releaseId) {
        Release release = new Release();
        String jsonResponse;
        try {
            // send the GET request to the Discogs API
            jsonResponse = sendGET(releaseId);
        } catch (SocketTimeoutException e) {
            logger.error(e.getMessage(), e);
            // TODO throw custom exception! socket timeout!
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // TODO throw generic exception!
            throw new RuntimeException(e);
        }
        if (jsonResponse != null) {
            Gson gson = new Gson();
            release = gson.fromJson(jsonResponse, Release.class);
            logger.info("Created release from json");
        }
        else {
            logger.error("Json response is null");
        }
        return release;
    }

    private static String sendGET(String releaseId) throws Exception {
        logger.info("Sending GET request to " + DISCOGS_API_URL + releaseId);

        URL url = new URI(DISCOGS_API_URL + releaseId).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);

        connection.setConnectTimeout(20000); // 20 seconds connection timeout
        connection.setReadTimeout(5000); // 5 seconds read timeout

        if (connection.getResponseCode() != 200){
            logger.error("Connetion timed out");
            // TODO add custom error window
            return null;
        }
        StringBuilder response = new StringBuilder();
        try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))){
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        logger.info("Got response from Discogs");

        return response.toString();
    }
}
