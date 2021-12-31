package kz.tech.smartgrades.child.dialog;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.models.ModelPrivateAccount;
import kz.tech.smartgrades.sponsor.models.ModelDiscontCard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_AddDiscontCard;
import static kz.tech.smartgrades._Web.func_Extract;

public class ChildDialogPrivateAccount extends Dialog implements View.OnClickListener {

    private ChildActivity activity;
    private String PARENT_ID;

    private Dialog DIALOG;
//scscscsc
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvCurentSum;
    private RadioGroup radioGroup;
    private RadioButton[] radioButtons = new RadioButton[5];
    private int SelectRadioButton;
    private TextView tvAddNewCard;
    private EditText etEnterSum;
    private TextView tvReplenish;
    private TextView tvLabel;

    private ModelPrivateAccount mPrivateAccount;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public ChildDialogPrivateAccount(ChildActivity activity, ModelPrivateAccount privateAccount) {
        super(activity, R.style.CustomDialog2);
        this.activity = activity;
        this.mPrivateAccount = privateAccount;

        DIALOG = this;
        DIALOG.setCanceledOnTouchOutside(true);

        View view = getLayoutInflater().inflate(R.layout.dialog_sponsor_replenish, null, false);
        this.setContentView(view);

        initViews(view);
    }

    private void initViews(View view){
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvCurentSum = view.findViewById(R.id.tvCurentSum);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtons[0] = view.findViewById(R.id.radioButton1);
        radioButtons[1] = view.findViewById(R.id.radioButton2);
        radioButtons[2] = view.findViewById(R.id.radioButton3);
        radioButtons[3] = view.findViewById(R.id.radioButton4);
        radioButtons[4] = view.findViewById(R.id.radioButton5);
        tvAddNewCard = view.findViewById(R.id.tvAddNewCard);
        tvAddNewCard.setOnClickListener(this);
        etEnterSum = view.findViewById(R.id.etEnterSum);
        tvReplenish = view.findViewById(R.id.tvReplenish);
        tvReplenish.setOnClickListener(this);

        tvTitle.setText("Вывод денег");
        tvAddNewCard.setText("*добавить способ вывода денег");
        tvReplenish.setText("Вывести");

        if (mPrivateAccount != null){
            tvCurentSum.setText("Текущая сумма: " + mPrivateAccount.getCurrentSum() + " " + mPrivateAccount.getAccountType());
            if (mPrivateAccount.getCards() != null){
                tvLabel.setVisibility(View.GONE);
                for(int i = 0; i < mPrivateAccount.getCards().size(); i++){
                    String cardNumber = mPrivateAccount.getCards().get(i).getCardNumber();
                    cardNumber = cardNumber.substring(cardNumber.length() - 4);
                    radioButtons[i].setText("На карту № ..." + cardNumber);
                    radioButtons[i].setVisibility(View.VISIBLE);
                }
            }
        }
        else tvCurentSum.setText("Текущая сумма: 0 KZT");

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case R.id.radioButton1: SelectRadioButton = 0; break;
                    case R.id.radioButton2: SelectRadioButton = 1; break;
                    case R.id.radioButton3: SelectRadioButton = 2; break;
                    case R.id.radioButton4: SelectRadioButton = 3; break;
                    case R.id.radioButton5: SelectRadioButton = 4; break;
                }

                etEnterSum.setBackgroundResource(R.drawable.background_square_blue_sea);
                etEnterSum.setEnabled(true);
                tvReplenish.setBackgroundResource(R.drawable.btn_background_purple);
                tvReplenish.setEnabled(true);
            }
        });
    }

    private void onBack() {
        DIALOG.dismiss();
    }

    private void onAddNewCard() {
        BottomSheetDialog bsdAddNewCard = new BottomSheetDialog(activity);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_add_new_card, null, false);
        bsdAddNewCard.setContentView(view);
        View bottomSheet = bsdAddNewCard.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        bottomSheet.setLayoutParams(layoutParams);
        layoutParams.height = _System.getWindowHeight(activity);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bsdAddNewCard.show();

        ImageView ivBack = view.findViewById(R.id.ivBack);
        EditText etEnterCardNumber = view.findViewById(R.id.etEnterCardNumber);
        EditText etEnterDate1 = view.findViewById(R.id.etEnterDate1);
        EditText etEnterDate2 = view.findViewById(R.id.etEnterDate2);
        EditText etEnterFullName = view.findViewById(R.id.etEnterFullName);
        EditText etCode = view.findViewById(R.id.etCode);
        TextView tvAdd = view.findViewById(R.id.tvAdd);

        etEnterDate1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 2)
                    if (Convert.Str2Int(s.toString()) > 12) {
                        etEnterDate1.setText("12");
                        etEnterDate1.setSelection(etEnterDate1.getText().length());
                    }
            }
        });
        etEnterDate2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 2)
                    if (Convert.Str2Int(s.toString()) < 21) {
                        etEnterDate2.setText("");
                        etEnterDate2.setSelection(etEnterDate2.getText().length());
                    }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsdAddNewCard.dismiss();
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bsdAddNewCard.dismiss();
                JsonObject jsonData = new JsonObject();
                jsonData.addProperty(F.UserId, PARENT_ID);
                jsonData.addProperty(F.CardNumber, etEnterCardNumber.getText().toString());
                jsonData.addProperty(F.Date, etEnterDate1.getText().toString() + "|" + etEnterDate2.getText().toString());
                jsonData.addProperty(F.FullName, etEnterFullName.getText().toString());
                jsonData.addProperty(F.Code, etCode.getText().toString());

                String SOAP = SoapRequest(func_AddDiscontCard, jsonData.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.code() >= 200 && response.code() <= 300) {
                            String xml = response.body().string();
                            String result = _Web.XMLToJson(xml);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (result.equals("0")) {
                                        activity.alert.onToast("Ошибка, попробуйте еще раз.");
                                    }
                                    else {
                                        activity.alert.onToast("Операция успешно завершена.");
                                        Type founderListType = new TypeToken<ArrayList<ModelDiscontCard>>(){}.getType();
                                        ArrayList<ModelDiscontCard> mUserCard = new Gson().fromJson(result, founderListType);
                                        tvLabel.setVisibility(View.GONE);
                                        for(int i = 0; i < mUserCard.size(); i++){
                                            String cardNumber = mUserCard.get(i).getCardNumber();
                                            radioButtons[i].setText("Через карту № ..." + cardNumber.substring(cardNumber.length() - 4, cardNumber.length()));
                                            radioButtons[i].setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void onExtract() {
        DIALOG.dismiss();
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.SourceId, PARENT_ID);
        jsonData.addProperty(F.CardNumber, mPrivateAccount.getCards().get(SelectRadioButton).getCardNumber());
        jsonData.addProperty(F.AccountId, mPrivateAccount.getAccountId());
        jsonData.addProperty(F.Sum, etEnterSum.getText().toString());

        String SOAP = SoapRequest(func_Extract, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.code() >= 200 && response.code() <= 300) {
                    String xml = response.body().string();
                    String result = _Web.XMLToJson(xml);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("0")) {
                                activity.alert.onToast("Ошибка, попробуйте еще раз.");
                            }
                            else {
                                activity.alert.onToast("Операция успешно завершена.");
                                activity.presenter.onStartPresenter();
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.tvReplenish:
                onExtract();
                break;
            case R.id.tvAddNewCard:
                onAddNewCard();
                break;
        }
    }
}