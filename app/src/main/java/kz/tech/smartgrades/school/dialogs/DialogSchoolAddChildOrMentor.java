package kz.tech.smartgrades.school.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades._Firebase;
import kz.tech.smartgrades.mentor.models.ModelMentorChat;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SelectGroupForAddedAdapter;
import kz.tech.smartgrades.school.adaptes.SelectLessonsForAddedAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolGroups;
import kz.tech.smartgrades.school.models.ModelSchoolLessons;
import kz.tech.smartgrades.school.models.ModelUsersList;


public class DialogSchoolAddChildOrMentor extends Dialog implements View.OnClickListener {

    private SchoolActivity activity;
    private Dialog dialog;
    private ModelUsersList modelUserList;
    private ModelSchoolGroups modelSchoolGroups;
    private ArrayList<ModelSchoolLessons> modelSchoolLessons;

    private CircleImageView civAvatar;
    private TextView tvFullName;
    private TextView tvLogin;
    private ImageView ivIcon;
    private TextView tvSelectGroup;
    private TextView tvSelectGroupValue;
    private TextView tvSelectLessons;
    private TextView tvSelectLessonValue;
    private Button btnSend;

    private boolean isSelectLessons = false;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ModelSchoolGroups modelSchoolGroups, ArrayList<ModelSchoolLessons> modelSchoolLessons);
    }

    public DialogSchoolAddChildOrMentor(@NonNull Context context) {
        super(context);
    }

    public DialogSchoolAddChildOrMentor(@NonNull Context context, int themeResId, SchoolActivity activity, ModelUsersList modelUserList) {
        super(context, themeResId);
        this.activity = activity;
        this.modelUserList = modelUserList;
        modelSchoolLessons = new ArrayList<>();
        dialog = this;
        dialog.setCanceledOnTouchOutside(true);

        View view = getLayoutInflater().inflate(R.layout.dialog_school_add_user, null, false);
        this.setContentView(view);

        ImageView ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        civAvatar = view.findViewById(R.id.civAvatar);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvLogin = view.findViewById(R.id.tvLogin);
        ivIcon = view.findViewById(R.id.ivIcon);
        tvSelectGroup = view.findViewById(R.id.tvTitle1);
        tvSelectGroupValue = view.findViewById(R.id.tvSelectGroupValue);
        tvSelectGroupValue.setOnClickListener(this);
        tvSelectLessons = view.findViewById(R.id.tvSelectLessons);
        tvSelectLessonValue = view.findViewById(R.id.tvSelectLessonValue);
        tvSelectLessonValue.setOnClickListener(this);
        btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        Picasso.get().load(modelUserList.getAvatar()).fit().centerInside().into(civAvatar);
        tvFullName.setText(modelUserList.getFullName());
        tvLogin.setText(modelUserList.getLogin());
        if (!modelUserList.getType().equals(S.MENTOR)) {
            ivIcon.setVisibility(View.GONE);
            tvSelectLessons.setVisibility(View.GONE);
            tvSelectLessonValue.setVisibility(View.GONE);
        }
        onTernarButton();
    }

    private InputFilter filters = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //noinspection EmptyCatchBlock
            try {
                int input = Integer.parseInt(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
                if (isInRange(0, 365, input)) {
                    return null;
                } else {
                    activity.alert.onAlertDialogSolo(activity, "", "Ошибка!\n" +
                            "\n" +
                            "Количество шагов\n" +
                            "не должно превышать 365.", "OK");
                }
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    };

    protected DialogSchoolAddChildOrMentor(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void onTernarButton() {
        boolean isActive = false;
        if (modelUserList.getType().equals(S.MENTOR)) {
            if (modelSchoolGroups != null && isSelectLessons) isActive = true;
        } else {
            if (modelSchoolGroups != null) isActive = true;
        }
        btnSend.setBackgroundResource(isActive ?
                R.drawable.btn_background_purple :
                R.drawable.btn_background_purple_alpha);
        btnSend.setEnabled(isActive);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.tvSelectGroupValue:
                onSelectGroup();
                break;
            case R.id.tvSelectLessonValue:
                onSelectLesson();
                break;
            case R.id.btnSend:
                onSend();
                break;
        }
    }

    private void onBack() {
        dialog.dismiss();
    }
    private void onSelectLesson() {

    }
    private void onSelectGroup() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_select_group_for_added, null, false);
        bottomSheetDialog.setContentView(view);

        SelectGroupForAddedAdapter adapter = new SelectGroupForAddedAdapter(activity);

        RecyclerView recyclerView = view.findViewById(R.id.rvGradesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        DatabaseReference drSchoolGroups = new _Firebase().getRefSchoolGroups();
        drSchoolGroups.child(activity.login.loadUserDate(LoginKey.ID)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<ModelSchoolGroups> SchoolGroups = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String type = snapshot.getKey();
                        for (DataSnapshot snapshot2 : dataSnapshot.child(type).getChildren()) {
                            ModelSchoolGroups valuesList = snapshot2.getValue(ModelSchoolGroups.class);
                            valuesList.setType(type);
                            SchoolGroups.add(valuesList);
                        }
                    }
                    adapter.updateData(SchoolGroups);
                    adapter.selectList(modelUserList.getType());
                    recyclerView.setAdapter(adapter);
                    bottomSheetDialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter.setOnItemClickListener(new SelectGroupForAddedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ModelSchoolGroups modelGroup) {
                bottomSheetDialog.dismiss();
                modelSchoolGroups = modelGroup;
                ModelMentorChat list = new ModelMentorChat();
//                list.setUserId(modelUserList.getId());
                //list.setGroupId(modelGroup.getGroupId());
                tvSelectGroupValue.setText(modelGroup.getGroupName());
                /*drSchoolGroups.child(activity.login.loadUserDate(LoginKey.ID)).child(modelGroup.getType())
                        .child(modelGroup.getGroupId()).child(S.CHILDREN_LIST).child(modelUserList.getId()).setValue(list);*/
                onTernarButton();
            }
        });
    }

    private void onSend() {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(modelSchoolGroups, modelSchoolLessons);
        }
        onBack();
    }
}