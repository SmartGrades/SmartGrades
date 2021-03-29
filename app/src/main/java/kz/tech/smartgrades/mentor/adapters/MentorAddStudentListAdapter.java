package kz.tech.smartgrades.mentor.adapters;

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
import kz.tech.smartgrades.auth.models.ModelUserData;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class MentorAddStudentListAdapter extends RecyclerView.Adapter<MentorAddStudentListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<ModelUserData> mStudents;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(ModelUserData m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public MentorAddStudentListAdapter(Context context){
        this.context = context;
        this.mStudents = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelUserData m = mStudents.get(position);
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(m.getAvatar()).fit().centerInside().into(holder.civAvatar);
        else holder.civAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));
        holder.tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        holder.ivMenu.setVisibility(View.GONE);
        holder.ivSponsorStatus.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount(){
        return mStudents.size();
    }
    private void onClear(){
        if(mStudents.size() > 0) mStudents.clear();
    }

    public void updateData(ArrayList<ModelUserData> list){
        onClear();
        if(!listIsNullOrEmpty(list)) {
            mStudents.addAll(list);
            notifyItemChanged(0, list.size());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_school_students_list, parent, false));
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
