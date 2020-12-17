package petsdb;

import io.micronaut.http.BasicAuth;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import petsdb.sauron.SauronClient;
import petsdb.sauron.SauronConfiguration;
import petsdb.sauron.SauronOperations;

/**
 * - default implementation of sauron client if sauron configuration is not included
 * -
 */
@Controller("/petsdb")
public class PetsdbController {
    public static final Logger logger = LoggerFactory.getLogger(PetsdbController.class);

    private final PetsdbStorage petsdbStorage;
    private final SauronOperations sauronOperations;
    private final BasicAuth sauronBasicAuth;

    public PetsdbController(PetsdbStorage petsdbStorage,
                            SauronOperations sauronOperations,
                            SauronConfiguration sauronConfiguration) {
        this.sauronOperations = sauronOperations;
        this.petsdbStorage = petsdbStorage;
        this.sauronBasicAuth = new BasicAuth(sauronConfiguration.getUser(), sauronConfiguration.getPassword());
    }

    @Get("/log")
    String log()
    {
        return sauronOperations.getLog(sauronBasicAuth);
    }

    @Post("/log")
    String log(@Body String json)
    {
        return sauronOperations.addLog(sauronBasicAuth, json);
    }

    @Get("/")
    String list() {
        String res = petsdbStorage.list();
        logger.debug("List pets" + res);
        log("{\"index\":{\"_id\":\"" + System.currentTimeMillis() + "\"}}\n{\"data\":\"" + "list()" + "\"}\n");
        return res;
    }

    @Get("/{name}")
    String get(String name) {
        String res = petsdbStorage.get(name);
        logger.debug("Get pet" + res);
        log("{\"index\":{\"_id\":\"" + System.currentTimeMillis() + "\"}}\n{\"data\":\"" + "get(" + name + ")" + "\"}\n");
        return res;
    }

    @Post("/")
    String add(@Body String pet)
    {
        logger.debug("Add pet" + pet);
        if(pet.length() == 0)
        {
            log("{\"index\":{\"_id\":\"" + System.currentTimeMillis() + "\"}}\n{\"data\":\"" + "add() error: empty pet" + "\"}\n");
            return "'Error':'Empty pet'";
        } else {
            String res = petsdbStorage.add(pet);
            log("{\"index\":{\"_id\":\"" + System.currentTimeMillis() + "\"}}\n{\"data\":\"" + "add()" + "\"}\n");
            return res;
        }
    }

}