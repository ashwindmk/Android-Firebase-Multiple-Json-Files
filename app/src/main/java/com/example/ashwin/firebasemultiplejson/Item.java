package com.example.ashwin.firebasemultiplejson;

/**
 * Created by ashwin on 2/12/16.
 */

public class Item
{
    private int position=0, age=0;
    private String name="", DOB="";

    public Item()
    {
        //essential for firebase
    }

    public Item(int age, String name, String DOB, int position) {
        this.age = age;
        this.name = name;
        this.DOB = DOB;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    @Override
    public String toString()
    {
        return getPosition()+". name : "+getName()+", age : "+getAge()+", DOB : "+getDOB();
    }
}
