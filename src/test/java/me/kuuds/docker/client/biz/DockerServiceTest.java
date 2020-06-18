package me.kuuds.docker.client.biz;

import lombok.RequiredArgsConstructor;
import me.kuuds.docker.client.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DockerServiceTest {

    private final DockerService dockerService;

    @Test
    public void shouldBeInjected() {
        Assert.notNull(dockerService, "DockerService should not be null.");
    }

    @Test
    public void shouldReturnTrue() throws InterruptedException {
        dockerService.pullImage(Utils.TARGET_IMAGE);
    }



}
