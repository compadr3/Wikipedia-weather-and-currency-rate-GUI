package zad1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PopUpWindowController {

    @FXML
    TextField txtFieldCountry, txtFieldCity, txtFieldCurrency;
    @FXML
    Button btnCancel;
    static FXMLLoader mainWindowLoader;
    static Stage popUpWindow;
    private Stage stage;
    private Scene scene;
    static Parent root;

    public void changeData(ActionEvent event) throws IOException {

        //FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));


        MainController mainController = mainWindowLoader.getController();

        mainController.setCity(txtFieldCity.getText());
        mainController.setCountry(txtFieldCountry.getText());
        mainController.setCurrency(txtFieldCurrency.getText());
        //mainWindowLoader.load();
        mainController.displayNewData();
    }

    public void closePopUpWindow() {
        popUpWindow.close();
    }
}
