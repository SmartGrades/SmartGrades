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
import kz.tech.smartgrades.school.models.ModelSchoolStudent;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class SchoolStudentsListAdapterV2 extends RecyclerView.Adapter<SchoolStudentsListAdapterV2.ViewHolder>{

    private Context context;
    private ArrayList<ModelSchoolStudent> mStudents;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(ModelSchoolStudent m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public SchoolStudentsListAdapterV2(Context context){
        this.context = context;
        this.mStudents = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolStudent m = mStudents.get(position);
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(m.getAvatar()).fit().centerInside().into(holder.civAvatar);
        else holder.civAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));
        holder.tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        holder.ivSponsorStatus.setVisibility(View.GONE);
        holder.ivMenu.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount(){
        return mStudents.size();
    }
    private void onClear(){
        if(mStudents.size() > 0) mStudents.clear();
    }

    public void updateData(ArrayList<ModelSchoolStudent> list){
        onClear();
        if(!listIsNullOrEmpty(list)) {
            mStudents.addAll(list);
            notifyItemChanged(0, list.size());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_school_students_list_v2, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civAvatar;
        TextView tvFullName;
        RecyclerView rvItemsList;
        ImageView ivSponsorStatus, ivMenu;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            rvItemsList = itemView.findViewById(R.id.rvItemsList);
            ivSponsorStatus = itemView.findViewById(R.id.ivSponsorStatus);
            ivMenu = itemView.findViewById(R.id.ivMenu);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onItemClick(mStudents.get(getAdapterPosition()));
                }
            });
        }
    }
}
