package kz.tech.smartgrades;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetUserAccess;
import static kz.tech.smartgrades._Web.func_SetUserAccess;

public class GET {
    private static String Code = "";
    private static boolean SecondAccessCode = false;
    private static String[] monthNames = { App.app().getString(R.string.January), App.app().getString(R.string.February), App.app().getString(R.string.March), App.app().getString(R.string.April), App.app().getString(R.string.May), App.app().getString(R.string.June), App.app().getString(R.string.July), App.app().getString(R.string.August), App.app().getString(R.string.September), App.app().getString(R.string.October), App.app().getString(R.string.November), App.app().getString(R.string.December) };

    public static String MonthName(int MonthNumber){
        return monthNames[MonthNumber - 1];
    }
    public static String CurrentMonth() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return MonthName(Integer.parseInt(dateFormat.format(date).trim()));
    }

    public static void onEditAccess(ParentActivity activity, String code, BottomSheetDialog dialog, ImageView[] ivDot, TextView tvTitle) {
        Code += code;
        ivDot[Code.length() - 1].setBackgroundResource(R.drawable.view_oval_green);
        if (Code.length() == 4) {
            if (SecondAccessCode){
                dialog.dismiss();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, Code);

                String SOAP = SoapRequest(func_SetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) { }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Code = "";
                                    SecondAccessCode = false;
                                    if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте еще раз.");
                                    else if (result.equals("1")) activity.alert.onToast("Код доступа изменен!");
                                }
                            });
                        }
                    }
                });
            }
            else{
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, Code);

                String SOAP = SoapRequest(func_GetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("0")) {
                                        Code = "";
                                        tvTitle.setText("Неверный код, попробуйте еще раз.");
                                        ivDot[0].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[1].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[2].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[3].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                    }
                                    else if (result.equals("1")) {
                                        SecondAccessCode = true;
                                        Code = "";
                                        tvTitle.setText("Введите новый код доступа.");
                                        ivDot[0].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[1].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[2].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[3].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }
    public static void onEditAccess(MentorActivity activity, String code, BottomSheetDialog dialog, ImageView[] ivDot, TextView tvTitle) {
        Code += code;
        ivDot[Code.length() - 1].setBackgroundResource(R.drawable.view_oval_green);
        if (Code.length() == 4) {
            if (SecondAccessCode){
                dialog.dismiss();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, Code);

                String SOAP = SoapRequest(func_SetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) { }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Code = "";
                                    SecondAccessCode = false;
                                    if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте еще раз.");
                                    else if (result.equals("1")) activity.alert.onToast("Код доступа изменен!");
                                }
                            });
                        }
                    }
                });
            }
            else{
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, Code);

                String SOAP = SoapRequest(func_GetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("0")) {
                                        Code = "";
                                        tvTitle.setText("Неверный код, попробуйте еще раз.");
                                        ivDot[0].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[1].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[2].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[3].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                    }
                                    else if (result.equals("1")) {
                                        SecondAccessCode = true;
                                        Code = "";
                                        tvTitle.setText("Введите новый код доступа.");
                                        ivDot[0].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[1].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[2].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[3].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }
    public static void onEditAccess(ChildActivity activity, String code, BottomSheetDialog dialog, ImageView[] ivDot, TextView tvTitle) {
        Code += code;
        ivDot[Code.length() - 1].setBackgroundResource(R.drawable.view_oval_green);
        if (Code.length() == 4) {
            if (SecondAccessCode){
                dialog.dismiss();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, Code);

                String SOAP = SoapRequest(func_SetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) { }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Code = "";
                                    SecondAccessCode = false;
                                    if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте еще раз.");
                                    else if (result.equals("1")) activity.alert.onToast("Код доступа изменен!");
                                }
                            });
                        }
                    }
                });
            }
            else{
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, Code);

                String SOAP = SoapRequest(func_GetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("0")) {
                                        Code = "";
                                        tvTitle.setText("Неверный код, попробуйте еще раз.");
                                        ivDot[0].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[1].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[2].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[3].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                    }
                                    else if (result.equals("1")) {
                                        SecondAccessCode = true;
                                        Code = "";
                                        tvTitle.setText("Введите новый код доступа.");
                                        ivDot[0].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[1].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[2].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[3].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }
    public static void onEditAccess(SponsorActivity activity, String code, BottomSheetDialog dialog, ImageView[] ivDot, TextView tvTitle) {
        Code += code;
        ivDot[Code.length() - 1].setBackgroundResource(R.drawable.view_oval_green);
        if (Code.length() == 4) {
            if (SecondAccessCode){
                dialog.dismiss();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, Code);

                String SOAP = SoapRequest(func_SetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) { }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Code = "";
                                    SecondAccessCode = false;
                                    if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте еще раз.");
                                    else if (result.equals("1")) activity.alert.onToast("Код доступа изменен!");
                                }
                            });
                        }
                    }
                });
            }
            else{
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, Code);

                String SOAP = SoapRequest(func_GetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("0")) {
                                        Code = "";
                                        tvTitle.setText("Неверный код, попробуйте еще раз.");
                                        ivDot[0].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[1].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[2].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[3].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                    }
                                    else if (result.equals("1")) {
                                        SecondAccessCode = true;
                                        Code = "";
                                        tvTitle.setText("Введите новый код доступа.");
                                        ivDot[0].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[1].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[2].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[3].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }
    public static void onEditAccess(SchoolActivity activity, String code, BottomSheetDialog dialog, ImageView[] ivDot, TextView tvTitle) {
        Code += code;
        ivDot[Code.length() - 1].setBackgroundResource(R.drawable.view_oval_green);
        if (Code.length() == 4) {
            if (SecondAccessCode){
                dialog.dismiss();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, Code);

                String SOAP = SoapRequest(func_SetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) { }
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Code = "";
                                    SecondAccessCode = false;
                                    if (result.equals("0")) activity.alert.onToast("Ошибка, попробуйте еще раз.");
                                    else if (result.equals("1")) activity.alert.onToast("Код доступа изменен!");
                                }
                            });
                        }
                    }
                });
            }
            else{
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, activity.login.loadUserDate(LoginKey.ID));
                jsonData.addProperty(F.Code, Code);

                String SOAP = SoapRequest(func_GetUserAccess, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("0")) {
                                        Code = "";
                                        tvTitle.setText("Неверный код, попробуйте еще раз.");
                                        ivDot[0].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[1].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[2].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[3].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                    }
                                    else if (result.equals("1")) {
                                        SecondAccessCode = true;
                                        Code = "";
                                        tvTitle.setText("Введите новый код доступа.");
                                        ivDot[0].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[1].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[2].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                        ivDot[3].setBackgroundResource(R.drawable.view_oval_rectangle_dots_active);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    public static boolean stringIsNullOrEmpty(String s){
        if (s == null) return true;
        else return s.replace("\n", "").replace(" ", "").isEmpty();
    }
    public static boolean listIsNullOrEmpty(ArrayList<?> list){
        if (list == null) return true;
        else return list.size() <= 0;
    }

    public static boolean isEmailOrSite(String string, boolean isEmail){
        if (isEmail && !string.contains("@")) return false;
        return string.contains(".kz") || string.contains(".ru")
                || string.contains(".com") || string.contains(".net");
    }
}
