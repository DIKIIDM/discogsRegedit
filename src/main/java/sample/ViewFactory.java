package sample;

import sample.ui.View;
import sample.ui.regedit.RegeditView;
import sample.ui.stage.StageView;
import sample.ui.vinyl.VinylListView;

public class ViewFactory {
    private Main mainApp = null;
    //----------------------------------------------------------------------------------
    public ViewFactory(Main mainApp) {
        this.mainApp = mainApp;
    }
    //----------------------------------------------------------------------------------
    public View regView(String selection, String representation) {
        View result = null;
        if (selection.equalsIgnoreCase("stage")) {
            if (representation.equalsIgnoreCase("main")) {
                result = new StageView(this.mainApp);
            }
        } else if (selection.equalsIgnoreCase("regedit")) {
            if (representation.equalsIgnoreCase("main")) {
                result = new RegeditView(this.mainApp);
            }
        } else if (selection.equalsIgnoreCase("vinyl")) {
            if (representation.equalsIgnoreCase("list")) {
                result = new VinylListView(this.mainApp);
            }
        }
        return result;
    }
}
