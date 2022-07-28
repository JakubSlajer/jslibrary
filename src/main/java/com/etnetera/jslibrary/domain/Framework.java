package com.etnetera.jslibrary.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document
public class Framework extends Item {

    @Id
    private String name;
    private List<String> versions;

    public Framework() {
    }

    public Framework(String name, List<String> versions) {
        this.name = name;
        this.versions = versions;
    }

    @Override
    public LocalDateTime getCreated() {
        return super.getCreated();
    }

    @Override
    public void setCreated(LocalDateTime created) {
        super.setCreated(created);
    }

    @Override
    public LocalDateTime getUpdated() {
        return super.getUpdated();
    }

    @Override
    public void setUpdated(LocalDateTime updated) {
        super.setUpdated(updated);
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
        return "Framework{" +
                ", name='" + name + '\'' +
                ", versions='" + versions + '\'' +
                ", created=" + super.getCreated() +
                ", updated=" + super.getUpdated() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Framework framework = (Framework) o;
        return Objects.equals(getName(),
                framework.getName()) && Objects.equals(getVersions(), framework.getVersions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getVersions());
    }
}
