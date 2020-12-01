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

    public String getSauronUri() {
        return applicationContext.getEnvironment().getProperty("sauronuri", String.class).orElse("undefined");
    }

    public String getSauronUser() {
        return applicationContext.getEnvironment().getProperty("sauronuser", String.class).orElse("undefined");
    }

    public String getSauronPassword() {
        return applicationContext.getEnvironment().getProperty("sauronpassword", String.class).orElse("undefined");
    }
}