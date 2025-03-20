package com.example.pracjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class CalcBackgroundController {
    @FXML
    private Label path;
    @FXML
    private GridPane grid;

    private Stage stage;
    private String imagePath;

    public void setStageGrid(Stage stage, GridPane grid) {
        this.stage = stage;
        this.grid = grid;
    }
    @FXML
    protected void chooseBtn() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));

        File selctedFile = fileChooser.showOpenDialog(stage);
        if (selctedFile != null) {
            imagePath = selctedFile.toURI().toString();
            path.setText(selctedFile.getAbsolutePath());
        }
    }
    @FXML
    protected void setBtn() {
        grid.setStyle("-fx-background-image: url('" + imagePath + "');\n" +
                "    -fx-background-repeat: no-repeat;\n" +
                "    -fx-background-position: center;\n" +
                "    -fx-background-size: cover;");
        stage.close();
    }
    @FXML
    protected void cancelBtn() {
        stage.close();
    }
    @FXML
    protected void defaultBtn() {
        grid.setStyle("-fx-background-color: transparent;");
        stage.close();
    }
}
