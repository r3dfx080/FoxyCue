package org.foxycue.foxycue;
import CueSheetCore.*;
//import CustomExceptions.*;

public class IO {
    public static CueSheetBase parsedToCueSheetBase(Release release){
        StringBuilder genres = new StringBuilder();
        for (String genre: release.getGenres())
            genres.append(genre).append(", ");
        genres.delete(genres.length()-2, genres.length());

        StringBuilder performers = new StringBuilder();
        for (Artist artist: release.getArtists())
            performers.append(artist.getName()).append(", ");
        performers.delete(performers.length()-2, performers.length());

        String filename = performers + " - " + release.getTitle() + ".flac";

        CueSheetBase cueSheetBase = new CueSheetBase(genres.toString(), String.valueOf(release.getYear()),
                "", performers.toString(), release.getTitle(), filename,
                release.getTracklist());
        return cueSheetBase;
    }
}
