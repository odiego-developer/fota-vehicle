package com.man.fotavehicle;

import com.man.fotavehicle.domain.VehicleFeature;
import com.man.fotavehicle.service.FotaVehicleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static  org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FotaVehicleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FotaVehicleService fotaVehicleService;

    private String BASE_URL;

    @Before
    public void startUp(){
        BASE_URL = "/fota/vehicles/";
    }


    @Test
    public void test_getInstallablePositive() throws Exception {

        String vin = "3C3CFFER4ET929645";

        VehicleFeature feature = createVehicleFeature(vin);

        Mockito.when(fotaVehicleService.getInstallable(vin)).thenReturn(Optional.of(feature));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( BASE_URL + vin + "/installable");

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(
                "{\"vin\":\"3C3CFFER4ET929645\",\"softwareCodes\":[\"FVlp0N\",\"I25pUg\",\"FhFXVE\"],\"hardwareCodes\":[\"jyP5PK\",\"6VO6Uq\",\"ZCLFOe\"]}",
                result.getResponse().getContentAsString()
        );
    }

    @Test
    public void test_getInstallableNegative() throws Exception {

        String rightVinCode = "3C3CFFER4ET929645";
        String wrongVinCode = "ABC";

        VehicleFeature feature = createVehicleFeature(rightVinCode);

        Mockito.when(fotaVehicleService.getInstallable("3C3CFFER4ET929645")).thenReturn(Optional.of(feature));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( BASE_URL + wrongVinCode + "/installable");

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void test_getIncompatiblePositive() throws Exception {

        String vin = "3C3CFFER4ET929645";

        VehicleFeature feature = createVehicleFeature(vin);

        Mockito.when(fotaVehicleService.getIncompatible(vin)).thenReturn(Optional.of(feature));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( BASE_URL + vin + "/incompatible");

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertEquals(
                "{\"vin\":\"3C3CFFER4ET929645\",\"softwareCodes\":[\"FVlp0N\",\"I25pUg\",\"FhFXVE\"],\"hardwareCodes\":[\"jyP5PK\",\"6VO6Uq\",\"ZCLFOe\"]}",
                result.getResponse().getContentAsString()
        );
    }

    @Test
    public void test_getIncompatibleNegative() throws Exception {

        String rightVinCode = "3C3CFFER4ET929645";
        String wrongVinCode = "ABC";

        VehicleFeature feature = createVehicleFeature(rightVinCode);

        Mockito.when(fotaVehicleService.getInstallable("3C3CFFER4ET929645")).thenReturn(Optional.of(feature));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get( BASE_URL + wrongVinCode + "/installable");

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }


    private VehicleFeature createVehicleFeature(String vin) {
        Set<String> hardwareCodes = new HashSet<>();
        hardwareCodes.add("FhFXVE");
        hardwareCodes.add("FVlp0N");
        hardwareCodes.add("I25pUg");

        Set<String> softwareCodes = new HashSet<>();
        softwareCodes.add("6VO6Uq");
        softwareCodes.add("ZCLFOe");
        softwareCodes.add("jyP5PK");

        return new VehicleFeature(vin,hardwareCodes,softwareCodes);
    }

}
