package kz.tech.smartgrades.school.dialogs;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;

public class ADSchoolMoveStudentOptions extends AlertDialog implements View.OnClickListener{

    private AlertDialog dialog;
    private SchoolActivity activity;
    private ModelSchoolStudent mStudent;
    private ModelSchoolClass mSchoolClass;

    private TextView tvTitle;
    private TextView tvClass;
    private TextView tvMove;
    private TextView tvDuplicate;
    private TextView tvCancel;
    private CardView cvMove;
    private CardView cvDuplicate;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onDuplicateClick();
        void onMoveClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public ADSchoolMoveStudentOptions(SchoolActivity activity, ModelSchoolStudent mStudent, ModelSchoolClass mSchoolClass){
        super(activity);
        this.activity = activity;
        this.mStudent = mStudent;
        this.mSchoolClass = mSchoolClass;

        View view = getLayoutInflater().inflate(R.layout.ad_school_move_student_warning, null);
        Builder builder = new Builder(activity).setView(view);

        initViews(view);

        dialog = this;
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void initViews(View view){
        tvTitle = view.findViewById(R.id.tvTitle);
        tvClass = view.findViewById(R.id.tvClass);
        tvMove = view.findViewById(R.id.tvMove);
        tvDuplicate = view.findViewById(R.id.tvDuplicate);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(this);
        cvMove = view.findViewById(R.id.cvMove);
        cvMove.setOnClickListener(this);
        cvDuplicate = view.findViewById(R.id.cvDuplicate);
        cvDuplicate.setOnClickListener(this);

        setTargetInfo();
    }

    private void setTargetInfo() {
        tvTitle.setText(mStudent.getFirstName() + " " + mStudent.getLastName() + "\n" + activity.getResources().getString(R.string.already_in_class));
        tvMove.setText(activity.getResources().getString(R.string.Move_this_student_to) + " " + mSchoolClass.getName());
        //tvDuplicate.setText("Оставить его в" + " " + );
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.tvCancel:
                onCancelClick();
                break;
            case R.id.cvMove:
                onMoveClick();
                break;
            case R.id.cvDuplicate:
                onDuplicateClick();
                break;
        }
    }

    private void onDuplicateClick() {
        dialog.dismiss();
        if(onItemClickListener == null) return;
        onItemClickListener.onDuplicateClick();
    }

    private void onMoveClick() {
        dialog.dismiss();
        if(onItemClickListener == null) return;
        onItemClickListener.onMoveClick();
    }

    private void onCancelClick(){
        dialog.dismiss();
    }
}