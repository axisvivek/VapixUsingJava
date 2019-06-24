package axisvapix;

import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
//import org.json.JSONArray;
//import org.json.JSONObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Request;

public class TestLogin {
    public static void TestHello() throws UnirestException, InterruptedException {
            System.out.println("Inside Function:");
            HttpResponse<String> response = Unirest.post("http://192.168.1.155/axis-cgi/param.cgi")
            .header("accept", "application/json")
            //action=list&responseformat=rfc
            .queryString("action", "list")
            .queryString("responseformat", "rfc")
            .basicAuth("root", "pass")
            //.field("parameter", "value")
            //.field("foo", "bar")
            .asString();
            System.out.println("Body:\n"+response.getBody());
            System.out.println("String:\n"+response.toString());
    }

    public static void TestDigest() throws UnirestException, InterruptedException {
        System.out.println("Inside Digest Function:");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
        .url("http://192.168.1.155/axis-cgi/param.cgi?action=list&responseformat=rfc")
        .get()
        .addHeader("cache-control", "no-cache")
        .addHeader("Postman-Token", "af3f16a3-167c-417f-aee4-518164f63494")
        .build();
        try
        {
            Response response = client.newCall(request).execute();
            System.out.println(response.toString());
        }
        catch(Exception e1)
        {
            System.out.println(e1.toString());
        }
      
        
}



    public static void main(String[] args) {
            System.out.println("Sending Request...");
            try
            {
                //TestHello();
                TestDigest();
            }
            catch(Exception e1)
            {
                System.out.println(e1.toString());
            }
            
    }
}