package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import db.Database;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Database.prepareDatabase();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/komisi.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1000, 760);
        stage.setTitle("Komisi Art - Daftar Pesanan");
        stage.getIcons().add(new Image(getClass().getResource("/image/logo.png").toExternalForm()));
        stage.setMinWidth(680);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
