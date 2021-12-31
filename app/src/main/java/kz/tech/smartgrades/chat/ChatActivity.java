package kz.tech.smartgrades.chat;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import kz.tech.smartgrades.App;
import kz.tech.esparta.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //Init DI Dagger 2
        //Init views
        initViews();
        //Init MVP
        initPresenter();
    }

    private void initViews() {

    }



    private void initPresenter() {

    }
}
