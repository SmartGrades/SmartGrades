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
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class SchoolTeachersListAdapterV2 extends RecyclerView.Adapter<SchoolTeachersListAdapterV2.ViewHolder>{

    private Context context;
    private ArrayList<ModelSchoolTeacher> mTeachers;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(ModelSchoolTeacher m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public SchoolTeachersListAdapterV2(Context context){
        this.context = context;
        this.mTeachers = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolTeacher m = mTeachers.get(position);
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(m.getAvatar()).fit().centerInside().into(holder.civAvatar);
        holder.tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        holder.ivSponsorStatus.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount(){
        return mTeachers.size();
    }
    private void onClear(){
        if(mTeachers.size() > 0) mTeachers.clear();
    }

    public void updateData(ArrayList<ModelSchoolTeacher> list){
        onClear();
        if(!listIsNullOrEmpty(list)) mTeachers.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_school_teacher_list_v2, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civAvatar;
        TextView tvFullName;
        RecyclerView rvItemsList;
        ImageView ivSponsorStatus;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            rvItemsList = itemView.findViewById(R.id.rvItemsList);
            ivSponsorStatus = itemView.findViewById(R.id.ivSponsorStatus);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onItemClick(mTeachers.get(getAdapterPosition()));
                }
            });
        }
    }
}
