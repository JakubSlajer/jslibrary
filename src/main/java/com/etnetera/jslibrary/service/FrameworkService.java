package com.etnetera.jslibrary.service;

import com.etnetera.jslibrary.domain.Framework;
import com.etnetera.jslibrary.repository.FrameworkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FrameworkService {

    private final FrameworkRepository frameworkRepository;

    public FrameworkService(FrameworkRepository frameworkRepository) {
        this.frameworkRepository = frameworkRepository;
    }

    public void findAll(){
        frameworkRepository.findAll();
    }

    public void findByName(String name){
        frameworkRepository.findByName(name);
    }

    public void updateName(String oldName, String newName) throws Exception {
        Optional<Framework> opt = frameworkRepository.findByName(oldName);
        if (opt.isPresent()){
            var fw = opt.get();
            fw.setName(newName);
            frameworkRepository.save(fw);
        } else {
            throw new Exception(String.format("can't find object with given name %s", oldName));
        }
    }

    public void addVersion(String fwName, String version){
        Framework fw = frameworkRepository.findByName(fwName).orElseThrow();
        List<String> versions = fw.getVersions();
        var new_versions_list = new ArrayList<String>(versions);
        new_versions_list.add(version);
        fw.setVersions(new_versions_list);
        frameworkRepository.save(fw);
    }

    public void delete(String name) throws Exception {
        Optional<Framework> instance = frameworkRepository.findByName(name);
        if(instance.isPresent()){
            frameworkRepository.deleteByName(name);
        } else {
            throw new Exception(String.format("object with given name not found %s", name));
        }
    }
}
