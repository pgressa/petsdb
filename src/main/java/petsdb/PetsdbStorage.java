package petsdb;

import javax.inject.Singleton;
import com.mysql.cj.xdevapi.*;

@Singleton
public class PetsdbStorage {

    // Connect to mysql server on OCI

    private Session session;
    private Schema petsDb;

    public PetsdbStorage(String dbHost, String dbUser, String dbPassword) {

        try  {
            session = new SessionFactory().getSession("mysqlx://" + dbHost + ":33060/pets?user=" + dbUser + "&password=" + dbPassword);
            petsDb = session.getSchema("pets");
            petsDb.createCollection("pets"); // Let's create the pets collection if database empty
        } catch (Exception e)
        {
            System.out.println("PetsStorage() " + e.toString());
        }
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
}
