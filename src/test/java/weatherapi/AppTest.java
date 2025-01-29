package weatherapi;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URL;
import java.net.HttpURLConnection;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class AppTest {

    @Mock
    private HttpURLConnection connection;
    @Test
    public void isAPIKeyValid() throws Exception {
        final String API_KEY = "12345";
        final String API_URL = "https://api.weatherapi.com/v1/current.json";
        final String city = "London";
        
        URI uri = new URI(API_URL + "?key=" + API_KEY + "&q=" + city);
        URL url = uri.toURL();
        assertDoesNotThrow(() -> {
            connection = (HttpURLConnection) url.openConnection();
        });
    }
}


