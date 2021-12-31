package kz.tech.smartgrades.root.tools;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;



public class ImageViewToByteArray {
    public static byte[] getData(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        // civAvatar.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        //          View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //  civAvatar.layout(0, 0, civAvatar.getMeasuredWidth(), civAvatar.getMeasuredHeight());
        // civAvatar.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();

    }
}
