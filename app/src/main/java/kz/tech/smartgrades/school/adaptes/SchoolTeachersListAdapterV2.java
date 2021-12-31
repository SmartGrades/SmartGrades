package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class SchoolTeachersListAdapterV2 extends RecyclerView.Adapter<SchoolTeachersListAdapterV2.ViewHolder>{

    private Context context;
    private ArrayList<ModelSchoolTeacher> mTeachers;
    private ArrayList<ModelSchoolTeacher> filterList;
    private String classId;

    private OnItemClickListener onItemClickListener;

    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(mTeachers);
        else for(ModelSchoolTeacher _class : mTeachers)
            if((_class.getFirstName() + " " + _class.getLastName()).toLowerCase().contains(toString.toLowerCase()))
                filterList.add(_class);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(ModelSchoolTeacher m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }


    public SchoolTeachersListAdapterV2(Context context){
        this.context = context;
        this.mTeachers = new ArrayList<>();
        filterList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolTeacher m = filterList.get(position);
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        holder.tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        holder.ivSponsorStatus.setVisibility(View.GONE);

        LessonsTagAdapter adapter = new LessonsTagAdapter();
        holder.rvLessonsList.setAdapter(adapter);
        holder.rvLessonsList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        adapter.UpdateData(m.getLessons());

        if(!listIsNullOrEmpty(m.getClasses())) {
            for (int i = 0; i < m.getClasses().size(); i++){
                if (!stringIsNullOrEmpty(classId) && m.getClasses().get(i).getId().equals(classId)) {
                    holder.container.setBackgroundColor(context.getResources().getColor(R.color.gray_disabled_v2));
                    holder.container.setClickable(false);
                    break;
                }
            }
        }
    }
    @Override
    public int getItemCount(){
        return filterList.size();
    }
    private void onClear(){
        if(mTeachers.size() > 0) mTeachers.clear();
        if(filterList.size() > 0) filterList.clear();
    }

    public void updateData(ArrayList<ModelSchoolTeacher> list){
        onClear();
        if(!listIsNullOrEmpty(list)) {
            mTeachers.addAll(list);
            filterList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_school_teacher_list_v2, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout container;
        CircleImageView civAvatar;
        TextView tvFullName;
        RecyclerView rvLessonsList;
        ImageView ivSponsorStatus;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            container = itemView.findViewById(R.id.container);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            rvLessonsList = itemView.findViewById(R.id.rvLessonsList);
            ivSponsorStatus = itemView.findViewById(R.id.ivSponsorStatus);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onItemClick(filterList.get(getAdapterPosition()));
                }
            });
        }
    }
}
