package kz.tech.smartgrades.mentor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class MentorStudentsListAdapterV2 extends RecyclerView.Adapter<MentorStudentsListAdapterV2.ViewHolder>{

    private Context context;
    private ArrayList<ModelSchoolStudent> mStudents;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemLongClick(ModelSchoolStudent m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MentorStudentsListAdapterV2(Context context){
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
        else notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentor_students_list_v2, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civAvatar;
        TextView tvFullName;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);

            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view){
                    if(onItemClickListener != null)
                        onItemClickListener.onItemLongClick(mStudents.get(getAdapterPosition()));
                    return true;
                }
            });
        }
    }
}
