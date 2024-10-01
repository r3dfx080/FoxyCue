package org.foxycue.foxycue;
import CueSheetCore.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        //sanitize!
        String releaseId = IO.extractReleaseId(releaseLink.getText());
        if (!releaseId.equals("err")) {
            lockAllFields();

            parsed_release = parse(releaseId);

            //null check
            if (parsed_release == null) {
                //throw custom error!
                statusTextField.setText("parsed release is null!");
                return;
            }

            statusTextField.setText("parsed release OK");
            unlockAllFields();
            clearAllFields();

            setFieldsFromParsed(parsed_release);
        }
        else {
            //throw custom exception!
            statusTextField.setText("parsing error!");
        }
    }
    @FXML
    private void onGeneratePressed(){
        lockAllFields();

        CueSheetBase generatedBase = fillCueFromFieldsAndParsed(parsed_release);

        unlockAllFields();

        textArea.setText(CueGenerator.generateCueFromBase(generatedBase));

        statusTextField.setText("generated cue OK");
    }
    @FXML
    private void onSavePressed(){
        lockAllFields();
        //parse name!
        String filename = (sanitizeFilename(performerField.getText() + " - " + titleField.getText() + ".cue"));
        try (PrintWriter out = new PrintWriter(filename)) {
            out.println(textArea.getText());
        } catch (FileNotFoundException e) {
            //throw custom exception!
            throw new RuntimeException(e);
        }
        finally {
            unlockAllFields();
            statusTextField.setText("saved cue OK");
        }
    }
    private void setFieldsFromParsed(Release release){
        unlockAllFields();

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