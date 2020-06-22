package me.kuuds.docker.client.biz;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.SaveImageCmd;
import com.github.dockerjava.api.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kuuds.docker.client.domain.RecentTags;
import me.kuuds.docker.client.domain.TagList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Default Impl
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DockerServiceImpl implements DockerService {

    @Value("${default-registry:'index.docker.io'}")
    private String registryUrl;
    @Value("${fetch-with-https:ture}")
    private Boolean fetchWithHttps;

    private final RestTemplate restClient;
    private final DockerClient client;

    @Override
    public InputStream saveImage(String imageUrl) {
        log.info("start save image [{}].", imageUrl);
        InputStream inputStream = null;
        try {
            log.info("start to save image [{}].", imageUrl);
            SaveImageCmd cmd = client.saveImageCmd(imageUrl);
            inputStream = cmd.exec();
            log.info("finish save image [{}].", imageUrl);
        } catch (NotFoundException e) {
            log.error("Can't found image [{}].", imageUrl);
        }
        return inputStream;
    }

    @Override
    public void pullImage(String imageUrl) throws InterruptedException {
        log.info("start to pull image [{}].", imageUrl);
        client.pullImageCmd(imageUrl).start().awaitCompletion(1, TimeUnit.HOURS);
        log.info("finish pull image [{}].", imageUrl);
    }

    @Override
    public RecentTags fetchTags(String repositoryName) {
        return Optional
                .of((fetchWithHttps ? "https://" : "http://") + registryUrl + "/v2" + repositoryName + "/tags/list")
                .map(url -> {
                    try {
                        return restClient.getForEntity(url, TagList.class);
                    } catch (RestClientException e) {
                        log.warn("rest client error.", e);
                        return null;
                    }
                }).filter(resp -> resp.getStatusCode().is2xxSuccessful()).map(ResponseEntity::getBody)
                .map(tags -> this.handleTagList(repositoryName, tags)).orElse(RecentTags.EMPTY);
    }

    private RecentTags handleTagList(String repositroyName, TagList tagList) {
        RecentTags recentTags = new RecentTags();
        recentTags.setTags(tagList.getTags().stream().map(tag -> {
            try {
                return Long.parseLong(tag);
            } catch (NumberFormatException e) {
                return null;
            }
        }).filter(Objects::nonNull).sorted((a, b) -> {
            if (a > b) {
                return -1;
            } else if (a == b) {
                return 0;
            } else {
                return 1;
            }
        }).limit(10).map(tag -> registryUrl + repositroyName + ":" + Long.toString(tag)).collect(Collectors.toList()));
        return recentTags;
    }
}
