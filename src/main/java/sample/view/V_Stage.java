package sample.view;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import sample.Main;
import sample.controller.C_Stage;

public class V_Stage extends View {
    TabPane tabPane;
    //----------------------------------------------------------------------------------
    public V_Stage(C_Stage controller, Main mainApp) {
        this.controller = controller;
        this.mainApp = mainApp;
        initialize();
    }
    //----------------------------------------------------------------------------------
    @Override
    public void initPane() {
        view = new BorderPane();
        MenuBar menuBar = new MenuBar();
        tabPane = new TabPane();
        ((BorderPane)view).setTop(menuBar);
        ((BorderPane)view).setCenter(tabPane);

        Menu mFile = new Menu("File");
        MenuItem miExit = new MenuItem("Exit");
        miExit.setOnAction(event -> {
            Platform.exit();
        });
        mFile.getItems().add(miExit);
        menuBar.getMenus().addAll(mFile);
    }
}
