package grvt.cloud.epam.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlliveController {
    @GetMapping("/alive")
    public String aliveEndpoint() {
        return "Hello from EPAM lab!\n Endpoint called ";
    }
}
