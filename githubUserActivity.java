import java.util.Scanner;

public class githubUserActivity{


    public static void main(String[] args){
       Scanner sc = new Scanner(System.in);
       System.out.println("Input github username: ");
       String name = sc.next();
       System.out.println(name);
       String githublink = "https://api.github.com/users/" +name+"/events";
       System.out.println(githublink);
       sc.close();
    }
}