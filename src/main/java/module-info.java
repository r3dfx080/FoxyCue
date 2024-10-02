module org.foxycue.foxycue {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires java.net.http;
    requires jdk.crypto.ec;

    opens CueSheetCore to com.google.gson;

    opens org.foxycue.foxycue to javafx.fxml;
    exports org.foxycue.foxycue;



    exports CueSheetCore;
}