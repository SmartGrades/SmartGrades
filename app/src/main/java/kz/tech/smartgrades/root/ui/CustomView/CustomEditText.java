package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;


import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.tools.GetInputFilterNumb;
import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.tools.EditTextInputFilterMinMax;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;

public class CustomEditText {
    public EditText onCustom(Context context, String[] paramsList, String hint) {
        EditText editText = new EditText(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            editText.setTextAppearance(androidx.appcompat.R.style.TextAppearance_AppCompat_Body1);
        } else {
            editText.setTextAppearance(context, androidx.appcompat.R.style.TextAppearance_AppCompat_Body1);
        }
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "RelLay": editText.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "FrmLay": editText.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                        case "LinLay": editText.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                    }
                    break;
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (editText.isCursorVisible())
            editText.setCursorVisible(false);
        }
        for (int j = 0; paramsList.length > j; j++) {//  TextView
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "Hint"://  BackgroundColor
                    switch (data[1]) {//  VALUE
                      //  case "0":  // editText.setHintTextColor(kz.tech.esparta.root.var_resources.ColorsRGB.GRAY_TWO); break;
                    }
                    break;
                case "pad"://  gravity
                    String[] strings = data[1].split(",");
                    int left = DimensionDP.sizeDP(Integer.parseInt(strings[0]), context);
                    int top = DimensionDP.sizeDP(Integer.parseInt(strings[1]), context);
                    int right = DimensionDP.sizeDP(Integer.parseInt(strings[2]), context);
                    int bottom = DimensionDP.sizeDP(Integer.parseInt(strings[3]), context);
                   // imageView.setPadding(left, top, right, bottom);
                    break;
                case "backC"://  BackgroundColor
                    switch (data[1]) {//  VALUE
                        case "0": editText.setBackgroundColor(0); break;
                       // case "": editText.setTextColor(kz.tech.esparta.root.tools.GetColorInText.getIntColor(data[1])); break;
                    }
                    break;
                case "grv"://  gravity
                    switch (data[1]) {//  VALUE
                        case "TCH"://  TOP_CENTER_HORIZONTAL
                            editText.setGravity(android.view.Gravity.TOP | android.view.Gravity.CENTER_HORIZONTAL);
                            break;
                        case "LCV"://  LEFT_CENTER_VERTICAL
                            editText.setGravity(android.view.Gravity.LEFT | android.view.Gravity.CENTER_VERTICAL);
                            break;
                        case "CHCV"://  CENTER_HORIZONTAL_CENTER_VERTICAL
                            editText.setGravity(android.view.Gravity.CENTER_HORIZONTAL | android.view.Gravity.CENTER_VERTICAL);
                            break;
                    }
                    break;
                case "InTy"://  InputType
                    switch (data[1]) {//  VALUE
                        case "NUMB"://  Only numbers
                            editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                            break;
                        case "FlagCap"://
                            editText.setInputType(android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                            break;
                        case "MAIL"://
                            editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                            break;
                    }
                    break;
                case "Filter"://  Filters
                    switch (data[1]) {//  VALUE
                        case "PIN":// 4
                            editText.setFilters(new android.text.InputFilter[] {
                                    GetInputFilterNumb.filter ,
                                    new android.text.InputFilter.LengthFilter((int)4)});
                            break;
                        case "Password":// 6-16
                            editText.setFilters(new android.text.InputFilter[] {
                              new android.text.InputFilter.LengthFilter((int)16)});
                            break;
                        case "Login":// 1-20
                              editText.setFilters(new android.text.InputFilter[] {
                              new android.text.InputFilter.LengthFilter((int)20)});
                            break;
                        case "OnlyLatin":// 1-20
                            editText.setFilters(new android.text.InputFilter[]{
                                    new android.text.InputFilter() {
                                        public CharSequence filter(CharSequence src, int start,
                                                                   int end, android.text.Spanned dst, int dstart, int dend) {
                                            if (src.equals("")) {
                                                return src;
                                            }
                                            if (src.toString().matches("[a-zA-Z ]+")) {
                                                return src;
                                            }
                                            return "";
                                        }
                                    }
                            });
                            break;
                    }
                    break;
                case "MaxL"://  Max Lines
                    editText.setMaxLines(1);
                    break;
                case "MinMax"://
                    editText.setFilters(new android.text.InputFilter[]{
                            new EditTextInputFilterMinMax(0, Integer.parseInt(data[1]))});
                    break;
                case "HIDE"://  set Visibility
                    switch (data[1]) {//  VALUE
                        case "SHOW"://
                            editText.setVisibility(View.VISIBLE);
                            break;
                        case "GONE"://
                            editText.setVisibility(View.GONE);
                            break;
                    }
                    break;
                case "txtC"://  textColor
                    editText.setTextColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "txtS"://  textSize
                    editText.setTextSize(Integer.parseInt(data[1]));
                    break;

            }
        }
        if (hint != null) { if (!hint.equals("")) { editText.setHint(hint); } }
        editText.setEms(10);
        return editText;
    }
}
