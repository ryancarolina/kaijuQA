package httpClient;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class GoogleHttpClientExample extends GenericUrl{

    static HttpTransport httpTransport = new NetHttpTransport();
    static JsonFactory jsonFactory = new JacksonFactory();

    public static void run() throws Exception {

        HttpRequestFactory requestFactory = httpTransport.createRequestFactory(
                (HttpRequest request) -> {

                    request.setParser(new JsonObjectParser(jsonFactory));

                });

        GitHubUrl url = new GitHubUrl("https://api.github.com/users");

        HttpRequest request = requestFactory.buildGetRequest(url);
        request.setParser(new JsonObjectParser(jsonFactory));

        Type type = new TypeToken<List<GithubUser>>() {}.getType();

        List<GithubUser> users = (List<GithubUser>) request.execute().parseAs(type);

        System.out.println(users);

    }

    public static void main(String[] args){
        try{
            run();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

}
