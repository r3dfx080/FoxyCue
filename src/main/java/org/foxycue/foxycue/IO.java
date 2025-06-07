package org.foxycue.foxycue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import CustomExceptions.*;

public class IO {
    private static final Logger logger = LogManager.getLogger(IO.class);

    public static String extractReleaseId(String releaseLink){
        String regex = "https://www\\.discogs\\.com/release/(\\d+)-.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(releaseLink);
        String releaseId = null;
        if (matcher.matches()) {
            releaseId = matcher.group(1);
        } else {
            logger.error("Invalid link format");
            // TODO throw custom error! invalid link format!
        }
        return releaseId;
    }
    public static String sanitizeFilename(String filename){
        String[] bannedSymbols = {"<", ">", ":", "\"", "/", "\\", "|", "?"};
        for (String symb: bannedSymbols)
            if (filename.contains(symb)) filename = filename.replace(symb, "");
        return filename;
    }
    public static String sanitizeTextField(String textFieldValue){
        if (textFieldValue.contains("\"")) textFieldValue = textFieldValue.replace("\"", "'");
        //removing diacritics and dialects
        textFieldValue = removeDiacriticsAndDialect(textFieldValue);
        return textFieldValue;
    }
    public static String removeDiacriticsAndDialect(String input){
        if (input == null) return null;
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
