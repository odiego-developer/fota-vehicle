package com.man.fotavehicle;

import com.man.fotavehicle.domain.FotaVehicleHardware;
import com.man.fotavehicle.domain.VehicleFeature;
import com.man.fotavehicle.persistence.FotaVehicleHardwareRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import static org.junit.Assert.*;

@CucumberContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = {
                FotaVehicleApplication.class
        })
public class CucumberStepDefinitions {

    private final Logger log = LoggerFactory.getLogger(CucumberStepDefinitions.class);

    @Autowired
    private FotaVehicleHardwareRepository fotaVehicleHardwareRepository;

    @LocalServerPort
    private int port;

    private final String BASE_URL = "http://localhost:";

    private RestTemplate restTemplate = new RestTemplate();

    private final Map<String,Object> context = new HashMap<>();

    @Given("the following hardware and vin codes were persisted in data base")
    public void theFollowingHardwareAndVinCodesWerePersistedInDataBase(List<Map<String,String>> dataTable) {

        Set<FotaVehicleHardware> hardwareCodes = new HashSet<>();
        dataTable.forEach(map -> {
            hardwareCodes.add(new FotaVehicleHardware(map.get("vinCode"), map.get("hardwareCode")));
        });
        fotaVehicleHardwareRepository.saveAll(hardwareCodes);
        log.info("hardware codes persisted {}",hardwareCodes.toString());
    }

    @When("^I get the installable features by vin code (\\S+)$")
    public void iGetTheInstallableFeaturesByVinCodeCode(String vinCode) {

        String url = BASE_URL + port +"/fota/vehicles/"+vinCode+"/installable";
        ResponseEntity<VehicleFeature> response = restTemplate.getForEntity(url, VehicleFeature.class);
        assertNotNull(response);
        context.put("statusCode", response.getStatusCode().value());
        context.put("hardwareResponseBody", response.getBody());
        log.info("Request processed  {}", url);

    }

    @Then("response status code is {int}")
    public void responseStatusCodeIs(int statusCode) {

        assertEquals(statusCode,context.get("statusCode"));
        log.info("Response status code  {}", context.get("statusCode"));


    }

    @And("the response body should only contains the following hardware codes")
    public void theResponseBodyShouldOnlyContainsTheFollowingHardwareCodes(List<Map<String,String>> dataTable) {
        Set<String> hardwareCodes = new HashSet<>();
        dataTable.forEach(map ->{
            hardwareCodes.add(map.get("hardwareCode"));
        });

        VehicleFeature feature = (VehicleFeature) context.get("hardwareResponseBody");
        assertNotNull(feature);
        log.info("Response body {}", feature.toString());
        assertFalse(feature.getHardwareCodes().isEmpty());
        assertTrue(feature.getHardwareCodes().containsAll(hardwareCodes));

    }
}
