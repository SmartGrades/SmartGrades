package kz.tech.smartgrades.mentor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.models.ModelMentorChat;
import kz.tech.smartgrades.parent.model.ModelPrivateChat;
import kz.tech.smartgrades.root.login.LoginKey;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class MentorChatAdapter extends RecyclerView.Adapter<MentorChatAdapter.ViewHolder> {

    private MentorActivity activity;
    private String MENTOR_ID;
    private ArrayList<ModelMentorChat> mChats;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onChatClick(ModelMentorChat m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public MentorChatAdapter(MentorActivity activity){
        this.activity = activity;
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);
        mChats = new ArrayList<>();
    }

    public void updateData(ArrayList<ModelMentorChat> mChats){
        onClear();
        if(!listIsNullOrEmpty(mChats)) this.mChats.addAll(mChats);
        notifyDataSetChanged();
    }
    private void onClear(){
        if(!listIsNullOrEmpty(mChats)) mChats.clear();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelMentorChat m = mChats.get(position);

        if(!stringIsNullOrEmpty(m.getChatAvatar()))
            Picasso.get().load(m.getChatAvatar()).fit().centerCrop().into(holder.civAvatar);
        else {
            if(m.getChatType().equals("Group"))
                holder.civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_chat_group));
            else if(m.getChatType().equals("Private"))
                holder.civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
        }

        if(!stringIsNullOrEmpty(m.getLastMessageDate())){
            Date date = Convert.String2DateTime(m.getLastMessageDate());
            if(date.getDate() == new Date().getDate())
                holder.tvDate.setText(Convert.Date2ShortTime(date));
            else holder.tvDate.setText(Convert.Date2ShortDate(date));
            holder.tvDate.setVisibility(View.VISIBLE);
        }
        else holder.tvDate.setVisibility(View.GONE);

        holder.tvName.setText(m.getChatName());
        holder.tvChatTag.setText(m.getChatTag());
        if(m.getLastMessageSourceId().equals(MENTOR_ID))
            holder.tvLastMassage.setText("Вы: " + m.getLastMessage());
        else holder.tvLastMassage.setText(m.getLastMessageSourceFirstName() + " " + m.getLastMessageSourceLastName() + ": " + m.getLastMessage());

        if(m.getNoCheckCount() > 0){

        }
        else {

        }
    }
    @Override
    public int getItemCount(){
        return mChats.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_chats_list, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civAvatar;
        TextView tvName, tvChatTag, tvLastMassage, tvDate;

        ViewHolder(@NonNull View view){
            super(view);
            civAvatar = view.findViewById(R.id.civAvatar);
            tvName = view.findViewById(R.id.tvName);
            tvChatTag = view.findViewById(R.id.tvChatTag);
            tvLastMassage = view.findViewById(R.id.tvLastMassage);
            tvDate = view.findViewById(R.id.tvDate);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onChatClick(mChats.get(getAdapterPosition()));
                }
            });
        }
    }
}
