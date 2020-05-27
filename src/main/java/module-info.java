module org.project {
    requires javafx.controls;
    requires org.tinylog.api;
    requires com.google.gson;

    exports game.view to javafx.graphics;
}