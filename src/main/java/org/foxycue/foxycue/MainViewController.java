package org.foxycue.foxycue;

import CueSheetCore.Artist;
import CueSheetCore.CueGenerator;
import CueSheetCore.CueSheetBase;
import CueSheetCore.Release;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.foxycue.foxycue.IO.*;
import static org.foxycue.foxycue.Parser.parseReleaseViaId;

public class MainViewController {
    public Button generateCueButton;
    public TextField releaseLink;
    public TextArea textArea;
    public Button saveButton;
    public Button fetchButton;
    public TextField genreField;
    public TextField dateField;
    public TextField commentField;
    public TextField performerField;
    public TextField titleField;
    public TextField filenameField;

    private Release parsed_release;

    private static final Logger logger = LogManager.getLogger(MainViewController.class);

    @FXML
    private void onFetchPressed() {
        if (releaseLink.getText().isEmpty()) return;

        String releaseId = IO.extractReleaseId(releaseLink.getText());
        if (releaseId == null)
        {
            showAndLogError("Invalid link format or ID field is empty", Alert.AlertType.ERROR, true);
            return;
        }

        logger.info("Parsed release ID: {}", releaseId);

        lockAllFields();

        parsed_release = parseReleaseViaId(releaseId);

        if (parsed_release == null) {
            showAndLogError("Parsed release is null", Alert.AlertType.ERROR, true);
            return;
        }

        logger.info(parsed_release);
        setFieldsFromParsed(parsed_release);
    }

    @FXML
    private void onGeneratePressed() {
        if (parsed_release == null) return;

        CueSheetBase generatedBase = fillCueFromFieldsAndParsed(parsed_release);
        logger.info("Generated .cue base successfully");

        textArea.setText(CueGenerator.generateCueFromBase(generatedBase));
    }

    @FXML
    private void onSavePressed() {
        lockAllFields();

        // name is empty and both performer and title are empty -> throw exception
        if (filenameField.getText().isEmpty() & (performerField.getText().isEmpty() & titleField.getText().isEmpty())) {
            unlockAllFields();
            IO.showAndLogError("File name, performer and title are empty", Alert.AlertType.ERROR, true);
            return;
        }
        String filename = (sanitizeFilename(performerField.getText() + " - " + titleField.getText() + ".cue"));

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save Cue file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setInitialFileName(filename);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Cue file", "*.cue"));

        File file = fileChooser.showSaveDialog(textArea.getScene().getWindow());

        // user closed save dialog -> return
        if (file == null) {
            unlockAllFields();
            return;
        }

        try (PrintWriter out = new PrintWriter(file)) {
            out.println(textArea.getText());
            logger.info("Successfully written .cue file");
        } catch (FileNotFoundException e) {
            IO.showAndLogError("File not found", Alert.AlertType.ERROR, false);
            logger.error(e.getMessage(), e);
        } finally {
            unlockAllFields();
        }
    }

    private void setFieldsFromParsed(Release release) {
        unlockAllFields();
        clearAllFields();

        StringBuilder genres = new StringBuilder();
        for (String genre : release.getGenres())
            genres.append(genre).append(", ");
        genres.delete(genres.length() - 2, genres.length());

        //no need for sanitization (correct format)
        genreField.setText(genres.toString());
        dateField.setText(String.valueOf(release.getYear()));
        commentField.setText("");

        StringBuilder performers = new StringBuilder();
        for (Artist artist : release.getArtists())
            performers.append(artist.getName()).append(", ");
        performers.delete(performers.length() - 2, performers.length());

        performerField.setText(sanitizeTextField(performers.toString()));
        titleField.setText(sanitizeTextField(release.getTitle()));

        filenameField.setText(sanitizeFilename(performers + " - " + release.getTitle() + ".flac"));

        logger.info("Set .cue fields successfully");
    }

    private CueSheetBase fillCueFromFieldsAndParsed(Release release) {
        lockAllFields();

        // sanitize comment after user input
        commentField.setText(sanitizeTextField(commentField.getText()));

        CueSheetBase filledCueBase = new CueSheetBase(
                genreField.getText(), dateField.getText(),
                commentField.getText(), performerField.getText(),
                titleField.getText(), filenameField.getText(),
                release.getTracklist());

        logger.info("Filled .cue base: {}", filledCueBase);

        unlockAllFields();

        return filledCueBase;
    }

    private void clearAllFields() {
        genreField.clear();
        dateField.clear();
        commentField.clear();
        performerField.clear();
        titleField.clear();
        filenameField.clear();
        textArea.clear();
        logger.info("Cleared all fields");
    }

    private void lockAllFields() {
        genreField.setDisable(true);
        dateField.setDisable(true);
        commentField.setDisable(true);
        performerField.setDisable(true);
        titleField.setDisable(true);
        filenameField.setDisable(true);
        textArea.setDisable(true);
        logger.info("Locked all fields");
    }

    private void unlockAllFields() {
        genreField.setDisable(false);
        dateField.setDisable(false);
        commentField.setDisable(false);
        performerField.setDisable(false);
        titleField.setDisable(false);
        filenameField.setDisable(false);
        textArea.setDisable(false);
        logger.info("Unlocked all fields");
    }
}