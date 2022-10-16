package ru.practicum.ewmmainservice.itegrationtests;

import initializer.Postges;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(initializers = {
        Postges.Initializer.class
})
@Transactional
public class IntegrationTestsBase {

    @BeforeAll

    public static void init(){
        Postges.container.start();
    }
}
