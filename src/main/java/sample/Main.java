package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main extends Application {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private ViewFactory viewFactory;
    //----------------------------------------------------------------------------------
    @Override
    public void start(Stage primaryStage) throws Exception{
        logger.info("start");
        viewFactory = new ViewFactory(this);

        primaryStage.setTitle("Discogs regedit");
        primaryStage.setScene(new Scene(viewFactory.regView("stage", "main").getNode(), 300, 275));
        primaryStage.show();
    }
    //----------------------------------------------------------------------------------
    public static void main(String[] args) {
        launch(args);
    }
    //----------------------------------------------------------------------------------
    public ViewFactory getViewFactory() {
        return this.viewFactory;
    }
}
