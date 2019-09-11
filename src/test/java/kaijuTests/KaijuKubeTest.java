package kaijuTests;

import io.kubernetes.client.ApiClient;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.Configuration;

import java.io.IOException;

public class KaijuKubeTest {

    public static void main(String[] args) throws IOException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api api = new CoreV1Api(client);

    }
}
