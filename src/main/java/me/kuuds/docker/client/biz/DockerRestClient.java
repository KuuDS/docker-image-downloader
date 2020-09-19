/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.biz;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import me.kuuds.docker.client.domain.TagList;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@ApplicationScoped
@RegisterRestClient(configKey = "docker-api")
public interface DockerRestClient {
    @GET
    @Path("/v2/{repository}/tags/list")
    @Produces("application/json")
    TagList getTags(@PathParam String repository);
}
