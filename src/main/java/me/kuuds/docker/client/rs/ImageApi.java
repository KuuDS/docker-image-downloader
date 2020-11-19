/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.rs;

import com.google.common.collect.Lists;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kuuds.docker.client.biz.DockerService;
import me.kuuds.docker.client.config.AppConfiguration;
import me.kuuds.docker.client.domain.RecentTags;
import me.kuuds.docker.client.exception.BizException;
import me.kuuds.docker.client.exception.BizExceptionHandler;
import me.kuuds.docker.client.rs.qo.ImageQO;
import me.kuuds.docker.client.util.AppUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Path("/image")
public class ImageApi {
    @ConfigProperty(name = "app.registry-prefix")
    String registryPrefixes;

    private static final int DEFAULT_CACHE_SIZE = 1024;
    private final DockerService dockerService;
    private final BizExceptionHandler bizExceptionHandler;
    private final AppConfiguration appConfiguration;

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

        StreamingOutput stream = os -> {
            byte[] b = new byte[DEFAULT_CACHE_SIZE];
            int len;
            while (-1 != (len = inputStream.read(b, 0, b.length))) {
                os.write(b, 0, len);
            }
            os.flush();
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
    public RecentTags availableImages(@QueryParam("name") String name) {
        return dockerService.fetchTags(name);
    }

    @PUT
    @Path("/pull")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> pullImage(ImageQO imageQO) {
        return Uni.createFrom().item(imageQO)
                .onItem().transform(qo -> dockerService.pullImage(imageQO))
                .onItem().transform(this::success)
                .onFailure().invoke(this::handleException);
    }

    @PUT
    @Path("/package")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> packageImage(ImageQO imageQO) {
        return Uni.createFrom().item(imageQO)
                .onItem().transform(qo -> dockerService.packageImage(imageQO))
                .onItem().transform(this::success)
                .onFailure().recoverWithItem(this::handleException);
    }

    @GET
    @Path("/download/{fileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Uni<Response> getFile(@PathParam("fileName") String fileName) {
        return Uni.createFrom().item(fileName)
                .onItem().transform(File::new)
                .onItem().transform(file -> Response.ok(file)
                        .header("Content-Disposition", "attachment;filename=" + fileName)
                        .build())
                .onFailure().recoverWithItem(this::handleException);
    }

    @GET
    @Path("/prefix")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> registryPrefix() {
        return Lists.newArrayList(registryPrefixes.split(","));
    }

    @GET
    @Path("/repo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getRepositories() {
        return appConfiguration.getRepositories();
    }

    @GET
    @Path("/context")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getContext() {
        return appConfiguration.getContext();
    }

    private Response handleException(Throwable e) {
        if (e instanceof BizException) {
            return bizExceptionHandler.toResponse((BizException) e);
        } else {
            return bizExceptionHandler.toResponse(new BizException("unknown exception.", e));
        }
    }

    public Response success(Object data) {
        return Response.ok(data).build();
    }
}
