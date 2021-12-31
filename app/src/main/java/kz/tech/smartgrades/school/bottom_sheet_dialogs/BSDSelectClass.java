package kz.tech.smartgrades.school.bottom_sheet_dialogs;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades._System;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolAddTeacherClassListAdapterV2;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import kz.tech.smartgrades.school.models.ModelTeacherProfileClasses;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;


public class BSDSelectClass extends BottomSheetDialog implements View.OnClickListener, SchoolAddTeacherClassListAdapterV2.OnItemCLickListener {

    private SchoolActivity activity;

    private ImageView ivAddClass;
    private RecyclerView rvClassList;

    private ArrayList<ModelSchoolClass> Classes;
    private ArrayList<ModelTeacherProfileClasses> ClassesSelected;

    private SchoolAddTeacherClassListAdapterV2 ClassListAdapter;

    private BSDSelectClass.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onCreateClassClick();
        void onAddClassClick(ModelSchoolClass m);
    }

    public void setOnItemClickListener(BSDSelectClass.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BSDSelectClass(@NonNull SchoolActivity activity, ArrayList<ModelTeacherProfileClasses> ClassesList) {
        super(activity);
        this.activity = activity;
        this.ClassesSelected = ClassesList;

        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_class_list, null, false);
        setContentView(view);

        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        bottomSheet.setLayoutParams(layoutParams);
        layoutParams.height = (int)(_System.getWindowHeight(activity) * 0.5);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        initViews(view);
    }

    private void initViews(View view) {
        ivAddClass = view.findViewById(R.id.ivAddClass);
        ivAddClass.setOnClickListener(this);
        rvClassList = view.findViewById(R.id.rvClassList);

        ClassListAdapter = new SchoolAddTeacherClassListAdapterV2();
        rvClassList = view.findViewById(R.id.rvClassList);
        rvClassList.setLayoutManager(new LinearLayoutManager(activity));
        ClassListAdapter.setOnItemCLickListener(this);
        rvClassList.setAdapter(ClassListAdapter);

        Classes = new ArrayList<>();

        boolean IsExist = false;
        if(!listIsNullOrEmpty(activity.getSchoolData().getClassessColumns())){
            Classes.clear();
            for(ModelSchoolClassesColumn _column : activity.getSchoolData().getClassessColumns())
                if(!listIsNullOrEmpty(_column.getClasses()))
                    for(ModelSchoolClass _class : _column.getClasses()){
                        if (!listIsNullOrEmpty(ClassesSelected)) {
                            for(ModelTeacherProfileClasses _SelectClass : ClassesSelected){
                                if(_SelectClass.getName().equals(_class.getName())){
                                    IsExist = true;
                                    break;
                                }
                            }
                        }
                        if(!IsExist){
                            ModelSchoolClass Class = new ModelSchoolClass();
                            Class.setId(_class.getId());
                            Class.setName(_class.getName());
                            Classes.add(Class);
                        }
                        IsExist = false;
                    }
            ClassListAdapter.UpdateData(Classes);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAddClass:
                onItemClickListener.onCreateClassClick();
                dismiss();
                break;
        }
    }

    @Override
    public void onClassClick(ModelSchoolClass m) {
        onItemClickListener.onAddClassClick(m);
        dismiss();
    }
}