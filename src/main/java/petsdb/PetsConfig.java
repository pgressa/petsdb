package petsdb;

import io.micronaut.context.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PetsConfig {

    @Inject
    ApplicationContext applicationContext;

    public String getDBHost() {
        return applicationContext.getEnvironment().getProperty("dbhost", String.class).orElse("undefined");
    }

    public String getDBUser() {
        return applicationContext.getEnvironment().getProperty("dbuser", String.class).orElse("undefined");
    }

    public String getDBPassword() {
        return applicationContext.getEnvironment().getProperty("dbpassword", String.class).orElse("undefined");
    }
}