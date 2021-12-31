package kz.tech.smartgrades.parent.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.ParentAddParentListAdapter;
import kz.tech.smartgrades.parent.model.ModelUser;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetParentsList;

public class ParentAddParentListFragment extends Fragment implements View.OnClickListener,
            ParentAddParentListAdapter.OnItemCLickListener{

    private ParentActivity activity;
    private String PARENT_ID;
    private ArrayList<ModelUser> parentList;

    private ImageView ivBack;
    private ImageView ivSearch;
    private ImageView ivCancel;
    private TextView tvCreateFamilyGroup;
    private EditText etSearch;
    private RecyclerView rvParentList;
    private CardView cvSearch;
    private TextView tvEmptyList;

    private ParentAddParentListAdapter parentListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_parent_search_parent, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        loadParentList();
    }
    private void initViews(View view){
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);
        ivCancel = view.findViewById(R.id.ivCancel);
        cvSearch = view.findViewById(R.id.cvSearch);
        tvCreateFamilyGroup = view.findViewById(R.id.tvCreateFamilyGroup);
        ivCancel.setOnClickListener(this);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                parentListAdapter.filter(editable.toString());
            }
        });
        rvParentList = view.findViewById(R.id.rvParentList);
        rvParentList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        parentListAdapter = new ParentAddParentListAdapter(activity);
        parentListAdapter.setOnItemCLickListener(this);
        rvParentList.setAdapter(parentListAdapter);
        tvEmptyList = view.findViewById(R.id.tvEmptyList);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.ivSearch:
                onSearch();
                break;
            case R.id.ivCancel:
                onCancel();
                break;
        }
    }

    private void onSearch() {
        tvCreateFamilyGroup.setVisibility(View.GONE);
        tvCreateFamilyGroup.setAlpha(1f);
        tvCreateFamilyGroup.animate().alpha(0f).setDuration(300);

        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = 0;
        cvSearch.setLayoutParams(params);
//        cvSearch.setAlpha(0f);

        cvSearch.animate().scaleX(1f).setDuration(300);
        ivSearch.setVisibility(View.GONE);
        ivSearch.setAlpha(1f);
        ivSearch.animate().alpha(0f).setDuration(300);
        etSearch.setVisibility(View.VISIBLE);
        ivSearch.setAlpha(0f);
        ivSearch.animate().alpha(1f).setDuration(300);
        etSearch.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        ivCancel.setVisibility(View.VISIBLE);
        ivCancel.setAlpha(0f);
        ivCancel.animate().alpha(1f).setDuration(300);
    }

    private void onCancel() {
        tvCreateFamilyGroup.setVisibility(View.VISIBLE);
        tvCreateFamilyGroup.setAlpha(0f);
        tvCreateFamilyGroup.animate().alpha(1f).setDuration(300);

        if(ivSearch.getVisibility() == View.VISIBLE) return;
        etSearch.setText(null);
        activity.alert.hideKeyboard(activity);
        ViewGroup.LayoutParams params = cvSearch.getLayoutParams();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        cvSearch.setLayoutParams(params);
//        cvSearch.setAlpha(0f);
        cvSearch.animate().alpha(0f).setDuration(300).alpha(1f).setDuration(300);
        ivSearch.setVisibility(View.VISIBLE);
        ivSearch.setAlpha(0f);
        ivSearch.animate().alpha(1f).setDuration(300);
        etSearch.setVisibility(View.GONE);
        etSearch.setAlpha(1f);
        etSearch.animate().alpha(0f).setDuration(300);
        ivCancel.setVisibility(View.GONE);
        ivCancel.setAlpha(1f);
        ivCancel.animate().alpha(0f).setDuration(300);
    }

    private void onBack() {
        activity.onBackPressed();
    }

    private void loadParentList() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.UserId, PARENT_ID);
        String SOAP = SoapRequest(func_GetParentsList, jsonObject.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(() -> {
                        if (result != null && !result.equals("null")) {
                            parentList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelUser>>() {
                            }.getType());
                            if (!listIsNullOrEmpty(parentList)) {
                                parentListAdapter.updateData(parentList);
                                tvEmptyList.setVisibility(View.GONE);
                            }
                            else tvEmptyList.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(ModelUser mParent) {
        activity.onNextFragment();
        activity.setParentModel(mParent);
    }
}
