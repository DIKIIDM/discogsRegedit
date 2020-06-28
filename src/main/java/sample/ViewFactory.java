package sample;

import sample.view.V_Regedit;
import sample.view.View;
import sample.view.V_Stage;
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
                result = new V_Stage(this.mainApp);
            }
        } else if (selection.equalsIgnoreCase("regedit")) {
            if (representation.equalsIgnoreCase("main")) {
                result = new V_Regedit(this.mainApp);
            }
        }
        return result;
    }
}
