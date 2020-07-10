package sample.ui.stage;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import sample.Main;
import sample.ui.View;

public class StageView extends View {
    private TabPane tabPane;
    //----------------------------------------------------------------------------------
    public StageView(Main mainApp) {
        this.controller = new StageController();
        this.mainApp = mainApp;
        initialize();

        regTab("regedit", "main");
    }
    //----------------------------------------------------------------------------------
    @Override
    protected void initPane() {
        this.node = new BorderPane();
        MenuBar menuBar = new MenuBar();
        tabPane = new TabPane();
        ((BorderPane) node).setTop(menuBar);
        ((BorderPane) node).setCenter(tabPane);

        Menu mFile = new Menu("File");
        MenuItem miExit = new MenuItem("Exit");
        miExit.setOnAction(event -> {
            Platform.exit();
        });
        mFile.getItems().add(miExit);
        Menu mDatabase = new Menu("Database");
        MenuItem miRegedit = new MenuItem("Regedit");
        miRegedit.setOnAction(event -> {
            regTab("regedit", "main");
        });
        mDatabase.getItems().add(miRegedit);
        menuBar.getMenus().addAll(mFile, mDatabase);
    }
    //----------------------------------------------------------------------------------
    private void regTab(String selection, String representation) {
        Tab tab = new Tab();
        tab.setClosable(true);
        tab.setText("Regedit");
        tab.setContent(mainApp.getViewFactory().regView(selection, representation).getNode());
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }
}
