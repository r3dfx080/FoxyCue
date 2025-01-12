package org.foxycue.foxycue;
import CueSheetCore.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.foxycue.foxycue.IO.*;
import static org.foxycue.foxycue.Parser.*;

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
    public TextField statusTextField;

    private Release parsed_release;
    @FXML
    private void onFetchPressed(){
        //skipping if text field is empty
        if (releaseLink.getText().isEmpty()) return;

        // TODO make link sanitizer
        String releaseId = IO.extractReleaseId(releaseLink.getText());
        if (releaseId != null) {
            lockAllFields();

            parsed_release = null;
            parsed_release = parseReleaseViaId(releaseId);

            //null check
            if (parsed_release == null) {
                // TODO throw custom error!
                statusTextField.setText("parsed release is null!");
                return;
            }

            statusTextField.setText("parsed release OK");
            unlockAllFields();

            setFieldsFromParsed(parsed_release);
        }
    }
    @FXML
    private void onGeneratePressed(){
        lockAllFields();

        if (parsed_release == null) {
            statusTextField.setText("parsed release is null!");
            // TODO throw custom exception! parsed release is null!
            unlockAllFields();
        }
        else {
            CueSheetBase generatedBase = fillCueFromFieldsAndParsed(parsed_release);

            textArea.setText(CueGenerator.generateCueFromBase(generatedBase));

            statusTextField.setText("generated cue OK");
        }
    }
    @FXML
    private void onSavePressed(){
        lockAllFields();
        // if name is empty and both performer and title are empty - throw exception
        if (filenameField.getText().isEmpty() & (performerField.getText().isEmpty() & titleField.getText().isEmpty())){
            unlockAllFields();
            statusTextField.setText("err: .cue name is empty");
            //TODO throw custom exception! .cue name is empty!
            return;
        }
        String filename = (sanitizeFilename(performerField.getText() + " - " + titleField.getText() + ".cue"));
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(textArea.getText());
            statusTextField.setText("saved cue OK");
        } catch (FileNotFoundException e) {
            //TODO throw custom exception! have to be caught beforehand
            throw new RuntimeException(e);
        }
        unlockAllFields();
    }
    private void setFieldsFromParsed(Release release){
        unlockAllFields();
        clearAllFields();

        StringBuilder genres = new StringBuilder();
        for (String genre: release.getGenres())
            genres.append(genre).append(", ");
        genres.delete(genres.length()-2, genres.length());

        //no need for sanitization (correct format)
        genreField.setText(genres.toString());
        dateField.setText(String.valueOf(release.getYear()));
        commentField.setText("");

        StringBuilder performers = new StringBuilder();
        for (Artist artist: release.getArtists())
            performers.append(artist.getName()).append(", ");
        performers.delete(performers.length()-2, performers.length());

        performerField.setText(sanitizeTextField(performers.toString()));
        titleField.setText(sanitizeTextField(release.getTitle()));

        filenameField.setText(sanitizeFilename(performers + " - " + release.getTitle() + ".flac"));

    }
    private CueSheetBase fillCueFromFieldsAndParsed(Release release){
        lockAllFields();

        //sanitize comment after user input
        commentField.setText(sanitizeTextField(commentField.getText()));

        CueSheetBase filledCueBase = new CueSheetBase(
                genreField.getText(), dateField.getText(),
                commentField.getText(), performerField.getText(),
                titleField.getText(), filenameField.getText(),
                release.getTracklist());

        unlockAllFields();
        return filledCueBase;
    }
    private void clearAllFields(){
        genreField.clear();
        dateField.clear();
        commentField.clear();
        performerField.clear();
        titleField.clear();
        filenameField.clear();
        textArea.clear();
    }
    private void lockAllFields(){
        genreField.setDisable(true);
        dateField.setDisable(true);
        commentField.setDisable(true);
        performerField.setDisable(true);
        titleField.setDisable(true);
        filenameField.setDisable(true);
        textArea.setDisable(true);
    }
    private void unlockAllFields(){
        genreField.setDisable(false);
        dateField.setDisable(false);
        commentField.setDisable(false);
        performerField.setDisable(false);
        titleField.setDisable(false);
        filenameField.setDisable(false);
        textArea.setDisable(false);
    }
}