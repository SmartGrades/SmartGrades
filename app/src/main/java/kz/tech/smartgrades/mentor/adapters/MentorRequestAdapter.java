package kz.tech.smartgrades.mentor.adapters;

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
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.parent.model.ModelInterFormOfMentoring;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class MentorRequestAdapter extends RecyclerView.Adapter<MentorRequestAdapter.ViewHolder>{

    private ArrayList<ModelInterFormOfMentoring> requests;
    private MentorActivity activity;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onRequestAcceptClick(ModelInterFormOfMentoring m);
        void onCancelAcceptClick(ModelInterFormOfMentoring m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MentorRequestAdapter(MentorActivity activity){
        this.activity = activity;
        this.requests = new ArrayList<>();
    }

    @Override
    public int getItemCount(){
        return requests.size();
    }

    private void onClear(){
        if(requests.size() > 0) requests.clear();
    }
    public void updateData(ArrayList<ModelInterFormOfMentoring> requests){
        onClear();
        this.requests.addAll(requests);
        notifyDataSetChanged();
    }
    public void removeItem(ModelInterFormOfMentoring m){
        requests.remove(m);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelInterFormOfMentoring m = requests.get(position);
        String avatar = m.getParentAvatar();
        if(!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerCrop().into(holder.civParentAvatar);
        holder.tvParentName.setText(m.getParentFirstName() + " " + m.getParentLastName());

        avatar = m.getChildAvatar();
        if(!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerCrop().into(holder.civChildAvatar);
        holder.tvChildName.setText(m.getChildFirstName() + " " + m.getChildLastName());

        holder.tvLessonName.setText(m.getLessonName());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_mentor_request, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civParentAvatar, civChildAvatar;
        TextView tvParentName, tvMessage, tvChildName, tvLessonName, tvAccept;
        ImageView ivCancel;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            civParentAvatar = itemView.findViewById(R.id.civParentAvatar);
            civChildAvatar = itemView.findViewById(R.id.civChildAvatar);
            tvParentName = itemView.findViewById(R.id.tvParentName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvChildName = itemView.findViewById(R.id.tvChildName);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            tvAccept = itemView.findViewById(R.id.tvAccept);
            ivCancel = itemView.findViewById(R.id.ivCancel);

            tvAccept.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener == null) return;
                    onItemClickListener.onRequestAcceptClick(requests.get(getAdapterPosition()));
                }
            });
            ivCancel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener == null) return;
                    onItemClickListener.onCancelAcceptClick(requests.get(getAdapterPosition()));
                }
            });
        }
    }
}