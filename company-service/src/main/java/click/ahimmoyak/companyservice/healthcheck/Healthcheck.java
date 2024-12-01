package click.ahimmoyak.companyservice.healthcheck;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class Healthcheck implements HealthIndicator {

    @Override
    public Health health() {
        boolean isServiceUp = checkServiceStatus();

        if (isServiceUp) {
            return Health.up()
                    .withDetail("CustomCheck", "Service is running")
                    .build();
        } else {
            return Health.down()
                    .withDetail("CustomCheck", "Service is down")
                    .build();
        }
    }

    private boolean checkServiceStatus() {
        return true;
    }
}