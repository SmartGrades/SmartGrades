package kz.tech.smartgrades.root.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TableFamilyRoom.class, TableFamilyList.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IFamilyDao tasksDao();

}

