package com.etnetera.jslibrary.utils;

import com.etnetera.jslibrary.managers.FrameworkVersionManager;
import com.vdurmont.semver4j.Semver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class VersionNumberGenerator {

    private final FrameworkVersionManager frameworkVersionManager;


    public VersionNumberGenerator(FrameworkVersionManager frameworkVersionManager) {
        this.frameworkVersionManager = frameworkVersionManager;
    }

    /**
     * Generate random number in range
     * @param origin origin
     * @param bounds bounds
     * @return random number in range
     */
    public Integer randInt(int origin, int bounds){
        Random random = new Random();
        return random.nextInt(origin, bounds);
    }

    /**
     * Generates random version number
     * @return Semver version
     */
    public Semver generateRandomVersionNumber(){
        return new Semver(String.format(
                "%s.%s.%s",
                randInt(1,10),
                randInt(1,10),
                randInt(1,10)
        ));
    }

    /**
     * Generates list of random version numbers
     * @param count version numbers count
     * @return list of version numbers
     */
    public List<String> generateRandomVersionList(int count){
        List<Semver> semverList = new ArrayList<>();
        Semver version;

        for (int i = 0; i <= count; i++){
            version = generateRandomVersionNumber();
            // if the last number in the list is greater than newly added number
            // or if the list is empty
            // add it at the end of the list
            if (semverList.isEmpty() || semverList.get(semverList.size() - 1).isGreaterThanOrEqualTo(version)){
                semverList.add(version);
            // if the newly added number is higher, add it at the beginning of a list
            } else {
                semverList.add(0, version);
            }
        }

        return frameworkVersionManager.mapSemverToList(semverList);
    }
}
