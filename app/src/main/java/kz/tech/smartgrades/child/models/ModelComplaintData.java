package kz.tech.smartgrades.child.models;

import java.util.ArrayList;

public class ModelComplaintData {
    private String SourceId;
    private String SchoolId;
    private ArrayList<ModelComplaintDataStudent> Students;
    private ArrayList<ModelComplaintDataTeacher> Teachers;

    public String getSourceId() {
        return SourceId;
    }

    public void setSourceId(String sourceId) {
        SourceId = sourceId;
    }

    public String getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(String schoolId) {
        SchoolId = schoolId;
    }

    public ArrayList<ModelComplaintDataStudent> getStudents() {
        return Students;
    }

    public void setStudents(ArrayList<ModelComplaintDataStudent> students) {
        Students = students;
    }

    public ArrayList<ModelComplaintDataTeacher> getTeachers() {
        return Teachers;
    }

    public void setTeachers(ArrayList<ModelComplaintDataTeacher> teachers) {
        Teachers = teachers;
    }
}
