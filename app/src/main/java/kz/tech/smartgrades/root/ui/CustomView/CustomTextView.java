package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.animation.GetButtonRipple;
import kz.tech.smartgrades.root.ui.animation.GetSelectDrawable;
import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.CardViewParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class CustomTextView {
    public TextView onCustom(Context c, String[] param, String text) {
        TextView tv = new TextView(c);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tv.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Body1);
        } else {
            tv.setTextAppearance(c, androidx.appcompat.R.style.TextAppearance_AppCompat_Body1);
        }
        for (int i = 0; param.length > i; i++) {//  LayoutParams
            String[] data = param[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "LinLay": tv.setLayoutParams(new LinearLayoutParams().getParams(c, param)); break;
                        case "RelLay": tv.setLayoutParams(new RelativeLayoutParams().getParams(c, param)); break;
                        case "FrmLay": tv.setLayoutParams(new FrameLayoutParams().getParams(c, param)); break;
                        case "CardView": tv.setLayoutParams(new CardViewParams().getParams(c, param)); break;
                    }
                    break;
            }
        }
        for (int j = 0; param.length > j; j++) {//  TextView
            String[] data = param[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "grv"://  gravity
                    switch (data[1]) {//  VALUE
                        case "TCH"://  TOP_CENTER_HORIZONTAL
                            tv.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                            break;
                        case "LCV"://  LEFT_CENTER_VERTICAL
                            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                            break;
                        case "CHCV"://  CENTER_HORIZONTAL_CENTER_VERTICAL
                            tv.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                            break;
                        case "RCV"://  CENTER_HORIZONTAL_CENTER_VERTICAL
                            tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                            break;
                        case "BCH"://  CENTER_HORIZONTAL_CENTER_VERTICAL
                            tv.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                            break;
                        case "BCV"://  CENTER_HORIZONTAL_CENTER_VERTICAL
                            tv.setGravity(Gravity.BOTTOM | Gravity.CENTER_VERTICAL);
                            break;
                    }
                    break;
                case "txtC"://  textColor
                    tv.setTextColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "txtS"://  textSize
                    tv.setTextSize(Integer.parseInt(data[1]));
                    break;
                case "MaxL"://  Max Lines
                    tv.setMaxLines(Integer.parseInt(data[1]));
                    break;
                case "pad"://  gravity
                    String[] strings = data[1].split(",");
                    int left = DimensionDP.sizeDP(Integer.parseInt(strings[0]), c);
                    int top = DimensionDP.sizeDP(Integer.parseInt(strings[1]), c);
                    int right = DimensionDP.sizeDP(Integer.parseInt(strings[2]), c);
                    int bottom = DimensionDP.sizeDP(Integer.parseInt(strings[3]), c);
                    tv.setPadding(left, top, right, bottom);
                    break;
                case "TyFa"://  typeFace
                    switch (data[1]) {//  VALUE
                        case "BOLD"://  DEFAULT_BOLD
                            tv.setTypeface(Typeface.DEFAULT_BOLD);
                            break;
                    }
                    break;
                case "img"://  textImage
                    switch (data[1]) {//  VALUE
                        case "left"://  Image position LEFT
                           // textView.setCompoundDrawablesWithIntrinsicBounds(images, 0, 0, 0);
                            break;
                        case "top"://  Image position TOP
                          //  textView.setCompoundDrawablesWithIntrinsicBounds(0, images, 0, 0);
                            break;
                        case "right"://  Image position RIGHT
                          //  textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, images, 0);
                            break;
                        case "bottom"://  Image position BOTTOM
                          //  textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, images);
                            break;
                        case "add_photo"://
                            tv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.add_photo, 0, 0, 0);
                            break;
                        case "add_user"://
                            tv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.add_user, 0, 0, 0);
                            break;
                        case "print"://
                            tv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.red_print, 0, 0, 0);
                            break;
                        case "close"://
                            tv.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.red_close, 0, 0, 0);
                            break;
                        case "top_plus"://
                            tv.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.plus_oval, 0, 0);
                            break;
                    }
                    break;//
                case "SctDrw"://  BackgroundColor
                    String[] strBackD = data[1].split(",");
                    float radius = Integer.parseInt(strBackD[0]);
                    int stroke = Integer.parseInt(strBackD[1]);
                    int colorStroke = GetColorInText.getIntColor(strBackD[2]);
                    int colorBackground = GetColorInText.getIntColor(strBackD[3]);
                    tv.setBackgroundDrawable(new GetSelectDrawable()
                            .onChangeBackground(radius, stroke, colorStroke, colorBackground));
                    break;
                case "backC"://  BackgroundColor
                    tv.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "backR"://  RippleDrawable
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tv.setBackgroundDrawable(new GetButtonRipple()
                                .getRipple(30f, 2, ColorsRGB.GRAY_THREE,
                                        GetColorInText.getIntColor(data[1]),//  First Color
                                        ColorsRGB.BLUE_ONE));//  Second Color
                    } else {
                        tv.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    }
                    break;
                case "backRR"://  RippleDrawable
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tv.setBackgroundDrawable(new GetButtonRipple()
                                .getRipple(0f, 0, ColorsRGB.GRAY_THREE,
                                        GetColorInText.getIntColor(data[1]),//  First Color
                                        ColorsRGB.BLUE_ONE));//  Second Color
                    } else {
                        tv.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    }
                    break;
                case "VIS"://  Visibility
                    switch (data[1]) {//  VALUE
                        case "GONE"://  GONE
                            tv.setVisibility(View.GONE);
                            break;
                        case "VISIBLE"://  GONE
                            tv.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case "txtApr"://  Visibility
                    switch (data[1]) {//  VALUE
                        case "Body1"://  GONE
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                tv.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Body1);
                            } else {
                                tv.setTextAppearance(c, androidx.appcompat.R.style.TextAppearance_AppCompat_Body1);
                            }
                            break;
                        case "Body2"://  GONE
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                tv.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Body2);
                            } else {
                                tv.setTextAppearance(c, androidx.appcompat.R.style.TextAppearance_AppCompat_Body2);
                            }
                            break;
                        case "Caption"://  GONE
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                tv.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Caption);
                            } else {
                                tv.setTextAppearance(c, androidx.appcompat.R.style.TextAppearance_AppCompat_Caption);
                            }
                            break;
                    }
                    break;
                case "Lines"://  Visibility
                    tv.setLines(Integer.parseInt(data[1]));
                    break;
                case "ID"://  Circle Background Color
                    tv.setId((int)Integer.parseInt(data[1]));
                    break;
            }
        }

        //textView.setCompoundDrawablesWithIntrinsicBounds(images, 0, 0, 0);
        if (text != null) { if (!text.equals("")) { tv.setText(text); } }
        tv.setEms(10);
        return tv;
    }
}
