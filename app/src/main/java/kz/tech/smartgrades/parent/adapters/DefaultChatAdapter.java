package kz.tech.smartgrades.parent.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.MSG_TYPE;
import kz.tech.smartgrades.mentor.models.ModelDefaultChat;

public class DefaultChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private String SourceId;
    private ArrayList<ModelDefaultChat> MessageList;

    private int[] FullNameColors;
    private String[] UserIdForColors;


    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(ModelDefaultChat m, int answerType, int gradePos);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public DefaultChatAdapter(Context context, String SourceId) {
        this.context = context;
        this.MessageList = new ArrayList<>();
        this.SourceId = SourceId;

        FullNameColors = new int[] { context.getResources().getColor(R.color.chat_fullname_color1), context.getResources().getColor(R.color.chat_fullname_color2), context.getResources().getColor(R.color.chat_fullname_color3), context.getResources().getColor(R.color.chat_fullname_color4)  };
        UserIdForColors = new String[3];
    }


    @Override
    public int getItemCount() {
        return MessageList.size();
    }

    private void onClear() {
        if (MessageList.size() > 0) MessageList.clear();
    }

    public void updateData(ArrayList<ModelDefaultChat> dataList) {
        if (dataList.size() == 0) return;
        onClear();
        this.MessageList.addAll(dataList);

        for(int i = 0; i < dataList.size(); i++){
            if (UserIdForColors[0] == null){
                if (dataList.get(i).getSourceId() != null && !dataList.get(i).getSourceId().isEmpty()){
                    UserIdForColors[0] = dataList.get(i).getSourceId();
                }
            }
            else if (UserIdForColors[1] == null){
                if (dataList.get(i).getSourceId() != null && !dataList.get(i).getSourceId().isEmpty()){
                    UserIdForColors[1] = dataList.get(i).getSourceId();
                }
            }
            else if (UserIdForColors[2] == null){
                if (dataList.get(i).getSourceId() != null && !dataList.get(i).getSourceId().isEmpty()){
                    UserIdForColors[2] = dataList.get(i).getSourceId();
                }
            }
            else break;
        }

        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        ModelDefaultChat m = MessageList.get(position);

        if (m.getType()==MSG_TYPE.Message || m.getType()==6){
            if (m.getSourceId().equals(SourceId)) return 1;
            else return 2;
        }
        else if (m.getType()==MSG_TYPE.Grade || m.getType()==2 || m.getType()==3 || m.getType()==4 || m.getType()==5){
            if (m.getSourceId().equals(SourceId)) return 3;
            else return 4;
        }
        else if (m.getType()==7 || m.getType()==8 || m.getType()==9){
            if (m.getSourceId().equals(SourceId)) return 5;
            else return 6;
        }
        else if (m.getType()==11){
            if (m.getSourceId().equals(SourceId)) return 7;
            else return 8;
        }
        return 0;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_msg_source, parent, false);
                return new vhMsg(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_msg_target, parent, false);
                return new vhMsg(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_grade_source, parent, false);
                return new vhGrades(view);
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_grade_target, parent, false);
                return new vhGrades(view);
            case 5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_money_source, parent, false);
                return new vhMoney(view);
            case 6:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_money_target, parent, false);
                return new vhMoney(view);
            case 7:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_inter_form_source, parent, false);
                return new vhInterForm(view);
            case 8:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_inter_form_target, parent, false);
                return new vhInterForm(view);

        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) return;

        ModelDefaultChat m = MessageList.get(position);

        if (holder.getItemViewType() == 1 || holder.getItemViewType() == 2){
            if (holder.getItemViewType() == 1) ((vhMsg) holder).tvFullName.setText("Вы:");
            else{
                if (m.getSourceFirstName().equals("Bot")){
                    ((vhMsg) holder).tvFullName.setText("Бот");
                    ((vhMsg) holder).civAvatar.setBackgroundResource(R.drawable.img_avatar_bot);
                }
                else{
                    ((vhMsg) holder).tvFullName.setText(m.getSourceFirstName() + " " + m.getSourceLastName());
                    String avatar = m.getSourceAvatar();
                    if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).fit().centerInside().into(((vhMsg) holder).civAvatar);
                    else ((vhMsg) holder).civAvatar.setBackgroundResource(R.drawable.img_default_avatar);
                }
            }
            for(int i = 0; i < UserIdForColors.length; i++){
                if (UserIdForColors[i].equals(m.getSourceId())){
                    ((vhMsg) holder).tvFullName.setTextColor(FullNameColors[i]);
                    break;
                }
            }

            if (m.getMessage() == null || m.getMessage().isEmpty()){
                ((vhMsg) holder).tvMessage.setVisibility(View.GONE);
                ((vhMsg) holder).tvDate.setVisibility(View.GONE);
            }
            else{
                ((vhMsg) holder).tvMessage.setText(m.getMessage());
                ((vhMsg) holder).tvDate.setText(Convert.String2ShortTime(m.getDate()));
            }
        }
        else if (holder.getItemViewType() == 3 || holder.getItemViewType() == 4){
            if (holder.getItemViewType() == 3) ((vhGrades) holder).tvFullName.setText("Вы:");
            else{
                ((vhGrades) holder).tvFullName.setText(m.getSourceFirstName() + " " + m.getSourceLastName());
                String avatar = m.getSourceAvatar();
                if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).fit().centerInside().into(((vhGrades) holder).civAvatar);
                else ((vhGrades) holder).civAvatar.setBackgroundResource(R.drawable.img_default_avatar);
            }
            for(int i = 0; i < UserIdForColors.length; i++){
                if (UserIdForColors[i].equals(m.getSourceId())){
                    ((vhGrades) holder).tvFullName.setTextColor(FullNameColors[i]);
                    break;
                }
            }

            ((vhGrades) holder).tvDate.setText(Convert.String2ShortTime(m.getDate()));
            if(m.getType()==MSG_TYPE.Grade){
                ((vhGrades) holder).tvMessage.setVisibility(View.GONE);
                ((vhGrades) holder).tvData.setVisibility(View.VISIBLE);
                ((vhGrades) holder).tvData.setText(m.getMessage());
                if (m.getMessage().equals("5")) ((vhGrades) holder).tvData.setTextColor(ContextCompat.getColor(context, R.color.background_oval_purple));
                else if (m.getMessage().equals("4")) ((vhGrades) holder).tvData.setTextColor(ContextCompat.getColor(context, R.color.background_oval_green));
                else if (m.getMessage().equals("3")) ((vhGrades) holder).tvData.setTextColor(ContextCompat.getColor(context, R.color.background_oval_yellow));
                else if (m.getMessage().equals("2")) ((vhGrades) holder).tvData.setTextColor(ContextCompat.getColor(context, R.color.background_oval_red));
            }
            else{
                ((vhGrades) holder).tvMessage.setText(m.getMessage());
                ((vhGrades) holder).tvMessage.setVisibility(View.VISIBLE);
                ((vhGrades) holder).tvData.setVisibility(View.GONE);
            }
        }
        else if (holder.getItemViewType() == 5 || holder.getItemViewType() == 6){
            if (holder.getItemViewType() == 5) ((vhMoney) holder).tvFullName.setText("Вы:");
            else {
                ((vhMoney) holder).tvFullName.setText(m.getSourceFirstName() + " " + m.getSourceLastName());
                String avatar = m.getSourceAvatar();
                if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).fit().centerInside().into(((vhMoney) holder).civAvatar);
                else ((vhMoney) holder).civAvatar.setBackgroundResource(R.drawable.img_default_avatar);
            }
            for(int i = 0; i < UserIdForColors.length; i++){
                if (UserIdForColors[i].equals(m.getSourceId())){
                    ((vhMoney) holder).tvFullName.setTextColor(FullNameColors[i]);
                    break;
                }
            }

            ((vhMoney) holder).tvData.setText(m.getMessage());
            if(m.getType()==8) ((vhMoney) holder).tvData.setTextColor(Color.RED);
            else if(m.getType()==9) {
                ((vhMoney) holder).tvData.setTextColor(Color.WHITE);
                ((vhMoney) holder).tvMessage.setText("Снятие со счета");
                ((vhMoney) holder).tvMessage.setVisibility(View.VISIBLE);
            }
        }
        else if (holder.getItemViewType() == 7 || holder.getItemViewType() == 8){
            if (holder.getItemViewType() == 7) ((vhInterForm) holder).tvFullName.setText("Вы:");
            else {
                if (m.getSourceFirstName().equals("Bot")){
                    ((vhInterForm) holder).tvFullName.setText("Бот");
                    ((vhInterForm) holder).civAvatar.setBackgroundResource(R.drawable.img_avatar_bot);
                }
                else{
                    ((vhInterForm) holder).tvFullName.setText(m.getSourceFirstName() + " " + m.getSourceLastName());
                    String avatar = m.getSourceAvatar();
                    if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).fit().centerInside().into(((vhInterForm) holder).civAvatar);
                    else ((vhInterForm) holder).civAvatar.setBackgroundResource(R.drawable.img_default_avatar);
                }
            }
            for(int i = 0; i < UserIdForColors.length; i++){
                if (UserIdForColors[i].equals(m.getSourceId())){
                    ((vhInterForm) holder).tvFullName.setTextColor(FullNameColors[i]);
                    break;
                }
            }

            if (m.getMessage() == null || m.getMessage().isEmpty()){
                ((vhInterForm) holder).tvMessage.setVisibility(View.GONE);
                ((vhInterForm) holder).tvDate.setVisibility(View.GONE);
            }
            else{
                ((vhInterForm) holder).tvMessage.setText(m.getMessage());
                ((vhInterForm) holder).tvDate.setText(Convert.String2ShortTime(m.getDate()));
            }

            //if (mSource.getType().equals("Sponsor")) ((vhInterForm) holder).tvEdit.setVisibility(View.VISIBLE);
        }
    }


    static class vhMsg extends RecyclerView.ViewHolder {
        TextView tvFullName, tvMessage, tvDate, tvData;
        CircleImageView civAvatar;
        vhMsg(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvData = itemView.findViewById(R.id.tvData);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
        }
    }
    class vhGrades extends RecyclerView.ViewHolder {
        TextView tvData, tvDate, tvMessage, tvFullName;
        CircleImageView civAvatar;

        vhGrades(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tvData);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            civAvatar = itemView.findViewById(R.id.civAvatar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener == null) return;
                    onItemClickListener.onItemClick(MessageList.get(getAdapterPosition()), 0, getAdapterPosition());
                }
            });
        }
    }
    static class vhMoney extends RecyclerView.ViewHolder {
        TextView tvData, tvDate, tvMessage, tvFullName;
        CircleImageView civAvatar;

        vhMoney(@NonNull View itemView) {
            super(itemView);
            tvData = itemView.findViewById(R.id.tvData);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            civAvatar = itemView.findViewById(R.id.civAvatar);
        }
    }
    class vhInterForm extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvMessage, tvDate, tvData;
        TextView tvCancel, tvNo, tvOkey, tvEdit;
        vhInterForm(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvData = itemView.findViewById(R.id.tvData);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvCancel = itemView.findViewById(R.id.tvCancel);
            tvNo = itemView.findViewById(R.id.tvNo);
            tvOkey = itemView.findViewById(R.id.tvOkey);
            tvEdit = itemView.findViewById(R.id.tvEdit);

            if (tvEdit != null){
                tvEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener == null) return;
                        onItemClickListener.onItemClick(MessageList.get(getAdapterPosition()), 4, getAdapterPosition());
                    }
                });
            }
            if (tvCancel != null){
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener == null) return;
                        onItemClickListener.onItemClick(MessageList.get(getAdapterPosition()), 3, getAdapterPosition());
                    }
                });
            }
            if (tvNo != null){
                tvNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener == null) return;
                        onItemClickListener.onItemClick(MessageList.get(getAdapterPosition()), 2, getAdapterPosition());
                    }
                });
            }
            if (tvOkey != null){
                tvOkey.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener == null) return;
                        onItemClickListener.onItemClick(MessageList.get(getAdapterPosition()), 1, getAdapterPosition());
                    }
                });
            }
        }
    }
}
