package kz.tech.smartgrades.child.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelUserData;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.adapters.ChildParentsListAdapter;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetParentsList;

public class ChildAddParentListFragment extends Fragment implements View.OnClickListener, ChildParentsListAdapter.OnItemCLickListener {

    private ChildActivity activity;
    private String PARENT_ID;
    private ImageView ivBack;
    private ChildParentsListAdapter parentsListAdapter;
    private RecyclerView rvParentList;

    private FrameLayout flSearch;
    private ImageView ivSearch;
    private EditText etSearch;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ChildActivity) getActivity();
    }
    public static ChildAddParentListFragment newInstance() {
        ChildAddParentListFragment fragment = new ChildAddParentListFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_search_parent, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        onLoadParentsList();
    }

    private void initViews(View view) {
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        flSearch = view.findViewById(R.id.flSearch);
        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){

            }
            @Override
            public void afterTextChanged(Editable editable){

            }
        });
        ivSearch = view.findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);

        parentsListAdapter = new ChildParentsListAdapter(activity);
        parentsListAdapter.setOnItemClickListener(this);
        rvParentList = view.findViewById(R.id.rvParentList);
        rvParentList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvParentList.setAdapter(parentsListAdapter);

    }

    private void onLoadParentsList() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, PARENT_ID);

        String SOAP = SoapRequest(func_GetParentsList, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("null")) {
                            } else {
                                ArrayList<ModelUserData> modelChats = new Gson().fromJson(result, new TypeToken<ArrayList<ModelUserData>>() {
                                }.getType());
                                parentsListAdapter.updateData(modelChats);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackClick();
                break;
            case R.id.ivSearch:
                onSearch();
                break;
        }
    }

    private void onSearch() {
        ConstraintLayout.LayoutParams layoutParams;
        if(etSearch.getVisibility() == View.GONE){
            etSearch.setVisibility(View.VISIBLE);
            ivSearch.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_delete_grey));
            layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, Convert.DpToPx(activity, 50));
            layoutParams.leftMargin = Convert.DpToPx(activity, 60);
        }
        else {
            etSearch.setVisibility(View.GONE);
            ivSearch.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_search_gray));
            layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, Convert.DpToPx(activity, 50));
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        }
        flSearch.setLayoutParams(layoutParams);
    }

    private void onBackClick() {
        activity.onBackPressed();
    }

    @Override
    public void onItemClick(ModelUserData m) {
        activity.onSetParentInfo(m);
        activity.onNextFragment();
    }
}
