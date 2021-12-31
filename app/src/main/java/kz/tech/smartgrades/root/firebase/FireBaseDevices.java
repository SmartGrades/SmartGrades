package kz.tech.smartgrades.root.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseDevices {
    public DatabaseReference getDevices() {
        return FirebaseDatabase.getInstance().getReference("Devices");
    }
}
