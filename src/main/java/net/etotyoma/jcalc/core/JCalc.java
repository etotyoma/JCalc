package net.etotyoma.jcalc.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.etotyoma.jcalc.Launcher;

public class JCalc extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("ui/basic-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("JCalc");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(windowEvent ->
                CalculatorLogger.log("Calculator stopped", CalculatorLogger.LogLevel.INFO));
    }

    public static void main(String[] args) {
        launch();
    }
}
