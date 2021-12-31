package kz.tech.smartgrades.sponsor.fragments;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.adapters.SponsorExtractCardListAdapter;
import kz.tech.smartgrades.sponsor.bottom_sheet_dialog.SponsorBSDExtract;
import kz.tech.smartgrades.sponsor.bottom_sheet_dialog.SponsorBSDRenameCard;
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
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_RemoveDiscontCard;

public class SponsorCashExtractFragment extends Fragment implements View.OnClickListener, SponsorExtractCardListAdapter.OnItemCLickListener{
    public static final String ARG_PAGE = "ARG_PAGE";
    private String PARENT_ID;
    private int mPage;
    private SponsorExtractCardListAdapter adapter;
    private SponsorActivity activity;
    private RecyclerView rvBankCards;
    private TextView tvAddNewCard;
    private ArrayList<ModelDiscontCard> cards;

    public SponsorCashExtractFragment(SponsorActivity activity, int i){
        this.activity = activity;
        this.PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_parent_extract, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view){
        rvBankCards = view.findViewById(R.id.rvBankCards);
        tvAddNewCard = view.findViewById(R.id.tvAddNewCard);
        tvAddNewCard.setOnClickListener(this);

        adapter = new SponsorExtractCardListAdapter(activity);
        rvBankCards.setAdapter(adapter);
        adapter.setOnItemCLickListener(this);
        rvBankCards.setLayoutManager(new LinearLayoutManager(activity));
        adapter.updateData(cards);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAddNewCard:
                activity.addCard();
        }
    }

    @Override
    public void onExtractClick(ModelDiscontCard mCard) {
        SponsorBSDExtract dialog = new SponsorBSDExtract(activity, mCard);
        dialog.show();
    }

    @Override
    public void onOptionClick(ModelDiscontCard mCard, View itemView) {
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        View popUpView = getLayoutInflater().inflate(R.layout.pw_bank_card_options, null);
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) popupWindow.setElevation(20);
        popupWindow.showAsDropDown(itemView, 0, 0);
        TextView tvRenameCard = popUpView.findViewById(R.id.tvRenameCard);
        TextView tvRemoveCard = popUpView.findViewById(R.id.tvRemoveCard);
        tvRenameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SponsorBSDRenameCard dialog = new SponsorBSDRenameCard(activity, mCard);
                dialog.show();
                popupWindow.dismiss();
            }
        });
        tvRemoveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(F.UserId, PARENT_ID);
                jsonObject.addProperty(F.Id, mCard.getId());
                String SOAP = SoapRequest(func_RemoveDiscontCard, jsonObject.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.alert.onToast(answer.getMessage());
                                    if (answer.isSuccess()) {
                                        activity.updatePresenter();
                                    }
                                }
                            });
                        }
                    }
                });
                popupWindow.dismiss();
            }
        });
    }

    public void addCard(ModelDiscontCard m) {
        cards.add(m);
    }

    public void setCards(ArrayList<ModelDiscontCard> cards) {
        this.cards = cards;
        if (adapter != null) {
            adapter.updateData(cards);
        }
    }
}