package kz.tech.smartgrades.mentor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;


public class MentorLessonsListAdapter extends RecyclerView.Adapter<MentorLessonsListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelSchoolLesson> mLessons;
    private ArrayList<ModelSchoolLesson> mFilter;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ModelSchoolLesson m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onFilter(String filterString) {
        filterString = filterString.toLowerCase();
        if (!listIsNullOrEmpty(mLessons)) {
            mFilter.clear();
            for (ModelSchoolLesson _lesson : mLessons)
                if (_lesson.getLessonName().toLowerCase().contains(filterString))
                    mFilter.add(_lesson);
            notifyDataSetChanged();
        }
    }

    public MentorLessonsListAdapter(Context context) {
        this.context = context;
        this.mLessons = new ArrayList<>();
        this.mFilter = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelSchoolLesson m = mFilter.get(position);
        holder.tvLessonName.setText(m.getLessonName());

        if (m.getStudentsCount() > 0) holder.tvTitleNotStudents.setVisibility(View.GONE);
        else holder.tvTitleNotStudents.setVisibility(View.VISIBLE);
    }
    @Override
    public int getItemCount() {
        return mFilter.size();
    }
    private void onClear() {
        if (mFilter.size() > 0) mFilter.clear();
    }

    public void updateData(ArrayList<ModelSchoolLesson> list) {
        onClear();
        if (!listIsNullOrEmpty(list)) {
            mLessons.addAll(list);
            mFilter.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_lesson_list, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLessonName, tvTitleNotStudents;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            tvTitleNotStudents = itemView.findViewById(R.id.tvTitleNotStudents);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(mFilter.get(getAdapterPosition()));
                }
            });
        }
    }
}
