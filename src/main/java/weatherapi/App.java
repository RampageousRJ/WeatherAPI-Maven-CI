package weatherapi;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class App extends Application {
    private static final String API_KEY = "6eb7933611b64558b7e72119231411";
    private static final String API_URL = "http://api.weatherapi.com/v1/current.json";

    public static void main(String[] args) {
        launch(args);
    }

    public App() {}

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather App");

        BorderPane borderPane = new BorderPane();
        VBox weatherInfoBox = new VBox();
        weatherInfoBox.setAlignment(Pos.CENTER);
        weatherInfoBox.setSpacing(10);

        TextField cityInput = new TextField();
        cityInput.setPromptText("Enter city name");
        Button getWeatherButton = new Button("Get Weather");

        getWeatherButton.setOnAction(event -> {
            String cityName = cityInput.getText();
            if (!cityName.isEmpty()) {
                JSONObject weatherData = getWeatherData(cityName);
                if (weatherData != null) {
                    JSONObject currentObject = weatherData.getJSONObject("current");
                    JSONObject conditionObject = currentObject.getJSONObject("condition");
                    String description = conditionObject.getString("text");
                    double temperature = currentObject.getDouble("temp_c");
                    double feelsLike = currentObject.getDouble("feelslike_c");
                    int humidity = currentObject.getInt("humidity");
                    double windSpeed = currentObject.getDouble("wind_kph");

                    String weatherInfo = String.format(
                        "Weather: %s\nTemperature: %.1f°C\nFeels Like: %.1f°C\nHumidity: %d%%\nWind Speed: %.1f kph",
                        description, temperature, feelsLike, humidity, windSpeed
                    );

                    weatherInfoBox.getChildren().clear();
                    weatherInfoBox.getChildren().add(new Label(weatherInfo));
                } else {
                    weatherInfoBox.getChildren().clear();
                    weatherInfoBox.getChildren().add(new Label("City not found or weather data unavailable."));
                }
            }
        });

        VBox inputBox = new VBox(cityInput, getWeatherButton);
        inputBox.setAlignment(Pos.CENTER);
        inputBox.setSpacing(10);

        borderPane.setTop(inputBox);
        borderPane.setCenter(weatherInfoBox);

        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private JSONObject getWeatherData(String city) {
        try {
            URI uri = new URI(API_URL + "?key=" + API_KEY + "&q=" + city);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                StringBuilder response = new StringBuilder();
                Scanner scanner = new Scanner(new InputStreamReader(conn.getInputStream()));
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                return new JSONObject(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}