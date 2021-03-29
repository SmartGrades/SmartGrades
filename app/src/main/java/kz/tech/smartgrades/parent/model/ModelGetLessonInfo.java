package kz.tech.smartgrades.parent.model;

import java.util.ArrayList;

import kz.tech.smartgrades.mentor.models.ModelGradesTableMonth;

public class ModelGetLessonInfo {
    private ArrayList<ModelGradesTableMonth> GradesTable;
    private ArrayList<ModelChatsLastMessages> LastMessages;

    public ArrayList<ModelGradesTableMonth> getGradesTable() {
        return GradesTable;
    }

    public void setGradesTable(ArrayList<ModelGradesTableMonth> gradesTable) {
        GradesTable = gradesTable;
    }

    public ArrayList<ModelChatsLastMessages> getChatsLastMessages() {
        return LastMessages;
    }

    public void setChatsLastMessages(ArrayList<ModelChatsLastMessages> chatsLastMessages) {
        LastMessages = chatsLastMessages;
    }
}
