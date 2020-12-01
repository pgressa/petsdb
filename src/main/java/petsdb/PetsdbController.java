package petsdb;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.DefaultHttpClientConfiguration;
import io.micronaut.http.client.HttpClientConfiguration;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.ApplicationConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;

@Controller("/petsdb")
public class PetsdbController {
    private PetsdbStorage petsdbStorage;
    private PetsConfig petsConfig;

    public PetsdbController(PetsConfig petsConfig) {
        this.petsConfig = petsConfig;
        System.out.println("DB Host " + petsConfig.getDBHost());
        System.out.println("DB User " + petsConfig.getDBUser());
        System.out.println("DB Password " + petsConfig.getDBPassword());
        petsdbStorage = new PetsdbStorage(petsConfig.getDBHost(), petsConfig.getDBUser(), petsConfig.getDBPassword());
    }

    @Client()
    @Inject
    RxHttpClient httpClient;

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

    @Get("/")
    String list() {
        String res = petsdbStorage.list();
        putLog("{\"index\":{\"_id\":\"" + System.currentTimeMillis() + "\"}}\n{\"data\":\"" + "list()" + "\"}\n");
        return res;
    }

    @Get("/{name}")
    String get(String name) {
        String res = petsdbStorage.get(name);
        putLog("{\"index\":{\"_id\":\"" + System.currentTimeMillis() + "\"}}\n{\"data\":\"" + "get(" + name + ")" + "\"}\n");
        return res;
    }

    @Post("/")
    String add(@Body String pet)
    {
        if(pet.length() == 0)
        {
            putLog("{\"index\":{\"_id\":\"" + System.currentTimeMillis() + "\"}}\n{\"data\":\"" + "add() error: empty pet" + "\"}\n");
            return "'Error':'Empty pet'";
        } else {
            String res = petsdbStorage.add(pet);
            putLog("{\"index\":{\"_id\":\"" + System.currentTimeMillis() + "\"}}\n{\"data\":\"" + "add()" + "\"}\n");
            return res;
        }
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