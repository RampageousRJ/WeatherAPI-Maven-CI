package weatherapi;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URL;
import java.net.HttpURLConnection;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import io.github.cdimascio.dotenv.Dotenv;

public class AppTest {

    @Mock
    private HttpURLConnection connection;
    @Test
    public void isAPIKeyValid() throws Exception {
        Dotenv dotenv = Dotenv.load();
        final String API_KEY = dotenv.get("API_KEY");
        final String API_URL = dotenv.get("API_ENDPOINT");
        final String city = "London";
        
        URI uri = new URI(API_URL + "?key=" + API_KEY + "&q=" + city);
        URL url = uri.toURL();
        assertDoesNotThrow(() -> {
            connection = (HttpURLConnection) url.openConnection();
        });
        assertNotNull(url);
        assertTrue(url.toString().contains(API_KEY)); // Ensure the API key is present in the URL
    }
}


