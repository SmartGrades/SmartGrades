package kz.tech.smartgrades.sponsor.adapters;

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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.model.ModelLessonsWithSmartGrades;
import kz.tech.smartgrades.sponsor.SponsorActivity;

public class SponsorLessonListAdapter extends RecyclerView.Adapter<SponsorLessonListAdapter.ViewHolder>{

    private SponsorActivity activity;
    private ArrayList<ModelLessonsWithSmartGrades> lessons;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public interface OnItemClickListener{
        void onSmartClick(ModelLessonsWithSmartGrades mLessonsWithSmartGrades);
    }

    public interface OnItemLongClickListener{
        void onSmartLongClickListener(ModelLessonsWithSmartGrades mLessonsWithSmartGrades);
    }

    public SponsorLessonListAdapter(SponsorActivity activity){
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
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_sponsoring_lesson, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorLessonListAdapter.ViewHolder holder, int position){
        ModelLessonsWithSmartGrades m = lessons.get(position);

//        holder.civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.girl1));
//
//        if(listIsNullOrEmpty(m.getMentors())) holder.relativeLayout.setVisibility(View.INVISIBLE);
//        else {
//            if(!stringIsNullOrEmpty(m.getMentors().get(m.getMentors().size()-1).getAvatar())){
//                Picasso.get().load(m.getMentors().get(m.getMentors().size()-1).getAvatar()).fit().centerCrop().into(holder.civAvatar);
//            }
//            if(m.getMentors().size()==1){
//                holder.civAvatar2.setVisibility(View.GONE);
//                holder.cvMentorsCount.setVisibility(View.GONE);
//                holder.tvMentorsCount.setVisibility(View.GONE);
//            }
//            else if(m.getMentors().size()==2){
//                if(!stringIsNullOrEmpty(m.getMentors().get(m.getMentors().size()-2).getAvatar())){
//                    Picasso.get().load(m.getMentors().get(m.getMentors().size()-2).getAvatar()).fit().centerCrop().into(holder.civAvatar2);
//                }
//            }
//            else if (m.getMentors().size() > 2) {
//                holder.civAvatar2.setVisibility(View.GONE);
//                holder.cvMentorsCount.setVisibility(View.VISIBLE);
//                holder.tvMentorsCount.setText(String.valueOf(m.getMentors().size()));
//            }
//            else {
//                holder.relativeLayout.setVisibility(View.GONE);
//            }
//        }
//
//        holder.tvLessonName.setText("Диана Нургазинова");
//
//
//        if(!stringIsNullOrEmpty(m.getLastMessage())){
//            if (m.getLastMessage().equals("5")
//                    || m.getLastMessage().equals("4")
//                    || m.getLastMessage().equals("3")
//                    || m.getLastMessage().equals("2")
//                    || m.getLastMessage().equals("0")) {
//                holder.tvLastMassage.setText("оценка " + m.getLastMessage());
//            } else {
//                holder.tvLastMassage.setText(m.getLastMessageDate());
//            }
//            holder.tvLastMassageTime.setText(m.getLastMessageDate());
//            holder.tvLastMassageTime.setVisibility(View.VISIBLE);
//        }
//        else{
//            holder.tvLastMassage.setText("Нет сообщений.");
//            holder.tvLastMassageTime.setVisibility(View.GONE);
//        }
//
//        if(m.isSchoolLesson()) holder.ivSchoolLesson.setVisibility(View.VISIBLE);
//        else holder.ivSchoolLesson.setVisibility(View.GONE);
//
//        if(!stringIsNullOrEmpty(m.getReward())){
//            holder.tvChildMoney.setText(m.getReward());
//        }
//        else holder.tvChildMoney.setText("₸ 0");
//
//        if(!stringIsNullOrEmpty(m.getAverageGrade())){
//            holder.tvChildLessonRating.setText(m.getAverageGrade());
//        }
//        else holder.tvChildLessonRating.setText("0,0");
    }


    @Override
    public int getItemCount(){
        return lessons.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLessonName;
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
//            cvMentorsCount = itemView.findViewById(R.id.cvMentorsCount);
//            relativeLayout = itemView.findViewById(R.id.relativeLayout);
//            tvLessonName = itemView.findViewById(R.id.tvLessonName);
//            civAvatar = itemView.findViewById(R.id.civAvatar);
//            civAvatar2 = itemView.findViewById(R.id.civAvatar2);
//            tvMentorsCount = itemView.findViewById(R.id.tvOtherMentors);
//            tvLastMassage = itemView.findViewById(R.id.tvLastMassage);
//            tvLastMassageTime = itemView.findViewById(R.id.tvLastMassageTime);
//            tvChildMoney = itemView.findViewById(R.id.tvChildMoney);
//            tvChildLessonRating = itemView.findViewById(R.id.tvChildLessonRating);
//            ivSchoolLesson = itemView.findViewById(R.id.ivSchoolLesson);
//            llSmartGrades = itemView.findViewById(R.id.llSmartGrades);
//            llSmartGrades.setVisibility(View.GONE);
//            ivMessage = itemView.findViewById(R.id.ivMessage);
//            ivChildLessonRatingStar = itemView.findViewById(R.id.ivChildLessonRatingStar);

//            itemView.setOnLongClickListener(new View.OnLongClickListener(){
//                @Override
//                public boolean onLongClick(View view){
//                    int position = getAdapterPosition();
//                    onItemLongClickListener.onSmartLongClickListener(lessons.get(position));
//                    return true;
//                }
//            });
//
//            itemView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view){
//                    int position = getAdapterPosition();
//                    onItemClickListener.onSmartClick(lessons.get(position));
//                }
//            });
        }
    }
}
