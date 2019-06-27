package axisvapix;

import java.security.Principal;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

import com.mashape.unirest.http.HttpResponse;
//import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
//import org.json.JSONArray;
//import org.json.JSONObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import org.apache.http.auth.Credentials;
import org.apache.http.impl.auth.DigestScheme;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Headers;
import axisvapix.DigestAuthenticator;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    

    public static void TestDigestForStatus() throws UnirestException, InterruptedException {
        DigestAuthenticator mDigestAuthenticator = new DigestAuthenticator();
        mDigestAuthenticator.mCredentials = new Credentials(){
        
            @Override
            public Principal getUserPrincipal() {
                return new Principal(){
                
                    @Override
                    public String getName() {
                        //Camera Username
                        return "root";
                    }
                };
            }
        
            @Override
            public String getPassword() {
                //Camera Password
                return "pass";
            }
        };

        mDigestAuthenticator.mDigestScheme = new DigestScheme();
        System.out.println(mDigestAuthenticator.mDigestScheme.getSchemeName());
        OkHttpClient client = new OkHttpClient();
        client.setAuthenticator(mDigestAuthenticator);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"apiVersion\": \"1.0\",\r\n  \"context\": \"abc\",\r\n  \"method\": \"status\"\r\n}");
        Request request = new Request.Builder()
        .url("http://192.168.1.155/axis-cgi/firmwaremanagement.cgi")
        .post(body)
        .addHeader("Content-Type", "application/json")
        .build();
        try
        {
            Response response = client.newCall(request).execute();
            //System.out.println(response.body());
            //System.out.println(response.toString());
            InputStream is = response.body().byteStream();
            BufferedReader rd = new BufferedReader(
                        new InputStreamReader(is));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result.toString());
        }
        catch(Exception e1)
        {
            System.out.println(e1.toString());
        }
      
        
}

    public static void TestDigestForUpgrade() throws UnirestException, InterruptedException {
    DigestAuthenticator mDigestAuthenticator = new DigestAuthenticator();
    mDigestAuthenticator.mCredentials = new Credentials(){
    
        @Override
        public Principal getUserPrincipal() {
            return new Principal(){
            
                @Override
                public String getName() {
                    //Camera Username
                    return "root";
                }
            };
        }
    
        @Override
        public String getPassword() {
            //Camera Password
            return "pass";
        }
    };

    mDigestAuthenticator.mDigestScheme = new DigestScheme();
    System.out.println(mDigestAuthenticator.mDigestScheme.getSchemeName());
    OkHttpClient client = new OkHttpClient();
    client.setAuthenticator(mDigestAuthenticator);
    String content = new String("D://Q6055-E_8_40_2_1.bin");
    byte[] sample = null;
    try {
        sample = Files.readAllBytes(Paths.get(content));
    } catch (Exception e) {
        System.out.println(e.getStackTrace().toString());
    }
    
    RequestBody requestBody = new MultipartBuilder("File1")
        .type(MultipartBuilder.FORM)
        .addFormDataPart("files", null,
            new MultipartBuilder("File2")
                .addPart(
                    Headers.of("Content-Disposition", "file; filename=\"Q6055-E_8_40_2_1.bin\""),
                    RequestBody.create(
                        MediaType.parse("application/json"), "{\r\n  \"apiVersion\": \"1.0\",\r\n  \"context\": \"abc\",\r\n  \"method\": \"upgrade\"\r\n}"))
                .addPart(
                    Headers.of(
                        "Content-Disposition", "file; filename=\"Q6055-E_8_40_2_1.bin\"",
                        "Content-Transfer-Encoding", "binary"),
                    RequestBody.create(
                        MediaType.parse("application/octet-stream"),sample
                        ))
                .build())
        .build();


        



    MediaType mediaType = MediaType.parse("application/json");
    //RequestBody body = RequestBody.create(mediaType, "{\r\n  \"apiVersion\": \"1.0\",\r\n  \"context\": \"abc\",\r\n  \"method\": \"status\"\r\n}");
    Request request = new Request.Builder()
    .url("http://192.168.1.155/axis-cgi/firmwaremanagement.cgi")
    .post(requestBody)
    .addHeader("content-type", "multipart/form-data; boundary=File1")
    .build();
    try
    {
        Response response = client.newCall(request).execute();
        //System.out.println(response.body());
        //System.out.println(response.toString());
        InputStream is = response.body().byteStream();
        BufferedReader rd = new BufferedReader(
                    new InputStreamReader(is));

    StringBuffer result = new StringBuffer();
    String line = "";
    while ((line = rd.readLine()) != null) {
        result.append(line);
    }

    System.out.println(result.toString());
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
                //TestDigestForStatus();
                TestDigestForUpgrade();
            }
            catch(Exception e1)
            {
                System.out.println(e1.toString());
            }
            
    }
}