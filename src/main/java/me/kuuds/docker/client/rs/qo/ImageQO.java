/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.rs.qo;

import java.net.URI;
import java.net.URL;
import lombok.Data;

/**
 * query object for image info
 *
 * @author KuuDS
 */
@Data
public class ImageQO {
    private String repo;
    private String context;
    private String image;
    private String tag;

    /**
     * convert from image url to {@link ImageQO}
     *
     * @param image image url
     * @return new instance
     */
    public static ImageQO of(String image) {
        URI uri = URI.create(image);
        final var qo = new ImageQO();
        return qo;
    }

    public String toUri() {
        return repo + context + "/" + image + ":" + tag;
    }
}
