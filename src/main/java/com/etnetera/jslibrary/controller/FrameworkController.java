package com.etnetera.jslibrary.controller;

import com.etnetera.jslibrary.domain.Framework;
import com.etnetera.jslibrary.domain.FrameworkVO;
import com.etnetera.jslibrary.service.FrameworkService;
import com.github.dozermapper.core.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera
 *
 */
@RestController
public class FrameworkController {

    private final FrameworkService frameworkService;
    private final Mapper mapper;

    public FrameworkController(FrameworkService frameworkService, Mapper mapper) {
        this.frameworkService = frameworkService;
        this.mapper = mapper;
    }

    /**
     * GET /frameworks
     * Retrieve all frameworks
     *
     * @return When operation is successful (status code 200 + list of all frameworks)
     */
    @GetMapping("/frameworks")
    public ResponseEntity<List<Framework>> retrieveAll() {
        return new ResponseEntity<List<Framework>>(frameworkService.findAll(), HttpStatus.OK);
    }

    /**
     * GET /frameworks/{frameworkName}
     * Retrieve framework by name
     *
     * @param frameworkName name of the framework to retrieve
     * @return  When operation is successful (status code 200 + framework)
     *          or When item was not found (status code 400)
     */
    @GetMapping(value = "/frameworks/{frameworkName}")
    public ResponseEntity<Framework> retrieveByName(@PathVariable("frameworkName") String frameworkName){
        Optional<Framework> optional = frameworkService.findByName(frameworkName);
        if (optional.isPresent()){
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * PUT /frameworks/{frameworkName}
     * Update framework name
     *
     * @param frameworkName name of the framework to update
     * @return  When operation is successful (status code 202 + updated framework)
     *          or When request is invalid (status code 400)
     */
    @PutMapping(value = "/frameworks/{frameworkName}",
            produces = { "application/json", "application/problem+json" },
            consumes = { "text/plain" })
    public ResponseEntity<Framework> updateName(
            @PathVariable("frameworkName") String frameworkName,
            @RequestBody String newName){
        try {
            return new ResponseEntity<>(frameworkService.updateName(frameworkName, newName), HttpStatus.ACCEPTED);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST /frameworks/{frameworkName}
     * Update framework name
     *
     * @return  When operation is successful (status code 202 + updated framework)
     *          or When request is invalid (status code 400)
     */
    @PostMapping(
            value = "/frameworks",
            produces = { "application/json", "application/problem+json" },
            consumes = { "application/json" })
    public ResponseEntity<Framework> createFramework(@RequestBody FrameworkVO frameworkVO){
        Framework framework = mapper.map(frameworkVO, Framework.class);
        try {
            frameworkService.create(framework);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * DELETE /frameworks/{frameworkName}
     * @param frameworkName framework to delete
     * @return  When operation is successful (status code 200)
     */
    @DeleteMapping(value = "/frameworks/{frameworkName}")
    public ResponseEntity<String> deleteFramework(@PathVariable("frameworkName") String frameworkName){
        try{
            frameworkService.delete(frameworkName);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
