package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import kz.tech.smartgrades.parent.model.ModelParentInFamilyGroup;
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
import static kz.tech.smartgrades._Web.func_GetParentsListInFamilyGroup;

public class ParentAddParentListFragment extends Fragment implements View.OnClickListener,
            ParentAddParentListAdapter.OnItemCLickListener{

    private ParentActivity activity;
    private String PARENT_ID;
    private ArrayList<ModelParentInFamilyGroup> parentList;

    private ImageView ivNav;
    private CardView cvSearch;
    private RecyclerView rvParentList;

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
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        cvSearch = view.findViewById(R.id.cvSearch);
        cvSearch.setOnClickListener(this);
        rvParentList = view.findViewById(R.id.rvParentList);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.ivNav:
                onBack();
                break;
            case R.id.cvSearch:
                break;
        }
    }

    private void onBack() {
        activity.onBackPressed();
    }

    private void setParentList() {
        rvParentList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        parentListAdapter = new ParentAddParentListAdapter(activity);
        parentListAdapter.setOnItemCLickListener(this);
        rvParentList.setAdapter(parentListAdapter);
        parentListAdapter.updateData(parentList);
    }

    private void loadParentList() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(F.ParentId, PARENT_ID);
        String SOAP = SoapRequest(func_GetParentsListInFamilyGroup, jsonObject.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!result.equals("null")) {
                                ArrayList<ModelParentInFamilyGroup> dataList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelParentInFamilyGroup>>(){
                                }.getType());
                                parentList = dataList;
                                setParentList();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(ModelParentInFamilyGroup mParent) {
        activity.onNextFragment();
        activity.setParentModel(mParent);
    }
}
