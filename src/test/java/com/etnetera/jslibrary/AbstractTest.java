package com.etnetera.jslibrary;


import com.etnetera.jslibrary.repository.FrameworkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AbstractTest {

    @Autowired
    FrameworkRepository frameworkRepository;

    /**
     * Resets the test collection before each test method
     */
    @BeforeEach
    public void beforeEach(){
        frameworkRepository.deleteAll();
    }
}
