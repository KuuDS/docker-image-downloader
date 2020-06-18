package me.kuuds.docker.client.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kuuds.docker.client.biz.DockerService;
import me.kuuds.docker.client.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.dockerjava.api.exception.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor(onConstructor = @__({ @Autowired }))
@Slf4j
public class ImageApi {

    private static final int DEFAULT_CACHE_SIZE = 1024;
    private final DockerService dockerService;

    @GetMapping("")
    public void downloadPDFResource(HttpSession session, HttpServletResponse response,
            @RequestParam("name") String imageUrl) throws IOException, InterruptedException {
        String sessionID = session.getId();
        dockerService.pullImage(imageUrl);
        InputStream inputStream = dockerService.saveImage(imageUrl);

        if (inputStream == null) {
            log.warn("[{}]: Can't find image [{}].", sessionID, imageUrl);
            response.sendError(HttpStatus.NOT_FOUND.value(), "Can't find image " + imageUrl + ".");
            return;
        }

        log.info("[{}]: start to pump raw data to response.", sessionID);
        String fileName = AppUtils.createFileName(imageUrl);
        response.setContentType("application/tar");
        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setStatus(HttpStatus.OK.value());
        pump(inputStream, response);
    }

    private void pump(InputStream inputStream, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[DEFAULT_CACHE_SIZE];
        int len;
        while (-1 != (len = inputStream.read(b, 0, b.length))) {
            outputStream.write(b, 0, len);
        }
        outputStream.flush();
    }

}
