package sample.view;

import javafx.scene.Parent;
import sample.controller.Controller;
import sample.Main;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class View {
    Parent view;
    Executor exec;
    Controller controller;
    Main mainApp;
    //----------------------------------------------------------------------------------
    public void initialize() {
        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
        initPane();
    }
    //----------------------------------------------------------------------------------
    public Parent asParent() {
        return view;
    }
    //----------------------------------------------------------------------------------
    public void initPane() {

    }
}
