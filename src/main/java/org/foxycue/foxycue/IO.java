package org.foxycue.foxycue;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IO {
    private static final Logger logger = LogManager.getLogger(IO.class);

    public static String extractReleaseId(String releaseLink) {
        String regex = "https://www\\.discogs\\.com/release/(\\d+)-.*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(releaseLink);
        String releaseId = null;
        if (matcher.matches()) {
            releaseId = matcher.group(1);
        } else {
            showAndLogError("Invalid link format", Alert.AlertType.ERROR, true);
        }
        return releaseId;
    }

    public static String sanitizeFilename(String filename) {
        String[] bannedSymbols = {"<", ">", ":", "\"", "/", "\\", "|", "?"};
        for (String symb : bannedSymbols)
            if (filename.contains(symb)) filename = filename.replace(symb, "");
        return filename;
    }

    public static String sanitizeTextField(String textFieldValue) {
        if (textFieldValue.contains("\"")) textFieldValue = textFieldValue.replace("\"", "'");
        //removing diacritics and dialects
        textFieldValue = removeDiacriticsAndDialect(textFieldValue);
        return textFieldValue;
    }

    public static String removeDiacriticsAndDialect(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static void showAndLogError(String message, Enum<Alert.AlertType> alertType, boolean logError) {
        Alert alert;
        switch (alertType) {
            case Alert.AlertType.ERROR -> alert = new Alert(Alert.AlertType.ERROR);
            case Alert.AlertType.WARNING -> alert = new Alert(Alert.AlertType.WARNING);
            default -> alert = new Alert(Alert.AlertType.INFORMATION);
        }
        alert.setContentText(message);
        alert.showAndWait();
        if (logError) logger.error(message);
    }
}
