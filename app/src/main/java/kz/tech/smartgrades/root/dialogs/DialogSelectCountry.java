package kz.tech.smartgrades.root.dialogs;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.root.adapters.DefaultAdapter;
import kz.tech.smartgrades.root.alert.IAlert;
import kz.tech.smartgrades.root.models.ModelCountry;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetCityList;
import static kz.tech.smartgrades._Web.func_GetCountryList;
import static kz.tech.smartgrades._Web.func_GetRegionList;

public class DialogSelectCountry extends Dialog implements View.OnClickListener, DefaultAdapter.OnItemClickListener {

    private final AppCompatActivity compatActivity;
    private DefaultAdapter adapter;
    private int CURRENT_ITEM = 0;
    private ModelCountry mCountry;
    private String CountryId;
    private ProgressBar progressBar;
    private TextView tvLabelListIsEmpty;
    private OnItemClickListener onItemClickListener;
    private EditText etFilter;

    public interface OnItemClickListener {
        void onClick(ModelCountry Country);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DialogSelectCountry(AppCompatActivity compatActivity) {
        super(compatActivity, R.style.DefaultCustomDialog);
        this.compatActivity = compatActivity;

        setCanceledOnTouchOutside(false);
        View view = getLayoutInflater().inflate(R.layout.ad_city_list, null, false);
        setContentView(view);
        initViews(view);
        onLoadData();
        show();
    }
    private void initViews(View view) {
        ImageView ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        tvLabelListIsEmpty = view.findViewById(R.id.tvLabelListIsEmpty);
        progressBar = view.findViewById(R.id.progressbar);

        etFilter = view.findViewById(R.id.etFilter);
        etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                adapter.setFilter(editable.toString());
            }
        });

        adapter = new DefaultAdapter(compatActivity);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(compatActivity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }
    private void onLoadData() {
        progressBar.setVisibility(View.VISIBLE);

        String SOAP;
        JsonObject json;
        if (CURRENT_ITEM == 0) SOAP = SoapRequest(func_GetCountryList, null);
        else if (CURRENT_ITEM == 1) {
            json = new JsonObject();
            json.addProperty("Id", CountryId);
            SOAP = SoapRequest(func_GetRegionList, json.toString());
        }
        else {
            json = new JsonObject();
            json.addProperty("Id", mCountry.getId());
            SOAP = SoapRequest(func_GetCityList, json.toString());
        }

        RequestBody body = RequestBody.create(SOAP, ContentType_XML);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    compatActivity.runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        if (!result.equals("null")) {
                            compatActivity.runOnUiThread(() -> {
                                Type founderListType = new TypeToken<ArrayList<ModelCountry>>() {
                                }.getType();
                                ArrayList<ModelCountry> mCountries = new Gson().fromJson(result, founderListType);
                                adapter.setData(mCountries);
                                tvLabelListIsEmpty.setVisibility(View.GONE);
                            });
                        }
                        else {
                            adapter.onClear();
                            tvLabelListIsEmpty.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onItemClick(ModelCountry Country) {
        etFilter.setText("");
        if (Country.getType() == 2) {//Выбран город
            if (onItemClickListener == null) return;
            onItemClickListener.onClick(Country);
            dismiss();
        }
        else {
            mCountry = Country;
            if (Country.getType() == 0) CountryId = mCountry.getId();
            CURRENT_ITEM++;
            onLoadData();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if (CURRENT_ITEM == 0) dismiss();
        else {
            CURRENT_ITEM--;
            onLoadData();
        }
    }
}