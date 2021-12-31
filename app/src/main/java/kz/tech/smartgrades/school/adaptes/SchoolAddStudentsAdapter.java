package kz.tech.smartgrades.school.adaptes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class SchoolAddStudentsAdapter extends RecyclerView.Adapter<SchoolAddStudentsAdapter.ViewHolder>{

    private SchoolActivity activity;
    private ArrayList<ModelSchoolStudent> StudentsList;
    private int ChekedPosition = -1;

    public interface OnItemCLickListener{
        void onSelectChildForUnite(ModelSchoolStudent index);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public SchoolAddStudentsAdapter(SchoolActivity activity){
        this.activity = activity;
        this.StudentsList = new ArrayList<>();
    }

    @Override
    public int getItemCount(){
        return StudentsList.size();
    }
    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }
    public void ClearData(){
        onClear(StudentsList);
        notifyDataSetChanged();
    }
    public void UpdateData(ArrayList<ModelSchoolStudent> ClassList){
        if(ClassList == null) return;
        onClear(this.StudentsList);
        this.StudentsList.addAll(ClassList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_add_student_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolStudent m = StudentsList.get(position);
        if(!stringIsNullOrEmpty(m.getAvatar()))
            Picasso.get().load(m.getAvatar()).fit().centerCrop().into(holder.civAvatar);
        holder.tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        if(ChekedPosition == position)
            holder.ivChecked.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_checked));
        else holder.ivChecked.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_unchecked));
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civAvatar;
        TextView tvFullName;
        ImageView ivChecked;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            ivChecked = itemView.findViewById(R.id.ivChecked);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    ChekedPosition = getAdapterPosition();
                    onItemCLickListener.onSelectChildForUnite(StudentsList.get(getAdapterPosition()));
                    ivChecked.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_circle_purple_checked));
                }
            });
        }
    }
}