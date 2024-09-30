package org.foxycue.foxycue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import CueSheetCore.*;
import com.google.gson.Gson;

public class Parser {

    private static final String USER_AGENT = "FoxyCue/0.0.1";
    private static final String DISCOGS_API_URL = "https://api.discogs.com/releases/";

    public static Release parse(String releaseId) {
        Release release = new Release();
        try {

            // Send the GET request to the Discogs API
            String jsonResponse = sendGET(releaseId);

            System.out.printf(jsonResponse);
            // Parse the JSON response into Java objects using Gson
            Gson gson = new Gson();
            release = gson.fromJson(jsonResponse, Release.class);

            System.out.println("Release Title: " + release.getTitle());
            System.out.println("Year: " + release.getYear());

            // Print artist names
            for (Artist artist : release.getArtists()) {
                System.out.println("Artist: " + artist.getName());
            }

            // Print tracklist
            for (Track track : release.getTracklist()) {
                System.out.println(track.getType() + " " + track.getPosition() + ": " + track.getTitle());
            }
            for (String genre: release.getGenres())
                System.out.println(genre);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return release;
        }
    }

    // The sendGET method is the same as the one I provided earlier
    private static String sendGET(String releaseId) throws Exception {
        String url = DISCOGS_API_URL + releaseId;
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
