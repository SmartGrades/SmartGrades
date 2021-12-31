package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class SchoolLessonsListAdapterV2 extends RecyclerView.Adapter<SchoolLessonsListAdapterV2.ViewHolder>{

    private Context context;
    private ArrayList<ModelSchoolLesson> mLessons;
    private ArrayList<ModelSchoolLesson> filterList;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(ModelSchoolLesson m);
    }

    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(mLessons);
        else for(ModelSchoolLesson _class : mLessons)
            if(!stringIsNullOrEmpty(_class.getLessonName()) && _class.getLessonName().toLowerCase().contains(toString.toLowerCase()))
                filterList.add(_class);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public SchoolLessonsListAdapterV2(Context context){
        this.context = context;
        this.mLessons = new ArrayList<>();
        filterList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolLesson m = filterList.get(position);
        holder.tvLessonName.setText(m.getLessonName());

        if(m.getStudentsCount() > 0) holder.tvTitleNotStudents.setVisibility(View.GONE);
        else holder.tvTitleNotStudents.setVisibility(View.VISIBLE);

        if(!listIsNullOrEmpty(m.getTeachers())){
            holder.tvTitleNotTeachers.setVisibility(View.GONE);
            if(!stringIsNullOrEmpty(m.getTeachers().get(m.getTeachers().size()-1).getAvatar()))
                Picasso.get().load(m.getTeachers().get(m.getTeachers().size()-1).getAvatar()).fit().centerCrop().into(holder.civAvatar1);
            holder.civAvatar1.setVisibility(View.VISIBLE);
            if(m.getTeachers().size() > 1){
                if(!stringIsNullOrEmpty(m.getTeachers().get(m.getTeachers().size()-2).getAvatar()))
                    Picasso.get().load(m.getTeachers().get(m.getTeachers().size()-2).getAvatar()).fit().centerCrop().into(holder.civAvatar2);
                holder.civAvatar2.setVisibility(View.VISIBLE);
            }
            else holder.civAvatar2.setVisibility(View.GONE);
            if(m.getTeachers().size() > 2){
                holder.tvTeachersCount.setText("+" + (m.getTeachers().size()-2));
                holder.tvTeachersCount.setVisibility(View.VISIBLE);
            }
            else holder.tvTeachersCount.setVisibility(View.GONE);
        }
        else{
            holder.tvTitleNotTeachers.setVisibility(View.VISIBLE);
            holder.civAvatar1.setVisibility(View.GONE);
            holder.civAvatar2.setVisibility(View.GONE);
            holder.tvTeachersCount.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount(){
        return filterList.size();
    }
    private void onClear(){
        if(mLessons.size() > 0) mLessons.clear();
        if(filterList.size() > 0) filterList.clear();
    }

    public void updateData(ArrayList<ModelSchoolLesson> list){
        onClear();
        if(!listIsNullOrEmpty(list)) {
            mLessons.addAll(list);
            filterList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_school_lesson_list_v2, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLessonName, tvTitleNotTeachers, tvTitleNotStudents, tvTeachersCount;
        CircleImageView civAvatar1, civAvatar2;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            tvTitleNotTeachers = itemView.findViewById(R.id.tvTitleNotTeachers);
            tvTitleNotStudents = itemView.findViewById(R.id.tvTitleNotStudents);
            civAvatar1 = itemView.findViewById(R.id.civAvatar1);
            civAvatar2 = itemView.findViewById(R.id.civAvatar2);
            tvTeachersCount = itemView.findViewById(R.id.tvTeachersCount);

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
