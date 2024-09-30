package org.foxycue.foxycue;

import CueSheetCore.*;
//import CustomExceptions.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static org.foxycue.foxycue.Parser.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("FoxyCue");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //launch();
        Release parsed_release = parse(String.valueOf(3203984));
        CueSheetBase cueFromParsed = IO.parsedToCueSheetBase(parsed_release);
        CueGenerator.generateCueFromBase(cueFromParsed);
    }
}