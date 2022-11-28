package com.insightfullogic.java8.practice;

import com.insightfullogic.java8.examples.chapter1.Track;

import java.util.List;
import java.util.stream.Stream;

public class Student {
    private Integer id;
    private String name;
    private String gender;
    private List<Class1> class1List;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Stream<Class1> getClass1List() {
        return class1List.stream();
    }

    public void setClass1List(List<Class1> class1List) {
        this.class1List = class1List;
    }

    public Student(Integer id, String name, String gender, List<Class1> class1List) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.class1List = class1List;
    }
}
