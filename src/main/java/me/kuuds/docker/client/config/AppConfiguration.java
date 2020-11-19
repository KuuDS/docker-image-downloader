package me.kuuds.docker.client.config;

import io.quarkus.arc.config.ConfigProperties;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
@ConfigProperties(prefix = "app")
public class AppConfiguration {

    @Getter
    private List<String> repositories;

    @Getter
    private List<String> context;

    @Setter
    @Getter
    private String url;

    public void setRepositories(String repositories) {
        this.repositories = Arrays.asList(repositories.split(","));
    }

    public void setContext(String context) {
        this.context = Arrays.asList(context.split(","));
    }

}
