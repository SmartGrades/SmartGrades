package kz.tech.smartgrades.root.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseAutoCharge {
    public DatabaseReference getAutoCharge() {
        return FirebaseDatabase.getInstance().getReference("AutoCharge");
    }
}
