package kz.tech.smartgrades.sponsor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.parent.model.ModelTransactionsMonth;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.adapters.SponsorTransactionsByMonthListAdapter;
import kz.tech.smartgrades.sponsor.models.ModelSponsorData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_GetTransactions;

public class SponsorTransactionFragment extends Fragment implements View.OnClickListener{

    private SponsorActivity activity;
    private ModelSponsorData mSponsor;
    private String SPONSOR_ID;
    private ArrayList<ModelTransactionsMonth> mTransactionsMonthList;

    private ImageView ivBack;
    private TextView tvMyReward;
    private TextView tvMyRewardLabel;
    private RecyclerView rvIncomeHistoryByDate;

    private SponsorTransactionsByMonthListAdapter sponsorTransactionsByMonthListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SponsorActivity) getActivity();
        mSponsor = activity.getMSponsorData();
        SPONSOR_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_child_income_history_by_date, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        loadTransactionData(SPONSOR_ID);
        if (!stringIsNullOrEmpty(mSponsor.getPrivateAccount().getCurrentSum())) {
            if (Integer.parseInt(mSponsor.getPrivateAccount().getCurrentSum()) > 0)
                tvMyReward.setText("+" + mSponsor.getPrivateAccount().getCurrentSum());
            else tvMyReward.setText(mSponsor.getPrivateAccount().getCurrentSum());
        } else tvMyReward.setText("0");
    }
    private void initViews(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvMyReward = view.findViewById(R.id.tvMyReward);
        tvMyRewardLabel = view.findViewById(R.id.tvMyRewardLabel);
        tvMyRewardLabel.setText(R.string.Your_balance);
        rvIncomeHistoryByDate = view.findViewById(R.id.rvIncomeHistoryByDate);
    }

    private void setTransactionList() {
        rvIncomeHistoryByDate.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));
        sponsorTransactionsByMonthListAdapter = new SponsorTransactionsByMonthListAdapter(activity);
        rvIncomeHistoryByDate.setAdapter(sponsorTransactionsByMonthListAdapter);
        sponsorTransactionsByMonthListAdapter.updateData(mTransactionsMonthList);
        if (!listIsNullOrEmpty(mTransactionsMonthList)) rvIncomeHistoryByDate.scrollToPosition(mTransactionsMonthList.size() - 1);
    }

    private void loadTransactionData(String id) {
        JsonObject json = new JsonObject();
        json.addProperty(F.UserId, id);

        String SOAP = SoapRequest(func_GetTransactions, json.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {}

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<ModelTransactionsMonth> modelTransactionsMonth = new Gson().fromJson(result, new TypeToken<ArrayList<ModelTransactionsMonth>>(){
                            }.getType());
                            if (!listIsNullOrEmpty(modelTransactionsMonth)) {
                                mTransactionsMonthList = modelTransactionsMonth;
                                setTransactionList();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.ivBack:
                onBack();
                break;
        }
    }

    private void onBack() {
        activity.onBackPressed();
    }
}
