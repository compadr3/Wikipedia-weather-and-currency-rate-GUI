package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Currency;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {

    static boolean countryExists = false;
    String country;
    Locale insertedLocale;
    Locale plLoc = new Locale("PL");

    public Service(String country) {
        Locale.setDefault(Locale.US);
        this.country = country;
        for (Locale l : Locale.getAvailableLocales()) {
            if (l.getDisplayCountry().equalsIgnoreCase(country)) {
                insertedLocale = l;
                countryExists = true;
            }
        }
    }

    public Double getRateFor(String currency) {
        Locale.setDefault(plLoc);
        String s = "";
        try {
            s = Currency.getInstance(insertedLocale).getCurrencyCode();
        } catch (NullPointerException e) {
            return 0.0;
        }
        if (s.equals(currency))
            return 1.0;

        try {

            URL url = new URL("https://api.exchangerate.host/latest?base=" + s + "&symbols=" + currency);
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String apiCurrencyLine;
            while ((apiCurrencyLine = br.readLine()) != null) {
                sb.append(apiCurrencyLine);
            }
            System.out.println("Currency");
            System.out.println(sb);

            Pattern currencyValue = Pattern.compile(currency + "\":(\\d+\\.\\d+)");
            Matcher matcherCurrencyValue = currencyValue.matcher(sb.toString());

            if (matcherCurrencyValue.find())
                return Double.parseDouble(matcherCurrencyValue.group(1));


        } catch (IOException e) {
            System.out.println("Blad. Nie znaleziono wartosci.");
            return 0.0;
        }
        return 0.0;
    }


    public Double getNBPRate() {

        Locale.setDefault(plLoc);
        String s = "";
        try {
            s = Currency.getInstance(insertedLocale).getCurrencyCode();
        } catch (NullPointerException e) {
            return 0.0;
        }
        if (insertedLocale.getCountry().equals("PL"))
            return 1.0;

        Pattern p = Pattern.compile(s + "\".+?" + "mid\":(\\d+\\.\\d+)");
        try {
            URL url1 = new URL("http://api.nbp.pl/api/exchangerates/tables/b/");
            URL url2 = new URL("http://api.nbp.pl/api/exchangerates/tables/a/");
            StringBuilder sb = new StringBuilder();
            for (URL url : Arrays.asList(url1, url2)) {
                InputStreamReader inputStreamReader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);

                BufferedReader br = new BufferedReader(inputStreamReader);
                String k;
                while ((k = br.readLine()) != null) {
                    sb.append(k);
                }
            }
            Matcher matcher = p.matcher(sb.toString());
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }
        } catch (IOException e) {
            return 0.0;
        }
        return 0.0;
    }

    public String getWeather(String city) {


        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city
                    + "," + insertedLocale.getCountry().toLowerCase()
                    + "&APPID=db55565f219ff9327ecb4a0ec0c6cfd1");

            StringBuilder sb = new StringBuilder();
            String apiWeatherLine;
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            while ((apiWeatherLine = br.readLine()) != null) {
                sb.append(apiWeatherLine);
            }
            System.out.println("Weather");
            System.out.println(sb);
            return sb.toString();

        } catch (IOException | NullPointerException e) {
            return "ERROR";
        }
    }

}
