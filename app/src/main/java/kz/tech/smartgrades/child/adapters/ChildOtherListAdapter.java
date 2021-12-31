package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ChildOtherListAdapter extends RecyclerView.Adapter<ChildOtherListAdapter.ViewHolder> {

    private ChildActivity activity;
    private ArrayList<ModelLessonsWithOutSmartGrades> lessons;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onOtherClick(ModelLessonsWithOutSmartGrades mLessonWithOutSmartGrades);
    }

    public ChildOtherListAdapter(ChildActivity activity) {
        this.activity = activity;
        this.lessons = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(ArrayList<ModelLessonsWithOutSmartGrades> lessons) {
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
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_child_other_lessons, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChildOtherListAdapter.ViewHolder holder, int position) {
        ModelLessonsWithOutSmartGrades model = lessons.get(position);

        holder.tvLessonName.setText(model.getLessonName());

        if (listIsNullOrEmpty(model.getMentors()))
            holder.relativeLayout.setVisibility(View.INVISIBLE);
        else {
            if (!stringIsNullOrEmpty(model.getMentors().get(model.getMentors().size() - 1).getAvatar())) {
                Picasso.get().load(model.getMentors().get(model.getMentors().size() - 1).getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(holder.civAvatar);
            }
            if (model.getMentors().size() == 1) {
                holder.civAvatar2.setVisibility(View.GONE);
                holder.cvMentorsCount.setVisibility(View.GONE);
                holder.tvMentorsCount.setVisibility(View.GONE);
            }
            else if (model.getMentors().size() == 2) {
                if (!stringIsNullOrEmpty(model.getMentors().get(model.getMentors().size() - 2).getAvatar())) {
                    Picasso.get().load(model.getMentors().get(model.getMentors().size() - 2).getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(holder.civAvatar2);
                }
            }
            else if (model.getMentors().size() > 2) {
                holder.civAvatar2.setVisibility(View.GONE);
                holder.cvMentorsCount.setVisibility(View.VISIBLE);
                holder.tvMentorsCount.setText(String.valueOf(model.getMentors().size()));
            }
            else {
                holder.relativeLayout.setVisibility(View.GONE);
            }
        }

        if (!stringIsNullOrEmpty(model.getLastMessage())) {
            if (model.getLastMessage().equals("5")
                    || model.getLastMessage().equals("4")
                    || model.getLastMessage().equals("3")
                    || model.getLastMessage().equals("2"))
                holder.tvLastMassage.setText(activity.getResources().getString(R.string.grade1) + " " + model.getLastMessage());
            else if (model.getLastMessage().equals("0"))
                holder.tvLastMassage.setText("Отсутствовал");
            else {
                holder.tvLastMassage.setText(model.getLastMessage());
            }
            holder.tvLastMassageTime.setText(model.getLastMessageDate());
            holder.tvLastMassageTime.setVisibility(View.VISIBLE);
        }
        else {
            holder.tvLastMassage.setText(R.string.no_messages);
            holder.tvLastMassage.setTextColor(activity.getResources().getColor(R.color.gray_default));
            holder.ivMessage.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_child_notification_gray));
        }
        if (!stringIsNullOrEmpty(model.getAverageGrade())) {
            holder.tvChildLessonRating.setText(model.getAverageGrade());
        }
        else holder.tvChildLessonRating.setText("0,0");
        if (!stringIsNullOrEmpty(model.getSchoolId()))
            holder.ivSchoolLesson.setVisibility(View.VISIBLE);
        else holder.ivSchoolLesson.setVisibility(View.INVISIBLE);
    }


    @Override
    public int getItemCount() {
//        return Integer.MAX_VALUE;
        return lessons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLessonName;
        CircleImageView civAvatar;
        CircleImageView civAvatar2;
        TextView tvLastMassage;
        TextView tvLastMassageTime;
        TextView tvChildMoney;
        TextView tvChildLessonRating;
        TextView tvMentorsCount;
        ImageView ivMessage;
        ImageView ivSchoolLesson;
        ImageView ivChildLessonRatingStar;
        RelativeLayout relativeLayout;
        CardView cvMentorsCount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvMentorsCount = itemView.findViewById(R.id.tvMentorsCount);
            tvLastMassage = itemView.findViewById(R.id.tvLastMassage);
            tvLastMassageTime = itemView.findViewById(R.id.tvLastMassageTime);
            tvChildMoney = itemView.findViewById(R.id.tvChildMoney);
            tvChildLessonRating = itemView.findViewById(R.id.tvChildLessonRating);
            ivMessage = itemView.findViewById(R.id.ivMessage);
            ivChildLessonRatingStar = itemView.findViewById(R.id.ivChildLessonRatingStar);
            ivSchoolLesson = itemView.findViewById(R.id.ivSchoolLesson);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            civAvatar2 = itemView.findViewById(R.id.civAvatar2);
            cvMentorsCount = itemView.findViewById(R.id.cvMentorsCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onOtherClick(lessons.get(position));
                }
            });
        }
    }
}
