/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package me.kuuds.docker.client.biz;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.SaveImageCmd;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import java.io.*;
import lombok.extern.slf4j.Slf4j;
import me.kuuds.docker.client.util.AppUtils;
import org.junit.jupiter.api.Test;

@Slf4j
public class DockerClientTest {
    private final String fullName = "alpine:3";

    @Test
    public void saveToLocalTest() {
        DockerClientConfig custom = DefaultDockerClientConfig
            .createDefaultConfigBuilder()
            .build();
        DockerClient client = DockerClientBuilder.getInstance(custom).build();
        SaveImageCmd cmd = client.saveImageCmd(fullName);
        InputStream inputStream = cmd.exec();
        log.info("finish {}.", fullName);
        String fileName = AppUtils.createFileName(fullName);
        OutputStream fout = null;
        try {
            File file = new File("target" + File.separatorChar + fileName);
            if (file.exists()) {
                if (!file.delete()) {
                    throw new IOException("fail to delete " + fileName);
                }
            }
            if (!file.createNewFile()) {
                throw new IOException("fail to create " + fileName);
            }
            fout = new FileOutputStream(fileName, true);
            byte[] b = new byte[1024];
            int len;
            while (-1 != (len = inputStream.read(b, 0, b.length))) {
                fout.write(b, 0, len);
            }
            log.info("finish write {}.", fullName);
        } catch (NotFoundException e) {
            log.error("Can't found image {}.", fullName);
        } catch (IOException e) {
            log.error("error", e);
            System.exit(-1);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
