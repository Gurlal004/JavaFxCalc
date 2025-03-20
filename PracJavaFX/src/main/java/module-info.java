module com.example.pracjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.pracjavafx to javafx.fxml;
    exports com.example.pracjavafx;
    exports ASTTree;
    opens ASTTree to javafx.fxml;
}