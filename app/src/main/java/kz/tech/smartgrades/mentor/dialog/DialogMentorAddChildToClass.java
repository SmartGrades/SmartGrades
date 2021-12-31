package kz.tech.smartgrades.mentor.dialog;

import android.app.Dialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.adapters.MentorClassListAdapterV2;
import kz.tech.smartgrades.mentor.models.ModelMentorClass;
import kz.tech.smartgrades.mentor.models.ModelMentorClassesColumn;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorGroups;
import kz.tech.smartgrades.school.models.ModelSchoolLessons;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class DialogMentorAddChildToClass extends Dialog implements View.OnClickListener, MentorClassListAdapterV2.OnItemCLickListener{

    private MentorActivity activity;
    private ModelInterForm mChild;
    private ModelMentorGroups modelMentorGroup;
    private ModelSchoolLessons modelSchoolLessons;

    private CircleImageView civAvatar;
    private TextView tvFullName;
    private Button btnSend;

    private ConstraintLayout clLabel;
    private ConstraintLayout clSearch;
    private EditText etSearch;
    private ImageView ivSearchArrow;
    private TextView tvSelectClass;
    private MentorClassListAdapterV2 classListAdapter;
    private RecyclerView rvClassList;

    private boolean isSelectLessons = false;

    private ModelMentorData modelMentorData;

    private ModelMentorClass mSelectClass;
    private ModelSchoolLessons mSelectLesson;

    private TextView tvClassListEmptyLabel;

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(ModelMentorClass mSelectClass);
    }

    public DialogMentorAddChildToClass(@NonNull MentorActivity activity, ModelMentorData mentorData, ModelInterForm mChild){
        super(activity, R.style.DefaultCustomDialog);
        this.activity = activity;
        this.mChild = mChild;
        modelSchoolLessons = new ModelSchoolLessons();
        modelMentorData = mentorData;

        this.setCanceledOnTouchOutside(true);
        View view = getLayoutInflater().inflate(R.layout.d_mentor_add_student_to_class, null, false);
        this.setContentView(view);

        ImageView ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAvatar = view.findViewById(R.id.civAvatar);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvSelectClass = view.findViewById(R.id.tvSelectClass);
        tvClassListEmptyLabel = view.findViewById(R.id.tvClassListEmptyLabel);

        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        String avatar = mChild.getChildAvatar();
        if(!stringIsNullOrEmpty(avatar))
            Picasso.get().load(avatar).fit().centerInside().into(civAvatar);
        tvFullName.setText(mChild.getChildFirstName() + " " + mChild.getChildLastName());

        classListAdapter = new MentorClassListAdapterV2(activity);
        classListAdapter.setOnItemCLickListener(this);
        rvClassList = view.findViewById(R.id.rvClassList);
        rvClassList.setLayoutManager(new LinearLayoutManager(activity));
        rvClassList.setAdapter(classListAdapter);

        etSearch = view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3){
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3){
            }
            @Override
            public void afterTextChanged(Editable arg0){
                classListAdapter.filter(arg0.toString());
            }
        });
        clLabel = view.findViewById(R.id.clLabel);
        clLabel.setOnClickListener(this);
        clSearch = view.findViewById(R.id.clSearch);
        ivSearchArrow = view.findViewById(R.id.ivSearchArrow);
        ivSearchArrow.setOnClickListener(this);

        clLabel.setVisibility(View.VISIBLE);
        clSearch.setVisibility(View.GONE);
        tvClassListEmptyLabel.setVisibility(View.GONE);
        rvClassList.setVisibility(View.GONE);

        show();
    }

    private void onTernarButton(){
        boolean isActive = false;
        if(modelMentorGroup != null) isActive = true;
        btnSend.setBackgroundResource(isActive ?
                R.drawable.btn_background_purple :
                R.drawable.btn_background_purple_alpha);
        btnSend.setEnabled(isActive);
    }

    private void onShowClassListClick(){
        if(!listIsNullOrEmpty(modelMentorData.getColumns())){
            ArrayList<ModelMentorClass> classes = new ArrayList<>();
            for(ModelMentorClassesColumn _column : modelMentorData.getColumns())
                if(stringIsNullOrEmpty(_column.getSchoolId())){
                    if(!listIsNullOrEmpty(_column.getClasses()))
                        classes.addAll(_column.getClasses());
                }

            if(!listIsNullOrEmpty(classes)){
                tvClassListEmptyLabel.setVisibility(View.GONE);
                classListAdapter.updateData(classes);
                rvClassList.setVisibility(View.VISIBLE);
            }
            else {
                tvClassListEmptyLabel.setText("Список классов пуст. Добавьте класс.");
                tvClassListEmptyLabel.setVisibility(View.VISIBLE);
            }
        }
        else{
            tvClassListEmptyLabel.setText("Список классов пуст. Добавьте класс.");
            tvClassListEmptyLabel.setVisibility(View.VISIBLE);
        }
        clLabel.setVisibility(View.GONE);
        clSearch.setVisibility(View.VISIBLE);
    }
    private void onHideLessonsListClick(){
        etSearch.setText("");
        classListAdapter.ClearData();
        clLabel.setVisibility(View.VISIBLE);
        clSearch.setVisibility(View.GONE);
        tvClassListEmptyLabel.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(ModelMentorClass m){
        mSelectClass = m;
        tvSelectClass.setText(m.getName());
        btnSend.setEnabled(true);
        btnSend.setBackgroundResource(R.drawable.btn_background_purple);
        onHideLessonsListClick();
        /*BSDSelectLessonV2 dialogSelectLesson = new BSDSelectLessonV2(activity, m);
        dialogSelectLesson.setOnItemClickListener(new BSDSelectLessonV2.OnItemClickListener(){
            @Override
            public void onItemClick(ModelSchoolLessons m){

            }
        });*/
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.btnSend:
                onSend();
                break;
            case R.id.clLabel:
                onShowClassListClick();
                break;
            case R.id.ivSearchArrow:
                onHideLessonsListClick();
                break;
        }
    }
    private void onBack(){
        this.dismiss();
    }
    private void onSend(){
        if(onItemClickListener != null)
            onItemClickListener.onItemClick(mSelectClass);
        onBack();
    }
}