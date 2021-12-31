package kz.tech.smartgrades.child.fragments;

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
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.adapters.ChildTransactionsByMonthListAdapter;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.parent.model.ModelTransactionsMonth;
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

public class ChildAllTransactionFragment extends Fragment implements View.OnClickListener{

    private ChildActivity activity;
    private ArrayList<ModelTransactionsMonth> mTransactionsMonthList;

    private ImageView ivBack;
    private TextView tvMyReward;
    private RecyclerView rvIncomeHistoryByDate;

    private ChildTransactionsByMonthListAdapter childTransactionsByMonthListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (ChildActivity) getActivity();
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
    }
    private void initViews(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvMyReward = view.findViewById(R.id.tvMyReward);
        rvIncomeHistoryByDate = view.findViewById(R.id.rvIncomeHistoryByDate);
    }

    private void setTransactionList() {
        rvIncomeHistoryByDate.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true));

        childTransactionsByMonthListAdapter = new ChildTransactionsByMonthListAdapter(activity);
        //parentTransactionsByMonthListAdapter.setOnItemClickListener(this);
        rvIncomeHistoryByDate.setAdapter(childTransactionsByMonthListAdapter);
        childTransactionsByMonthListAdapter.updateData(mTransactionsMonthList);
        if (!listIsNullOrEmpty(mTransactionsMonthList)) rvIncomeHistoryByDate.scrollToPosition(mTransactionsMonthList.size() - 1);
    }

    private void loadTransactionData(String childId) {
        JsonObject json = new JsonObject();
        json.addProperty(F.UserId, childId);

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

    public void setChildModel(ModelChildData mChild, String childId) {
        loadTransactionData(childId);
        if (!stringIsNullOrEmpty(mChild.getPrivateAccount().getCurrentSum())) {
            if (Integer.parseInt(mChild.getPrivateAccount().getCurrentSum()) > 0)
                tvMyReward.setText("+" + mChild.getPrivateAccount().getCurrentSum());
            else tvMyReward.setText(mChild.getPrivateAccount().getCurrentSum());
        } else tvMyReward.setText("0");
    }
}
