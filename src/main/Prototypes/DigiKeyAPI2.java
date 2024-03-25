import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.FileWriter;
import java.io.IOException;

public class DigiKeyAPI2 {

    /*
        When updating client id and client secret, search for CLIENT_ID and CLIENT_SECRET
        To change the item to search for, search for KEYWORD
     */

    private static final String KEYWORD = "Capacitors";

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

            // Step 2: Send POST request to API endpoint with access token
            int offset = 0;
            int limit = 1;

            /*
                GUI team can create a window with a drop-down or a write-in opinion for the
                client to select which keyword to pull.
                Action listener?

             */

            String fileName = "";

            switch(KEYWORD) {
                case "Resistors":
                    fileName = "Resistors";
                    break;
                case "Capacitors":
                    fileName = "Capacitors";
                    break;
                default:
                    System.out.println("Keyword selected for search does not match expected category name. Default file name will be provided.");
                    System.out.println("File named: Default.json");
                    fileName = "Default";
            }


            FileWriter fileWriter = new FileWriter("src/main/java/" + fileName + ".json");

            int productIndex = 1;

            while (true) {
                // Send API request with current offset
                String responseBody = sendPostRequest(API_URL, newAccessToken, offset, limit, KEYWORD);

                String prefix = responseBody.substring(0, 10);

                String suffix = responseBody.substring(10);

                String indexedResponseBody = prefix + Integer.toString(productIndex) + suffix;

                System.out.println(indexedResponseBody.substring(0, 15));
                productIndex++;

                // Append responseBody to the file
                fileWriter.write(indexedResponseBody);
                fileWriter.write(System.lineSeparator()); // Add a new line for separation


                // Process the current page of results
                System.out.println("API Response (Offset: " + offset + "):");
//                System.out.println(responseBody);

                // Check if there are more results
                if (!hasNextPage(responseBody, limit)) {
                    break; // Exit the loop if there are no more pages
                }

                // Increment offset for the next page
                offset += limit;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertTemplate(String filePath) throws IOException {
        // Inserting
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

    private static boolean hasNextPage(String responseBody, int limit) {
        // Implement logic to check if there are more pages based on the structure of the API response
        // You need to analyze the API response format to determine if there are more results.
        // For example, you may check if the number of returned items is less than the specified limit.

        // Placeholder logic (adjust based on the actual API response structure):
        return responseBody.length() >= limit;
    }

    private static String sendPostRequest(String apiUrl, String accessToken, int offset, int limit, String keyword) throws Exception {
        // Create HTTP client
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // Create HTTP request to API endpoint
        HttpPost apiRequest = new HttpPost(apiUrl);
        apiRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        apiRequest.addHeader("X-DIGIKEY-Client-Id", "wNGv9df3Jw9hZt6DasTMOjKYN4PZz1Fi");

        // Set request body with current offset and limit
        String requestBody = "{\"Keywords\": \""+ keyword + "\", \"limit\": " + limit + ", \"offset\":" + offset + "}";
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