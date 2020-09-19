/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DockerClientConfiguration {
    @ConfigProperty(name = "app.docker-host")
    String dockerHost;

    @Produces
    public DockerClientConfig dockerClientConfig() {
        return DefaultDockerClientConfig
            .createDefaultConfigBuilder()
            .withDockerHost(dockerHost)
            .build();
    }

    @Produces
    public DockerClient dockerClient(
        DockerClientConfig config,
        DockerHttpClient httpClient
    ) {
        return DockerClientImpl.getInstance(config, httpClient);
    }

    @Produces
    public DockerHttpClient dockerHttpClient(DockerClientConfig config) {
        return new ZerodepDockerHttpClient.Builder()
            .dockerHost(config.getDockerHost())
            .build();
    }
}
