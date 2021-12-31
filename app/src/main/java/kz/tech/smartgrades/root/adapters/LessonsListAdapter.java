package kz.tech.smartgrades.root.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelLesson;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class LessonsListAdapter extends RecyclerView.Adapter<LessonsListAdapter.ViewHolder>{

    private ArrayList<ModelLesson> LessonsList;
    private ArrayList<ModelLesson> FilterList;
    private ArrayList<ModelLesson> FavoriteList;

    private OnItemCLickListener onItemCLickListener;
    public interface OnItemCLickListener{
        void onItemClick(ModelLesson m);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }

    public void filter(String toString){
        FilterList.clear();
        if(stringIsNullOrEmpty(toString)) FilterList.addAll(LessonsList);
        else for(ModelLesson _class : LessonsList)
            if(_class.getLessonName().toLowerCase().contains(toString.toLowerCase()))
                FilterList.add(_class);
        notifyDataSetChanged();
    }

    public LessonsListAdapter(){
        this.LessonsList = new ArrayList<>();
        this.FilterList = new ArrayList<>();
        this.FavoriteList = new ArrayList<>();
    }

    @Override
    public int getItemCount(){
        return FilterList.size();
    }

    private void onClear(ArrayList<?> DataList){
        if(DataList.size() > 0) DataList.clear();
    }

    public void setFavoriteLessons(ArrayList<ModelLesson> mFavoriteLessons){
        if(!listIsNullOrEmpty(mFavoriteLessons)) FavoriteList.addAll(mFavoriteLessons);
    }
    private boolean IsLessonExistInFavorite(String LessonId){
        for(ModelLesson _favorite : FavoriteList)
            if(_favorite.getLessonId().equals(LessonId)) return true;
        return false;
    }
    public void updateData(ArrayList<ModelLesson> lessonsList){
        if(lessonsList == null) return;
        onClear(LessonsList);
        onClear(FilterList);

        if(!listIsNullOrEmpty(FavoriteList)){
            this.LessonsList.addAll(FavoriteList);
            this.FilterList.addAll(FavoriteList);
            for(ModelLesson _lesson : lessonsList)
                if(!IsLessonExistInFavorite(_lesson.getLessonId())){
                    this.LessonsList.add(_lesson);
                    this.FilterList.add(_lesson);
                }
        }
        else{
            this.LessonsList.addAll(lessonsList);
            this.FilterList.addAll(lessonsList);
        }

        notifyDataSetChanged();
    }
    public void ClearData(){
        onClear(LessonsList);
        onClear(FilterList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelLesson m = FilterList.get(position);
        holder.tvName.setText(m.getLessonName());

        if(FavoriteList.contains(m)) holder.ivFavorite.setVisibility(View.VISIBLE);
        else holder.ivFavorite.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_class_list, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civAvatar;
        TextView tvName;
        ImageView ivFavorite;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(onItemCLickListener == null) return;
                    onItemCLickListener.onItemClick(FilterList.get(getAdapterPosition()));
                }
            });
        }
    }
}