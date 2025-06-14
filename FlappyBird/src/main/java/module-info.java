module application.FlappyBird {
    requires javafx.controls;
    requires javafx.fxml;

    opens application.FlappyBird to javafx.fxml;
    exports application.FlappyBird;
}
