package powersell.cheapat9.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/")
    public String home() {
        log.info("Home API called");
        return "Welcome to CheapAt9!";
    }
}
