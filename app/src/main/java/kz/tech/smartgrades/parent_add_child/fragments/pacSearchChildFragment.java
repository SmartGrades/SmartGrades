package kz.tech.smartgrades.parent_add_child.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.mentor.adapters.ChildListForAddedAdapter;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;
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
import static kz.tech.smartgrades._Web.func_GetChildrenList;

public class pacSearchChildFragment extends Fragment implements View.OnClickListener, ChildListForAddedAdapter.OnItemClickListener {

    private ParentAddChildActivity activity;
    private ChildListForAddedAdapter childListForAddedAdapter;

    private EditText etSearch;
    private RecyclerView recyclerView;
    private String PARENT_ID;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentAddChildActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_search_child, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        loadData();
    }

    private void loadData() {
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, PARENT_ID);

        String SOAP = SoapRequest(func_GetChildrenList, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result.equals("0")) {}
                            else {
                                ArrayList<ModelUser> userList = new Gson().fromJson(result, new TypeToken<ArrayList<ModelUser>>(){}.getType());
                                childListForAddedAdapter.updateData(userList);
                                childListForAddedAdapter.selectList();
                            }
                        }
                    });

                }
            }
        });
    }

    private void initViews(View view) {
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
        etSearch = view.findViewById(R.id.etSearch);
        recyclerView = view.findViewById(R.id.rvGradesList);
        childListForAddedAdapter = new ChildListForAddedAdapter(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(childListForAddedAdapter);
        childListForAddedAdapter.setOnItemClickListener(this);
    }

    private void onAddChild() {
        activity.onNextFragment();
    }

    private void onSearchChild() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddChild:
                onAddChild();
                break;
            case R.id.btnSearchChild:
                onSearchChild();
                break;
        }
    }

    @Override
    public void onItemClick(ModelUser m) {
        activity.onNextFragment(m);
    }
    //
}
