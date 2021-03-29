package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;

public class CustomCheckBox {
    public CheckBox onCustom(Context context, String[] paramsList, String text) {
        CheckBox checkBox = new CheckBox(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkBox.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Body1);
        } else {
            checkBox.setTextAppearance(context, androidx.appcompat.R.style.TextAppearance_AppCompat_Body1);
        }
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "FrmLay": checkBox.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                        case "LinLay": checkBox.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                    }
                    break;
            }
        }
        for (int j = 0; paramsList.length > j; j++) {//  TextView
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "pad"://  gravity
                    String[] strings = data[1].split(",");
                    int left = DimensionDP.sizeDP(Integer.parseInt(strings[0]), context);
                    int top = DimensionDP.sizeDP(Integer.parseInt(strings[1]), context);
                    int right = DimensionDP.sizeDP(Integer.parseInt(strings[2]), context);
                    int bottom = DimensionDP.sizeDP(Integer.parseInt(strings[3]), context);
                    checkBox.setPadding(left, top, right, bottom);
                    break;
                case "txtC":
                    checkBox.setTextColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "RTL":
                    switch (data[1]) {
                        case "RIGHT":
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            }
                            break;
                    }
                    break;
                case "grv"://  gravity
                    switch (data[1]) {//  VALUE
                        case "TCH"://  TOP_CENTER_HORIZONTAL
                            checkBox.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                            break;
                        case "LCV"://  LEFT_CENTER_VERTICAL
                            checkBox.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                            break;
                        case "CHCV"://  CENTER_HORIZONTAL_CENTER_VERTICAL
                            checkBox.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                            break;
                        case "RCV"://  CENTER_HORIZONTAL_CENTER_VERTICAL
                            checkBox.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                            break;
                        case "BCH"://  CENTER_HORIZONTAL_CENTER_VERTICAL
                            checkBox.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                            break;
                    }
                    break;
            }
        }
        if (text != null) { if (!text.equals("")) { checkBox.setText(text); } }

        return checkBox;
    }
}
