package com.etnetera.jslibrary.managers;

import com.etnetera.jslibrary.utils.MapUtils;
import com.vdurmont.semver4j.Semver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FrameworkVersionManager {

    private Integer index;
    private final MapUtils mapUtils;

    public FrameworkVersionManager(MapUtils mapUtils) {
        this.mapUtils = mapUtils;
    }

    public List<String> sortListOfVersions(List<String> unsortedList){

        Map<String, Integer> versionToIndexMap = new HashMap<>();

        List<Semver> listSemver = new ArrayList<>();
        // convert list of strings to a list of semvers
        unsortedList.forEach( version -> { listSemver.add(new Semver(version)); });

        // iterate through list of versions in listSemver
        for (Semver semver: listSemver) {
            // reset abstract index value
            index = -1;
            // compare each semver with all other semvers in a list
            listSemver.forEach( version -> {
                if (semver.isGreaterThanOrEqualTo(version)){
                    // if it's greater than the compared one add 1 to abstract index
                    index++;
                }
            });

            // assign index to a semver and add it to a map
            versionToIndexMap.put(semver.toString(), index);
        }

        //sort map by comparator
        var versionToIndexMapSorted = mapUtils.sortByComparator(versionToIndexMap, true);

        //for each version in dictionary add to result list with index of its key value
        //create list of versions /strings/ ordered from lower to higher
        List<String> resultList = new ArrayList<>(versionToIndexMapSorted.keySet());

        return resultList;

    }

    public List<String> mapSemverToList(List<Semver> semverList){
        return semverList.stream().map(Semver::getValue).toList();
    }


}
