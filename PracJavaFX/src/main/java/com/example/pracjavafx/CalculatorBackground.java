package com.example.pracjavafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CalculatorBackground {

    public void openBackgroundChoose(GridPane grid) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("choose-background-view.fxml"));
        AnchorPane pane = fxmlLoader.load();
        Scene scene = new Scene(pane);
        CalcBackgroundController controller = fxmlLoader.getController();

        controller.setStageGrid(stage, grid);

        scene.getStylesheets().add(getClass().getResource("/background.css").toExternalForm());

        stage.setTitle("Choose a background");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }
}
