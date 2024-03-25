import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;

public class DigiKeyAPI3 {

    /*
        When updating client id and client secret, search for CLIENT_ID and CLIENT_SECRET
        To change the item to search for, search for KEYWORD
     */

    private static final String KEYWORD = "Mica and PTFE Capacitors";

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
            int limit = 50;

            /*
                GUI team can create a window with a drop-down or a write-in opinion for the
                client to select which keyword to pull.
                Action listener?
             */

            String fileName = "";

            switch(KEYWORD) {
                // Capacitor Subcategories
                case "Capacitor Accessories":
                    fileName = "Capacitor Accessories";
                    break;
                case "Resistors":
                    fileName = "Resistors";
                    break;
                case "Capacitors":
                    fileName = "Capacitors";
                    break;
                case "Switches":
                    fileName = "Switches";
                    break;
                case "Aluminum - Polymer Capacitors":
                    fileName = "Aluminum - Polymer Capacitors";
                    break;
                case "Aluminum Electrolytic Capacitors":
                    fileName = "Aluminum Electrolytic Capacitors";
                    break;
                case "Capacitor Networks, Arrays":
                    fileName = "Capacitor Networks, Arrays";
                    break;
                case "Ceramic Capacitors":
                    fileName = "Ceramic Capacitors";
                    break;
                case "Electric Double Layer Capacitors (EDLC), Supercapacitors":
                    fileName = "Electric Double Layer Capacitors (EDLC), Supercapacitors";
                    break;
                case "Film Capacitors":
                    fileName = "Film Capacitors";
                    break;
                case "Mica and PTFE Capacitors":
                    fileName = "Mica and PTFE Capacitors";
                    break;
                case "Niobium Oxide Capacitors":
                    fileName = "Niobium Oxide Capacitors";
                    break;
                case "Silicon Capacitors":
                    fileName = "Silicon Capacitors";
                    break;
                case "Tantalum - Polymer Capacitors":
                    fileName = "Tantalum - Polymer Capacitors";
                    break;
                case "Tantalum Capacitors":
                    fileName = "Tantalum Capacitors";
                    break;
                case "Thin Film Capacitors":
                    fileName = "Thin Film Capacitors";
                    break;
                case "Trimmers, Variable Capacitors":
                    fileName = "Trimmers, Variable Capacitors";
                    break;
                // Crystals, Oscillators, Resonators Categories
                case "Crystal, Oscillator, Resonator Accessories":
                    fileName = "Crystal, Oscillator, Resonator Accessories";
                    break;
                case "Crystals":
                    fileName = "Crystals";
                    break;
                case "Oscillators":
                    fileName = "Oscillators";
                    break;
                case "Pin Configurable/Selectable Oscillators":
                    fileName = "Pin Configurable-Selectable Oscillators";
                    break;
                case "Programmable Oscillators":
                    fileName = "Programmable Oscillators";
                    break;
                case "Resonators":
                    fileName = "Resonators";
                    break;
                case "Stand Alone Programmers":
                    fileName = "Stand Alone Programmers";
                    break;
                case "VCOs (Voltage Controlled Oscillators)":
                    fileName = "VCOs (Voltage Controlled Oscillators)";
                    break;

                default:
                    System.out.println("Keyword selected for search does not match expected category name. Default file name will be provided.");
                    System.out.println("File named: Default.json");
                    fileName = "Default";
            }

            String fileFolder;

            switch (KEYWORD) {
                case "Capacitor Accessories":
                case "Aluminum - Polymer Capacitors":
                case "Aluminum Electrolytic Capacitors":
                case "Capacitor Networks, Arrays":
                case "Ceramic Capacitors":
                case "Electric Double Layer Capacitors (EDLC), Supercapacitors":
                case "Film Capacitors":
                case "Mica and PTFE Capacitors":
                case "Niobium Oxide Capacitors":
                case "Silicon Capacitors":
                case "Tantalum - Polymer Capacitors":
                case "Tantalum Capacitors":
                case "Thin Film Capacitors":
                case "Trimmers, Variable Capacitors":
                    fileFolder = "Capacitors/";
                    break;
                case "Crystal, Oscillator, Resonator Accessories":
                case "Crystals":
                case "Oscillators":
                case "Pin Configurable-Selectable Oscillators":
                case "Programmable Oscillators":
                case "Resonators":
                case "Stand Alone Programmers":
                case "VCOs (Voltage Controlled Oscillators)":
                    fileFolder = "Crystals-Oscillators-Resonators/";
                    break;
                default:
                    fileFolder = "";
            }

            String filePath = "Postman Exports/" + fileFolder + fileName + ".json";
            try {
                // Insert template into the file
                insertTemplate(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int productIndex = 1;

            while (true) {
                // Send API request with current offset
                String responseBody = sendPostRequest(API_URL, newAccessToken, offset, limit, KEYWORD);

                String prefix = responseBody.substring(0, 10);
                String suffix = responseBody.substring(10);
                String indexedResponseBody = prefix + Integer.toString(productIndex) + suffix;

                // When responseBody has no more content, break while loop
                if (suffix.substring(0,4).equals("\":[]")) {
                    break;
                }
                if (prefix.substring(0,8).equals("{\"fault\"")) {
                    System.out.println("Execution of ServiceCallout SC-Quota failed\n\n" + "Resource Limitations:" +
                            " Your system or the API server may" +
                            " have resource limitations that are exceeded when handling a large amount of data.\n" +
                            "Rate Limiting: Some APIs impose rate limits to prevent abuse. If you exceed the " +
                            "allowed rate, you might encounter rate-limiting errors");
                    break;
                }
                if (!prefix.equals("{\"Products")) {
                    System.out.println(responseBody);
                    break;
                }

                insertNewLines(filePath, indexedResponseBody);
                productIndex++;

                // Process the current page of results
                System.out.println("API Response (Offset: " + offset + "):");
//                System.out.println(responseBody);

                // Check if there are more results
                if (!hasNextPage(indexedResponseBody, limit)) {
                    break; // Exit the loop if there are no more pages
                }

                // Increment offset for the next page
                offset += limit;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertNewLines(String filePath, String newLine) throws IOException {
        // Reading existing content from the file
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line).append(System.lineSeparator());
        }
        reader.close();

        // Insert the new line into the second to last position
        int totalLines = content.toString().split(System.lineSeparator()).length;
        int insertLineNumber = Math.max(totalLines - 1, 0); // Ensure it's at least 0

        // Insert the new line at the specified line number
        content.insert(getPosition(content.toString(), insertLineNumber), newLine + System.lineSeparator());

        // Writing the modified content back to the file
        try (FileWriter writer = new FileWriter(filePath);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write(content.toString());
        }
    }

    private static int getPosition(String content, int lineNumber) {
        // Calculate the position to insert the new line
        int position = 0;
        for (int i = 0; i < lineNumber; i++) {
            position = content.indexOf(System.lineSeparator(), position) + 1;
            if (position == 0) {
                break; // Break if the line separator is not found
            }
        }
        return position;
    }

    private static void insertTemplate(String filePath) throws IOException {
        // Inserting template
        String template = "{\"Products\":[\n\n] }";
        FileWriter writer = new FileWriter(filePath);
        writer.write(template);
        writer.close();
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