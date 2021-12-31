package kz.tech.smartgrades.parent.adapters;

import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.adaptes.SchoolLessonsListAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolData;

public class ParentSchoolListAdapter extends RecyclerView.Adapter<ParentSchoolListAdapter.ViewHolder> {

    private ParentActivity activity;
    private String PARENT_ID;
    private ArrayList<ModelSchoolData> schoolList;

    private OnItemCLickListener onItemCLickListener;
    public interface OnItemCLickListener {
        void onSchoolClick(ModelSchoolData m);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public ParentSchoolListAdapter(ParentActivity activity) {
        this.activity = activity;
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
        this.schoolList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return schoolList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelSchoolData> schoolList) {
        if (schoolList==null) return;
        this.schoolList = schoolList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolData m = schoolList.get(position);
        holder.tvSchool.setText(m.getName() + "\n" + m.getAddress());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSchool;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSchool = itemView.findViewById(R.id.tvSchool);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onSchoolClick(schoolList.get(getAdapterPosition()));
                }
            });
        }
    }
}