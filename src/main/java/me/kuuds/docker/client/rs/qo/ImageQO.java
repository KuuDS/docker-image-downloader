package me.kuuds.docker.client.rs.qo;

import lombok.Data;

import java.net.URI;
import java.net.URL;

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
