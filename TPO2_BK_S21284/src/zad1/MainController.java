package zad1;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;

public class MainController implements Initializable {

    WebEngine engine;
    @FXML
    WebView wwwView;
    @FXML
    AnchorPane anchPane;

    @FXML
    Button btnChangeData, btnShowInfo;
    @FXML
    Text location, sky, temperature, pressure, humidity, wind, currencyRate, plnRate, txtLoc;
    @FXML
    Pane paneCurrency, panePLN;
    @FXML
    Text weatherInfo;


    public static String country, weather, city, currency;
    public static double rateFor, nbpRate;
    static Stage primaryStage;
    static Stage popUpWindow;

    //TODO: dodac Text do kursów żeby było łatwiej czytać np pln to eur
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine = wwwView.getEngine();
        loadPage();
    }

    public void loadPage() {
        engine.load("https://en.wikipedia.org/wiki/" + city);
    }

    public void showPopUpWindow(ActionEvent event) throws IOException {
        Parent settingsParent = FXMLLoader.load(getClass().getResource("popUpMenu.fxml"));
        Scene settingsScene = new Scene(settingsParent);
        popUpWindow = new Stage();
        popUpWindow.setTitle("Change data");
        popUpWindow.setScene(settingsScene);
        popUpWindow.initModality(Modality.WINDOW_MODAL);
        popUpWindow.initOwner(anchPane.getScene().getWindow());
        popUpWindow.show();
        PopUpWindowController.popUpWindow = popUpWindow;
    }

    public void displayNewData() {
        if (popUpWindow != null) {
            popUpWindow.close();
        }

        loadPage();
        Service service = new Service(country);
        double rateFor = service.getRateFor(currency);

        currencyRate.setText(Double.toString(rateFor) + " " + currency);
        txtLoc.setText(city + ", " + country);

        if (rateFor == 0.0) {
            plnRate.setText("no data");
        } else {
            plnRate.setText(String.valueOf(service.getNBPRate()) + " PLN");
        }

        weatherInfo.setText(getWeather(service.getWeather(city)));
    }

    public String getWeather(String s) {
        if (s.equals("ERROR")) {
            return "Sorry, no data available.";
        }
        Pattern sky = Pattern.compile("description\":\"(.+?)\"");

        Pattern temp = Pattern.compile("temp\":(.+?),");

        Pattern pressure = Pattern.compile("pressure\":(.+?),");

        Pattern humidity = Pattern.compile("humidity\":(.+?)}");

        Pattern wind = Pattern.compile("speed\":(.+?),");

        Matcher m1 = sky.matcher(s), m2 = temp.matcher(s), m3 = wind.matcher(s), m4 = pressure.matcher(s), m5 = humidity.matcher(s);
        m1.find();
        m2.find();
        m3.find();
        m4.find();
        m5.find();
        return "Sky: " + m1.group(1) + "\nTemperature: "
                + BigDecimal.valueOf((Double.parseDouble(m2.group(1)) - 273)).round(new MathContext(2, RoundingMode.HALF_UP)).toString()
                + " \u2103\nPressure: " + m4.group(1) + " hPa" + "\nHumidity: " + m5.group(1) + " %\tWind speed: " + m3.group(1) + " km/h";

    }


    public void setCity(String s) {
        city = s;
    }

    public void setCountry(String s) {
        country = s;
    }

    public void setCurrency(String s) {
        currency = s;
    }

}
