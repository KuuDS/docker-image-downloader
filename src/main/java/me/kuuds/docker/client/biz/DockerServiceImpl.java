/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.biz;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.SaveImageCmd;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.exception.NotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import me.kuuds.docker.client.domain.RecentTags;
import me.kuuds.docker.client.domain.TagList;
import me.kuuds.docker.client.exception.BizException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 * Default Impl
 */
@ApplicationScoped
@Slf4j
public class DockerServiceImpl implements DockerService {
    @ConfigProperty(name = "app.default-registry")
    String registryUrl;

    @Inject
    @RestClient
    DockerRestClient dockerRestClient;

    @Inject
    DockerClient client;

    @Override
    public InputStream saveImage(final String imageUrl) throws BizException {
        try {
            log.info("start to save image [{}].", imageUrl);
            final SaveImageCmd cmd = client.saveImageCmd(imageUrl);
            var inputStream = cmd.exec();
            log.info("finish save image [{}].", imageUrl);
            return inputStream;
        } catch (final NotFoundException e) {
            log.error("Can't found image [{}].", imageUrl);
            return null;
        } catch (DockerException e) {
            throw new BizException("docker exception.", e);
        } catch (Exception e) {
            throw new BizException("Unknown exception.", e);
        }
    }

    @Override
    public void pullImage(final String imageUrl) throws BizException {
        try {
            log.info("start to pull image [{}].", imageUrl);
            client.pullImageCmd(imageUrl).start().awaitCompletion(1, TimeUnit.HOURS);
            log.info("finish pull image [{}].", imageUrl);
        } catch (InterruptedException e) {
            throw new BizException("fail to pull image", e);
        } catch (DockerException e) {
            throw new BizException("docker exception.", e);
        } catch (Exception e) {
            throw new BizException("Unknown exception.", e);
        }
    }

    @Override
    public RecentTags fetchTags(final String repositoryName) {
        return Optional
            .ofNullable(dockerRestClient.getTags(repositoryName))
            .map(tags -> handleTagList(repositoryName, tags))
            .orElse(RecentTags.EMPTY);
    }

    private RecentTags handleTagList(final String repositroyName, final TagList tagList) {
        final var recentTags = new RecentTags();
        recentTags.setTags(
            tagList
                .getTags()
                .stream()
                .map(
                    tag -> {
                        try {
                            return Long.parseLong(tag);
                        } catch (final NumberFormatException e) {
                            return null;
                        }
                    }
                )
                .filter(Objects::nonNull)
                .sorted(
                    (a, b) -> {
                        if (a > b) {
                            return -1;
                        } else if (a == b) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }
                )
                .limit(10)
                .map(tag -> registryUrl + repositroyName + ":" + Long.toString(tag))
                .collect(Collectors.toList())
        );
        return recentTags;
    }
}
