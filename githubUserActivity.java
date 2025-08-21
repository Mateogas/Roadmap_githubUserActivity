import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import java.io.StringReader;

public class githubUserActivity{


    public static void main(String[] args){
       Scanner sc = new Scanner(System.in);
       System.out.println("Input github username: ");
       String name = sc.next();
       System.out.println(name);
       String githublink = "https://api.github.com/users/" +name+"/events";
       System.out.println(githublink);
       try {
           URL url = new URL(githublink);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int respCode = conn.getResponseCode();

            if(respCode != 200){
                throw new RuntimeException("Http response:"+respCode);

            }else{
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while(scanner.hasNext()){
                    informationString.append(scanner.nextLine());


                }
                scanner.close();

                System.out.println(informationString);
                
            }
           // You can use 'url' here for further processing
       } catch (Exception e) {
           System.out.println("Invalid URL: " + e.getMessage());
       }

    }
}