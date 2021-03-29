package kz.tech.smartgrades.root.ui.CustomLayoutParams;

import android.content.Context;

import androidx.appcompat.widget.Toolbar;

import kz.tech.smartgrades.root.var_resources.DimensionDP;

public class ToolbarParams {
    public Toolbar.LayoutParams getParams(Context context, String[] paramsList) {
        int tMatchParent = Toolbar.LayoutParams.MATCH_PARENT;
        int tWrapContent = Toolbar.LayoutParams.WRAP_CONTENT;
        int layout_width = 0;
        int layout_height = 0;

        Toolbar.LayoutParams params = null;
        for (int i = 0; paramsList.length > i; i++) {
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "layW":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_width = tMatchParent; break;
                        case "wCnt": layout_width = tWrapContent; break;
                        case "0": layout_width = 0; break;
                    }
                    break;
                case "layH":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_height = tMatchParent; break;
                        case "wCnt": layout_height = tWrapContent; break;
                        case "0": layout_height = 0; break;
                    }
                    break;
            }
        }
        params = new Toolbar.LayoutParams(layout_width, layout_height);
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
                case "wei":
                    //   params.weight = Integer.parseInt(data[1]);
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
