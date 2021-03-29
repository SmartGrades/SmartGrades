package kz.tech.smartgrades.root.tools;

import android.graphics.Bitmap;
import android.view.View;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetAvatarByte {
    public static byte[] getAvatar(CircleImageView civAvatar) {
        civAvatar.setDrawingCacheEnabled(true);
       // civAvatar.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
      //          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
      //  civAvatar.layout(0, 0, civAvatar.getMeasuredWidth(), civAvatar.getMeasuredHeight());
       // civAvatar.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(civAvatar.getDrawingCache());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();

    }
}
