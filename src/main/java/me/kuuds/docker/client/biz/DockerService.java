package me.kuuds.docker.client.biz;

import java.io.InputStream;

import me.kuuds.docker.client.domain.RecentTags;

/**
 * Docker Operation Service Layer.
 *
 * @author KuuDS
 */
public interface DockerService {

    /**
     * fetch tar raw data from docker client.
     *
     * @param imageUrl full image url
     * @return {@link InputStream}
     */
    InputStream saveImage(String imageUrl);

    /**
     * Pull image from docker client.
     *
     * @param imageUrl
     */
    void pullImage(String imageUrl) throws InterruptedException;

    /**
     * Fetch tags' list for specific repository.
     *
     * @param repositoryName
     * @return {@link RecentTags}
     */
    RecentTags fetchTags(String repositoryName);

}
