package CueSheetCore;

import java.util.List;

public class CueSheetBase {
    private String genre;
    private String date;
    private String comment;
    private String performer;
    private String title;
    private String filename;
    int nOfTracks;
    private List<Track> tracks;

    public CueSheetBase(String genre, String date, String comment, String performer, String title, String filename, List<Track> tracks) {
        setGenre(genre);
        setDate(date);
        setComment(comment);
        setPerformer(performer);
        setTitle(title);
        setFilename(filename);
        setTracks(tracks);
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public int getnOfTracks() {
        return nOfTracks;
    }

    public void setnOfTracks(int nOfTracks) {
        this.nOfTracks = nOfTracks;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
