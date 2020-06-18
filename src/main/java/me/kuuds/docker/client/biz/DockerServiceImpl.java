package me.kuuds.docker.client.biz;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.command.SaveImageCmd;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.PullResponseItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DockerServiceImpl implements DockerService {

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


}
