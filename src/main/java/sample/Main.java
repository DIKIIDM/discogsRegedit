package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sample.controller.C_Stage;
import sample.view.V_Stage;

public class Main extends Application {
    private static final Logger logger = LogManager.getLogger(Main.class);
    @Override
    public void start(Stage primaryStage) throws Exception{
        logger.info("start");
        C_Stage cStage = new C_Stage();
        V_Stage vStage = new V_Stage(cStage, this);

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(vStage.asParent(), 300, 275));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
