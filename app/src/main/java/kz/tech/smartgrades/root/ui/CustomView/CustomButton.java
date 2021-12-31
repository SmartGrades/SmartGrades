package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;
import android.os.Build;
import android.widget.Button;


import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.animation.GetButtonRipple;
import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class CustomButton {
    public Button onCustom(Context context, String[] paramsList, String text) {
        Button button = new Button(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "LinLay": button.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
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
                    button.setPadding(left, top, right, bottom);
                    break;
                case "backR"://  RippleDrawable
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        button.setBackgroundDrawable(new GetButtonRipple()
                                .getRipple(30f, 2, ColorsRGB.GRAY_THREE,
                                        GetColorInText.getIntColor(data[1]),//  First Color
                                        ColorsRGB.BLUE_ONE));//  Second Color
                    } else {
                        button.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    }
                    break;
                case "txtC":
                    button.setTextColor(GetColorInText.getIntColor(data[1]));
                    break;
            }
        }
        if (text != null) { if (!text.equals("")) { button.setText(text); } }
        return button;
    }
}
