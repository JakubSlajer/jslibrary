package com.etnetera.jslibrary.domain;

import java.io.Serializable;
import java.util.List;

public class FrameworkVO implements Serializable {
    public String name;
    public List<String> versions;

    public FrameworkVO() {
    }

    public FrameworkVO(String name, List<String> versions) {
        this.name = name;
        this.versions = versions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    @Override
    public String toString() {
        return "FrameworkVO{" +
                ", name='" + name + '\'' +
                ", versions=" + versions +
                '}';
    }
}
