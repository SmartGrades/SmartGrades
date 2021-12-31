package kz.tech.smartgrades.school.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_SchoolEnableComplaint;

public class SchoolPageTab1Fragment extends Fragment implements View.OnClickListener {

    private SchoolActivity activity;
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private Button btnEnableOffers;
    private String SCHOOL_ID;

    public static SchoolPageTab1Fragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        SchoolPageTab1Fragment fragment = new SchoolPageTab1Fragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mPage = getArguments().getInt(ARG_PAGE);
        }
        activity = (SchoolActivity) getActivity();
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_school_page_tab1, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }
    private void initViews(View view) {
        btnEnableOffers = view.findViewById(R.id.btnEnableOffers);
        btnEnableOffers.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnEnableOffers:
                onEnableOffersClick();
                break;
        }
    }
    private void onEnableOffersClick() {
        //activity.alert.onToast("Данная функция временно не доступна.");
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.Id, SCHOOL_ID);

        String SOAP = SoapRequest(func_SchoolEnableComplaint, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
                System.out.println(e.toString());
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            if(answer.isSuccess()) activity.presenter.onStartPresenter();
                            activity.alert.onToast(answer.getMessage());
                        }
                    });
                }
            }
        });
    }
}