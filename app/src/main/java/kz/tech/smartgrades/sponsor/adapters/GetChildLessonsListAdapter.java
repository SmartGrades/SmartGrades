package kz.tech.smartgrades.sponsor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.sponsor.models.ModelLessonsForSponsorship;


public class GetChildLessonsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<ModelLessonsForSponsorship> getChildLessons;
    private ArrayList<ModelLessonsForSponsorship> SelectList;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(ModelLessonsForSponsorship m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public GetChildLessonsListAdapter(Context context){
        this.context = context;
        this.getChildLessons = new ArrayList<>();
        this.SelectList = new ArrayList<>();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sponsor_add_child_lessons_list, parent, false);
        return new GetChildLessonsListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        ModelLessonsForSponsorship m = getChildLessons.get(position);
        ((ViewHolder) holder).tvLessonName.setText(m.getLessonName());

        double AverageGrade = m.getAverageGrade();
        ((ViewHolder) holder).tvAverageGrade.setText(String.valueOf(AverageGrade));
        if(AverageGrade > 0 && AverageGrade < 3)
            ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setBackgroundResource(R.drawable.background_oval_red);
        else if(AverageGrade >= 3 && AverageGrade < 4)
            ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setBackgroundResource(R.drawable.background_oval_yellow);
        else if(AverageGrade >= 4 && AverageGrade < 5)
            ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setBackgroundResource(R.drawable.background_oval_green);
        else if(AverageGrade == 5)
            ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setBackgroundResource(R.drawable.background_oval_purple);
        else ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setBackgroundResource(R.drawable.background_oval_gray);
//        ((GetChildLessonsListAdapter.ViewHolder) holder).tvLessonName.setText(m.getLessonName());
//        int TotalGradeCount = m.getGrades().size();
//        if (TotalGradeCount > 0){
//            ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setText(m.getAverageGrade());
//            double AverageGrade = (double) Convert.Str2Double(m.getAverageGrade());
//            if (AverageGrade < 3)
//                ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setBackgroundResource(R.drawable.img_oval_red);
//            else if (AverageGrade >= 3 && AverageGrade < 4)
//                ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setBackgroundResource(R.drawable.img_oval_yellow);
//            else if (AverageGrade >= 4 && AverageGrade < 5)
//                ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setBackgroundResource(R.drawable.img_oval_green);
//            else if (AverageGrade == 5)
//                ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setBackgroundResource(R.drawable.img_oval_purple);
//        }
//        else {
//            ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setText("-");
//            ((GetChildLessonsListAdapter.ViewHolder) holder).tvAverageGrade.setBackgroundResource(R.drawable.background_oval_gray);
//        }
    }
    @Override
    public int getItemCount(){
        return getChildLessons.size();
    }
    private void onClear(){
        if(getChildLessons.size() > 0) getChildLessons.clear();
    }
    public void updateData(ArrayList<ModelLessonsForSponsorship> arrayList2){
        onClear();
        getChildLessons.addAll(arrayList2);
        SelectList.addAll(arrayList2);
        notifyDataSetChanged();
    }
    public void selectList(ModelLessonsForSponsorship selectList){
        for(int i = 0; i < getChildLessons.size(); i++){
            if(getChildLessons.get(i).getLessonName().equals(selectList.getLessonName())){
                getChildLessons.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLessonName, tvAverageGrade;
        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            tvAverageGrade = itemView.findViewById(R.id.tvAverageGrade);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    onItemClickListener.onItemClick(getChildLessons.get(position));
                    selectList(getChildLessons.get(position));
                }
            });
        }
    }

}