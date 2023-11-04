package com.zachm.demo;

public class Person {
    private String FirstName;
    private String LastName;
    private long Age;
    private String Gender;

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public Person(String firstName, String lastName, long age, String gender) {
        FirstName = firstName;
        LastName = lastName;
        Age = age;
        Gender = gender;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public long getAge() {
        return Age;
    }



    @Override
    public String toString() {
        if(LastName == null) {
            LastName = "Unknown";
        }
        return "Name:" + FirstName + " " + LastName + ", Age:" + Age + ", Gender: " + Gender;
    }
}
