package uk.gov.ofwat.fountain.modelbuilder.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * Created by Adam Edgar on 14/06/2016.
 */
@Service
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "modelBuilder.fountainRestService")
public class FountainRestService {

    private String url;
    private String user;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
