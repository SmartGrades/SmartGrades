package kz.tech.smartgrades.root.ui.CustomLayoutParams;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;

import kz.tech.smartgrades.root.var_resources.DimensionDP;

public class FrameLayoutParams {
    public FrameLayout.LayoutParams getParams(Context context, String[] paramsList) {
        int flMatchParent = FrameLayout.LayoutParams.MATCH_PARENT;
        int flWrapContent = FrameLayout.LayoutParams.WRAP_CONTENT;
        int layout_width = 0;
        int layout_height = 0;
        FrameLayout.LayoutParams params = null;
        for (int i = 0; paramsList.length > i; i++) {
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "layW":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_width = flMatchParent; break;
                        case "wCnt": layout_width = flWrapContent; break;
                        case "0": layout_width = 0; break;
                    }
                    break;
                case "layH":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_height = flMatchParent; break;
                        case "wCnt": layout_height = flWrapContent; break;
                        case "0": layout_height = 0; break;
                    }
                    break;
            }
        }
        params = new FrameLayout.LayoutParams(layout_width, layout_height);
        for (int j = 0; paramsList.length > j; j++) {
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "parW":
                    int width = Integer.parseInt(data[1]);
                    if (width > 0) { params.width = DimensionDP.sizeDP(width, context); }
                    break;
                case "parH":
                    int height = Integer.parseInt(data[1]);
                    if (height > 0) { params.height = DimensionDP.sizeDP(height, context); }
                    break;
                case "GRAV":
                    switch (data[1]) {//  VALUE
                        case "CORE": params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL; break;
                        case "VER": params.gravity = Gravity.CENTER_VERTICAL; break;
                        case "HOR": params.gravity = Gravity.CENTER_HORIZONTAL; break;
                        case "LEFT": params.gravity = Gravity.LEFT; break;
                        case "RIGHT": params.gravity = Gravity.RIGHT; break;
                        case "RIGHT_VER": params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL; break;
                        case "BOTTOM_LEFT": params.gravity = Gravity.BOTTOM | Gravity.LEFT; break;
                        case "BOTTOM_RIGHT": params.gravity = Gravity.BOTTOM | Gravity.RIGHT; break;
                        case "BOTTOM_HOR": params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL; break;
                        case "BOTTOM": params.gravity = Gravity.BOTTOM; break;
                        case "TOP_LEFT": params.gravity = Gravity.TOP | Gravity.LEFT; break;
                        case "TOP_RIGHT": params.gravity = Gravity.TOP | Gravity.RIGHT; break;
                        case "TOP": params.gravity = Gravity.TOP; break;
                        case "TOP_HOR": params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL; break;
                        case "LEFT_VER": params.gravity = Gravity.LEFT| Gravity.CENTER_VERTICAL; break;
                    }
                    break;
                case "mrg":
                    String[] strings = data[1].split(",");
                    int left = DimensionDP.sizeDP(Integer.parseInt(strings[0]), context);
                    int top = DimensionDP.sizeDP(Integer.parseInt(strings[1]), context);
                    int right = DimensionDP.sizeDP(Integer.parseInt(strings[2]), context);
                    int bottom = DimensionDP.sizeDP(Integer.parseInt(strings[3]), context);
                    params.setMargins(left, top, right, bottom);
                    break;
            }
        }
        return params;
    }
}
