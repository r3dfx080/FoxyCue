package CueSheetCore;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static CueSheetCore.CueGenerator.generateCueFromBase;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CueGeneratorTest {

    @Test
    void cueSheetShouldMatchBaseParameters() {
        List<Track> tracks = new ArrayList<>();
        for (int i = 0; i < 3;)
            tracks.add(new Track(i++, "Track" + i));

        var result = generateCueFromBase(new CueSheetBase("Genre", "Date 00-00-0000", "Comment", "Performer",
                "Title", "file-name.flac", tracks));
        var expected = """
                REM DATE Date 00-00-0000
                REM COMMENT Comment
                PERFORMER "Performer"
                TITLE "Title"
                FILE "file-name.flac" WAVE
                  TRACK 01 AUDIO
                    TITLE "Track1"
                    INDEX 01 00:00:00
                  TRACK 02 AUDIO
                    TITLE "Track2"
                    INDEX 01 00:00:00
                  TRACK 03 AUDIO
                    TITLE "Track3"
                    INDEX 01 00:00:00
                """;

        assertEquals(expected, result);
    }
}