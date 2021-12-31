package kz.tech.smartgrades.root.ui.CustomLayout;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.animation.GetSelectDrawable;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.CardViewParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RecyclerViewParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.ScrollViewParams;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class CustomLinearLayout {
    public LinearLayout onCustom(Context context, String[] paramsList) {
        LinearLayout linearLayout = new LinearLayout(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "FrmLay": linearLayout.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                        case "LinLay": linearLayout.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "ScrLay": linearLayout.setLayoutParams(new ScrollViewParams().getParams(context, paramsList)); break;
                        case "RelLay": linearLayout.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "CardView": linearLayout.setLayoutParams(new CardViewParams().getParams(context, paramsList)); break;
                        case "RecyclerView": linearLayout.setLayoutParams(new RecyclerViewParams().getParams(context, paramsList)); break;
                    }
                    break;
            }
        }
        for (int j = 0; paramsList.length > j; j++) {//  LinearLayout
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "orn"://  Orientation
                    switch (data[1]) {//  VALUE
                        case "ver"://  VERTICAL
                            linearLayout.setOrientation(LinearLayout.VERTICAL);
                            break;
                        case "hor"://  HORIZONTAL
                            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            break;
                    }
                    break;
                case "WeiSum"://  textColor
                    int weightSum = Integer.parseInt(data[1]);
                    linearLayout.setWeightSum(weightSum);
                    break;
                case "SctDrw"://  BackgroundColor
                    String[] strBackD = data[1].split(",");
                    float radius = Integer.parseInt(strBackD[0]);
                    int stroke = Integer.parseInt(strBackD[1]);
                    int colorStroke = GetColorInText.getIntColor(strBackD[2]);
                    int colorBackground = GetColorInText.getIntColor(strBackD[3]);
                    linearLayout.setBackgroundDrawable(new GetSelectDrawable()
                            .onChangeBackground(radius, stroke, colorStroke, colorBackground));
                    break;
                case "backC"://  BackgroundColor
                    switch (data[1]) {//  VALUE
                        case "WHITE"://  WHITE
                            linearLayout.setBackgroundColor(ColorsRGB.WHITE);
                            break;
                    }
                    break;
                case "img"://  textColor
                    switch (data[1]) {//  VALUE
                        case "left"://  Image position LEFT
                            // textView.setCompoundDrawablesWithIntrinsicBounds(images, 0, 0, 0);
                            break;
                        case "top"://  Image position TOP
                            // textView.setCompoundDrawablesWithIntrinsicBounds(0, images, 0, 0);
                            break;
                        case "right"://  Image position RIGHT
                            // textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, images, 0);
                            break;
                        case "bottom"://  Image position BOTTOM
                            //  textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, images);
                            break;
                    }
                    break;
                case "VIS"://  Visibility
                    switch (data[1]) {//  VALUE
                        case "GONE"://  GONE
                            linearLayout.setVisibility(View.GONE);
                            break;
                        case "VISIBLE"://  GONE
                            linearLayout.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case "GRAV":
                    switch (data[1]) {//  VALUE
                        case "CEN_VER"://  GONE
                            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                            break;
                    }
                    break;
                case "ID":
                    linearLayout.setId((int)Integer.parseInt(data[1]));
                    break;
            }
        }



        return linearLayout;
    }
}
