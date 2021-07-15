/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.config;

import io.quarkus.arc.config.ConfigProperties;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.List;

//@ApplicationScoped
//@ConfigProperties(prefix = "app")
@Getter
@Setter
public class AppConfiguration {

    private List<String> repositories;

    private List<String> context;

    private String url;

//    public void setRepositories(String repositories) {
//        this.repositories = Arrays.asList(repositories.split(","));
//    }

//    public void setContext(LString context) {
//        this.context = Arrays.asList(context.split(","));
//    }
}
