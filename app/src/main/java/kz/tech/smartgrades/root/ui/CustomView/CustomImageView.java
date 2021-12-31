package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;


public class CustomImageView {
    public ImageView onCustom(Context context, String[] paramsList, int images) {
        ImageView imageView = new ImageView(context);
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "LinLay": imageView.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "RelLay": imageView.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "FrmLay": imageView.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
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
                    imageView.setPadding(left, top, right, bottom);
                    break;
                case "backC"://  BackgroundColor
                    switch (data[1]) {//  VALUE
                        case "WHITE"://  WHITE
                            imageView.setBackgroundColor(ColorsRGB.WHITE);
                            break;
                    }
                    break;
                case "ImgType"://  BackgroundColor
                    switch (data[1]) {//  VALUE
                        case "CENTER_CROP"://  WHITE
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            break;
                    }
                    break;
                case "HIDE"://  set Visibility
                    switch (data[1]) {//  VALUE
                        case "SHOW"://
                            imageView.setVisibility(View.VISIBLE);
                            break;
                        case "GONE"://
                            imageView.setVisibility(View.GONE);
                            break;
                    }
                    break;
                case "Alpha"://  set Visibility
                    switch (data[1]) {//  VALUE
                        case "4"://
                            imageView.setAlpha(0.4f);
                            break;
                        case "10"://
                            imageView.setAlpha(1.0f);
                            break;
                    }
                    break;
                case "MinHeight"://  BackgroundColor
                    imageView.setMinimumHeight(DimensionDP.sizeDP(Integer.parseInt(data[1]), context));
                    break;
                case "ID"://  Circle Background Color
                    imageView.setId((int)Integer.parseInt(data[1]));
                    break;

            }
        }
        if (images > 0) { imageView.setImageResource(images); }



        return imageView;
    }
}
