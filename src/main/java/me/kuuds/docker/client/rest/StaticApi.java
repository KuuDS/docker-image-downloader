package me.kuuds.docker.client.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticApi {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
