package click.ahimmoyak.companyservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = CompanyServiceApplicationTests.class)
@ActiveProfiles(profiles = "test")
class CompanyServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
