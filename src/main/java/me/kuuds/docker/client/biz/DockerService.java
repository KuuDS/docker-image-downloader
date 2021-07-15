/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.biz;

import java.io.InputStream;
import me.kuuds.docker.client.domain.RecentTags;
import me.kuuds.docker.client.domain.TaskInfo;
import me.kuuds.docker.client.exception.BizException;
import me.kuuds.docker.client.rs.qo.ImageQO;

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
    InputStream saveImage(String imageUrl) throws BizException;

    /**
     * Pull image from docker client.
     *
     * @param imageUrl
     */
    void pullImage(String imageUrl) throws BizException;

    /**
     * Fetch tags' list for specific repository.
     *
     * @param repositoryName
     * @return {@link RecentTags}
     */
    RecentTags fetchTags(String repositoryName);
}
