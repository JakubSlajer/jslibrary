package com.etnetera.jslibrary.managers;

import com.vdurmont.semver4j.Semver;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FrameworkVersionManager {

    public List<String> sortListOfVersions(List<String> unsortedList) {
        // nothing to do, just return empty list
        if (CollectionUtils.isEmpty(unsortedList)) {
            return Collections.emptyList();
        }

        final var semvers = unsortedList.stream()
                .map(Semver::new) // creating Semver instances
                .sorted() // Semver class already implements Comparable IF, so it's not necessary to write some specific comparator
                .map(Semver::getValue) // mapping Semver back to version
                .toList(); // creating a new list of sorted versions

        return semvers;
    }

    public List<String> mapSemverToList(List<Semver> semverList) {
        return semverList.stream().map(Semver::getValue).toList();
    }


}
