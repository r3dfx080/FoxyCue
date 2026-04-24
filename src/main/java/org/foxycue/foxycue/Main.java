package org.foxycue.foxycue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static final Logger logger = LogManager.getLogger(Main.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("FoxyCue");
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/org/foxycue/foxycue/icon.png"))));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void stop() {
        logger.info("Stopping application");
    }
}