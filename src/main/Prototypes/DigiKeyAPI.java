import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class DigiKeyAPI {

    // OAuth 2.0 client credentials
    private static final String CLIENT_ID = "wNGv9df3Jw9hZt6DasTMOjKYN4PZz1Fi";
    private static final String CLIENT_SECRET = "R4hJSM1YzaLGOjfq";

    // OAuth 2.0 authorization endpoints
    private static final String AUTH_URL = "https://api.digikey.com/v1/oauth2/authorize";
    private static final String ACCESS_TOKEN_URL = "https://api.digikey.com/v1/oauth2/token";

    // API endpoint
    private static final String API_URL = "https://api.digikey.com/products/v4/search/keyword";

    public static void main(String[] args) {
        try {
            // Step 1: Obtain OAuth 2.0 access token
            String accessToken = getAccessToken(CLIENT_ID, CLIENT_SECRET);

            /*
                The accessToken above provides a dictionary in the following format:
                Access Token:
                            {
                            "access_token":"MVOSd7RVyfid6zgiB9A7PNIUPxYL",
                            "expires_in":599,
                            "token_type":"Bearer"
                            }
             */

            /*
                The variable below extracts the access_token value
             */
            String newAccessToken = accessToken.substring(43, 71);


            System.out.println("Access Token: " + newAccessToken);

            // Step 2: Send POST request to API endpoint with access token
            String responseBody = sendPostRequest(API_URL, newAccessToken);

            // Print API response
            System.out.println("API Response:");
            System.out.println(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getAccessToken(String clientId, String clientSecret) throws Exception {
        // Create HTTP client
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Create HTTP request to obtain access token
        HttpPost tokenRequest = new HttpPost(ACCESS_TOKEN_URL);
        tokenRequest.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

        // Set request parameters (client credentials grant type)
        String requestBody = "grant_type=client_credentials&client_id=" + clientId + "&client_secret=" + clientSecret;
        tokenRequest.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_FORM_URLENCODED));

        // Execute the request
        HttpResponse tokenResponse = httpClient.execute(tokenRequest);

        // Check the response status
        int statusCode = tokenResponse.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new RuntimeException("Token request failed with status code: " + statusCode);
        }

        // Read and return the access token from the response body
        HttpEntity tokenEntity = tokenResponse.getEntity();
        String accessToken = EntityUtils.toString(tokenEntity);

        // Close the HTTP client
        httpClient.close();

        return accessToken;
    }

    private static String sendPostRequest(String apiUrl, String accessToken) throws Exception {
        // Create HTTP client
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Create HTTP request to API endpoint
        HttpPost apiRequest = new HttpPost(apiUrl);
        apiRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        apiRequest.addHeader("X-DIGIKEY-Client-Id", "wNGv9df3Jw9hZt6DasTMOjKYN4PZz1Fi");

        // Set request body as needed (e.g., "Keywords": "Resistors")

        int offset = 0;
        int limit = 10;

        // Insert while loop using hasNext


        String requestBody = "{\"Keywords\": \"Resistors\", \"limit\": "+ limit+ ", \"offset\":" + offset + "}";

        apiRequest.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

        // Execute the request
        HttpResponse apiResponse = httpClient.execute(apiRequest);
        HttpEntity apiEntity = apiResponse.getEntity();
        String responseBody = EntityUtils.toString(apiEntity);

        // Close the HTTP client
        httpClient.close();

        return responseBody;
    }
}