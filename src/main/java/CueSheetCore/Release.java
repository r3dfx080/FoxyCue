package CueSheetCore;

import java.util.List;
import java.util.StringJoiner;

public class Release {
    private int id;
    private String[] genres;
    private String title;
    private List<Artist> artists;
    private int year;
    private List<Track> tracklist;

    @Override
    public String toString(){
        StringJoiner sj = new StringJoiner("|", "[", "]");
        sj.add("id :" + id)
                .add("year: " + year)
                .add("title: " + title)
                .add("artists: ");

        for (Artist a : artists)
            sj.add(a.getName());

        sj.add("genres: ");
        for (String s : genres)
            sj.add(s);

        sj.add("tracklist: ");
        for (Track t : tracklist)
            sj.add(t.getPosition() + "-" + t.getTitle());

        return sj.toString();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Track> getTracklist() {
        return tracklist;
    }

    public void setTracklist(List<Track> tracklist) {
        this.tracklist = tracklist;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }
}

