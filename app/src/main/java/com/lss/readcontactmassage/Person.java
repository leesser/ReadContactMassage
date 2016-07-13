package com.lss.readcontactmassage;

/**
 * Created by shuai on 16-7-12.
 */
public class Person {
    private int id;
    private String name;
    private String number;
    public Person(){
    }
    public Person(int id,String name,String number){
        this.id=id;
        this.name=name;
        this.number=number;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
