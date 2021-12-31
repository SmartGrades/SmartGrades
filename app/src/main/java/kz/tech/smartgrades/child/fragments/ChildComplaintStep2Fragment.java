package kz.tech.smartgrades.child.fragments;

import android.content.ClipData;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.adapters.ChildComplaintQAdapter;
import kz.tech.smartgrades.child.adapters.ChildDayListAdapter;
import kz.tech.smartgrades.root.dialogs.DefaultDialog;
import kz.tech.smartgrades.root.login.LoginKey;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._System.Vibrator;

public class ChildComplaintStep2Fragment extends Fragment implements
        View.OnClickListener,
        View.OnLongClickListener,
        View.OnDragListener,
        ChildComplaintQAdapter.OnItemLongClickListener,
        ChildDayListAdapter.OnItemClickListener,
        DefaultDialog.OnClickListener{

    private ChildActivity activity;
    private String CHILD_ID;
    private boolean isOk[] = new boolean[7];
    private boolean isGoBack = false;

    private ScrollView svMain;

    private ImageView ivBack;
    private TextView tvBack;
    private CardView cvSend;

    private CardView cvStudent;
    private CardView cvParent;
    private CardView cvTeacher;
    private CardView cvPsychologist;
    private CardView cvDirector;
    private CardView cvInspector;
    private CardView cvProtection;

    private TextView tv1QTime;
    private TextView tv2QTime;
    private TextView tv3QTime;
    private TextView tv4QTime;
    private TextView tv5QTime;
    private TextView tv6QTime;
    private TextView tv7QTime;

    private ImageView iv1QTimer;
    private ImageView iv2QTimer;
    private ImageView iv3QTimer;
    private ImageView iv4QTimer;
    private ImageView iv5QTimer;
    private ImageView iv6QTimer;
    private ImageView iv7QTimer;

    private ConstraintLayout cl1Q;
    private ConstraintLayout cl2Q;
    private ConstraintLayout cl3Q;
    private ConstraintLayout cl4Q;
    private ConstraintLayout cl5Q;
    private ConstraintLayout cl6Q;
    private ConstraintLayout cl7Q;

    private RecyclerView rv1Q;
    private RecyclerView rv2Q;
    private RecyclerView rv3Q;
    private RecyclerView rv4Q;
    private RecyclerView rv5Q;
    private RecyclerView rv6Q;
    private RecyclerView rv7Q;

    private String selected;

    private ChildComplaintQAdapter childComplaintQAdapter1;
    private ChildComplaintQAdapter childComplaintQAdapter2;
    private ChildComplaintQAdapter childComplaintQAdapter3;
    private ChildComplaintQAdapter childComplaintQAdapter4;
    private ChildComplaintQAdapter childComplaintQAdapter5;
    private ChildComplaintQAdapter childComplaintQAdapter6;
    private ChildComplaintQAdapter childComplaintQAdapter7;

    private TextView tv1QDes;
    private TextView tv2QDes;
    private TextView tv3QDes;
    private TextView tv4QDes;
    private TextView tv5QDes;
    private TextView tv6QDes;
    private TextView tv7QDes;

    private TextView tv1Q;
    private TextView tv2Q;
    private TextView tv3Q;
    private TextView tv4Q;
    private TextView tv5Q;
    private TextView tv6Q;
    private TextView tv7Q;

    private PopupWindow pw1 = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private PopupWindow pw2 = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private PopupWindow pw3 = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private PopupWindow pw4 = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private PopupWindow pw5 = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private PopupWindow pw6 = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private PopupWindow pw7 = new PopupWindow(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    private LinearLayout llRoles;

    private int vUpId = R.id.vUp;
    private int vDownId = R.id.vDown;
    private View vUp;
    private View vDown;
    private int MoveType = 0;
    private Timer timer;
    private ArrayList<Integer> days = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ChildActivity) getActivity();
        CHILD_ID = activity.login.loadUserDate(LoginKey.ID);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_complaint_step2, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setAdapter();
    }

    private void setAdapter() {
        childComplaintQAdapter1 = new ChildComplaintQAdapter(activity);
        childComplaintQAdapter1.setOnItemLongClickListener(this);
        rv1Q.setLayoutManager(new GridLayoutManager(activity, 3));
        rv1Q.setAdapter(childComplaintQAdapter1);

        childComplaintQAdapter2 = new ChildComplaintQAdapter(activity);
        childComplaintQAdapter2.setOnItemLongClickListener(this);
        rv2Q.setLayoutManager(new GridLayoutManager(activity, 3));
        rv2Q.setAdapter(childComplaintQAdapter2);

        childComplaintQAdapter3 = new ChildComplaintQAdapter(activity);
        childComplaintQAdapter3.setOnItemLongClickListener(this);
        rv3Q.setLayoutManager(new GridLayoutManager(activity, 3));
        rv3Q.setAdapter(childComplaintQAdapter3);

        childComplaintQAdapter4 = new ChildComplaintQAdapter(activity);
        childComplaintQAdapter4.setOnItemLongClickListener(this);
        rv4Q.setLayoutManager(new GridLayoutManager(activity, 3));
        rv4Q.setAdapter(childComplaintQAdapter4);

        childComplaintQAdapter5 = new ChildComplaintQAdapter(activity);
        childComplaintQAdapter5.setOnItemLongClickListener(this);
        rv5Q.setLayoutManager(new GridLayoutManager(activity, 3));
        rv5Q.setAdapter(childComplaintQAdapter5);

        childComplaintQAdapter6 = new ChildComplaintQAdapter(activity);
        childComplaintQAdapter6.setOnItemLongClickListener(this);
        rv6Q.setLayoutManager(new GridLayoutManager(activity, 3));
        rv6Q.setAdapter(childComplaintQAdapter6);

        childComplaintQAdapter7 = new ChildComplaintQAdapter(activity);
        childComplaintQAdapter7.setOnItemLongClickListener(this);
        rv7Q.setLayoutManager(new GridLayoutManager(activity, 3));
        rv7Q.setAdapter(childComplaintQAdapter7);
    }

    private void initViews(View view) {
        svMain = view.findViewById(R.id.svMain);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvBack = view.findViewById(R.id.tvBack);
        tvBack.setOnClickListener(this);
        cvSend = view.findViewById(R.id.cvSend);
        cvSend.setOnClickListener(this);
        cvSend.setClickable(false);
        cvStudent = view.findViewById(R.id.cvStudent);
        cvStudent.setOnLongClickListener(this);
        cvParent = view.findViewById(R.id.cvParent);
        cvParent.setOnLongClickListener(this);
        cvTeacher = view.findViewById(R.id.cvTeacher);
        cvTeacher.setOnLongClickListener(this);
        cvPsychologist = view.findViewById(R.id.cvPsychologist);
        cvPsychologist.setOnLongClickListener(this);
        cvDirector = view.findViewById(R.id.cvDirector);
        cvDirector.setOnLongClickListener(this);
        cvInspector = view.findViewById(R.id.cvInspector);
        cvInspector.setOnLongClickListener(this);
        cvProtection = view.findViewById(R.id.cvProtection);
        cvProtection.setOnLongClickListener(this);
        tv1QTime = view.findViewById(R.id.tv1QTime);
        tv2QTime = view.findViewById(R.id.tv2QTime);
        tv3QTime = view.findViewById(R.id.tv3QTime);
        tv4QTime = view.findViewById(R.id.tv4QTime);
        tv5QTime = view.findViewById(R.id.tv5QTime);
        tv6QTime = view.findViewById(R.id.tv6QTime);
        tv7QTime = view.findViewById(R.id.tv7QTime);
        tv1QTime.setOnClickListener(this);
        tv2QTime.setOnClickListener(this);
        tv3QTime.setOnClickListener(this);
        tv4QTime.setOnClickListener(this);
        tv5QTime.setOnClickListener(this);
        tv6QTime.setOnClickListener(this);
        tv7QTime.setOnClickListener(this);
        tv1QTime.setClickable(false);
        tv2QTime.setClickable(false);
        tv3QTime.setClickable(false);
        tv4QTime.setClickable(false);
        tv5QTime.setClickable(false);
        tv6QTime.setClickable(false);
        tv7QTime.setClickable(false);
        iv1QTimer = view.findViewById(R.id.iv1QTimer);
        iv2QTimer = view.findViewById(R.id.iv2QTimer);
        iv3QTimer = view.findViewById(R.id.iv3QTimer);
        iv4QTimer = view.findViewById(R.id.iv4QTimer);
        iv5QTimer = view.findViewById(R.id.iv5QTimer);
        iv6QTimer = view.findViewById(R.id.iv6QTimer);
        iv7QTimer = view.findViewById(R.id.iv7QTimer);
        iv1QTimer.setOnClickListener(this);
        iv2QTimer.setOnClickListener(this);
        iv3QTimer.setOnClickListener(this);
        iv4QTimer.setOnClickListener(this);
        iv5QTimer.setOnClickListener(this);
        iv6QTimer.setOnClickListener(this);
        iv7QTimer.setOnClickListener(this);
        iv1QTimer.setClickable(false);
        iv2QTimer.setClickable(false);
        iv3QTimer.setClickable(false);
        iv4QTimer.setClickable(false);
        iv5QTimer.setClickable(false);
        iv6QTimer.setClickable(false);
        iv7QTimer.setClickable(false);
        cl1Q = view.findViewById(R.id.cl1Q);
        cl2Q = view.findViewById(R.id.cl2Q);
        cl3Q = view.findViewById(R.id.cl3Q);
        cl4Q = view.findViewById(R.id.cl4Q);
        cl5Q = view.findViewById(R.id.cl5Q);
        cl6Q = view.findViewById(R.id.cl6Q);
        cl7Q = view.findViewById(R.id.cl7Q);
        rv1Q = view.findViewById(R.id.rv1Q);
        rv2Q = view.findViewById(R.id.rv2Q);
        rv3Q = view.findViewById(R.id.rv3Q);
        rv4Q = view.findViewById(R.id.rv4Q);
        rv5Q = view.findViewById(R.id.rv5Q);
        rv6Q = view.findViewById(R.id.rv6Q);
        rv7Q = view.findViewById(R.id.rv7Q);
        rv1Q.setOnLongClickListener(this);
        rv2Q.setOnLongClickListener(this);
        rv3Q.setOnLongClickListener(this);
        rv4Q.setOnLongClickListener(this);
        rv5Q.setOnLongClickListener(this);
        rv6Q.setOnLongClickListener(this);
        rv7Q.setOnLongClickListener(this);
        cl1Q.setOnDragListener(this);
        cl2Q.setOnDragListener(this);
        cl3Q.setOnDragListener(this);
        cl4Q.setOnDragListener(this);
        cl5Q.setOnDragListener(this);
        cl6Q.setOnDragListener(this);
        cl7Q.setOnDragListener(this);
        tv1QDes = view.findViewById(R.id.tv1QDes);
        tv2QDes = view.findViewById(R.id.tv2QDes);
        tv3QDes = view.findViewById(R.id.tv3QDes);
        tv4QDes = view.findViewById(R.id.tv4QDes);
        tv5QDes = view.findViewById(R.id.tv5QDes);
        tv6QDes = view.findViewById(R.id.tv6QDes);
        tv7QDes = view.findViewById(R.id.tv7QDes);
        tv1Q = view.findViewById(R.id.tv1Q);
        tv2Q = view.findViewById(R.id.tv2Q);
        tv3Q = view.findViewById(R.id.tv3Q);
        tv4Q = view.findViewById(R.id.tv4Q);
        tv5Q = view.findViewById(R.id.tv5Q);
        tv6Q = view.findViewById(R.id.tv6Q);
        tv7Q = view.findViewById(R.id.tv7Q);
        vUp = view.findViewById(R.id.vUp);
        vDown = view.findViewById(R.id.vDown);
        vUp.setOnDragListener(this);
        vDown.setOnDragListener(this);
        llRoles = view.findViewById(R.id.llRoles);
        llRoles.setOnDragListener(this);
        days.add(3);
        days.add(4);
        days.add(5);
        days.add(6);
        days.add(7);
        days.add(8);
        days.add(9);
        days.add(10);
        days.add(11);
        days.add(12);
        days.add(13);
        days.add(14);
    }

    private void checkOk() {
        if (cvStudent.getVisibility() == View.INVISIBLE ||
                cvParent.getVisibility() == View.INVISIBLE ||
                cvTeacher.getVisibility() == View.INVISIBLE ||
                cvProtection.getVisibility() == View.INVISIBLE ||
                cvDirector.getVisibility() == View.INVISIBLE ||
                cvInspector.getVisibility() == View.INVISIBLE ||
                cvPsychologist.getVisibility() == View.INVISIBLE) {
            cvSend.setClickable(true);
            cvSend.setCardBackgroundColor(activity.getResources().getColor(R.color.red));
        } else {
            cvSend.setClickable(false);
            cvSend.setCardBackgroundColor(activity.getResources().getColor(R.color.gray_disabled_v2));
        }
    }

    private void onBack() {
        cl1Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_gray_disable_border));
        tv1QDes.setVisibility(View.VISIBLE);
        cl2Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_gray_disable_border));
        tv2QDes.setVisibility(View.VISIBLE);
        tv2Q.setVisibility(View.GONE);
        iv2QTimer.setVisibility(View.GONE);
        tv2QTime.setVisibility(View.GONE);
        cl2Q.setVisibility(View.GONE);
        cl3Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_gray_disable_border));
        tv3QDes.setVisibility(View.VISIBLE);
        tv3Q.setVisibility(View.GONE);
        iv3QTimer.setVisibility(View.GONE);
        tv3QTime.setVisibility(View.GONE);
        cl3Q.setVisibility(View.GONE);
        cl4Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_gray_disable_border));
        tv4QDes.setVisibility(View.VISIBLE);
        tv4Q.setVisibility(View.GONE);
        iv4QTimer.setVisibility(View.GONE);
        tv4QTime.setVisibility(View.GONE);
        cl4Q.setVisibility(View.GONE);
        cl5Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_gray_disable_border));
        tv5QDes.setVisibility(View.VISIBLE);
        tv5Q.setVisibility(View.GONE);
        iv5QTimer.setVisibility(View.GONE);
        tv5QTime.setVisibility(View.GONE);
        cl5Q.setVisibility(View.GONE);
        cl6Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_gray_disable_border));
        tv6QDes.setVisibility(View.VISIBLE);
        tv6Q.setVisibility(View.GONE);
        iv6QTimer.setVisibility(View.GONE);
        tv6QTime.setVisibility(View.GONE);
        cl6Q.setVisibility(View.GONE);
        cl7Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_gray_disable_border));
        tv7QDes.setVisibility(View.VISIBLE);
        tv7Q.setVisibility(View.GONE);
        iv7QTimer.setVisibility(View.GONE);
        tv7QTime.setVisibility(View.GONE);
        cl7Q.setVisibility(View.GONE);
        childComplaintQAdapter1.clearList();
        childComplaintQAdapter2.clearList();
        childComplaintQAdapter3.clearList();
        childComplaintQAdapter4.clearList();
        childComplaintQAdapter5.clearList();
        childComplaintQAdapter6.clearList();
        childComplaintQAdapter7.clearList();
        cvStudent.setVisibility(View.VISIBLE);
        cvParent.setVisibility(View.VISIBLE);
        cvTeacher.setVisibility(View.VISIBLE);
        cvPsychologist.setVisibility(View.VISIBLE);
        cvDirector.setVisibility(View.VISIBLE);
        cvInspector.setVisibility(View.VISIBLE);
        cvProtection.setVisibility(View.VISIBLE);
        activity.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
            case R.id.tvBack:
                onBack();
                break;
            case R.id.cvSend:
                onSend();
                break;
            case R.id.tv1QTime:
            case R.id.iv1QTimer:
                onPickTime1Q();
                break;
            case R.id.tv2QTime:
            case R.id.iv2QTimer:
                onPickTime2Q();
                break;
            case R.id.tv3QTime:
            case R.id.iv3QTimer:
                onPickTime3Q();
                break;
            case R.id.tv4QTime:
            case R.id.iv4QTimer:
                onPickTime4Q();
                break;
            case R.id.tv5QTime:
            case R.id.iv5QTimer:
                onPickTime5Q();
                break;
            case R.id.tv6QTime:
            case R.id.iv6QTimer:
                onPickTime6Q();
                break;
            case R.id.tv7QTime:
            case R.id.iv7QTimer:
                onPickTime7Q();
                break;
        }
    }

    private void onPickTime1Q() {
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_q_time, null);
        RecyclerView rv = view.findViewById(R.id.rvDays);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        ChildDayListAdapter adapter = new ChildDayListAdapter(activity, 1);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
        adapter.updateData(days);
        pw1.setContentView(view);
        pw1.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw1.setElevation(20);
        }
        pw1.showAsDropDown(tv1QTime);
    }
    private void onPickTime2Q() {
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_q_time, null);
        RecyclerView rv = view.findViewById(R.id.rvDays);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        ChildDayListAdapter adapter = new ChildDayListAdapter(activity, 2);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
        adapter.updateData(days);
        pw2.setContentView(view);
        pw2.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw2.setElevation(20);
        }
        pw2.showAsDropDown(tv2QTime);
    }
    private void onPickTime3Q() {
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_q_time, null);
        RecyclerView rv = view.findViewById(R.id.rvDays);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        ChildDayListAdapter adapter = new ChildDayListAdapter(activity, 3);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
        adapter.updateData(days);
        pw3.setContentView(view);
        pw3.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw3.setElevation(20);
        }
        pw3.showAsDropDown(tv3QTime);
    }
    private void onPickTime4Q() {
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_q_time, null);
        RecyclerView rv = view.findViewById(R.id.rvDays);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        ChildDayListAdapter adapter = new ChildDayListAdapter(activity, 4);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
        adapter.updateData(days);
        pw4.setContentView(view);
        pw4.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw4.setElevation(20);
        }
        pw4.showAsDropDown(tv4QTime);
    }
    private void onPickTime5Q() {
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_q_time, null);
        RecyclerView rv = view.findViewById(R.id.rvDays);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        ChildDayListAdapter adapter = new ChildDayListAdapter(activity, 5);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
        adapter.updateData(days);
        pw5.setContentView(view);
        pw5.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw5.setElevation(20);
        }
        pw5.showAsDropDown(tv5QTime);
    }
    private void onPickTime6Q() {
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_q_time, null);
        RecyclerView rv = view.findViewById(R.id.rvDays);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        ChildDayListAdapter adapter = new ChildDayListAdapter(activity, 6);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
        adapter.updateData(days);
        pw6.setContentView(view);
        pw6.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw6.setElevation(20);
        }
        pw6.showAsDropDown(tv6QTime);
    }
    private void onPickTime7Q() {
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_q_time, null);
        RecyclerView rv = view.findViewById(R.id.rvDays);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        ChildDayListAdapter adapter = new ChildDayListAdapter(activity, 7);
        adapter.setOnItemClickListener(this);
        rv.setAdapter(adapter);
        adapter.updateData(days);
        pw7.setContentView(view);
        pw7.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pw7.setElevation(20);
        }
        pw7.showAsDropDown(tv7QTime);
    }

    @Override
    public void onDayClick(String day, int tag) {
        switch (tag) {
            case 1:
                tv1QTime.setText(day);
                pw1.dismiss();
                break;
            case 2:
                tv2QTime.setText(day);
                pw2.dismiss();
                break;
            case 3:
                tv3QTime.setText(day);
                pw3.dismiss();
                break;
            case 4:
                tv4QTime.setText(day);
                pw4.dismiss();
                break;
            case 5:
                tv5QTime.setText(day);
                pw5.dismiss();
                break;
            case 6:
                tv6QTime.setText(day);
                pw6.dismiss();
                break;
            case 7:
                tv7QTime.setText(day);
                pw7.dismiss();
                break;
        }
    }

    private void onSend() {
        DefaultDialog dialog = new DefaultDialog(activity);
        dialog.setCancel(activity.getResources().getColor(R.color.gray_average_dark), getString(R.string.cancel));
        dialog.setExit(activity.getResources().getColor(R.color.red), getString(R.string.send));
        dialog.setMessage(getString(R.string.Are_you_sure_you_want_to_submit_a_complaint));
        dialog.setBody(getString(R.string.Send_a_complaint_only_if_your_problem_is_real_and_prevents_you_from_living_or_studying_in_peace));
        dialog.setOnClickListener(this);
        dialog.show();
    }

    private void sendComplaint() {
        //
    }

    @Override
    public boolean onLongClick(View view) {
        Vibrator(activity);
        //view.setVisibility(View.INVISIBLE);
        String id = "";
        if (view.getId() == R.id.cvStudent) id = "Student";
        else if (view.getId() == R.id.cvParent) id = "Parent";
        else if (view.getId() == R.id.cvTeacher) id = "Teacher";
        else if (view.getId() == R.id.cvPsychologist) id = "Psychologist";
        else if (view.getId() == R.id.cvDirector) id = "Director";
        else if (view.getId() == R.id.cvInspector) id = "Inspector";
        else if (view.getId() == R.id.cvProtection) id = "Protection";
        setSelect(id);
        ClipData data = ClipData.newPlainText("", "" );
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            view.startDragAndDrop(data, shadowBuilder, view, 0);
        else view.startDrag(data, shadowBuilder, view, 0);
        return true;
    }

    private void setSelect(String selected) {
        this.selected = selected;
    }

    private String getSelect() {
        return selected;
    }

    @Override
    public void onLongClick(String type, View itemView) {
        Vibrator(activity);
        setSelect(type);
        ClipData data = ClipData.newPlainText("", "" );
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(itemView);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            itemView.startDragAndDrop(data, shadowBuilder, itemView, 0);
        else itemView.startDrag(data, shadowBuilder, itemView, 0);
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        checkOk();
        View vSource = (View) dragEvent.getLocalState();
        vSource.setVisibility(View.INVISIBLE);
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_ENTERED:
                if(view.getId() == vUpId || view.getId() == vDownId){
                    if(vSource.getId() == vDownId) MoveType = 1;
                    timer = new Timer();
                    timer.schedule(new TimerScrollTask(), 0, 5);
                }
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                if (view.getId() == vUpId || view.getId() == vDownId) {
                    if(timer != null){
                        timer.cancel();
                        timer = null;
                    }
                }
                break;

            case DragEvent.ACTION_DROP:
                if (view.getId() == cl1Q.getId()) {
                    setItemToQ(selected, childComplaintQAdapter1);
                    tv1QDes.setVisibility(View.GONE);
                    cl1Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_green_border));
                }
                else if (view.getId() == cl2Q.getId()) {
                    setItemToQ(selected, childComplaintQAdapter2);
                    tv2QDes.setVisibility(View.GONE);
                    cl2Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_green_border));
                    tv3Q.setVisibility(View.VISIBLE);
                    iv3QTimer.setVisibility(View.VISIBLE);
                    cl3Q.setVisibility(View.VISIBLE);
                    tv3QTime.setVisibility(View.VISIBLE);
                }
                else if (view.getId() == cl3Q.getId()) {
                    setItemToQ(selected, childComplaintQAdapter3);
                    tv3QDes.setVisibility(View.GONE);
                    cl3Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_green_border));
                    tv4Q.setVisibility(View.VISIBLE);
                    iv4QTimer.setVisibility(View.VISIBLE);
                    cl4Q.setVisibility(View.VISIBLE);
                    tv4QTime.setVisibility(View.VISIBLE);
                }
                else if (view.getId() == cl4Q.getId()) {
                    setItemToQ(selected, childComplaintQAdapter4);
                    tv4QDes.setVisibility(View.GONE);
                    cl4Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_green_border));
                    tv5Q.setVisibility(View.VISIBLE);
                    iv5QTimer.setVisibility(View.VISIBLE);
                    cl5Q.setVisibility(View.VISIBLE);
                    tv5QTime.setVisibility(View.VISIBLE);
                }
                else if (view.getId() == cl5Q.getId()) {
                    setItemToQ(selected, childComplaintQAdapter5);
                    tv5QDes.setVisibility(View.GONE);
                    cl5Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_green_border));
                    tv6Q.setVisibility(View.VISIBLE);
                    iv6QTimer.setVisibility(View.VISIBLE);
                    cl6Q.setVisibility(View.VISIBLE);
                    tv6QTime.setVisibility(View.VISIBLE);
                }
                else if (view.getId() == cl6Q.getId()) {
                    setItemToQ(selected, childComplaintQAdapter6);
                    tv6QDes.setVisibility(View.GONE);
                    cl6Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_green_border));
                    tv7Q.setVisibility(View.VISIBLE);
                    iv7QTimer.setVisibility(View.VISIBLE);
                    cl7Q.setVisibility(View.VISIBLE);
                    tv7QTime.setVisibility(View.VISIBLE);
                }
                else if (view.getId() == cl7Q.getId()) {
                    setItemToQ(selected, childComplaintQAdapter7);
                    tv7QDes.setVisibility(View.GONE);
                    cl7Q.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_green_border));
                }
                else if (view.getId() == llRoles.getId()) {
                    isGoBack = true;
                }
                break;

            case DragEvent.ACTION_DRAG_ENDED:
                vSource.setVisibility(View.VISIBLE);
                checkAdapter(selected, childComplaintQAdapter1, cl1Q, tv1QDes);
                checkAdapter(selected, childComplaintQAdapter2, cl2Q, tv2QDes);
                checkAdapter(selected, childComplaintQAdapter3, cl3Q, tv3QDes);
                checkAdapter(selected, childComplaintQAdapter4, cl4Q, tv4QDes);
                checkAdapter(selected, childComplaintQAdapter5, cl5Q, tv5QDes);
                checkAdapter(selected, childComplaintQAdapter6, cl6Q, tv6QDes);
                checkAdapter(selected, childComplaintQAdapter7, cl7Q, tv7QDes);
                if (view.getId() == vUpId || view.getId() == vDownId) {
                    if(timer != null){
                        timer.cancel();
                        timer = null;
                    }
                }
                checkTime();
                break;
        }
        return true;
    }

    private void checkAdapter(String tag, ChildComplaintQAdapter adapter, View target, View text) {
        if (listIsNullOrEmpty(adapter.getList())) {
            text.setVisibility(View.VISIBLE);
            target.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_gray_disable_border));
        }
        if (isGoBack) {
            switch (tag) {
                case "Student":
                    removeTag("Student");
                    cvStudent.setVisibility(View.VISIBLE);
                    break;
                case "Parent":
                    removeTag("Parent");
                    cvParent.setVisibility(View.VISIBLE);
                    break;
                case "Teacher":
                    removeTag("Teacher");
                    cvTeacher.setVisibility(View.VISIBLE);
                    break;
                case "Psychologist":
                    removeTag("Psychologist");
                    cvPsychologist.setVisibility(View.VISIBLE);
                    break;
                case "Director":
                    removeTag("Director");
                    cvDirector.setVisibility(View.VISIBLE);
                    break;
                case "Inspector":
                    removeTag("Inspector");
                    cvInspector.setVisibility(View.VISIBLE);
                    break;
                case "Protection":
                    removeTag("Protection");
                    cvProtection.setVisibility(View.VISIBLE);
                    break;
            }
            isGoBack = false;
            return;
        }
        switch (tag) {
            case "Student":
                if (adapter.getList().contains("Student"))
                    cvStudent.setVisibility(View.INVISIBLE);
                break;
            case "Parent":
                if (adapter.getList().contains("Parent"))
                    cvParent.setVisibility(View.INVISIBLE);
                break;
            case "Teacher":
                if (adapter.getList().contains("Teacher"))
                    cvTeacher.setVisibility(View.INVISIBLE);
                break;
            case "Psychologist":
                if (adapter.getList().contains("Psychologist"))
                    cvPsychologist.setVisibility(View.INVISIBLE);
                break;
            case "Director":
                if (adapter.getList().contains("Director"))
                    cvDirector.setVisibility(View.INVISIBLE);
                break;
            case "Inspector":
                if (adapter.getList().contains("Inspector"))
                    cvInspector.setVisibility(View.INVISIBLE);
                break;
            case "Protection":
                if (adapter.getList().contains("Protection"))
                    cvProtection.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void setItemToQ(String tag, ChildComplaintQAdapter adapter) {
        if (tag.equals("Student")) {
            if (!adapter.getList().contains("Student")) {
                removeTag("Student");
                adapter.updateData("Student");
            }
        }
        else if (tag.equals("Parent")) {
            if (!adapter.getList().contains("Parent")) {
                removeTag("Parent");
                adapter.updateData("Parent");
            }
        }
        else if (tag.equals("Teacher")) {
            if (!adapter.getList().contains("Teacher")) {
                removeTag("Teacher");
                adapter.updateData("Teacher");
            }
        }
        else if (tag.equals("Psychologist")) {
            if (!adapter.getList().contains("Psychologist")) {
                removeTag("Psychologist");
                adapter.updateData("Psychologist");
            }
        }
        else if (tag.equals("Director")) {
            if (!adapter.getList().contains("Director")) {
                removeTag("Director");
                adapter.updateData("Director");
            }
        }
        else if (tag.equals("Inspector")) {
            if (!adapter.getList().contains("Inspector")) {
                removeTag("Inspector");
                adapter.updateData("Inspector");
            }
        }
        else if (tag.equals("Protection")) {
            if (!adapter.getList().contains("Protection")) {
                removeTag("Protection");
                adapter.updateData("Protection");
            }
        }
        //checkTime();
    }

    private void removeTag(String tag) {
        childComplaintQAdapter1.remove(tag);
        childComplaintQAdapter2.remove(tag);
        childComplaintQAdapter3.remove(tag);
        childComplaintQAdapter4.remove(tag);
        childComplaintQAdapter5.remove(tag);
        childComplaintQAdapter6.remove(tag);
        childComplaintQAdapter7.remove(tag);
    }

    private void checkTime() {
        if (listIsNullOrEmpty(childComplaintQAdapter1.getList())) {
            tv1QTime.setText("0");
            tv1QTime.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
            tv1QTime.setClickable(false);
            iv1QTimer.setClickable(false);
        } else {
            tv1QTime.setText(days.get(0) + " " + activity.getResources().getString(R.string.day2));
            tv1QTime.setTextColor(activity.getResources().getColor(R.color.blue_sea));
            tv1QTime.setClickable(true);
            iv1QTimer.setClickable(true);
        }
        if (listIsNullOrEmpty(childComplaintQAdapter2.getList())) {
            tv2QTime.setText("0");
            tv2QTime.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
            tv2QTime.setClickable(false);
            iv2QTimer.setClickable(false);
        } else {
            tv2QTime.setText(days.get(0) + " " + activity.getResources().getString(R.string.day2));
            tv2QTime.setTextColor(activity.getResources().getColor(R.color.blue_sea));
            tv2QTime.setClickable(true);
            iv2QTimer.setClickable(true);
        }
        if (listIsNullOrEmpty(childComplaintQAdapter3.getList())) {
            tv3QTime.setText("0");
            tv3QTime.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
            tv3QTime.setClickable(false);
            iv3QTimer.setClickable(false);
        } else {
            tv3QTime.setText(days.get(0) + " " + activity.getResources().getString(R.string.day2));
            tv3QTime.setTextColor(activity.getResources().getColor(R.color.blue_sea));
            tv3QTime.setClickable(true);
            iv3QTimer.setClickable(true);
        }
        if (listIsNullOrEmpty(childComplaintQAdapter4.getList())) {
            tv4QTime.setText("0");
            tv4QTime.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
            tv4QTime.setClickable(false);
            iv4QTimer.setClickable(false);
        } else {
            tv4QTime.setText(days.get(0) + " " + activity.getResources().getString(R.string.day2));
            tv4QTime.setTextColor(activity.getResources().getColor(R.color.blue_sea));
            tv4QTime.setClickable(true);
            iv4QTimer.setClickable(true);
        }
        if (listIsNullOrEmpty(childComplaintQAdapter5.getList())) {
            tv5QTime.setText("0");
            tv5QTime.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
            tv5QTime.setClickable(false);
            iv5QTimer.setClickable(false);
        } else {
            tv5QTime.setText(days.get(0) + " " + activity.getResources().getString(R.string.day2));
            tv5QTime.setTextColor(activity.getResources().getColor(R.color.blue_sea));
            tv5QTime.setClickable(true);
            iv5QTimer.setClickable(true);
        }
        if (listIsNullOrEmpty(childComplaintQAdapter6.getList())) {
            tv6QTime.setText("0");
            tv6QTime.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
            tv6QTime.setClickable(false);
            iv6QTimer.setClickable(false);
        } else {
            tv6QTime.setText(days.get(0) + " " + activity.getResources().getString(R.string.day2));
            tv6QTime.setTextColor(activity.getResources().getColor(R.color.blue_sea));
            tv6QTime.setClickable(true);
            iv6QTimer.setClickable(true);
        }
        if (listIsNullOrEmpty(childComplaintQAdapter7.getList())) {
            tv7QTime.setText("0");
            tv7QTime.setTextColor(activity.getResources().getColor(R.color.gray_average_dark));
            tv7QTime.setClickable(false);
            iv7QTimer.setClickable(false);
        } else {
            tv7QTime.setText(days.get(0) + " " + activity.getResources().getString(R.string.day2));
            tv7QTime.setTextColor(activity.getResources().getColor(R.color.blue_sea));
            tv7QTime.setClickable(true);
            iv7QTimer.setClickable(true);
        }
    }

    @Override
    public void onCancelDialog() {

    }

    @Override
    public void onExitDialog() {
        sendComplaint();
        activity.onBackPressed();
        activity.onBackPressed();
    }

    class TimerScrollTask extends TimerTask {
        public void run(){
            svMain.post(new Runnable(){
                @Override
                public void run(){
                    if(MoveType == 0) svMain.scrollBy(0, 10);
                    else svMain.scrollBy(0, -10);
                }
            });
        }
    }
}
