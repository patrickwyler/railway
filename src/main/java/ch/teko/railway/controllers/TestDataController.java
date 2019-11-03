package ch.teko.railway.controllers;

import ch.teko.railway.services.TestDataService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Testdata controller can be removed if no longer needed for testing purposes
 */
@Controller
@Getter
public class TestDataController {

    TestDataService testDataService;

    @Autowired
    public TestDataController(TestDataService testDataService) {
        this.testDataService = testDataService;
    }

    @PostMapping("/createTestData")
    public String createTestData() {
        getTestDataService().createTestData();
        return "index";
    }

}
