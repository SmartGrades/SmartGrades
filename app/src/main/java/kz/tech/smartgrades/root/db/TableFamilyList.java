package kz.tech.smartgrades.root.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "familyList")
public class TableFamilyList {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String zid;

    public String login;

    public TableFamilyList() {}

    public TableFamilyList(String zid, String login) {
        this.zid = zid;
        this.login = login;
    }

}
