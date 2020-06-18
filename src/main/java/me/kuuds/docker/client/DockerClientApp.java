package me.kuuds.docker.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
public class DockerClientApp {
    public static void main(String[] args) {
        SpringApplication.run(DockerClientApp.class);
    }

    @EventListener(ApplicationFailedEvent.class)
    public void onFailed(ApplicationFailedEvent event){
        log.warn("Start to exit.", event.getException());
        System.exit(-1);
    }
}
