package kz.tech.smartgrades.sponsor.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.SponsorActivity;
import kz.tech.smartgrades.sponsor.adapters.GetChildLessonsListAdapter;
import kz.tech.smartgrades.sponsor.models.ModelChildrenListForSponsorship;
import kz.tech.smartgrades.sponsor.models.ModelLessonsForSponsorship;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class SponsorChildInterFormDialog extends Dialog implements View.OnClickListener {

    private SponsorActivity activity;
    private String SPONSOR_ID;
    private ModelChildrenListForSponsorship mChild;

    private TextView tvChildLogin, tvAverageGrade, tvGradeCount, tvAmountOfPayments;
    private CircleImageView civChildAvatar, civParentAvatar;

    private ImageView ivBack;
    private Button btnSaveEdit;

    private CardView[] cvLesson = new CardView[4];
    private TextView[] tvSelectLessonLabel = new TextView[4];
    private TextView[] tvSelectLessonAvr = new TextView[4];
    private ImageView[] ivSelectLessonImg = new ImageView[4];

    private GetChildLessonsListAdapter LessonsListAdapter;
    private ArrayList<ModelLessonsForSponsorship> LessonSelectedList;
    private ModelLessonsForSponsorship[] LessonSelected = new ModelLessonsForSponsorship[4];
    private ModelInterForm mInterForm;

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(ModelInterForm modelChats);
    }

    public SponsorChildInterFormDialog(@NonNull SponsorActivity activity, ModelChildrenListForSponsorship mChild) {
        super(activity, R.style.CustomDialog2);
        this.activity = activity;
        this.mChild = mChild;

        this.setCanceledOnTouchOutside(true);
        View view = getLayoutInflater().inflate(R.layout.fragment_sponsor_edit_parent_inter_form, null, false);
        this.setContentView(view);

        initViews(view);
        setChildData();
    }
    private void initViews(View view){
        SPONSOR_ID = activity.login.loadUserDate(LoginKey.ID);

        tvChildLogin = view.findViewById(R.id.tvChildLogin);
        tvAverageGrade = view.findViewById(R.id.tvAverageGrade);
        tvGradeCount = view.findViewById(R.id.tvGradeCount);
        tvAmountOfPayments = view.findViewById(R.id.tvAmountOfPayments);
        civChildAvatar = view.findViewById(R.id.civMentorAvatar);
        civParentAvatar = view.findViewById(R.id.civParentAvatar);

        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        btnSaveEdit = view.findViewById(R.id.btnSaveEdit);
        btnSaveEdit.setOnClickListener(this);

        cvLesson[0] = view.findViewById(R.id.cvLesson1);
        cvLesson[0].setOnClickListener(this);
        cvLesson[1] = view.findViewById(R.id.cvLesson2);
        cvLesson[1].setOnClickListener(this);
        cvLesson[2] = view.findViewById(R.id.cvLesson3);
        cvLesson[2].setOnClickListener(this);
        cvLesson[3] = view.findViewById(R.id.cvLesson4);
        cvLesson[3].setOnClickListener(this);

        tvSelectLessonLabel[0] = view.findViewById(R.id.tvSelectLesson1Label);
        tvSelectLessonAvr[0] = view.findViewById(R.id.tvSelectLesson1Avr);
        ivSelectLessonImg[0] = view.findViewById(R.id.ivSelectLesson1Img);
        ivSelectLessonImg[0].setOnClickListener(this);
        tvSelectLessonLabel[1] = view.findViewById(R.id.tvSelectLesson2Label);
        tvSelectLessonAvr[1] = view.findViewById(R.id.tvSelectLesson2Avr);
        ivSelectLessonImg[1] = view.findViewById(R.id.ivSelectLesson2Img);
        ivSelectLessonImg[1].setOnClickListener(this);
        tvSelectLessonLabel[2] = view.findViewById(R.id.tvSelectLesson3Label);
        tvSelectLessonAvr[2] = view.findViewById(R.id.tvSelectLesson3Avr);
        ivSelectLessonImg[2] = view.findViewById(R.id.ivSelectLesson3Img);
        ivSelectLessonImg[2].setOnClickListener(this);
        tvSelectLessonLabel[3] = view.findViewById(R.id.tvSelectLesson4Label);
        tvSelectLessonAvr[3] = view.findViewById(R.id.tvSelectLesson4Avr);
        ivSelectLessonImg[3] = view.findViewById(R.id.ivSelectLesson4Img);
        ivSelectLessonImg[3].setOnClickListener(this);

        LessonSelectedList = new ArrayList<>();
        LessonsListAdapter = new GetChildLessonsListAdapter(activity);
    }

    private void setChildData() {
        String avatar = mChild.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civChildAvatar);
        avatar = mChild.getParentAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civParentAvatar);
        String name = mChild.getFirstName() + " " + mChild.getLastName();
        if (!stringIsNullOrEmpty(name))
            tvChildLogin.setText(name);
        else tvChildLogin.setText(mChild.getLogin());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.btnSaveEdit:
                onBtnSaveClick();
                break;
            case R.id.cvLesson1:
                LessonSelectClick(0);
                break;
            case R.id.cvLesson2:
                LessonSelectClick(1);
                break;
            case R.id.cvLesson3:
                LessonSelectClick(2);
                break;
            case R.id.cvLesson4:
                LessonSelectClick(3);
                break;
            case R.id.ivSelectLesson1Img:
                onDeleteLessonClick(0);
                break;
            case R.id.ivSelectLesson2Img:
                onDeleteLessonClick(1);
                break;
            case R.id.ivSelectLesson3Img:
                onDeleteLessonClick(2);
                break;
            case R.id.ivSelectLesson4Img:
                onDeleteLessonClick(3);
                break;
        }
    }
    private void onBack() {
        this.dismiss();
    }
    private void LessonSelectClick(int number) {
        BottomSheetDialog bottomDialog = new BottomSheetDialog(activity);
        View viewDialog = getLayoutInflater().inflate(R.layout.dialog_add_child_select_lesson, null, false);
        bottomDialog.setContentView(viewDialog);
        bottomDialog.show();

        TextView tvListCleanLabel = viewDialog.findViewById(R.id.tvListCleanLabel);

        if(!listIsNullOrEmpty(mChild.getLessons())){
            tvListCleanLabel.setVisibility(View.GONE);
            ArrayList<ModelLessonsForSponsorship> newList = new ArrayList<>();
            for(ModelLessonsForSponsorship m : mChild.getLessons()) if(!LessonSelectedList.contains(m)) newList.add(m);
            LessonsListAdapter.updateData(newList);
            RecyclerView recyclerView = viewDialog.findViewById(R.id.rvGradesList);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(LessonsListAdapter);
            LessonsListAdapter.setOnItemClickListener(m -> {
                bottomDialog.dismiss();
                LessonSelected[number] = m;
                LessonSelectedList.add(m);
                tvSelectLessonLabel[number].setText(m.getLessonName());
                ivSelectLessonImg[number].setImageDrawable(activity.getResources().getDrawable(R.drawable.img_delete_grey));
                double AverageGrade = m.getAverageGrade();
                tvSelectLessonAvr[number].setText(String.valueOf(AverageGrade));
                if(AverageGrade > 0 && AverageGrade < 3)
                    tvSelectLessonAvr[number].setBackgroundResource(R.drawable.background_oval_red);
                else if(AverageGrade >= 3 && AverageGrade < 4)
                    tvSelectLessonAvr[number].setBackgroundResource(R.drawable.background_oval_yellow);
                else if(AverageGrade >= 4 && AverageGrade < 5)
                    tvSelectLessonAvr[number].setBackgroundResource(R.drawable.background_oval_green);
                else if(AverageGrade == 5)
                    tvSelectLessonAvr[number].setBackgroundResource(R.drawable.background_oval_purple);
                else tvSelectLessonAvr[number].setBackgroundResource(R.drawable.background_oval_gray);
                tvSelectLessonAvr[number].setVisibility(View.VISIBLE);
                onCanculate();
            });
        }
        else{
            tvListCleanLabel.setVisibility(View.VISIBLE);
            tvListCleanLabel.setText("Список школьных предметов пуст");
        }
    }
    @SuppressLint("DefaultLocale")
    private void onCanculate(){
        if(!listIsNullOrEmpty(LessonSelectedList)){
            double avr = 0;
            for(ModelLessonsForSponsorship m : LessonSelectedList) avr += m.getAverageGrade();
            if (LessonSelectedList.size() >= 2 && avr == 0) avr = 3.5;
            else avr = avr / LessonSelectedList.size();
            tvAverageGrade.setText(String.format("%.1f", avr));
            tvGradeCount.setText(String.valueOf(LessonSelectedList.size() * 2));

        }
        else {
            tvAverageGrade.setText("0.0");
            tvGradeCount.setText("0");
        }
        if(LessonSelectedList.size() >= 2){
            btnSaveEdit.setEnabled(true);
            btnSaveEdit.setBackgroundResource(R.drawable.btn_background_purple);
        }
        else {
            btnSaveEdit.setEnabled(false);
            btnSaveEdit.setBackgroundResource(R.drawable.btn_background_purple_alpha);
        }
    }
    private void onDeleteLessonClick(int index){
        if(LessonSelected[index] == null) LessonSelectClick(index);
        else {
            LessonSelectedList.remove(LessonSelected[index]);
            LessonSelected[index] = null;
            ivSelectLessonImg[index].setImageDrawable(activity.getResources().getDrawable(R.drawable.img_arrow_down));
            tvSelectLessonLabel[index].setText("");
            tvSelectLessonAvr[index].setText("0.0");
            tvSelectLessonAvr[index].setVisibility(View.GONE);
            onCanculate();
        }
    }
    private void onBtnSaveClick(){
        if(onItemClickListener==null) return;
        mInterForm = new ModelInterForm();
        mInterForm.setSourceId(SPONSOR_ID);
        mInterForm.setTargetId(mChild.getParentId());
        mInterForm.setChildId(mChild.getUserId());
        mInterForm.setAverageGrade(tvAverageGrade.getText().toString());
        mInterForm.setCountGrade(tvGradeCount.getText().toString());
        mInterForm.setReward(tvAmountOfPayments.getText().toString().replace(" ",""));
        mInterForm.setTotalReward(Integer.toString((Integer.parseInt(tvAmountOfPayments.getText().toString().replace(" ","")) * 4)));
        ArrayList<ModelLessonsForSponsorship> lessonList = new ArrayList<>();
        ModelLessonsForSponsorship lesson1 = new ModelLessonsForSponsorship();
        ModelLessonsForSponsorship lesson2 = new ModelLessonsForSponsorship();
        ModelLessonsForSponsorship lesson3 = new ModelLessonsForSponsorship();
        ModelLessonsForSponsorship lesson4 = new ModelLessonsForSponsorship();
        if(LessonSelected[0]!=null) {
            lesson1.setChildLessonId(LessonSelected[0].getChildLessonId());
            lessonList.add(lesson1);
        }
        if(LessonSelected[1]!=null) {
            lesson2.setChildLessonId(LessonSelected[1].getChildLessonId());
            lessonList.add(lesson2);
        }
        if(LessonSelected[2]!=null) {
            lesson3.setChildLessonId(LessonSelected[2].getChildLessonId());
            lessonList.add(lesson3);
        }
        if(LessonSelected[3]!=null) {
            lesson4.setChildLessonId(LessonSelected[3].getChildLessonId());
            lessonList.add(lesson4);
        }
        mInterForm.setLessons(lessonList);
        onItemClickListener.onItemClick(mInterForm);
    }
}