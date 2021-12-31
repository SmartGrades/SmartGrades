package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class SchoolFormalStudentsListAdapter extends RecyclerView.Adapter<SchoolFormalStudentsListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ModelSchoolStudent> mStudents;
    private ArrayList<ModelSchoolStudent> filterList;
    private String classId;

    private OnItemClickListener onItemClickListener;

    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(mStudents);
        else for(ModelSchoolStudent _class : mStudents)
            if((_class.getFirstName() + " " + _class.getLastName()).toLowerCase().contains(toString.toLowerCase()))
                filterList.add(_class);
        notifyDataSetChanged();
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public interface OnItemClickListener{
        void onItemClick(ModelSchoolStudent m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public SchoolFormalStudentsListAdapter(Context context){
        this.context = context;
        this.mStudents = new ArrayList<>();
        filterList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolStudent m = filterList.get(position);
        holder.tvName.setText(m.getFirstName() + " " + m.getLastName());
        ClassTagAdapter listAdapter = new ClassTagAdapter();
        holder.rvLessons.setAdapter(listAdapter);
        holder.rvLessons.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        listAdapter.UpdateData(m.getClasses());
    }
    @Override
    public int getItemCount(){
        return filterList.size();
    }
    private void onClear(){
        if(mStudents.size() > 0) mStudents.clear();
        if(filterList.size() > 0) filterList.clear();
    }

    public void updateData(ArrayList<ModelSchoolStudent> list){
        onClear();
        if(!listIsNullOrEmpty(list)) {
            mStudents.addAll(list);
            filterList.addAll(list);
            notifyItemChanged(0, list.size());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_formal_student, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        RecyclerView rvLessons;

        ViewHolder(@NonNull View itemView){
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            rvLessons = itemView.findViewById(R.id.rvLessons);

            tvName.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onItemClick(filterList.get(getAdapterPosition()));
                }
            });
        }
    }
}
