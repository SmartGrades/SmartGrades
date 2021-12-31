package kz.tech.smartgrades.mentor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelLesson;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class MentorEditProfileLessonsListAdapter extends RecyclerView.Adapter<MentorEditProfileLessonsListAdapter.ViewHolder>{

    private ArrayList<ModelLesson> mLessons;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onRemoveLesson(ModelLesson m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MentorEditProfileLessonsListAdapter(){
        this.mLessons = new ArrayList<>();
    }

    private void onClear(){
        if(mLessons.size() > 0) mLessons.clear();
    }
    public void updateData(ArrayList<ModelLesson> list){
        onClear();
        if(!listIsNullOrEmpty(list)) mLessons.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelLesson m = mLessons.get(position);
        holder.tvLessonName.setText(m.getLessonName());
    }

    @Override
    public int getItemCount(){
        return mLessons.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor_profile_edit_lessons_tag, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivDelete;
        TextView tvLessonName;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);

            ivDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onRemoveLesson(mLessons.get(getAdapterPosition()));
                }
            });
        }
    }
}