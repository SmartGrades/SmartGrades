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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.onEditAccess;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class SchoolStudentsListAdapterV2 extends RecyclerView.Adapter<SchoolStudentsListAdapterV2.ViewHolder>{

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


    public SchoolStudentsListAdapterV2(Context context){
        this.context = context;
        this.mStudents = new ArrayList<>();
        filterList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolStudent m = filterList.get(position);
        holder.tvClasses.setText("");
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        else holder.civAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));
        holder.tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        holder.ivSponsorStatus.setVisibility(View.GONE);
        holder.ivMenu.setVisibility(View.GONE);
        if(!listIsNullOrEmpty(m.getClasses())) {
            String text = "";
            holder.tvClasses.setTextColor(Color.BLACK);
            for (int i = 0; i < m.getClasses().size(); i++){
                text += m.getClasses().get(i).getName();
                if (i < m.getClasses().size() - 1) text += ", ";
                if (!stringIsNullOrEmpty(classId) && m.getClasses().get(i).getId().equals(classId)) {
                    holder.clContainer.setBackgroundColor(context.getResources().getColor(R.color.gray_disabled_v2));
                    holder.clContainer.setClickable(false);
                    break;
                }
            }
            holder.tvClasses.setText(text);
        }
        else {
            holder.tvClasses.setText("Не состоит в классе");
            holder.tvClasses.setTextColor(Color.RED);
        }
        if (stringIsNullOrEmpty(m.getUserId()))
            holder.tvFormalLabel.setVisibility(View.VISIBLE);
        else holder.tvFormalLabel.setVisibility(View.GONE);
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
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_school_students_list_v2, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout clContainer;
        CircleImageView civAvatar;
        TextView tvFullName, tvClasses, tvFormalLabel;
        RecyclerView rvItemsList;
        ImageView ivSponsorStatus, ivMenu;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            clContainer = itemView.findViewById(R.id.clContainer);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvClasses = itemView.findViewById(R.id.tvClasses);
            tvFormalLabel = itemView.findViewById(R.id.tvFormalLabel);
            ivSponsorStatus = itemView.findViewById(R.id.ivSponsorStatus);
            ivMenu = itemView.findViewById(R.id.ivMenu);

            clContainer.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onItemClick(filterList.get(getAdapterPosition()));
                }
            });
        }
    }
}
