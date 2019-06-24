package axisvapix;

import kong.unirest.Unirest;
import kong.unirest.HttpResponse;;

public class TestLogin {
    public static void TestHello() {
            System.out.println("A new instance of the Test class was created!");
    }

    public static void main(String[] args) {
            System.out.println("Sending Request...");
        HttpResponse<String> response = Unirest.post("http://192.168.1.155/axis-cgi/param.cgi")
        .header("accept", "application/json")
        .queryString("action", "list")
        .queryString("responseformat", "rfc")
        .basicAuth("root", "pass")
        //.field("parameter", "value")
        //.field("foo", "bar")
        .asString();
        System.out.println(response.getBody());
    }
}