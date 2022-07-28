package com.etnetera.jslibrary.service;

import com.etnetera.jslibrary.domain.Framework;
import com.etnetera.jslibrary.managers.FrameworkVersionManager;
import com.etnetera.jslibrary.repository.FrameworkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FrameworkService {

    private final FrameworkRepository frameworkRepository;
    private final FrameworkVersionManager frameworkVersionManager;

    public FrameworkService(FrameworkRepository frameworkRepository,
                            FrameworkVersionManager frameworkVersionManager) {
        this.frameworkRepository = frameworkRepository;
        this.frameworkVersionManager = frameworkVersionManager;
    }

    public List<Framework> findAll() {
        return frameworkRepository.findAll();
    }

    public Optional<Framework> findById(String id) {
        return frameworkRepository.findById(id);
    }

    public Framework updateName(String oldName, String newName) throws Exception {
        Optional<Framework> opt = frameworkRepository.findById(oldName);
        if (opt.isPresent()) {
            var fw = opt.get();
            fw.setName(newName);
            fw.setUpdated(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
            frameworkRepository.save(fw);
            return fw;
        } else {
            throw new Exception(String.format("can't find object with given name %s", oldName));
        }
    }

    public void addVersion(String fwName, List<String> versionList) {

        Framework fw = frameworkRepository.findById(fwName).orElseThrow();
        var newVersionsList = new ArrayList<>(fw.getVersions());
        newVersionsList.addAll(versionList);
        fw.setVersions(frameworkVersionManager.sortListOfVersions(newVersionsList));
        fw.setUpdated(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        frameworkRepository.save(fw);
    }

    public void create(Framework framework) {
        frameworkRepository.save(framework);
    }

    public void delete(String name) throws Exception {
        Optional<Framework> instance = frameworkRepository.findById(name);
        if (instance.isPresent()) {
            frameworkRepository.deleteById(name);
        } else {
            throw new Exception(String.format("object with given name not found %s", name));
        }
    }
}
