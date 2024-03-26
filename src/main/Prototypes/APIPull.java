//import javax.net.ssl.HttpsURLConnection;
//import java.io.IOException;
//import java.net.URL;
//import java.util.Scanner;
//
//public class APIPull {
//
//    public static <JSONParser> void main(String[] args) throws IOException {
//
//        try {
//
//            // "https://sandbox-api.digikey.com/Search/v3/Categories" -H "accept: application/json" -H "Authorization: JaXYD4WxWlDlRjOBTnjb0xShgxX89Im4" -H "X-DIGIKEY-Client-Id: gNXws4RAQSOWsfM7" -H "X-DIGIKEY-Locale-Site: US" -H "X-DIGIKEY-Locale-Language: en" -H "X-DIGIKEY-Locale-Currency: USD"
//
//            URL url = new URL();
//
//            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.connect();
//
//            // Check if connection is made
//            int responseCode = conn.getResponseCode();
//
//            // 200 OK
//            if (responseCode != 200) {
//                throw new RuntimeException("HttpSResponseCode: " + responseCode);
//            }
//            else {
//                StringBuilder informationString = new StringBuilder();
//                Scanner scanner = new Scanner(url.openStream());
//
//                while (scanner.hasNext()) {
//                    informationString.append(scanner.nextLine());
//                }
//                // Close scanner
//                scanner.close();
//
//                System.out.println(informationString);
//
//                // JSON simple library Step with Maven is used to convert string to JSON
//                JSONParser parse = new JSONParser();
//                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));
//
//
//            }
//        }
//
//
//
//    }
//}
