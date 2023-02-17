package grvt.cloud.epam_web.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlliveController {
    @GetMapping("/allive")
    public String greeting() {
        return "Hello from EPAM lab!\n Endpoint called ";
    }
}
