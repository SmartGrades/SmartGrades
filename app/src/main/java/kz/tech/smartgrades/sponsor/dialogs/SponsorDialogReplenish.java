package kz.tech.smartgrades.sponsor.dialogs;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.models.ModelPaymentResponse;
import kz.tech.smartgrades.sponsor.models.ModelPrivateAccount;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.cloudpayments.sdk.cp_card.CPCard;
import ru.cloudpayments.sdk.three_ds.ThreeDsDialogFragment;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_Replenish;

public class SponsorDialogReplenish extends Dialog implements View.OnClickListener {

    private SponsorActivity activity;
    private String SPONSOR_ID;

//scscscsc
    private ImageView ivBack;
    private TextView tvCurentSum;
    private RadioGroup radioGroup;
    private RadioButton[] radioButtons = new RadioButton[4];
    private int SelectRadioButton;
    private TextView tvAddNewCard;
    private EditText etEnterSum;
    private TextView tvReplenish;

    private CardView cvAddCard;
    private boolean[] isReplenishBtnEnable = new boolean[5];
    private EditText etEnterCardNumber, etEnterDate1, etEnterDate2, etEnterFullName, etCode;
    private CheckBox cbSaveCard;

    private ModelPrivateAccount mPrivateAccount;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public SponsorDialogReplenish(SponsorActivity activity, ModelPrivateAccount privateAccount) {
        super(activity, R.style.CustomDialog2);
        this.activity = activity;
        this.mPrivateAccount = privateAccount;

        setCanceledOnTouchOutside(true);
        View view = getLayoutInflater().inflate(R.layout.dialog_sponsor_replenish, null, false);
        this.setContentView(view);

        initViews(view);
    }

    private void SetUserCardsListData(){
        if (!listIsNullOrEmpty(mPrivateAccount.getCards())){
            for(int i = 0; i < mPrivateAccount.getCards().size(); i++){
                String cardNumber = mPrivateAccount.getCards().get(i).getCardNumber();
                cardNumber = cardNumber.substring(cardNumber.length() - 4);
                radioButtons[i].setText("Через карту № ..." + cardNumber);
                radioButtons[i].setVisibility(View.VISIBLE);
            }
            radioButtons[3].setVisibility(View.VISIBLE);
        }
        else {
            cvAddCard.setVisibility(View.VISIBLE);
            SelectRadioButton = 3;
        }
    }

    private void initViews(View view){
        SPONSOR_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvCurentSum = view.findViewById(R.id.tvCurentSum);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtons[0] = view.findViewById(R.id.radioButton1);
        radioButtons[1] = view.findViewById(R.id.radioButton2);
        radioButtons[2] = view.findViewById(R.id.radioButton3);
        radioButtons[3] = view.findViewById(R.id.radioButton4);
        tvAddNewCard = view.findViewById(R.id.tvAddNewCard);
        tvAddNewCard.setOnClickListener(this);
        etEnterSum = view.findViewById(R.id.etEnterSum);
        tvReplenish = view.findViewById(R.id.tvReplenish);
        tvReplenish.setOnClickListener(this);

        cvAddCard = view.findViewById(R.id.cvAddCard);
        etEnterCardNumber = view.findViewById(R.id.etEnterCardNumber);
        etEnterFullName = view.findViewById(R.id.etEnterFullName);
        etEnterDate1 = view.findViewById(R.id.etEnterDate1);
        etEnterDate2 = view.findViewById(R.id.etEnterDate2);
        etCode = view.findViewById(R.id.etCode);
        cbSaveCard = view.findViewById(R.id.cbSaveCard);

        tvCurentSum.setText("Текущая сумма: " + mPrivateAccount.getCurrentSum() + " " + mPrivateAccount.getAccountType());

        SetUserCardsListData();
        onInitAddNewCard();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                etEnterSum.setBackgroundResource(R.drawable.background_square_blue_sea);
                etEnterSum.setEnabled(true);
                tvReplenish.setBackgroundResource(R.drawable.btn_background_purple);
                tvReplenish.setEnabled(true);
                switch(checkedId)
                {
                    case R.id.radioButton1: cvAddCard.setVisibility(View.GONE); SelectRadioButton = 0; break;
                    case R.id.radioButton2: cvAddCard.setVisibility(View.GONE); SelectRadioButton = 1; break;
                    case R.id.radioButton3: cvAddCard.setVisibility(View.GONE); SelectRadioButton = 2; break;
                    case R.id.radioButton4: cvAddCard.setVisibility(View.VISIBLE); isReplenishBtnEnable(); SelectRadioButton = 3; break;
                }
            }
        });
    }

    private void onBack() {
        dismiss();
    }

    private void isReplenishBtnEnable(){
        if (isReplenishBtnEnable[0] && isReplenishBtnEnable[1] && isReplenishBtnEnable[2] && isReplenishBtnEnable[3] && isReplenishBtnEnable[4]){
            tvReplenish.setEnabled(true);
            tvReplenish.setBackgroundResource(R.drawable.btn_background_purple);
            etEnterSum.setEnabled(true);
            etEnterSum.setBackgroundResource(R.drawable.background_square_blue_sea);
        }
        else{
            tvReplenish.setEnabled(false);
            tvReplenish.setBackgroundResource(R.drawable.btn_background_purple_alpha);
            etEnterSum.setEnabled(false);
            etEnterSum.setBackgroundResource(R.drawable.background_square_gray);
        }
    }
    private void onInitAddNewCard() {
        etEnterCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 16) {
                    isReplenishBtnEnable[0] = true;
                    etEnterFullName.requestFocus();
                }
                else isReplenishBtnEnable[0] = false;
                isReplenishBtnEnable();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        etEnterFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 5) isReplenishBtnEnable[3] = true;
                else isReplenishBtnEnable[3] = false;
                isReplenishBtnEnable();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        etEnterDate1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 2){
                    if (Convert.Str2Int(s.toString()) > 12) {
                        etEnterDate1.setText("12");
                        etEnterDate1.setSelection(etEnterDate1.getText().length());
                    }
                    isReplenishBtnEnable[1] = true;
                    etEnterDate2.requestFocus();
                }
                else isReplenishBtnEnable[1] = false;
                isReplenishBtnEnable();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        etEnterDate2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 2){
                    if (Convert.Str2Int(s.toString()) < 21) {
                        etEnterDate2.setText("");
                        etEnterDate2.setSelection(etEnterDate2.getText().length());
                    }
                    isReplenishBtnEnable[2] = true;
                    etCode.requestFocus();
                }
                else isReplenishBtnEnable[2] = false;
                isReplenishBtnEnable();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 3) isReplenishBtnEnable[4] = true;
                else isReplenishBtnEnable[4] = false;
                isReplenishBtnEnable();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private void onReplenish() {
        String sum = etEnterSum.getText().toString().trim();
        if (Convert.Str2Int(sum) <= 0){
            activity.alert.onToast("Сумма указана не верно!");
            return;
        }
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, SPONSOR_ID);
        jsonData.addProperty(F.AccountId, mPrivateAccount.getAccountId());
        jsonData.addProperty(F.Sum, sum);
        jsonData.addProperty("SaveCard", cbSaveCard.isChecked());
        CPCard card;
        if (SelectRadioButton == 3){
            String cardNumber = etEnterCardNumber.getText().toString().trim();
            String FullName = etEnterFullName.getText().toString().toUpperCase();
            String dateMM = etEnterDate1.getText().toString().trim();
            String dateYY = etEnterDate2.getText().toString().trim();
            String Date = dateMM + dateYY;
            String CVC = etCode.getText().toString();

            if (stringIsNullOrEmpty(cardNumber) || !CPCard.isValidNumber(cardNumber)) {
                activity.alert.onToast("Номер карты указан не верно!");
                return;
            }
            if (stringIsNullOrEmpty(Date) || !CPCard.isValidExpDate(Date)) {
                activity.alert.onToast("Срок действия карты указан неверно!");
                return;
            }

            card = new CPCard(cardNumber, Date, CVC);
            String cardCryptogram = null;
            try {
                cardCryptogram = card.cardCryptogram("pk_6641d376b1bce563542d5269b00a3");
            } catch (Exception e) {
                activity.alert.onToast("Ошибка ввода данных, попробуйте еще раз.");
                return;
            }

            jsonData.addProperty(F.CardNumber, cardNumber.substring(cardNumber.length() - 4));
            jsonData.addProperty(F.FullName, FullName);
            jsonData.addProperty(F.Date, Date);
            jsonData.addProperty(F.Cryptogram, cardCryptogram);
        }
        else jsonData.addProperty(F.Index, mPrivateAccount.getCards().get(SelectRadioButton).getIndex());

        String SOAP = SoapRequest(func_Replenish, jsonData.toString());
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
                                ModelPaymentResponse m = new Gson().fromJson(result, ModelPaymentResponse.class);
                                if (m!=null){
                                    if (m.isSuccess() && stringIsNullOrEmpty(m.getModel().getAcsUrl()))
                                        activity.alert.onToast(m.getModel().getCardHolderMessage());
                                    else if (!m.isSuccess() && !stringIsNullOrEmpty(m.getModel().getAcsUrl()))
                                        ThreeDsDialogFragment.newInstance(m.getModel().getAcsUrl(),m.getModel().getTransactionId(),m.getModel().getPaReq()).show(activity.getSupportFragmentManager(), "3DS");
                                }
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
                onReplenish();
                break;
            case R.id.tvAddNewCard:
                onInitAddNewCard();
                break;
        }
    }
}