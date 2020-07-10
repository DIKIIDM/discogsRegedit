package sample.ui;

import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import sample.Main;
import sample.controller.Controller;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class View {
    protected Parent node;
    protected Executor exec;
    protected Controller controller;
    protected Main mainApp;
    protected String caption;
    //----------------------------------------------------------------------------------
    protected void initialize() {
        this.exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
        initPane();
    }
    //----------------------------------------------------------------------------------
    public Parent getNode() {
        return node;
    }
    //----------------------------------------------------------------------------------
    protected abstract void initPane();
    //----------------------------------------------------------------------------------
    public void setCursor(Cursor cursor) {
        Platform.runLater(() -> {
            mainApp.getPrimaryStage().getScene().setCursor(cursor);
        });
    }
}
