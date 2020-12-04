package ru.open.api.models;

public class UnregisteredUser {
    public String name;
    public String job;
    public int id;
    public String createdAt;

    public UnregisteredUser(String name, String job, int id, String createdAt) {
        this.name = name;
        this.job = job;
        this.id = id;
        this.createdAt = createdAt;
    }

//    @Override
//    public String toString() {
//        String result = "{";
//        int fieldsCounter = 0;
//        if (name != null) {
//            result += "name='" + name + '\'';
//            fieldsCounter++;
//        }
//        if (job != null) {
//            if (fieldsCounter > 0) {
//                result += ", ";
//            }
//            result += "job='" + job + '\'';
//            fieldsCounter++;
//        }
//        if (id != null) {
//            if (fieldsCounter > 1) {
//                result += ", ";
//            }
//            result += "id='" + id + '\'';
//            fieldsCounter++;
//        }
//        if (createdAt != null) {
//            if (fieldsCounter > 2) {
//                result += ", ";
//            }
//            result += "createdAt='" + createdAt + '\'';
//        }
//        result += "}";
//        return result;
//    }
}
