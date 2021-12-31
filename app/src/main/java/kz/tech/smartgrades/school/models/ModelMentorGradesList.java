package kz.tech.smartgrades.school.models;

public class ModelMentorGradesList {

    private String TotalGrades;
    private String TotalGradesSelect;
    private String TotalSponsored;
    private String TotalSponsoredSelect;
    private String TotalWasAbsent;
    private String CenterGrade;

    private String MentorId;

    public ModelMentorGradesList() { }

    public ModelMentorGradesList(String TotalGrades, String TotalGradesSelect, String TotalSponsored, String TotalSponsoredSelect, String TotalWasAbsent, String CenterGrade) {
        this.TotalGrades = TotalGrades;
        this.TotalGradesSelect = TotalGradesSelect;
        this.TotalSponsored = TotalSponsored;
        this.TotalSponsoredSelect = TotalSponsoredSelect;
        this.TotalWasAbsent = TotalWasAbsent;
        this.CenterGrade = CenterGrade;
    }


    public String getTotalGrades() {
        return TotalGrades;
    }

    public void setTotalGrades(String totalGrades) {
        TotalGrades = totalGrades;
    }

    public String getTotalGradesSelect() {
        return TotalGradesSelect;
    }

    public void setTotalGradesSelect(String totalGradesSelect) {
        TotalGradesSelect = totalGradesSelect;
    }

    public String getTotalSponsored() {
        return TotalSponsored;
    }

    public void setTotalSponsored(String totalSponsored) {
        TotalSponsored = totalSponsored;
    }

    public String getTotalSponsoredSelect() {
        return TotalSponsoredSelect;
    }

    public void setTotalSponsoredSelect(String totalSponsoredSelect) {
        TotalSponsoredSelect = totalSponsoredSelect;
    }

    public String getTotalWasAbsent() {
        return TotalWasAbsent;
    }

    public void setTotalWasAbsent(String totalWasAbsent) {
        TotalWasAbsent = totalWasAbsent;
    }

    public String getCenterGrade() {
        return CenterGrade;
    }

    public void setCenterGrade(String centerGrade) {
        CenterGrade = centerGrade;
    }

    public String getMentorId() {
        return MentorId;
    }

    public void setMentorId(String mentorId) {
        MentorId = mentorId;
    }
}