package stepdefs;

import com.reser_pro.auth_service.AuthServiceApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = AuthServiceApplication.class)
public class CucumberSpringConfiguration {

}