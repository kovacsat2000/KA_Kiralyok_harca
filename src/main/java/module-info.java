module org.project {
    requires javafx.controls;
    requires org.tinylog.api;
    requires org.apache.commons.lang3;
    requires com.google.gson;

    exports game.view to javafx.graphics;
}