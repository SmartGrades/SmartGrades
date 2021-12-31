package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.App;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.models.ModelTeachersListLesson;

public class LessonsTagAdapterV3 extends RecyclerView.Adapter<LessonsTagAdapterV3.ViewHolder>{

    private Context context;
    private ArrayList<ModelTeachersListLesson> LessonsList;

    public interface OnItemCLickListener{
        void onSelectLessonForClassClick(ModelTeachersListLesson m);
        void onSelectLessonForDeleteClick(ModelTeachersListLesson m);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public LessonsTagAdapterV3(){
        this.context = App.app();
        this.LessonsList = new ArrayList<>();
    }

    public ArrayList<ModelTeachersListLesson> getLessonsList() {
        return this.LessonsList;
    }

    @Override
    public int getItemCount(){
        return LessonsList.size();
    }
    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }
    public void ClearData(){
        onClear(LessonsList);
        notifyDataSetChanged();
    }
    public void UpdateData(ArrayList<ModelTeachersListLesson> ClassList){
        if(ClassList == null) return;
        onClear(this.LessonsList);
        this.LessonsList.addAll(ClassList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lessons_tag, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelTeachersListLesson m = LessonsList.get(position);
        holder.tvLessonName.setText(m.getLessonName());
        if (m.isAddedToClass()) {
            holder.tvLessonName.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvLessonName.setBackground(context.getResources().getDrawable(R.drawable.background_square_green));
        } else {
            holder.tvLessonName.setTextColor(context.getResources().getColor(R.color.gray_average_dark));
            holder.tvLessonName.setBackground(context.getResources().getDrawable(R.drawable.background_square_gray_v2));
        }
        holder.tvLessonName.setPadding((int)Convert.DpToPx(5), (int)Convert.DpToPx(2), (int)Convert.DpToPx(5), (int)Convert.DpToPx(2));
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLessonName;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if (LessonsList.get(getAdapterPosition()).isAddedToClass()) {
                        LessonsList.get(getAdapterPosition()).setAddedToClass(false);
                    } else if (!LessonsList.get(getAdapterPosition()).isAddedToClass()){
                        LessonsList.get(getAdapterPosition()).setAddedToClass(true);
                    }
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}