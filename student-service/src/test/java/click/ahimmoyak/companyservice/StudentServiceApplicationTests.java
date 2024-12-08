package click.ahimmoyak.companyservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = StudentServiceApplicationTests.class)
@ActiveProfiles(profiles = "test")
class StudentServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
