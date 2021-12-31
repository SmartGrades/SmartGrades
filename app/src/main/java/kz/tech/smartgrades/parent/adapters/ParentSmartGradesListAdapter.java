package kz.tech.smartgrades.parent.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentSmartGradesListAdapter extends RecyclerView.Adapter<ParentSmartGradesListAdapter.ViewHolder>{

    private ParentActivity activity;
    private ArrayList<ModelLessonsWithSmartGrades> lessons;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemClickListener{
        void onSmartClick(ModelLessonsWithSmartGrades mLessonsWithSmartGrades);
    }

    public interface OnItemLongClickListener{
        void onSmartLongClickListener(ModelLessonsWithSmartGrades mLessonsWithSmartGrades, View itemView);
    }

    public ParentSmartGradesListAdapter(ParentActivity activity){
        this.activity = activity;
        this.lessons = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void updateData(ArrayList<ModelLessonsWithSmartGrades> lessons){
        onClear(this.lessons);
        this.lessons = lessons;
        notifyDataSetChanged();
    }

    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_child_income_source, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ParentSmartGradesListAdapter.ViewHolder holder, int position){
        ModelLessonsWithSmartGrades m = lessons.get(position);

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

        holder.tvLessonName.setText(m.getLessonName());
        if(!stringIsNullOrEmpty(m.getLastMessage())){
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
        else{
            holder.tvLastMassage.setText(R.string.no_messages);
            holder.tvLastMassage.setTextColor(activity.getResources().getColor(R.color.gray_default));
            holder.ivMessage.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_child_notification_gray));

        }

        if(m.isSchoolLesson()) holder.ivSchoolLesson.setVisibility(View.VISIBLE);
        else holder.ivSchoolLesson.setVisibility(View.INVISIBLE);

        if(!stringIsNullOrEmpty(m.getReward())){
            holder.tvChildMoney.setText(m.getReward());
        }
        else holder.tvChildMoney.setText("₸ 0");

        if(!stringIsNullOrEmpty(m.getAverageGrade())){
            holder.tvChildLessonRating.setText(m.getAverageGrade());
        }
        else holder.tvChildLessonRating.setText("0,0");
        if (!stringIsNullOrEmpty(m.getSchoolId())) holder.ivSchoolLesson.setVisibility(View.VISIBLE);
        else holder.ivSchoolLesson.setVisibility(View.INVISIBLE);

        if(m.getGradesSettings()!=null) {
            if (m.getGradesSettings().getRewardFor2() != 0) {
                holder.tvChild2grade.setVisibility(View.VISIBLE);
                holder.tvChild2grade.setText(Integer.toString(m.getGradesSettings().getRewardFor2()));
            }
            if (m.getGradesSettings().getRewardFor4() > 0) {
                holder.tvChild4grade.setVisibility(View.VISIBLE);
                holder.tvChild4grade.setText("+" + m.getGradesSettings().getRewardFor4());
            } else if (m.getGradesSettings().getRewardFor4() < 0){
                holder.tvChild4grade.setVisibility(View.VISIBLE);
                holder.tvChild4grade.setText(m.getGradesSettings().getRewardFor4() + "");
            }
            if (m.getGradesSettings().getRewardFor5() != 0) {
                holder.tvChild5grade.setVisibility(View.VISIBLE);
                holder.tvChild5grade.setText("+" + m.getGradesSettings().getRewardFor5());
            }
            if (m.getGradesSettings().getRewardFor3() != 0) {
                holder.tvChild3grade.setVisibility(View.VISIBLE);
                holder.tvChild3grade.setText(m.getGradesSettings().getRewardFor3() + "");
            }
        }
    }


    @Override
    public int getItemCount(){
        return lessons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLessonName;
        TextView tvChild5grade;
        TextView tvChild4grade;
        TextView tvChild3grade;
        TextView tvChild2grade;
        CircleImageView civAvatar;
        CircleImageView civAvatar2;
        TextView tvMentorsCount;
        TextView tvLastMassage;
        TextView tvLastMassageTime;
        TextView tvChildMoney;
        TextView tvChildLessonRating;
        ImageView ivSchoolLesson;
        LinearLayout llSmartGrades;
        ImageView ivMessage;
        ImageView ivChildLessonRatingStar;
        RelativeLayout relativeLayout;
        CardView cvMentorsCount;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            cvMentorsCount = itemView.findViewById(R.id.cvMentorsCount);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            tvChild5grade = itemView.findViewById(R.id.tvChild5grade);
            tvChild4grade = itemView.findViewById(R.id.tvChild4grade);
            tvChild3grade = itemView.findViewById(R.id.tvChild3grade);
            tvChild2grade = itemView.findViewById(R.id.tvChild2grade);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            civAvatar2 = itemView.findViewById(R.id.civAvatar2);
            tvMentorsCount = itemView.findViewById(R.id.tvMentorsCount);
            tvLastMassage = itemView.findViewById(R.id.tvLastMassage);
            tvLastMassageTime = itemView.findViewById(R.id.tvLastMassageTime);
            tvChildMoney = itemView.findViewById(R.id.tvChildMoney);
            tvChildLessonRating = itemView.findViewById(R.id.tvChildLessonRating);
            ivSchoolLesson = itemView.findViewById(R.id.ivSchoolLesson);
            llSmartGrades = itemView.findViewById(R.id.llSmartGrades);
            ivMessage = itemView.findViewById(R.id.ivMessage);
            ivChildLessonRatingStar = itemView.findViewById(R.id.ivChildLessonRatingStar);

            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view){
                    int position = getAdapterPosition();
                    onItemLongClickListener.onSmartLongClickListener(lessons.get(position), tvLessonName);
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    onItemClickListener.onSmartClick(lessons.get(position));
                }
            });
        }
    }
}
