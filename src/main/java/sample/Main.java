package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class Main extends Application {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private ViewFactory viewFactory;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        logger.info("start");
        this.primaryStage = primaryStage;
        viewFactory = new ViewFactory(this);

        this.primaryStage.setTitle("Discogs regedit");
        Scene scene = new Scene(viewFactory.regView("stage", "main").getNode(), 300, 275);
        scene.getStylesheets().add(Main.class.getResource("/style/style.css").toExternalForm());

        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ViewFactory getViewFactory() {
        return this.viewFactory;
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
    
    //----------------------------------------------------------------------------------
    public boolean showDialogMsg(String sTitle, String sHeader, String sText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(sTitle);
        alert.setHeaderText(sHeader);
        alert.setContentText(sText);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == ButtonType.OK);
    }
    //----------------------------------------------------------------------------------
    public void alertError(String sTitle, String sHeader, String sText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(sTitle);
        alert.setHeaderText(sHeader);
        alert.setContentText(sText);
        //  alert.initOwner(getPrimaryStage());
        alert.showAndWait();
    }
}
