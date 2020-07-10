package sample.ui.regedit;

import javafx.scene.layout.BorderPane;
import sample.Main;
import sample.ViewFactory;
import sample.ui.View;

public class RegeditView extends View {

    public RegeditView(Main mainApp) {
        this.mainApp = mainApp;
        initialize();
    }

    @Override
    public void initPane() {
        node = new BorderPane();

        View vinylView = mainApp.getViewFactory().regView("vinyl", "list");
        ((BorderPane)node).setCenter(vinylView.getNode());
    }
}
