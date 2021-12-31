package kz.tech.smartgrades.sponsor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;

public class SponsoredListAdapter extends RecyclerView.Adapter<SponsoredListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelLessonsWithSmartGrades> lessons;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemClickListener {
        void onSmartClick(ModelLessonsWithSmartGrades mLessonsWithSmartGrades);
    }

    public interface OnItemLongClickListener {
        void onSmartLongClickListener(ModelLessonsWithSmartGrades mLessonsWithSmartGrades);
    }

    public SponsoredListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.lessons = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void updateData(ArrayList<ModelLessonsWithSmartGrades> lessons) {
        onClear(this.lessons);
        this.lessons = lessons;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_child_income_source, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SponsoredListAdapter.ViewHolder holder, int position) {
        //ModelChildData model = childList.get(position); // error
//        Picasso.get().load(model.getAvatar()).fit().centerInside().into(holder.civAvatar);
//        holder.tvFocusedChild.setText("Имя Фамилия");
        holder.tvLessonName.setText(lessons.get(position).getLessonName());
        holder.tvLastMassage.setText(lessons.get(position).getLastMessage());
        holder.tvLastMassageTime.setText(lessons.get(position).getLastMessageDate());
        if (holder.tvLastMassageTime.getText().toString() == null || holder.tvLastMassageTime.getText().toString().isEmpty())
            holder.ivMessage.setVisibility(View.INVISIBLE);
        if (lessons.get(position).isSchoolLesson())
            holder.ivSchoolLesson.setVisibility(View.VISIBLE);
        if (lessons.get(position).getAverageGrade() == null || lessons.get(position).getAverageGrade().isEmpty())
            holder.ivChildLessonRatingStar.setVisibility(View.INVISIBLE);
        holder.tvChildMoney.setText(lessons.get(position).getReward());
        holder.tvChildLessonRating.setText(lessons.get(position).getAverageGrade());
    }



    @Override
    public int getItemCount() {
//        return Integer.MAX_VALUE;
        return lessons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLessonName;
        CircleImageView ivAvatar;
        TextView tvOtherMentors;
        TextView tvLastMassage;
        TextView tvLastMassageTime;
        TextView tvChildMoney;
        TextView tvChildLessonRating;
        ImageView ivSchoolLesson;
        LinearLayout llSmartGrades;
        ImageView ivMessage;
        ImageView ivChildLessonRatingStar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            ivAvatar = itemView.findViewById(R.id.civAvatar);
            tvOtherMentors = itemView.findViewById(R.id.tvMentorsCount);
            tvLastMassage = itemView.findViewById(R.id.tvLastMassage);
            tvLastMassageTime = itemView.findViewById(R.id.tvLastMassageTime);
            tvChildMoney = itemView.findViewById(R.id.tvChildMoney);
            tvChildLessonRating = itemView.findViewById(R.id.tvChildLessonRating);
            ivSchoolLesson = itemView.findViewById(R.id.ivSchoolLesson);
            llSmartGrades = itemView.findViewById(R.id.llSmartGrades);
            ivMessage = itemView.findViewById(R.id.ivMessage);
            ivChildLessonRatingStar = itemView.findViewById(R.id.ivChildLessonRatingStar);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    onItemLongClickListener.onSmartLongClickListener(lessons.get(position));
                    return true;
                }
            });

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onSmartClick(lessons.get(position));
                }
            });
        }
    }
}
