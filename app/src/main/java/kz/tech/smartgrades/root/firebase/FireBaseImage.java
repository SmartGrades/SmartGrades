package kz.tech.smartgrades.root.firebase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FireBaseImage {
    public StorageReference uploadImage(String folder) {
        String urlName = String.valueOf(System.currentTimeMillis()) ;//+ ".jpg"
        return FirebaseStorage.getInstance().getReference(folder).child(urlName);
    }
}
