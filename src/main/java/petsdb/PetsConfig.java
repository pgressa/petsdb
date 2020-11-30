package petsdb;

import io.micronaut.context.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PetsConfig {

    @Inject
    ApplicationContext applicationContext;

    public String getDBHost() {
        return applicationContext.getEnvironment().getProperty("db-host", String.class).orElse("10.0.1.4");
    }

    public String getDBUser() {
        return applicationContext.getEnvironment().getProperty("db-user", String.class).orElse("pet");
    }

    public String getDBPassword() {
        return applicationContext.getEnvironment().getProperty("db-password", String.class).orElse("Qazwsx12!");
    }
}