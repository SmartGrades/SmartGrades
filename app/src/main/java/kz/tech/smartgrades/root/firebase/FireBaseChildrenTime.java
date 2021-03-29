package kz.tech.smartgrades.root.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseChildrenTime {
    public DatabaseReference getChildrenTime() {
        return FirebaseDatabase.getInstance().getReference("ChildrenTime");
    }
}
