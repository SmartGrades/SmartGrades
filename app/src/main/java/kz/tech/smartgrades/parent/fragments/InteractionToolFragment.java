package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.InteractionToolGradeAdapter;
import kz.tech.smartgrades.parent.adapters.InteractionToolStepsAdapter;
import kz.tech.smartgrades.parent.model.ModelFamilyGroup;
import kz.tech.smartgrades.root.var_resources.DimensionDP;

public class InteractionToolFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ParentActivity activity;

    private ModelFamilyGroup m;

    private ImageView ivBack;
    private TextView tvLogin;
    private CircleImageView civAvatar;
    private TextView tvFullName;

    private Switch sPushNotifications;
    private TextView tvCheckedPushNotifications;

    //Declaring Temporary Coins Variable
    private Switch sTemporaryCoins;
    private LinearLayout llTemporaryCoins;
    private boolean isTemporaryCoinsOn = false;
    private boolean isTemporaryCoinsExists = false;
    private FrameLayout flCoins, flPiggy, flBank, flAutoCharge;
    private TextView tvCoins, tvPiggy, tvBank;

    //Declaring Gadget Time Variable
    private Switch sGadgetTime;
    private LinearLayout llGadgetTime;
    private boolean isGadgetTimeOn = false;
    private boolean isGadgetTimeExists = false;

    //Declaring Steps Variable
    private Switch sSteps;
    private LinearLayout llSteps;
    private InteractionToolStepsAdapter stepsAdapter;
    private RecyclerView rvSteps;
    private FrameLayout flAddSteps;
    private boolean isStepsOn = false;
    private boolean isStepsExists = false;

    //Declaring Money Variable
    private Switch sMoney;
    private LinearLayout llMoney;
    private FrameLayout flLocked;
    private boolean isMoneyOn = false;
    private boolean isMoneyExists = false;

    //Declaring Smart Variable
    private Switch sSmart;
    private LinearLayout llSmart;
    private InteractionToolGradeAdapter smartAdapter;
    private RecyclerView rvSmart;
    private boolean isSmartOn = false;
    private boolean isSmartExists = false;

    //Declaring Ordinary Variable
    private Switch sOrdinary;
    private LinearLayout llOrdinary;
    private boolean isOrdinaryOn = false;

    private boolean isAlertDialogOnTool = false;

    public static InteractionToolFragment newInstance(ModelFamilyGroup m) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", m);
        InteractionToolFragment fragment = new InteractionToolFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ParentActivity) getActivity();
        if (getArguments() != null) {
            m = getArguments().getParcelable("model");
            Log.d("TAG", "GOOD");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_child_interaction_tool, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvLogin = view.findViewById(R.id.tvLogin);
        civAvatar = view.findViewById(R.id.civAvatar);
        tvFullName = view.findViewById(R.id.tvFullName);

        sPushNotifications = view.findViewById(R.id.sPushNotifications);
        sPushNotifications.setOnCheckedChangeListener(this);
        tvCheckedPushNotifications = view.findViewById(R.id.tvCheckedPushNotifications);

        //Init Temporary Coins Views
        sTemporaryCoins = view.findViewById(R.id.sTemporaryCoins);
        sTemporaryCoins.setOnCheckedChangeListener(this);

        llTemporaryCoins = view.findViewById(R.id.llTemporaryCoins);
        flCoins = view.findViewById(R.id.flCoins);
        flPiggy = view.findViewById(R.id.flPiggy);
        flBank = view.findViewById(R.id.flBank);
        flBank.setOnClickListener(this);
        flAutoCharge = view.findViewById(R.id.flAutoCharge);
        flAutoCharge.setOnClickListener(this);
        tvCoins = view.findViewById(R.id.tvCoins);
        tvPiggy = view.findViewById(R.id.tvPiggy);
        tvBank = view.findViewById(R.id.tvBank);

        //Init Gadget Time Views
        sGadgetTime = view.findViewById(R.id.sGadgetTime);
        sGadgetTime.setOnCheckedChangeListener(this);

        llGadgetTime = view.findViewById(R.id.llGadgetTime);

        //Init Steps Steps Views
        sSteps = view.findViewById(R.id.sSteps);
        sSteps.setOnCheckedChangeListener(this);

        llSteps = view.findViewById(R.id.llSteps);
        flAddSteps = view.findViewById(R.id.flAddSteps);
        flAddSteps.setOnClickListener(this);
        stepsAdapter = new InteractionToolStepsAdapter(activity);
        rvSteps = view.findViewById(R.id.rvSteps);
        rvSteps.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvSteps.setAdapter(stepsAdapter);

        //Declaring Money Variable
        sMoney = view.findViewById(R.id.sMoney);
        sMoney.setOnCheckedChangeListener(this);
        llMoney = view.findViewById(R.id.llMoney);
        flLocked = view.findViewById(R.id.flLocked);
        flLocked.setOnClickListener(this);

        //Declaring Smart Variable
        sSmart = view.findViewById(R.id.sSmart);
        sSmart.setOnCheckedChangeListener(this);
        llSmart = view.findViewById(R.id.llSmart);
        smartAdapter = new InteractionToolGradeAdapter(activity);
        rvSmart = view.findViewById(R.id.rvSmart);
        rvSmart.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rvSmart.setAdapter(smartAdapter);

        //Declaring Ordinary Variable
        sOrdinary = view.findViewById(R.id.sOrdinary);
        sOrdinary.setOnCheckedChangeListener(this);
        llOrdinary = view.findViewById(R.id.llOrdinary);
    }

    private void onPushNotifications(boolean isChecked) {
        tvCheckedPushNotifications.setText(isChecked ? "Включены" : "Выключены");

    }

    private void onBack() {
        activity.onRemoveFragment(2);
        //activity.onNextFragment(1, m);
    }

    private void onTernarLayoutHide(View view, boolean isVisible) {
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void onAlertDialogLimitations() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View v = activity.getLayoutInflater().inflate(R.layout.alert_dialog_builder_money_limitations, null);
        builder.setView(v);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView tvInstall = v.findViewById(R.id.tvInstall);
        EditText etEnterMoney = v.findViewById(R.id.etEnterMoney);

        etEnterMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                //      adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Toast.makeText(getApplicationContext(),"before text change",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                //   Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
                if (arg0.length() == 0) {
                    onTernarTextView(tvInstall, false);
                } else if (arg0.length() > 0) {
                    onTernarTextView(tvInstall, true);
                }
            }
        });

        tvInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    private void onTernarTextView(View view, boolean isActive) {
        view.setBackgroundResource(isActive ?
                R.drawable.btn_view_background_color_blue_sea :
                R.drawable.btn_view_background_color_gray_line);
        view.setEnabled(isActive);
        view.setPadding(DimensionDP.sizeDP(50, activity), 0, DimensionDP.sizeDP(50, activity), 0);
    }


    private void onBank() {

    }

    private void onAutoCharge() {

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.flLocked:
                onAlertDialogLimitations();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sPushNotifications:
                onPushNotifications(isChecked);
                break;
        }
    }
}