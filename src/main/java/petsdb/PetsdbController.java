package petsdb;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

@Controller("/petsdb")
public class PetsdbController {
    private PetsdbStorage petsdbStorage;

    public PetsdbController(PetsConfig petsConfig) {
        System.out.println("DB Host " + petsConfig.getDBHost());
        System.out.println("DB User " + petsConfig.getDBUser());
        System.out.println("DB Password " + petsConfig.getDBPassword());
        petsdbStorage = new PetsdbStorage(petsConfig.getDBHost(), petsConfig.getDBUser(), petsConfig.getDBPassword());
    }

    @Get("/")
    String list() {
        return petsdbStorage.list();
    }

    @Get("/{name}")
    String get(String name) {
        return petsdbStorage.get(name);
    }

    @Post("/")
    String add (@Body String pet)
    {
        if(pet.length() == 0)
        {
            return "'Error':'Empty pet'";
        } else {
            return petsdbStorage.add(pet);
        }
    }
}