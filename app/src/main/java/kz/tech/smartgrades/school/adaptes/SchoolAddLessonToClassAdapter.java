package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelLesson;

public class SchoolAddLessonToClassAdapter extends RecyclerView.Adapter<SchoolAddLessonToClassAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ModelLesson> LessonsList;

    public interface OnItemCLickListener{
        void onSelectLessonForClassClick(ModelLesson m);
        void onSelectLessonForRemoveClick(ModelLesson m);
    }
    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public SchoolAddLessonToClassAdapter(Context context){
        this.context = context;
        this.LessonsList = new ArrayList<>();
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
    public void UpdateData(ArrayList<ModelLesson> ClassList){
        if(ClassList == null) return;
        onClear(this.LessonsList);
        this.LessonsList.addAll(ClassList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_lesson_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelLesson m = LessonsList.get(position);
        holder.tvName.setText(m.getLessonName());
        if (m.isSelected()) {
            holder.cvContainer.setCardBackgroundColor(context.getResources().getColor(R.color.green_background));
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        CardView cvContainer;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            cvContainer = itemView.findViewById(R.id.cvContainer);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    if (LessonsList.get(getAdapterPosition()).isSelected()) {
                        onItemCLickListener.onSelectLessonForRemoveClick(LessonsList.get(getAdapterPosition()));
                    } else {
                        onItemCLickListener.onSelectLessonForClassClick(LessonsList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}