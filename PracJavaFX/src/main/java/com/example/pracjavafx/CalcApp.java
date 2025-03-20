package com.example.pracjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class CalcApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalcApp.class.getResource("calc-view.fxml"));
        AnchorPane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        CalcController controller = fxmlLoader.getController();
        Image icon = new Image(getClass().getResourceAsStream("/icon.png"));

        pane.setOnKeyPressed(controller::handleKeyPressed);
        pane.setOnKeyReleased(controller::handleKeyReleased);
        pane.setFocusTraversable(true);
        pane.requestFocus();

        scene.getStylesheets().add(getClass().getResource("/calc.css").toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinHeight(450);
        stage.setMinWidth(300);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}