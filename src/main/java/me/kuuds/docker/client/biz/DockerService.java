package me.kuuds.docker.client.biz;

import java.io.InputStream;

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
     * @param imageUrl
     */
    void pullImage(String imageUrl) throws InterruptedException;
}
