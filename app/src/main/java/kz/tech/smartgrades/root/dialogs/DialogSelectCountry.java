package kz.tech.smartgrades.root.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
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
import kz.tech.smartgrades.root.models.ModelCountry;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetCityList;
import static kz.tech.smartgrades._Web.func_GetCountryList;
import static kz.tech.smartgrades._Web.func_GetRegionList;


public class DialogSelectCountry extends Dialog implements View.OnClickListener, DefaultAdapter.OnItemClickListener {

    private AppCompatActivity compatActivity;
    private ImageView ivBack;
    private DefaultAdapter adapter;
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private int CURRENT_INDEX = 0;
    private ModelCountry Country;

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
        onLoadContryList(func_GetCountryList, null);
        show();
    }

    private void initViews(View view) {
        adapter = new DefaultAdapter(compatActivity);
        recyclerView = view.findViewById(R.id.recyclerView);

        EditText etFilter = view.findViewById(R.id.etFilter);
        etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(compatActivity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void onLoadContryList(String funcName, String Id) {
        if (stringIsNullOrEmpty(funcName)) return;
        JsonObject json = new JsonObject();
        json.addProperty("Id", Id);

        String SOAP;
        if (stringIsNullOrEmpty(Id)) SOAP = SoapRequest(funcName, null);
        else SOAP = SoapRequest(funcName, json.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLReader(response.body().string());
                    if (!result.equals("null")){
                        compatActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Type founderListType = new TypeToken<ArrayList<ModelCountry>>() {
                                }.getType();
                                ArrayList<ModelCountry> mCountries = new Gson().fromJson(result, founderListType);
                                adapter.setData(mCountries);
                            }
                        });
                    }
                }
            }
        });
    }

    private void onBack() {
        dismiss();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
        }
    }
    @Override
    public void onItemClick(ModelCountry Country) {
        this.Country = Country;
        CURRENT_INDEX++;
        if (Country.getType() == 0) onLoadContryList(func_GetRegionList, Country.getId());
        else if (Country.getType() == 1) onLoadContryList(func_GetCityList, Country.getId());
        else if (Country.getType() == 2){
            if (onItemClickListener == null) return;
            onItemClickListener.onClick(Country);
            dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (CURRENT_INDEX <= 0) dismiss();
        else adapter.selectOldData();
        CURRENT_INDEX--;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();
        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                final InputMethodManager im = (InputMethodManager) compatActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                View focus = getCurrentFocus();
                if (focus != null) {
                    im.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                }
            }

        }
        return super.dispatchTouchEvent(ev);
    }
}