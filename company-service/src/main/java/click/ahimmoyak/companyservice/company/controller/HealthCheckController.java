package click.ahimmoyak.companyservice.company.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthCheckController {


    @GetMapping("/actuator/health")
    public ResponseEntity<String> healthCheck() {

        log.info("/ healthy check");
        return ResponseEntity.ok("Health Check Passed!");
    }
}
