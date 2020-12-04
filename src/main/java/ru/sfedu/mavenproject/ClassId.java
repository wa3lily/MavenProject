package ru.sfedu.mavenproject;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.util.Objects;

public class ClassId implements Serializable {

    @CsvBindByName
    long id;

    public ClassId() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassId classId = (ClassId) o;
        return id == classId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ClassId{" +
                "id=" + id +
                '}';
    }
}
