package kz.tech.smartgrades.root.ui.widget_tools;

import android.os.Build;
import android.widget.EditText;

public class CursorEditText {
    public static void isCursorVisible(EditText editText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (!editText.isCursorVisible())
                editText.setCursorVisible(true);
        }
    }
}
