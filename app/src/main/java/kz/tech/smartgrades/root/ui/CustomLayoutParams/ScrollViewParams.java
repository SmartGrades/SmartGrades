package kz.tech.smartgrades.root.ui.CustomLayoutParams;

import android.content.Context;
import android.widget.ScrollView;

import kz.tech.smartgrades.root.var_resources.DimensionDP;


public class ScrollViewParams {
    public ScrollView.LayoutParams getParams(Context context, String[] paramsList) {
        int svMatchParent = ScrollView.LayoutParams.MATCH_PARENT;
        int svWrapContent= ScrollView.LayoutParams.WRAP_CONTENT;
        int layout_width = 0;
        int layout_height = 0;
        ScrollView.LayoutParams params = null;
        for (int i = 0; paramsList.length > i; i++) {
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "layW":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_width = svMatchParent; break;
                        case "wCnt": layout_width = svWrapContent; break;
                        case "0": layout_width = 0; break;
                    }
                    break;
                case "layH":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_height = svMatchParent; break;
                        case "wCnt": layout_height = svWrapContent; break;
                        case "0": layout_height = 0; break;
                    }
                    break;
            }
        }
        params = new ScrollView.LayoutParams(layout_width,layout_height);

        for (int j = 0; paramsList.length > j; j++) {
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "mrg":
                    String[] strings = data[1].split(",");
                    int left = DimensionDP.sizeDP(Integer.parseInt(strings[0]), context);
                    int top = DimensionDP.sizeDP(Integer.parseInt(strings[1]), context);
                    int right = DimensionDP.sizeDP(Integer.parseInt(strings[2]), context);
                    int bottom = DimensionDP.sizeDP(Integer.parseInt(strings[3]), context);
                    params.setMargins(left, top, right, bottom);
                    break;
                case "parW":


                    break;
                case "parH":


                    break;
                case "wei":

                    break;

            }
        }
        return params;
    }
}
