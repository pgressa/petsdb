package petsdb.sauron;


import io.micronaut.context.annotation.ConfigurationProperties;


@ConfigurationProperties(SauronConfiguration.PREFIX)
public class SauronConfiguration {

    public static final String PREFIX = "configuration.sauron";

    private String uri;
    private String user;
    private String password;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}