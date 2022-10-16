package initializer;

import lombok.experimental.UtilityClass;
import org.apache.catalina.core.ApplicationContext;
import org.junit.ClassRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

@UtilityClass
public class Postges {
    @ClassRule
    public static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14-alpine");
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url="+ container.getJdbcUrl(),
                    "spring.datasource.username="+ container.getUsername(),
                    "spring.datasource.password="+container.getPassword()
            ).applyTo(applicationContext);
        }
    }
}
