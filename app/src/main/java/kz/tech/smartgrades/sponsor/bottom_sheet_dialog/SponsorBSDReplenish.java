package kz.tech.smartgrades.sponsor.bottom_sheet_dialog;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import kz.tech.smartgrades.sponsor.models.ModelDiscontCard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.cloudpayments.sdk.three_ds.ThreeDsDialogFragment;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_Replenish;

public class SponsorBSDReplenish extends BottomSheetDialog implements View.OnClickListener{

    private SponsorActivity activity;
    private String SPONSOR_ID;
    private ModelDiscontCard card;

    private TextView tvLabel;
    private TextView tvWrong;
    private TextView tvCancel;
    private TextView tvReplenish;
    private CardView cvReplenish;
    private EditText etSum;

    public SponsorBSDReplenish(@NonNull SponsorActivity activity, ModelDiscontCard card) {
        super(activity, R.style.BottomSheetStyle);
        this.activity = activity;
        SPONSOR_ID = activity.login.loadUserDate(LoginKey.ID);
        this.card = card;
        View view = getLayoutInflater().inflate(R.layout.bsd_money_extract, null, false);
        setContentView(view);
        initViews(view);
    }

    private void initViews(View view) {
        cvReplenish = view.findViewById(R.id.cvReplenish);
        tvReplenish = view.findViewById(R.id.tvReplenish);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvWrong = view.findViewById(R.id.tvWrong);
        tvLabel = view.findViewById(R.id.tvLabel);
        tvWrong.setVisibility(View.GONE);
        tvLabel.setText(R.string.Specify_the_amount_of_replenishment);
        tvReplenish.setText(R.string.TopUp);
        tvLabel = view.findViewById(R.id.tvLabel);
        tvCancel.setOnClickListener(this);
        cvReplenish.setOnClickListener(this);
        cvReplenish.setClickable(false);
        etSum = view.findViewById(R.id.etSum);
        etSum.requestFocus();
        etSum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (stringIsNullOrEmpty(editable.toString())) {
                    cvReplenish.setCardBackgroundColor(activity.getResources().getColor(R.color.gray_average_dark));
                    cvReplenish.setClickable(false);
                }
                else {
                    cvReplenish.setCardBackgroundColor(activity.getResources().getColor(R.color.blue_sea));
                    cvReplenish.setClickable(true);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                dismiss();
                break;
            case R.id.cvReplenish:
                onReplenish();
                break;
        }
    }

    private void onReplenish() {
        String sum = etSum.getText().toString().trim();
        if (Convert.Str2Int(sum) <= 0){
            activity.alert.onToast(activity.getString(R.string.The_amount_is_incorrect));
            return;
        }
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, SPONSOR_ID);
        jsonData.addProperty(F.AccountId, activity.getMSponsorData().getPrivateAccount().getAccountId());
        jsonData.addProperty(F.Id, card.getId());
        jsonData.addProperty(F.Sum, sum);

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
                                activity.alert.onToast(activity.getString(R.string.Error_please_try_again));
                            }
                            else {
                                ModelPaymentResponse m = new Gson().fromJson(result, ModelPaymentResponse.class);
                                if (m!=null){
                                    if (m.isSuccess() && stringIsNullOrEmpty(m.getModel().getAcsUrl()))
                                        activity.alert.onToast(m.getModel().getCardHolderMessage());
                                    else if (!m.isSuccess() && !stringIsNullOrEmpty(m.getModel().getAcsUrl()))
                                        ThreeDsDialogFragment.newInstance(m.getModel().getAcsUrl(),m.getModel().getTransactionId(),m.getModel().getPaReq()).show(activity.getSupportFragmentManager(), "3DS");
                                }
                                dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}
