package petsdb;

import javax.inject.Singleton;
import com.mysql.cj.xdevapi.*;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Instaed of creating the queries, the micronaut-data provides Repository
 * bean that does lot of thins for you for free:
 * TODO - Use repository
 */
@Singleton
public class PetsdbStorage {

    public static final Logger log = LoggerFactory.getLogger(PetsdbStorage.class);

    public static final String COLLECTION = "pets";
    private Schema petsDb;

    public PetsdbStorage(Schema petsDb) {
        this.petsDb = petsDb;
    }

    String list() {
        try  {
            com.mysql.cj.xdevapi.Collection pets = petsDb.getCollection("pets");
            DocResult res = pets.find("name like :param").bind("param", "%").execute();
            String results =  res.fetchAll().toString();
            System.out.println("list(): " + results);
            return results;
        } catch (Exception e)
        {
            System.out.println("list() " + e.toString());
            return "'Error':list(): " + e.toString() + "'";
        }
    }

    String get(String name) {
        try  {
            com.mysql.cj.xdevapi.Collection pets = petsDb.getCollection("pets");
            DocResult res = pets.find("name like :param").bind("param", name).execute();
            String results =  res.fetchAll().toString();
            return results;
        } catch (Exception e)
        {
            System.out.println("get(" + name + ") " + e.toString());
            return "'Error':'get(" + name + "): " + e.toString() + "'";
        }
    }

    String add(String pet) {
        try  {
            com.mysql.cj.xdevapi.Collection pets = petsDb.getCollection("pets");
            pets.add(pet).execute();
            return "Success";
        } catch (Exception e)
        {
            System.out.println("add(" + pet + ") " + e.toString());
            return "'Error':'add(" + pet + ") : " + e.toString() + "'";
        }
    }

    @EventListener
    void onStartup(StartupEvent startupEvent){
        try {
            petsDb.getCollection(PetsdbStorage.COLLECTION, true);
            log.debug("Collection " + PetsdbStorage.COLLECTION + " exists.");
        }catch (Exception e) {
            log.debug("Initializing " + PetsdbStorage.COLLECTION + " collection.");
            petsDb.createCollection(PetsdbStorage.COLLECTION); // Let's create the pets collection if database empty
        }
    }
}
