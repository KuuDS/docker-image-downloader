/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.rs;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kuuds.docker.client.biz.DockerService;
import me.kuuds.docker.client.domain.RecentTags;
import me.kuuds.docker.client.exception.BizException;
import me.kuuds.docker.client.util.AppUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Path("/image")
public class ImageApi {
    @ConfigProperty(name = "app.registry-prefix")
    String registryPrefixes;

    private static final int DEFAULT_CACHE_SIZE = 1024;
    private final DockerService dockerService;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadImage(@QueryParam("name") String imageUrl)
        throws BizException {
        dockerService.pullImage(imageUrl);
        final var inputStream = dockerService.saveImage(imageUrl);

        if (inputStream == null) {
            log.warn("Can't find image [{}].", imageUrl);
            return Response.status(Status.NOT_FOUND).build();
        }

        log.info("Start to pump raw data to response for image [{}].", imageUrl);
        final var fileName = AppUtils.createFileName(imageUrl);

        StreamingOutput stream = new StreamingOutput() {

            @Override
            public void write(OutputStream os)
                throws IOException, WebApplicationException {
                byte[] b = new byte[DEFAULT_CACHE_SIZE];
                int len;
                while (-1 != (len = inputStream.read(b, 0, b.length))) {
                    os.write(b, 0, len);
                }
                os.flush();
            }
        };
        return Response
            .ok()
            .entity(stream)
            .type(MediaType.APPLICATION_OCTET_STREAM)
            .header("Content-Disposition", "attachment; filename=" + fileName)
            .build();
    }

    @GET
    @Path("/tags")
    @Produces(MediaType.APPLICATION_JSON)
    public RecentTags avaibleImages(@QueryParam("name") String name) {
        return dockerService.fetchTags(name);
    }

    @GET
    @Path("/prefix")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> registryPrefix() {
        return Lists.newArrayList(registryPrefixes.split(","));
    }
}
