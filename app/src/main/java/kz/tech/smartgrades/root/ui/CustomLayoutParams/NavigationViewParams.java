package kz.tech.smartgrades.root.ui.CustomLayoutParams;

import android.content.Context;

import com.google.android.material.navigation.NavigationView;

public class NavigationViewParams {
    public NavigationView.LayoutParams getParams(Context context, String[] paramsList) {
        int nvMatchParent = NavigationView.LayoutParams.MATCH_PARENT;
        int nvWrapContent = NavigationView.LayoutParams.WRAP_CONTENT;
        int layout_width = 0;
        int layout_height = 0;
        NavigationView.LayoutParams params = null;
        for (int i = 0; paramsList.length > i; i++) {
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "layW":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_width = nvMatchParent; break;
                        case "wCnt": layout_width = nvWrapContent; break;
                        case "0": layout_width = 0; break;
                    }
                    break;
                case "layH":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_height = nvMatchParent; break;
                        case "wCnt": layout_height = nvWrapContent; break;
                        case "0": layout_height = 0; break;
                    }
                    break;
            }
        }
        params = new NavigationView.LayoutParams(layout_width,layout_height);



        for (int j = 0; paramsList.length > j; j++) {
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "parW":


                    break;
                case "parH":


                    break;
                case "wei":

                    break;
                case "mrg":

                    break;
            }
        }

        return params;
    }
}
