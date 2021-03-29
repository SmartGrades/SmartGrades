package kz.tech.smartgrades.root.firebase;

import com.google.firebase.database.DatabaseReference;

public interface IFireBase {
    DatabaseReference getFireBase(String path);
}
