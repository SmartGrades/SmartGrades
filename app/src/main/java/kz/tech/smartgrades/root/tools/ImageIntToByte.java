package kz.tech.smartgrades.root.tools;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImageIntToByte {
    public static byte[] intToByteArray(int value) {
        if ((value >>> 24) > 0) {
            return new byte[]{
                    (byte) (value >>> 24),
                    (byte) (value >>> 16),
                    (byte) (value >>> 8),
                    (byte) value
            };
        } else if ((value >> 16) > 0) {
            return new byte[]{
                    (byte) (value >>> 16),
                    (byte) (value >>> 8),
                    (byte) value
            };
        } else if ((value >> 8) > 0) {
            return new byte[]{
                    (byte) (value >>> 8),
                    (byte) value
            };
        } else {
            return new byte[]{
                    (byte) value
            };
        }
    }
}
