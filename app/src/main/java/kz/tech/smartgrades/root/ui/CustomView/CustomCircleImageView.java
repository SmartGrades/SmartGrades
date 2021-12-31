package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;
import android.os.Build;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.ui.animation.GetButtonRipple;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;
import kz.tech.smartgrades.root.var_resources.DimensionDP;


public class CustomCircleImageView {
    public CircleImageView onCustom(Context context, String[] paramsList) {
        CircleImageView circleImageView = new CircleImageView(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "LinLay": circleImageView.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "RelLay": circleImageView.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "FrmLay": circleImageView.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                    }
                    break;
            }
        }
        for (int j = 0; paramsList.length > j; j++) {//  TextView
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "BordC"://  Border Color
                   circleImageView.setBorderColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "BordW"://   Border Width
                    circleImageView.setBorderWidth(Integer.parseInt(data[1]));
                    break;
                case "BackC"://  Circle Background Color
                    circleImageView.setBorderColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "img"://  RippleDrawable
                    switch (data[1]) {
                        case "add_photo": circleImageView.setImageResource(R.mipmap.add_photo); break;
                        case "avatar": circleImageView.setImageResource(R.mipmap.avatar); break;
                        case "red_add_photo": circleImageView.setImageResource(R.mipmap.red_add_photo); break;
                    }
                 //   circleImageView.setBackgroundColor(kz.tech.esparta.root.tools.GetColorInText.getIntColor(data[1]));
                    break;
                case "pad"://  gravity
                    String[] strings = data[1].split(",");
                    int left = DimensionDP.sizeDP(Integer.parseInt(strings[0]), context);
                    int top = DimensionDP.sizeDP(Integer.parseInt(strings[1]), context);
                    int right = DimensionDP.sizeDP(Integer.parseInt(strings[2]), context);
                    int bottom = DimensionDP.sizeDP(Integer.parseInt(strings[3]), context);
                    circleImageView.setPadding(left, top, right, bottom);
                    break;
                case "backR"://  RippleDrawable
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleImageView.setBackgroundDrawable(new GetButtonRipple()
                                .getRipple(30f, 2, ColorsRGB.GRAY_THREE,
                                        GetColorInText.getIntColor(data[1]),//  First Color
                                        ColorsRGB.BLUE_ONE));//  Second Color
                    } else {
                        circleImageView.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    }
                    break;
                case "ID"://  Circle Background Color
                    circleImageView.setId((int)Integer.parseInt(data[1]));
                    break;

            }
        }


        return circleImageView;
    }
}
