package com.example.myproject;

public class ClassModel {

    private int id;
    private String ClassName;
    private float Grade;
    private boolean status;

    public ClassModel(int id, String className, float grade, boolean status) {
        this.id = id;
        ClassName = className;
        Grade = grade;
        this.status = status;
    }

    public ClassModel() {
    }

    @Override
    public String toString() {
        return "ClassModel{" +
                "id=" + id +
                ", ClassName='" + ClassName + '\'' +
                ", Grade=" + Grade +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public float getGrade() {
        return Grade;
    }

    public void setGrade(float grade) {
        Grade = grade;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
