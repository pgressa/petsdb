package auth;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import org.apache.commons.codec.binary.Base64;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;

@Controller("/auth")
public class PetsdbController {
    private PetsConfig petsConfig;

    public PetsdbController(PetsConfig petsConfig) {
        this.petsConfig = petsConfig;
    }

    @Client()
    @Inject
    RxHttpClient httpClient;

    @Get("/")
    String authQuery(@QueryValue String token)
    {
        String auth = "mika:123456";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        String authString = new String(encodedAuth);
        System.out.println("TOKEN: '" + authString + "'");
        System.out.println("INPUT: '" + token + "'");
        String ret = "";
        if(authString.equals(token)) {
            System.out.println("MATCH!");
            ret =
                    "{" +
                            "\"active\": true," +
                            "\"principal\": \"mrinne@oracle.com\"," +
                            "\"scope\": [\"petsdb\", \"log\"]," +
                            "\"expiresAt\": \"2020-12-31T00:00:00+00:00\"," +
                            "}";
        } else {
            System.out.println("NO MATCH!");
            ret =
                    "{" +
                            "\"active\": false," +
                            "\"expiresAt\": \"2020-12-31T00:00:00+00:00\"," +
                            "\"wwwAuthenticate\": \"Bearer realm=\"" +
                            "}";
        }
        return ret;
    }

    @Get("/log")
    String log()
    {
        return getLog();
    }

    @Post("/log")
    String log(@Body String json)
    {
        return putLog(json);
    }

    public String getLog()
    {
        if(petsConfig.getSauronUri() != "undefined") {
            HttpResponse<String> rsp = httpClient.toBlocking().exchange(HttpRequest.GET(petsConfig.getSauronUri() + "/_search")
                            .contentType(MediaType.APPLICATION_JSON)
                            .basicAuth(petsConfig.getSauronUser(), petsConfig.getSauronPassword()),
                    String.class);

            System.out.println(rsp.getBody());
            return rsp.getBody().orElse("");
        } else {
            return "";
        }
    }

    public String putLog(String json)
    {
        if(petsConfig.getSauronUri() != "undefined") {
            System.out.println("=====> LOG PUT:\n" + json);
            HttpResponse<String> rsp = httpClient.toBlocking().exchange(HttpRequest.POST(petsConfig.getSauronUri() + "/_bulk", json)
                            .contentType(MediaType.APPLICATION_JSON)
                            .basicAuth(petsConfig.getSauronUser(), petsConfig.getSauronPassword()),
                    String.class);

            System.out.println(rsp.getBody());
            return rsp.getBody().orElse("");
        } else {
            return ""; 
        }
    }
}