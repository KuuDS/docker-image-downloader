/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.biz;

import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import lombok.RequiredArgsConstructor;
import me.kuuds.docker.client.Utils;
import me.kuuds.docker.client.exception.BizException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
@RequiredArgsConstructor(onConstructor = @__({ @Inject }))
public class DockerServiceTest {
    private final DockerService dockerService;

    @Test
    public void shouldBeInjected() {
        Assertions.assertNotNull(dockerService, "DockerService should not be null.");
    }

    @Test
    public void shouldReturnTrue() throws BizException {
        dockerService.pullImage(Utils.TARGET_IMAGE);
    }
}
