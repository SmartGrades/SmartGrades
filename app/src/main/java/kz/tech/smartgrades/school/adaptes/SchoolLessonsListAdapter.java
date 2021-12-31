package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelUsersList;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class SchoolLessonsListAdapter extends RecyclerView.Adapter<SchoolLessonsListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ModelSchoolLesson> mLessons;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(ModelSchoolLesson m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public SchoolLessonsListAdapter(Context context){
        this.context = context;
        this.mLessons = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolLesson m = mLessons.get(position);
        holder.tvName.setText(m.getLessonName());
    }
    @Override
    public int getItemCount(){
        return mLessons.size();
    }
    private void onClear(){
        if(mLessons.size() > 0) mLessons.clear();
    }

    public void updateData(ArrayList<ModelSchoolLesson> list){
        onClear();
        if(!listIsNullOrEmpty(list)) mLessons.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_school_lesson_list, parent, false));
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    /*if(onItemClickListener == null) return;
                    onItemClickListener.onItemClick(mLessons.get(getAdapterPosition()));*/
                }
            });
        }
    }
}
