import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class githubUserActivity {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input github username: ");
        String name = sc.next();
        System.out.println(name);
        String githublink = "https://api.github.com/users/" + name + "/events";
        System.out.println(githublink);
        try {
            URL url = new URL(githublink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int respCode = conn.getResponseCode();

            if (respCode != 200) {
                throw new RuntimeException("Http response:" + respCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(conn.getInputStream());
                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                // The GitHub events API returns a JSON array
                JSONArray events = new JSONArray(informationString.toString());
                int count = 0;
                for(int i = 0; i < events.length(); i++){
                    JSONObject event = events.getJSONObject(i);
                    String type = event.getString("type");
                    JSONObject repo = event.getJSONObject("repo");
                    JSONObject payload = event.getJSONObject("payload");

                    if (type.equals("PushEvent")) {
                        JSONArray commits = payload.getJSONArray("commits");
                        String infoMessage = "- Pushed " + commits.length() + " commits to " + repo.getString("name");
                        System.out.println(infoMessage);
                        count += commits.length();

                    } else if (type.equals("IssuesEvent")) {
                        // "action" exists only for IssuesEvent
                        String action = payload.getString("action"); 
                        if (action.equals("opened")) {
                            String additionalMessage = "- Opened a new issue in " + repo.getString("name");
                            System.out.println(additionalMessage);
                        }

                    }

                }
                System.out.println("Total commits: " + count);
            }
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL: " + e.getMessage());
        } catch (ProtocolException e) {
            System.out.println("Protocol error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}