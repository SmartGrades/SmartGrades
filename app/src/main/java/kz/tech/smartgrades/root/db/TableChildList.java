package kz.tech.smartgrades.root.db;

import androidx.room.PrimaryKey;

public class TableChildList {
    @PrimaryKey(autoGenerate = true)
    public long id;


    private String fullNameChild;
    private String birthdayChild;
    private String loginChild;
    private String familyStatusChild;
    private String avatarSelectChild;
    private String accessCodeChild;
    private String idChild;
}
