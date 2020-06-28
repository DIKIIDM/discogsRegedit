package sample.view;

import javafx.scene.Parent;
import sample.controller.Controller;
import sample.Main;

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
}
