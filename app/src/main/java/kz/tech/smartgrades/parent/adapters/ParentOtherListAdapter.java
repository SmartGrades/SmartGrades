package kz.tech.smartgrades.parent.adapters;

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
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelLessonsWithOutSmartGrades;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentOtherListAdapter extends RecyclerView.Adapter<ParentOtherListAdapter.ViewHolder> {

    private ParentActivity activity;
    private ArrayList<ModelLessonsWithOutSmartGrades> lessons;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemClickListener {
        void onOtherClick(ModelLessonsWithOutSmartGrades mLessonWithOutSmartGrades);
    }

    public interface OnItemLongClickListener{
        void onOtherLongClickListener(ModelLessonsWithOutSmartGrades mLessonsWithOutSmartGrades, View itemView);
    }

    public ParentOtherListAdapter(ParentActivity activity) {
        this.activity = activity;
        this.lessons = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
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
    public void onBindViewHolder(@NonNull ParentOtherListAdapter.ViewHolder holder, int position) {
        ModelLessonsWithOutSmartGrades m = lessons.get(position);

        holder.tvLessonName.setText(m.getLessonName());

        if(listIsNullOrEmpty(m.getMentors())) holder.relativeLayout.setVisibility(View.INVISIBLE);
        else {
            if(!stringIsNullOrEmpty(m.getMentors().get(m.getMentors().size()-1).getAvatar())){
                Picasso.get().load(m.getMentors().get(m.getMentors().size()-1).getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(holder.civAvatar);
            }
            if(m.getMentors().size()==1){
                holder.civAvatar2.setVisibility(View.GONE);
                holder.cvMentorsCount.setVisibility(View.GONE);
                holder.tvMentorsCount.setVisibility(View.GONE);
            }
            else if(m.getMentors().size()==2){
                if(!stringIsNullOrEmpty(m.getMentors().get(m.getMentors().size()-2).getAvatar())){
                    Picasso.get().load(m.getMentors().get(m.getMentors().size()-2).getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(holder.civAvatar2);
                }
            }
            else if (m.getMentors().size() > 2) {
                holder.civAvatar2.setVisibility(View.GONE);
                holder.cvMentorsCount.setVisibility(View.VISIBLE);
                holder.tvMentorsCount.setText(String.valueOf(m.getMentors().size()));
            }
            else {
                holder.relativeLayout.setVisibility(View.GONE);
            }
        }

        if(!stringIsNullOrEmpty(m.getLastMessageDate())){
            if (m.getLastMessage().equals("5")
                    || m.getLastMessage().equals("4")
                    || m.getLastMessage().equals("3")
                    || m.getLastMessage().equals("2")
                    || m.getLastMessage().equals("0")) {
                holder.tvLastMassage.setText(activity.getResources().getString(R.string.grade1) + " " + m.getLastMessage());
            } else {
                holder.tvLastMassage.setText(m.getLastMessage());
            }
            holder.tvLastMassageTime.setText(m.getLastMessageDate());
            holder.tvLastMassageTime.setVisibility(View.VISIBLE);
        }
        else {
            holder.tvLastMassage.setText(R.string.no_messages);
            holder.tvLastMassage.setTextColor(activity.getResources().getColor(R.color.gray_default));
            holder.ivMessage.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_child_notification_gray));

        }
        if (!stringIsNullOrEmpty(m.getAverageGrade())) {
            holder.tvChildLessonRating.setText(m.getAverageGrade());
        } else holder.tvChildLessonRating.setText("0,0");
        if (!stringIsNullOrEmpty(m.getSchoolId())) holder.ivSchoolLesson.setVisibility(View.VISIBLE);
        else holder.ivSchoolLesson.setVisibility(View.INVISIBLE);
    }



    @Override
    public int getItemCount() {
//        return Integer.MAX_VALUE;
        return lessons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvMentorsCount;
        TextView tvLessonName;
        RelativeLayout relativeLayout;
        CircleImageView civAvatar;
        CircleImageView civAvatar2;
        TextView tvLastMassage;
        TextView tvLastMassageTime;
        TextView tvChildMoney;
        TextView tvChildLessonRating;
        ImageView ivMessage;
        ImageView ivChildLessonRatingStar;
        TextView tvMentorsCount;
        ImageView ivSchoolLesson;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvMentorsCount = itemView.findViewById(R.id.cvMentorsCount);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            tvMentorsCount = itemView.findViewById(R.id.tvMentorsCount);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            civAvatar2 = itemView.findViewById(R.id.civAvatar2);
            tvLastMassage = itemView.findViewById(R.id.tvLastMassage);
            tvLastMassageTime = itemView.findViewById(R.id.tvLastMassageTime);
            tvChildMoney = itemView.findViewById(R.id.tvChildMoney);
            tvChildLessonRating = itemView.findViewById(R.id.tvChildLessonRating);
            ivMessage = itemView.findViewById(R.id.ivMessage);
            ivSchoolLesson = itemView.findViewById(R.id.ivSchoolLesson);
            ivChildLessonRatingStar = itemView.findViewById(R.id.ivChildLessonRatingStar);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    onItemClickListener.onOtherClick(lessons.get(position));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view){
                    int position = getAdapterPosition();
                    onItemLongClickListener.onOtherLongClickListener(lessons.get(position), tvLessonName);
                    return true;
                }
            });
        }
    }
}
