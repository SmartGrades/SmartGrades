package kz.tech.smartgrades.sponsor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.adapters.SponsorChildListAdapter;
import kz.tech.smartgrades.sponsor.models.ModelChildrenListForSponsorship;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetChildrenListForSponsored;

public class SponsorChildListFragment extends Fragment implements View.OnClickListener,
        SponsorChildListAdapter.OnItemCLickListener{

    private SponsorActivity activity;
    private String SPONSOR_ID;
    private ArrayList<ModelChildrenListForSponsorship> childrenList;

    private ImageView ivNav;
    private CardView cvSearch;
    private TextView tvRandomSearch;
    private RecyclerView rvChildList;

    private SponsorChildListAdapter sponsorChildListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        activity = (SponsorActivity) getActivity();
        SPONSOR_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_sponsor_find_child, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        loadChildList();
    }
    private void initViews(View view){
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);
        cvSearch = view.findViewById(R.id.cvSearch);
        cvSearch.setOnClickListener(this);
        tvRandomSearch = view.findViewById(R.id.tvRandomSearch);
        tvRandomSearch.setOnClickListener(this);
        rvChildList = view.findViewById(R.id.rvChildList);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.ivNav:
                onBack();
                break;
            case R.id.cvSearch:
                break;
            case R.id.tvRandomSearch:
                onRandomClick();
                break;
        }
    }
    private void onRandomClick(){
        if(sponsorChildListAdapter != null) sponsorChildListAdapter.onRandom();
    }

    private void onBack(){
        activity.onBackPressed();
    }

    private void setChildList(){
        rvChildList.setLayoutManager(new LinearLayoutManager(activity));
        sponsorChildListAdapter = new SponsorChildListAdapter(activity);
        sponsorChildListAdapter.setOnItemCLickListener(this);
        rvChildList.setAdapter(sponsorChildListAdapter);
        sponsorChildListAdapter.updateData(childrenList);
    }

    private void loadChildList(){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, SPONSOR_ID);

        String SOAP = SoapRequest(func_GetChildrenListForSponsored, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLReader(response.body().string());
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(!result.equals("null")){
                                ArrayList<ModelChildrenListForSponsorship> dataList = new Gson().fromJson(result,
                                        new TypeToken<ArrayList<ModelChildrenListForSponsorship>>(){
                                        }.getType());
                                childrenList = dataList;
                                setChildList();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(ModelChildrenListForSponsorship mChild){
        activity.onNextFragment();
        activity.setChildModel(mChild);
    }
}
