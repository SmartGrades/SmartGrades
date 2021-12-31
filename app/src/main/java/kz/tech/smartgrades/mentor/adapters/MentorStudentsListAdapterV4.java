package kz.tech.smartgrades.mentor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelMentorStudentLessons;
import kz.tech.smartgrades.mentor.models.ModelMentorStudentsList;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.adaptes.LessonsTagAdapter;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class MentorStudentsListAdapterV4 extends RecyclerView.Adapter<MentorStudentsListAdapterV4.ViewHolder>{

    private Context context;
    private ArrayList<ModelMentorStudentsList> mStudents;
    private ArrayList<ModelMentorStudentsList> mFilter;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemLongClick(ModelMentorStudentsList m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void onFilter(String filterString){
        filterString = filterString.toLowerCase();
        if(!listIsNullOrEmpty(mStudents)){
            mFilter.clear();
            for(ModelMentorStudentsList _student : mStudents)
                if(_student.getFirstName().toLowerCase().contains(filterString)
                        || _student.getLastName().toLowerCase().contains(filterString))
                    mFilter.add(_student);
            notifyDataSetChanged();
        }
    }

    public MentorStudentsListAdapterV4(Context context){
        this.context = context;
        this.mStudents = new ArrayList<>();
        this.mFilter = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelMentorStudentsList m = mFilter.get(position);
        String avatar = m.getAvatar();
        if(!stringIsNullOrEmpty(avatar))
            Picasso.get().load(m.getAvatar()).fit().centerInside().into(holder.civAvatar);
        else
            holder.civAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));

        if(!stringIsNullOrEmpty(m.getFirstName()) || !stringIsNullOrEmpty(m.getLastName())){
            holder.tvLastName.setText(m.getLastName());
            holder.tvFirstName.setText(m.getFirstName());
        }
        else {
            holder.tvLastName.setText(m.getLogin());
            holder.tvFirstName.setText("");
        }

//        if(!listIsNullOrEmpty(m.getLessons())){
//            ArrayList<ModelLesson> lessons = new ArrayList<>();
//            LessonsTagAdapter adapter = new LessonsTagAdapter();
//            holder.rvGrades.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
//            holder.rvGrades.setAdapter(adapter);
//            for(ModelMentorStudentLessons _lesson : m.getLessons()){
//                ModelLesson lesson = new ModelLesson();
//                lesson.setLessonName(_lesson.getLessonName());
//                lessons.add(lesson);
//            }
//            adapter.UpdateData(lessons);
//        }
    }
    @Override
    public int getItemCount(){
        return mFilter.size();
    }
    private void onClear(){
        if(mStudents.size() > 0) mStudents.clear();
        if(mFilter.size() > 0) mFilter.clear();
    }

    public void updateData(ArrayList<ModelMentorStudentsList> list){
        onClear();
        if(!listIsNullOrEmpty(list)){
            mStudents.addAll(list);
            mFilter.addAll(list);
            notifyDataSetChanged();
        }
        else notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_students_list_v4, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civAvatar;
        TextView tvLastName, tvFirstName;
        RecyclerView rvGrades;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvLastName = itemView.findViewById(R.id.tvLastName);
            tvFirstName = itemView.findViewById(R.id.tvFirstName);
            rvGrades = itemView.findViewById(R.id.rvGrades);

            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view){
                    if(onItemClickListener != null)
                        onItemClickListener.onItemLongClick(mStudents.get(getAdapterPosition()));
                    return true;
                }
            });
        }
    }
}
