package kz.tech.smartgrades;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;

public class _Firebase {

    public DatabaseReference getRefUsers() {
        return FirebaseDatabase.getInstance().getReference("Users");
    }
    public DatabaseReference getRefMentors() {
        return FirebaseDatabase.getInstance().getReference("Mentors");
    }

    public DatabaseReference getRefChildRoom() {
        return FirebaseDatabase.getInstance().getReference("ChildRoom");
    }
    public DatabaseReference getRefMentorRoom() {
        return FirebaseDatabase.getInstance().getReference("MentorRoom");
    }
    public DatabaseReference getRefFamilyRoom() {
        return FirebaseDatabase.getInstance().getReference("FamilyRoom");
    }
    public DatabaseReference getRefSponsorRoom() {
        return FirebaseDatabase.getInstance().getReference("SponsorRoom");
    }
    public DatabaseReference getRefSchoolRoom() {
        return FirebaseDatabase.getInstance().getReference("SchoolRoom");
    }

    public DatabaseReference getRefMentorGroups() {
        return FirebaseDatabase.getInstance().getReference("MentorGroups");
    }
    public DatabaseReference getRefSchoolGroups() {
        return FirebaseDatabase.getInstance().getReference("SchoolGroups");
    }
    public DatabaseReference getRefSponsorGroups() {
        return FirebaseDatabase.getInstance().getReference("SponsorGroups");
    }

    public DatabaseReference getRefSponsorCash() {
        return FirebaseDatabase.getInstance().getReference("SponsorCash");
    }
    public DatabaseReference getRefParentCash() {
        return FirebaseDatabase.getInstance().getReference("ParentCash");
    }

    public DatabaseReference getRefInterFormIn() {
        return FirebaseDatabase.getInstance().getReference("InteractionFormIn");
    }

    public DatabaseReference getRefInterFormOut() {
        return FirebaseDatabase.getInstance().getReference("InteractionFormOut");
    }

    public DatabaseReference getRefChildData() {
        return FirebaseDatabase.getInstance().getReference("ChildData");
    }
    public DatabaseReference getRefChats() {
        return FirebaseDatabase.getInstance().getReference("Chats");
    }
    public DatabaseReference getRefLessons() {
        return FirebaseDatabase.getInstance().getReference("Lessons");
    }

    public static void sendGrade(String sourceId, String targetId, String groupId, String lessonId, String msgType, String data, String msg) {
        DatabaseReference drChats = new _Firebase().getRefChats().child(targetId);
        Map<String, String> hmChat = new HashMap<>();
        hmChat.put("data", data);
        hmChat.put("date", new Date().toString());
        hmChat.put("message", msg);
        hmChat.put("sourceId", sourceId);
        hmChat.put("groupId", groupId);
        hmChat.put("lessonId", lessonId);
        hmChat.put("type", msgType);
        String idChat = drChats.push().getKey();
        drChats.child(idChat).setValue(hmChat);
    }

    public static void sendMsg(String sourceId, String targetId, String msgType, String data, String msg) {
        DatabaseReference drChats = new _Firebase().getRefChats().child(targetId);

        Map<String, String> hmChat = new HashMap<>();
        hmChat.put("data", data);
        hmChat.put("date", new Date().toString());
        hmChat.put("message", msg);
        hmChat.put("sourceId", sourceId);
        hmChat.put("type", msgType);
        String idChat = drChats.push().getKey();
        drChats.child(idChat).setValue(hmChat);
    }

    public static void sendMsgEx(String sourceId, String targetId, String chatId, String msgType, int data, String msg) {
        DatabaseReference dbrChats = new _Firebase().getRefChats();
        if (chatId == null) chatId = dbrChats.push().getKey();
        String msgId = dbrChats.push().getKey();
        ModelDefaultMessages modelDefaultChat = new ModelDefaultMessages();
        modelDefaultChat.setChatId(chatId);
        modelDefaultChat.setSourceId(sourceId);
        modelDefaultChat.setTargetId(targetId);
        modelDefaultChat.setDate(new Date().toString());
        modelDefaultChat.setNoCheckCount(data);
        modelDefaultChat.setMessage(msg);
        modelDefaultChat.setMessageId(msgId);
        dbrChats.child(chatId).child(msgId).setValue(modelDefaultChat);
    }

    public static void sendMoney(String ChildId, String ParentId) {
        DatabaseReference SponsorRoom = new _Firebase().getRefSponsorRoom();
        SponsorRoom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    ArrayList<String> sponsorsId = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String sponsorId = snapshot.getKey();
                        for (DataSnapshot snapshot2 : dataSnapshot.child(sponsorId).getChildren()){
                            String childId = snapshot2.getKey();
                            if (childId.equals(ChildId))
                                sponsorsId.add(sponsorId);
                        }
                    }
                    DatabaseReference Cash = new _Firebase().getRefSponsorCash();
                    Cash.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                int finalSendMoney = 0;
                                for (int j = 0; j < sponsorsId.size(); j++){
                                    String string = (String) dataSnapshot.child(sponsorsId.get(j)).getValue();
                                    int sendMoney = 8167;
                                    int i = Convert.Str2Int(string);
                                    if (i == 0) return;
                                    else if (i >= sendMoney) i -= sendMoney;
                                    else {
                                        sendMoney = i;
                                        i = 0;
                                    }
                                    Cash.child(sponsorsId.get(j)).setValue(String.valueOf(i));
                                    sendMsg(sponsorsId.get(j), ParentId, "msg", "", "Вам начислено + " + sendMoney + "тенге. Вы молодцы!");
                                    finalSendMoney += sendMoney;
                                }
                                DatabaseReference Cash = new _Firebase().getRefParentCash();
                                int finalSendMoney1 = finalSendMoney;
                                Cash.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String string = (String) dataSnapshot.child(ParentId).getValue();
                                        int i = Convert.Str2Int(string) + finalSendMoney1;
                                        Cash.child(ParentId).setValue(String.valueOf(i));
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public static void setChildToolEnable(String chilId, String toolType, boolean enable) {
        DatabaseReference drTools = FirebaseDatabase.getInstance().getReference("Settings").child(chilId).child(toolType);
        drTools.child("Enable").setValue(enable);
    }
}