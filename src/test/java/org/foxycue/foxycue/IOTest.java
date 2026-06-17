package org.foxycue.foxycue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IOTest {
    @Test
    void sanitizeTextFieldShouldReturnNoDiacritics() {
        assertEquals("'ACEuY", IO.sanitizeTextField("\"ÂÇÉù̧Ȳ"));
    }

    @Test
    void sanitizeTextFieldShouldPassNormalASCII() {
        assertEquals("'AmCyETueYxt", IO.sanitizeTextField("\"ÂmÇyÉTù̧eȲxt"));
    }

    @Test
    void sanitizeFilenameShouldReturnEmptyString() {
        assertEquals("", IO.sanitizeFilename("<>:|\"/\\|?"));
    }

    @Test
    void sanitizeFilenameLeavesAllowedTextUnchanged() {
        assertEquals("filename_1", IO.sanitizeFilename("f<i>l:e|n\"a/\\m|e_?1"));
    }

    @Test
    void extractReleaseIdReturnsNullOnIncorrectLink() {
        assertNull(IO.extractReleaseId("https://www.something-something.gov"));
    }

    @Test
    void extractReleaseIdReturnsCorrectId() {
        assertEquals("14535253",
                IO.extractReleaseId("https://www.discogs.com/release/14535253-Something-Something"));
    }

    @Test
    void extractReleaseIdReturnsNullOnIncorrectUrl() {
        assertNull(IO.extractReleaseId("https://www.discogs.com/release/14535@253-Something-Something"));
    }
}