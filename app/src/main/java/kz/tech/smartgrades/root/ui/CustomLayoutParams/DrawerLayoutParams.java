package kz.tech.smartgrades.root.ui.CustomLayoutParams;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;

import androidx.drawerlayout.widget.DrawerLayout;

public class DrawerLayoutParams {
    public DrawerLayout.LayoutParams getParams(Context context, String[] paramsList) {
        DrawerLayout.LayoutParams params = null;
        int dlMatchParent = DrawerLayout.LayoutParams.MATCH_PARENT;
        int dlWrapContent = DrawerLayout.LayoutParams.WRAP_CONTENT;
        int layout_width = 0;
        int layout_height = 0;
        for (int i = 0; paramsList.length > i; i++) {
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "layW":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_width = dlMatchParent; break;
                        case "wCnt": layout_width = dlWrapContent; break;
                        case "0": layout_width = 0; break;
                    }
                    break;
                case "layH":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_height = dlMatchParent; break;
                        case "wCnt": layout_height = dlWrapContent; break;
                        case "0": layout_height = 0; break;
                    }
                    break;
            }
        }
        params = new DrawerLayout.LayoutParams(layout_width, layout_height);
        params.gravity = Gravity.START;
        return params;
    }
}
