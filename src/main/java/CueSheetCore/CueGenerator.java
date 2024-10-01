package CueSheetCore;

import java.util.List;

public class CueGenerator {
    private CueSheetBase cueSheetBase;
    private static StringBuilder formattedOutput;
    private static StringBuilder performerStr;

    public static String generateCueFromBase(CueSheetBase cueSheetBase){

        List<Track> tracks_in = cueSheetBase.getTracks();
        int trackNo = 1;
        for (Track track : tracks_in) {
            track.setNumber(trackNo);
            //sanitizing track titles (no " allowed)
            track.setTitle(track.getTitle().replace("\"", "'"));
            trackNo++;
        }

        formattedOutput = new StringBuilder();
        if (cueSheetBase.getGenre() != null) formattedOutput
                .append("REM GENRE ")
                .append(cueSheetBase.getGenre())
                .append("\n");
        if (cueSheetBase.getDate() != null) formattedOutput
                .append("REM DATE ")
                .append(cueSheetBase.getDate())
                .append("\n");
        if (cueSheetBase.getComment() !=null) formattedOutput
                .append("REM COMMENT ")
                .append(cueSheetBase.getComment())
                .append("\n");
        performerStr = new StringBuilder();
        performerStr.append("PERFORMER ")
                .append('"')
                .append(cueSheetBase.getPerformer())
                .append('"');
        formattedOutput.append(performerStr).append("\n");
        formattedOutput.append("TITLE ")
                .append('"')
                .append(cueSheetBase.getTitle())
                .append('"')
                .append("\n");
        formattedOutput.append("FILE ")
                .append('"')
                .append(cueSheetBase.getFilename())
                .append('"')
                .append(" WAVE")
                .append("\n");

        for(Track track: tracks_in){
            formattedOutput.append("  TRACK ");
            if (track.getNumber()<10) {
                formattedOutput.append("0")
                        .append(track.getNumber());
            }
            else formattedOutput.append(track.getNumber());
            formattedOutput.append(" AUDIO\n")
                    .append("    TITLE ")
                    .append('"')
                    .append(track.getTitle())
                    .append('"')
                    .append('\n');
            //formattedOutput.append("    ").append(performerStr).append('\n');
            formattedOutput.append("    INDEX 01 ")
                    .append(track.getStart_time())
                    .append('\n');
        }
        //System.out.printf(String.valueOf(formattedOutput));
        return String.valueOf(formattedOutput);
    }
}
