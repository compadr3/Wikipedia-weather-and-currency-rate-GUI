package zad1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController.primaryStage = primaryStage;


        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        PopUpWindowController.mainWindowLoader = loader;
        Parent root = loader.load();

        //Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        PopUpWindowController.root = root;
        Scene sceneParent = new Scene(root);
        primaryStage.setTitle("City Info");
        primaryStage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setScene(sceneParent);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Service s = new Service("Poland");
        String weatherJson = s.getWeather("Warsaw");
        Double rate1 = s.getRateFor("USD");
        Double rate2 = s.getNBPRate();
        // ...
        // część uruchamiająca GUI
        MainController.city = "Warsaw";
        MainController.country = "Poland";
        MainController.currency = "USD";
        MainController.rateFor = rate1;
        MainController.nbpRate = rate2;
        MainController.weather = weatherJson;

        launch(args);
    }
}
