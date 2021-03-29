package kz.tech.smartgrades.root.ui.CustomLayoutParams;

import android.content.Context;
import android.widget.RelativeLayout;

import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.var_resources.VarID;

public class RelativeLayoutParams {
    public RelativeLayout.LayoutParams getParams(Context context, String[] paramsList) {
        int rlMatchParent = RelativeLayout.LayoutParams.MATCH_PARENT;
        int rlWrapContent = RelativeLayout.LayoutParams.WRAP_CONTENT;
        int layout_width = 0;
        int layout_height = 0;
        RelativeLayout.LayoutParams params = null;
        for (int i = 0; paramsList.length > i; i++) {
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "layW":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_width = rlMatchParent; break;
                        case "wCnt": layout_width = rlWrapContent; break;
                        case "0": layout_width = 0; break;
                    }
                    break;
                case "layH":
                    switch (data[1]) {//  VALUE
                        case "mPrt": layout_height = rlMatchParent; break;
                        case "wCnt": layout_height = rlWrapContent; break;
                        case "0": layout_height = 0; break;
                    }
                    break;
            }
        }

        params = new RelativeLayout.LayoutParams(layout_width, layout_height);
        for (int j = 0; paramsList.length > j; j++) {
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "Rule":
                    switch (data[1]) {
                        case "LEFT":
                            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                            break;
                        case "TOP":
                            params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                            break;
                        case "RIGHT":
                            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                            break;
                        case "BOTTOM":
                            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                            break;
                        case "CEN_HOR":
                            params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                            break;
                        case "CEN_VER":
                            params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                            break;
                        case "CEN_PAR":
                            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                            break;
                        case "BLOCK":
                            params.addRule(RelativeLayout.ABOVE, VarID.ID_IV_BLOCK);
                            break;
                        case "BELOW":

                            break;
                    }
                    break;
                case "ABOVE":
                    params.addRule(RelativeLayout.ABOVE, (int)Integer.parseInt(data[1]));
                    break;
                case "BELOW":
                    params.addRule(RelativeLayout.BELOW, (int)Integer.parseInt(data[1]));
                    break;
                case "RIGHT_OF":
                    params.addRule(RelativeLayout.RIGHT_OF, (int)Integer.parseInt(data[1]));
                    break;
                case "parW":
                    int width = Integer.parseInt(data[1]);
                    if (width > 0) { params.width = DimensionDP.sizeDP(width, context); }
                    break;
                case "parH":
                    int height = Integer.parseInt(data[1]);
                    if (height > 0) { params.height = DimensionDP.sizeDP(height, context); }
                    break;
                case "ID"://400001
                  //  params.gr
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
