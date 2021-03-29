package kz.tech.smartgrades.root.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBasePath implements IFireBase {

    public FireBasePath() { }


    @Override
    public DatabaseReference getFireBase(String path) {
        return FirebaseDatabase.getInstance().getReference(path);
    }
}
