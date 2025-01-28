import com.example.config.ConfigLoader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Base64;

import static org.apache.http.HttpStatus.SC_OK;

public static class AuthManager {

    public static String getAccessToken(String clientId, String clientSecret) {
        String clientCredentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(clientCredentials.getBytes());

        RequestSpecification request = RestAssured.given()
                .baseUri(new ConfigLoader().getBaseUrl())
                .header("Authorization", "Basic " + encodedCredentials)
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .log().all();

        Response response = request.post("/api/token");

        if (response.getStatusCode() == SC_OK) {
            return response.jsonPath().getString("access_token");
        } else {
            throw new RuntimeException("Failed to get access token: " + response.getStatusLine());
        }
    }
}

public static void main(String[] args) {
    ConfigLoader configLoader = new ConfigLoader();
    String accessToken = AuthManager.getAccessToken(configLoader.getClientId(), configLoader.getClientSecret());
    System.out.println("Access Token: " + accessToken);
}
