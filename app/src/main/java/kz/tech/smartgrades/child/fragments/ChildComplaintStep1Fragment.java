package kz.tech.smartgrades.child.fragments;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.adapters.ChildComplaintSelectedStudentAdapter;
import kz.tech.smartgrades.child.adapters.ChildComplaintSelectedTeacherAdapter;
import kz.tech.smartgrades.child.adapters.ChildStudentsListAdapter;
import kz.tech.smartgrades.child.adapters.ChildTeachersListAdapter;
import kz.tech.smartgrades.child.models.ModelComplaintData;
import kz.tech.smartgrades.child.models.ModelComplaintDataStudent;
import kz.tech.smartgrades.child.models.ModelComplaintDataTeacher;
import kz.tech.smartgrades.root.dialogs.DefaultDialog;
import kz.tech.smartgrades.root.login.LoginKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.Convert.TIME_FORMAT_SHORT_SECOND;
import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_GetDataForComplaint;

public class ChildComplaintStep1Fragment extends Fragment implements
        View.OnClickListener,
        View.OnTouchListener,
        ChildStudentsListAdapter.OnItemCLickListener,
        ChildTeachersListAdapter.OnItemCLickListener,
        DefaultDialog.OnClickListener{

    private ChildActivity activity;
    private String CHILD_ID;
    private boolean isOk[] = new boolean[4];
    private boolean complaintTo[] = new boolean[3];
    private ModelComplaintData mComplaintData;
    private ModelComplaintDataStudent mStudent;
    private ModelComplaintDataTeacher mTeacher;

    private ImageView ivBack;
    private TextView tvCancel;
    private TextView tvNext;

    private EditText etComplaintLabel;
    private ImageView ivStudentCheck;
    private ImageView ivTeacherCheck;
    private ImageView ivOtherCheck;
    private RecyclerView rvStudentsList;
    private CardView cvStudentsList;
    private ConstraintLayout clLabelStudent;
    private TextView tvSelectStudent;
    private RecyclerView rvSelectStudentsList;
    private ConstraintLayout clSearchStudent;
    private ImageView ivSearchStudent;
    private EditText etSearchStudent;
    private ImageView ivSearchArrowStudent;
    private RecyclerView rvTeachersList;
    private CardView cvTeachersList;
    private ConstraintLayout clLabelTeacher;
    private TextView tvSelectTeacher;
    private RecyclerView rvSelectTeachersList;
    private ConstraintLayout clSearchTeacher;
    private ImageView ivSearchTeacher;
    private EditText etSearchTeacher;
    private ImageView ivSearchArrowTeacher;
    private CardView cvOtherLabel;
    private EditText etOtherLabel;

    private CardView cvDescription;
    private EditText etDescription;
    private ImageView ivGallery;
    private ImageView ivMicrophone;
    private TextView tvTimer;

    private ImageView ivAnonymouslyCheck;
    private ImageView ivOpenCheck;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String fileName;

    //template
    private ImageView ivPlay;
    private ImageView ivDelete;

    private Timer _timer;
    private int period = 0;
    private int max_period = 0;

    private int timer;
    private int timerFull;
    private boolean isRun = false;

    private boolean isPlay = false;

    private ChildStudentsListAdapter StudentListAdapter;
    private ChildTeachersListAdapter TeacherListAdapter;

    private ChildComplaintSelectedStudentAdapter childComplaintSelectedStudentAdapter;
    private ChildComplaintSelectedTeacherAdapter childComplaintSelectedTeacherAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ChildActivity) getActivity();
        CHILD_ID = activity.login.loadUserDate(LoginKey.ID);
        fileName = Environment.getExternalStorageDirectory() + "/record.3gpp";
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_complaint_step1, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        showMediaButtons();
    }
    @SuppressLint("ClickableViewAccessibility")
    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        tvNext = view.findViewById(R.id.tvNext);
        tvNext.setOnClickListener(this);
        tvNext.setClickable(false);
        etComplaintLabel = view.findViewById(R.id.etComplaintLabel);
        etComplaintLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                isOk[0] = !stringIsNullOrEmpty(editable.toString());
                isOkCheck();
            }
        });
        ivStudentCheck = view.findViewById(R.id.ivStudentCheck);
        ivStudentCheck.setOnClickListener(this);
        ivTeacherCheck = view.findViewById(R.id.ivTeacherCheck);
        ivTeacherCheck.setOnClickListener(this);
        ivOtherCheck = view.findViewById(R.id.ivOtherCheck);
        ivOtherCheck.setOnClickListener(this);
        rvStudentsList = view.findViewById(R.id.rvStudentsList);
        cvStudentsList = view.findViewById(R.id.cvStudentsList);
        rvTeachersList = view.findViewById(R.id.rvTeachersList);
        cvTeachersList = view.findViewById(R.id.cvTeachersList);
        cvOtherLabel = view.findViewById(R.id.cvOtherLabel);
        etOtherLabel = view.findViewById(R.id.etOtherLabel);
        etOtherLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (!stringIsNullOrEmpty(editable.toString())) isOk[1] = true;
                else isOk[1] = false;
                isOkCheck();
            }
        });
        cvDescription = view.findViewById(R.id.cvDescription);
        etDescription = view.findViewById(R.id.etDescription);
        etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                isOk[2] = !stringIsNullOrEmpty(editable.toString());
                isOkCheck();
            }
        });
        ivGallery = view.findViewById(R.id.ivGallery);
        ivMicrophone = view.findViewById(R.id.ivMicrophone);
        ivMicrophone.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                _System.Vibrator(activity);
                recordStart();
                ivMicrophone.setOnTouchListener(null);
                ivMicrophone.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            recordStop();
                            ivMicrophone.setOnTouchListener(null);
                            ivMicrophone.setOnTouchListener(this);
                        }
                        return false;
                    }
                });
                return false;
            }
        });
        tvTimer = view.findViewById(R.id.tvTimer);
        ivAnonymouslyCheck = view.findViewById(R.id.ivAnonymouslyCheck);
        ivAnonymouslyCheck.setOnClickListener(this);
        ivOpenCheck = view.findViewById(R.id.ivOpenCheck);
        ivOpenCheck.setOnClickListener(this);
        clLabelStudent = view.findViewById(R.id.clLabelStudent);
        clLabelStudent.setOnClickListener(this);
        tvSelectStudent = view.findViewById(R.id.tvSelectStudent);
        rvSelectStudentsList = view.findViewById(R.id.rvSelectStudentsList);
        tvSelectTeacher = view.findViewById(R.id.tvSelectTeacher);
        clLabelTeacher = view.findViewById(R.id.clLabelTeacher);

        clLabelTeacher.setOnClickListener(this);
        rvSelectTeachersList = view.findViewById(R.id.rvSelectTeachersList);
        clSearchStudent = view.findViewById(R.id.clSearchStudent);
        clSearchStudent.setOnClickListener(this);
        ivSearchStudent = view.findViewById(R.id.ivSearchStudent);
        ivSearchStudent.setOnClickListener(this);
        etSearchStudent = view.findViewById(R.id.etSearchStudent);
        etSearchStudent.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
            }
            @Override
            public void afterTextChanged(Editable arg0){
                StudentListAdapter.filter(arg0.toString());
            }
        });
        ivSearchArrowStudent = view.findViewById(R.id.ivSearchArrowStudent);
        ivSearchArrowStudent.setOnClickListener(this);
        clSearchTeacher = view.findViewById(R.id.clSearchTeacher);
        clSearchTeacher.setOnClickListener(this);
        ivSearchTeacher = view.findViewById(R.id.ivSearchTeacher);
        ivSearchTeacher.setOnClickListener(this);
        etSearchTeacher = view.findViewById(R.id.etSearchTeacher);
        etSearchTeacher.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
            }
            @Override
            public void afterTextChanged(Editable arg0){
                TeacherListAdapter.filter(arg0.toString());
            }
        });
        ivSearchArrowTeacher = view.findViewById(R.id.ivSearchArrowTeacher);
        ivSearchArrowTeacher.setOnClickListener(this);

        StudentListAdapter = new ChildStudentsListAdapter();
        StudentListAdapter.setOnItemCLickListener(this);
        rvSelectStudentsList = view.findViewById(R.id.rvSelectStudentsList);
        rvSelectStudentsList.setLayoutManager(new LinearLayoutManager(activity));
        rvSelectStudentsList.setAdapter(StudentListAdapter);

        TeacherListAdapter = new ChildTeachersListAdapter();
        TeacherListAdapter.setOnItemCLickListener(this);
        rvSelectTeachersList = view.findViewById(R.id.rvSelectTeachersList);
        rvSelectTeachersList.setLayoutManager(new LinearLayoutManager(activity));
        rvSelectTeachersList.setAdapter(TeacherListAdapter);

        childComplaintSelectedStudentAdapter = new ChildComplaintSelectedStudentAdapter(activity);
        rvStudentsList.setLayoutManager(new GridLayoutManager(activity, 2));
        rvStudentsList.setAdapter(childComplaintSelectedStudentAdapter);

        childComplaintSelectedTeacherAdapter = new ChildComplaintSelectedTeacherAdapter(activity);
        rvTeachersList.setLayoutManager(new GridLayoutManager(activity, 2));
        rvTeachersList.setAdapter(childComplaintSelectedTeacherAdapter);

        //template
        ivPlay = view.findViewById(R.id.ivPlay);
        ivPlay.setOnClickListener(this);
        ivDelete = view.findViewById(R.id.ivDelete);
        ivDelete.setOnClickListener(this);
    }

    private void showMediaButtons() {
        KeyboardVisibilityEvent.setEventListener(
                getActivity(),
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            ivGallery.setVisibility(View.VISIBLE);
                            ivMicrophone.setVisibility(View.VISIBLE);
                        } else {
                            ivGallery.setVisibility(View.GONE);
                            ivMicrophone.setVisibility(View.GONE);
                            tvTimer.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void isOkCheck() {
        if (isOk[0] && isOk[1] && isOk[2] && isOk[3]) {
            tvNext.setClickable(true);
            tvNext.setTextColor(activity.getResources().getColor(R.color.blue_sea));
        } else {
            tvNext.setClickable(false);
            tvNext.setTextColor(activity.getResources().getColor(R.color.gray_disabled_v2));
        }
    }

    private void onBack() {
        onDestroy();
        StudentListAdapter = null;
        TeacherListAdapter = null;
        childComplaintSelectedStudentAdapter.clearList();
        childComplaintSelectedTeacherAdapter.clearList();
        isRun = false;
        isPlay = false;
        period = 0;
        max_period = 0;
        isOk = new boolean[4];
        complaintTo = new boolean[3];
        ivOtherCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        ivStudentCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        ivTeacherCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        ivAnonymouslyCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        ivOpenCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        rvStudentsList.setVisibility(View.GONE);
        cvStudentsList.setVisibility(View.GONE);
        rvTeachersList.setVisibility(View.GONE);
        cvTeachersList.setVisibility(View.GONE);
        cvOtherLabel.setVisibility(View.GONE);
        etOtherLabel.setText(null);
        etDescription.setText(null);
        activity.onBackPressed();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
            case R.id.tvCancel:
                onCancel();
                break;
            case R.id.tvNext:
                onNext();
                break;
            case R.id.ivStudentCheck:
                onStudentCheck();
                break;
            case R.id.ivTeacherCheck:
                onTeacherCheck();
                break;
            case R.id.ivOtherCheck:
                onOtherCheck();
                break;
            case R.id.ivAnonymouslyCheck:
                onAnonymouslyCheck();
                break;
            case R.id.ivOpenCheck:
                onOpenCheck();
                break;
            case R.id.ivPlay:
                playStart();
                break;
            case R.id.ivDelete:
                playStop();
                break;
            case R.id.clLabelStudent:
            case R.id.clLabelTeacher:
                onShowListClick();
                break;
            case R.id.clSearchStudent:
                break;
            case R.id.ivSearchStudent:
                break;
            case R.id.ivSearchArrowStudent:
            case R.id.ivSearchArrowTeacher:
                onHideLessonsListClick();
                break;
            case R.id.clSearchTeacher:
                break;
            case R.id.ivSearchTeacher:
                break;
        }
    }

    private void onShowListClick() {
        if (mComplaintData == null) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(F.SourceId, CHILD_ID);
            String SOAP = SoapRequest(func_GetDataForComplaint, jsonObject.toString());
            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
            Request request = new Request.Builder().url(URL).post(body).build();
            HttpClient.newCall(request).enqueue(new Callback(){
                @Override
                public void onFailure(final Call call, IOException e){
                }
                @Override
                public void onResponse(Call call, final Response response) throws IOException{
                    if(response.isSuccessful()){
                        String result = _Web.XMLToJson(response.body().string());
                        activity.runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                if(!result.equals("null")){
                                    mComplaintData = new Gson().fromJson(result, new TypeToken<ModelComplaintData>(){
                                    }.getType());
                                    StudentListAdapter.updateData(mComplaintData.getStudents());
                                    TeacherListAdapter.updateData(mComplaintData.getTeachers());
                                    clLabelStudent.setVisibility(View.GONE);
                                    clSearchStudent.setVisibility(View.VISIBLE);
                                    clLabelTeacher.setVisibility(View.GONE);
                                    clSearchTeacher.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                }
            });
        }
        else {
            if (!listIsNullOrEmpty(mComplaintData.getStudents())) {
                StudentListAdapter.updateData(mComplaintData.getStudents());
                clLabelStudent.setVisibility(View.GONE);
                clSearchStudent.setVisibility(View.VISIBLE);
            }
            if (!listIsNullOrEmpty(mComplaintData.getTeachers())) {
                TeacherListAdapter.updateData(mComplaintData.getTeachers());
                clLabelTeacher.setVisibility(View.GONE);
                clSearchTeacher.setVisibility(View.VISIBLE);
            }
        }
    }

    private void onHideLessonsListClick(){
        etSearchStudent.setText("");
        StudentListAdapter.ClearData();
        clLabelStudent.setVisibility(View.VISIBLE);
        clSearchStudent.setVisibility(View.GONE);
        etSearchTeacher.setText("");
        TeacherListAdapter.ClearData();
        clLabelTeacher.setVisibility(View.VISIBLE);
        clSearchTeacher.setVisibility(View.GONE);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return (event.getAction() == MotionEvent.ACTION_MOVE);
    }

    private void runTimer() {
        Handler handler = new Handler();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                if (isRun) {
                    handler.postDelayed(this, 1000);
                    timer++;
                }
            }
        };
        handler.removeCallbacks(task);
        handler.post(task);
    }

    private void recordStart() {
        try {
            releaseRecorder();

            File outFile = new File(fileName);
            if (outFile.exists()) {
                outFile.delete();
            }
            tvTimer.setVisibility(View.VISIBLE);

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(fileName);
            mediaRecorder.prepare();
            mediaRecorder.start();
            isRun = true;

            _timer = new Timer();
            _timer.schedule(new TimerScrollTask(), 0, 1000);
            period = 0;
            //runTimer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void recordStop() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
                isRun = false;
                timerFull = timer;
                timer = 0;
            } catch (Exception e) {}
        }
        if(_timer != null){
            _timer.cancel();
            _timer = null;
        }
        ivGallery.setVisibility(View.GONE);
        ivMicrophone.setVisibility(View.GONE);
        ivPlay.setVisibility(View.VISIBLE);
        ivDelete.setVisibility(View.VISIBLE);
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void playStart() {
        try {
            if (ivPlay.getDrawable().getConstantState() == activity.getResources().getDrawable(R.drawable.img_pouse).getConstantState()) {
                ivPlay.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_play));
            } else {
                ivPlay.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_pouse));
            }

            tvTimer.setVisibility(View.VISIBLE);
            if(mediaPlayer != null && mediaPlayer.isPlaying()){
                mediaPlayer.pause();
                _timer.cancel();
                _timer = null;
                isRun = false;
            }
            else  if(mediaPlayer != null && isPlay){
                _timer = new Timer();
                _timer.schedule(new TimerScrollTask(), 0, 1000);
                mediaPlayer.start();
                isRun = true;
            }
            else {
                releasePlayer();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(fileName);
                mediaPlayer.prepare();
                mediaPlayer.start();
                max_period = period;

                if (_timer != null) {
                    _timer.cancel();
                    _timer = null;
                } else {

                }
                period = 0;
                _timer = new Timer();
                _timer.schedule(new TimerScrollTask(), 0, 1000);
            }
            isPlay = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playStop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        if(_timer != null){
            _timer.cancel();
            _timer = null;
        }
        isPlay = false;
        ivGallery.setVisibility(View.VISIBLE);
        ivMicrophone.setVisibility(View.VISIBLE);
        tvTimer.setVisibility(View.GONE);
        ivPlay.setVisibility(View.GONE);
        ivDelete.setVisibility(View.GONE);
    }

    private void onOpenCheck() {
        ivAnonymouslyCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        ivOpenCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_checked));
        isOk[3] = true;
        isOkCheck();
    }

    private void onAnonymouslyCheck() {
        ivOpenCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        ivAnonymouslyCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_checked));
        isOk[3] = true;
        isOkCheck();
    }

    private void onOtherCheck() {
        complaintTo[0] = false;
        complaintTo[1] = false;
        complaintTo[2] = true;
        ivOtherCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_checked));
        ivStudentCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        ivTeacherCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        rvStudentsList.setVisibility(View.GONE);
        cvStudentsList.setVisibility(View.GONE);
        rvTeachersList.setVisibility(View.GONE);
        cvTeachersList.setVisibility(View.GONE);
        cvOtherLabel.setVisibility(View.VISIBLE);
    }

    private void onTeacherCheck() {
        isOk[1] = true;
        isOkCheck();
        complaintTo[0] = false;
        complaintTo[1] = true;
        complaintTo[2] = false;
        ivTeacherCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_checked));
        ivOtherCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        ivStudentCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        rvStudentsList.setVisibility(View.GONE);
        cvStudentsList.setVisibility(View.GONE);
        rvTeachersList.setVisibility(View.VISIBLE);
        cvTeachersList.setVisibility(View.VISIBLE);
        cvOtherLabel.setVisibility(View.GONE);
        etOtherLabel.setText(null);
    }

    private void onStudentCheck() {
        isOk[1] = true;
        isOkCheck();
        complaintTo[0] = true;
        complaintTo[1] = false;
        complaintTo[2] = false;
        ivStudentCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_checked));
        ivOtherCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        ivTeacherCheck.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
        rvStudentsList.setVisibility(View.VISIBLE);
        cvStudentsList.setVisibility(View.VISIBLE);
        rvTeachersList.setVisibility(View.GONE);
        cvTeachersList.setVisibility(View.GONE);
        cvOtherLabel.setVisibility(View.GONE);
        etOtherLabel.setText(null);
    }

    private void onNext() {
        //заполнение модели
        if (!listIsNullOrEmpty(childComplaintSelectedTeacherAdapter.getList()) ||
                !listIsNullOrEmpty(childComplaintSelectedStudentAdapter.getList()) ||
                ivOtherCheck.getDrawable().getConstantState() == activity.getResources().getDrawable(R.drawable.img_circle_purple_checked).getConstantState()) {
            activity.onNextFragment();
        }
    }

    private void onCancel() {
        DefaultDialog dialog = new DefaultDialog(activity);
        dialog.setCancel(activity.getResources().getColor(R.color.gray_average_dark), getString(R.string.cancel));
        dialog.setExit(activity.getResources().getColor(R.color.red), getString(R.string.exit));
        dialog.setMessage(getString(R.string.Do_you_really_get_out_of_making_a_complaint));
        dialog.setBody(getString(R.string.All_completed_information_is_not_saved));
        dialog.setOnClickListener(this);
        dialog.show();
    }

    @Override
    public void onCancelDialog() {

    }

    @Override
    public void onExitDialog() {
        onBack();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        releaseRecorder();
    }

    @Override
    public void onItemClick(ModelComplaintDataStudent m) {
        mStudent = m;
        if (childComplaintSelectedStudentAdapter.getList().contains(m)) return;
        childComplaintSelectedStudentAdapter.updateData(m);
        StudentListAdapter.ClearData();
        //tvSelectStudent.setText(m.getFirstName() + " " + m.getLastName());
        onHideLessonsListClick();
    }

    @Override
    public void onItemClick(ModelComplaintDataTeacher m) {
        mTeacher = m;
        if (childComplaintSelectedTeacherAdapter.getList().contains(m)) return;
        childComplaintSelectedTeacherAdapter.updateData(m);
        TeacherListAdapter.ClearData();
        //tvSelectTeacher.setText(m.getFirstName() + " " + m.getLastName());
        onHideLessonsListClick();
    }

    class TimerScrollTask extends TimerTask{
        public void run(){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    period++;
                    tvTimer.setText(String.valueOf(TIME_FORMAT_SHORT_SECOND.format(period * 1000)));
                    if(max_period > 0 && period >= max_period){
                        if(_timer != null){
                            _timer.cancel();
                            _timer = null;
                            ivPlay.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_play));
                        }
                    }
                }
            });
        }
    }


}
