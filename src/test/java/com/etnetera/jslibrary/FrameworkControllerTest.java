package com.etnetera.jslibrary;


import com.etnetera.jslibrary.domain.Framework;
import com.etnetera.jslibrary.domain.FrameworkVO;
import com.etnetera.jslibrary.repository.FrameworkRepository;
import com.github.dozermapper.core.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FrameworkControllerTest extends AbstractTest {

    @Autowired
    FrameworkRepository frameworkRepository;
    @Autowired
    Mapper mapper;

    private final String baseUrl = "http://localhost:8081";
    private RestTemplate restTemplate;

    Framework fwReactJs;
    Framework fwVueJs;
    Framework fwAngular;

    @BeforeEach
    void setUpTestCaseData(){
        restTemplate = new RestTemplate();


        fwReactJs = new Framework(
                "ReactJs",
                List.of("0.0.1", "0.0.2", "0.0.3")
        );

        fwVueJs = new Framework(
                "VueJs",
                List.of("0.0.1", "0.0.2", "0.0.3")
        );

        fwAngular = new Framework(
                "Angular",
                List.of("0.0.1", "0.0.2", "0.0.3")
        );

        frameworkRepository.saveAll(List.of(fwReactJs, fwVueJs, fwAngular));
    }

    @Test
    void testRetrieveAll(){
        FrameworkVO[] response = restTemplate.getForObject(baseUrl + "/frameworks", FrameworkVO[].class);
        assertNotNull(response);
        List<FrameworkVO> listFrameworkVO = new ArrayList<>(List.of(response));
        List<Framework> frameworks = listFrameworkVO.stream().map(frameworkVO -> {
            return mapper.map(frameworkVO, Framework.class);
        }).toList();

        assertEquals(frameworks, List.of(fwReactJs, fwVueJs, fwAngular));
    }

    @Test
    void testRetrieveByName(){
        ResponseEntity<FrameworkVO> response = restTemplate.getForEntity(
                baseUrl + "/frameworks/" + fwReactJs.getName(), FrameworkVO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        try {
            restTemplate.getForEntity(baseUrl + "/frameworks/" + "non_existing_name", String.class);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    void testUpdateName(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"name\":\"some name\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/frameworks/" + fwReactJs.getName(), HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        Framework framework = frameworkRepository.findById("some name").orElseThrow();
        assertNotNull(framework);
        assertNotNull(framework.getUpdated());

        try {
            restTemplate.exchange(
                    baseUrl + "/frameworks/" + "non_existing_name", HttpMethod.PUT, entity, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    void testAddVersions(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"versions\":[\"0.0.1-alpha\", \"0.0.3-alpha\"]}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/frameworks/version/" + fwReactJs.getName(), HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        Framework framework = frameworkRepository.findById(fwReactJs.getName()).orElseThrow();
        assertEquals(List.of("0.0.1-alpha", "0.0.1", "0.0.2", "0.0.3-alpha", "0.0.3"), framework.getVersions());
        assertNotNull(framework.getUpdated());

        try {
            restTemplate.exchange(
                    baseUrl + "/frameworks/version/" + "non_existing_name", HttpMethod.PUT, entity, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    void testCreateFramework(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"name\":\"EmberJs\", \"versions\":[\"0.0.1-alpha\", \"0.0.3-alpha\"]}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/frameworks", HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(frameworkRepository.findById("EmberJs"));
    }

    @Test
    void testDeleteFramework(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/frameworks/" + fwReactJs.getName(), HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Framework> frameworks = frameworkRepository.findAll();
        assertEquals(frameworks, List.of(fwVueJs, fwAngular));
    }
}
