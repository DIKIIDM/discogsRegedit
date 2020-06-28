package sample.view;

import javafx.scene.layout.BorderPane;
import sample.Main;

public class V_Regedit extends View {
    //----------------------------------------------------------------------------------
    public V_Regedit(Main mainApp) {
        this.mainApp = mainApp;
        initialize();
    }
    @Override
    public void initPane() {
        this.node = new BorderPane();
    }
}
