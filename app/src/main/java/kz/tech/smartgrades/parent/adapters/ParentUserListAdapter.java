package kz.tech.smartgrades.parent.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelParentData;
import kz.tech.smartgrades.root.login.LoginKey;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class ParentUserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ParentActivity activity;
    private String PARENT_ID;

    private ArrayList<ModelParentData> ParentData;
    private ArrayList<ModelParentData> SelectList;

    private OnItemCLickListener onItemCLickListener;
    public interface OnItemCLickListener {
        void onChatClick(ModelParentData m);
        void onMentorClick(int type, ModelParentData m);
        void onLessonrClick(int i, ModelParentData modelParentData);
    }
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public ParentUserListAdapter(ParentActivity activity) {
        this.activity = activity;
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);

        this.ParentData = new ArrayList<>();
        this.SelectList = new ArrayList<>();
    }


    @Override
    public int getItemCount() {
        return SelectList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ModelParentData mParentData, boolean ShowPrivateChat) {
        if (mParentData==null) return;
        if (ShowPrivateChat){
            onClear(SelectList);
            /*if (mParentData.getPrivateChats() != null){
                for (int j = 0; j < mParentData.getPrivateChats().size(); j++){
                    ModelParentData m = new ModelParentData();
                    ModelPrivateChat mPrivateChat = mParentData.getPrivateChats().get(j);
                    m.setModelType(F.PrivateChat);
                    m.setPrivateChatData(mPrivateChat);
                    SelectList.add(m);
                }
            }*/
            notifyDataSetChanged();
        }
        else {
            onClear(ParentData);
            if (!listIsNullOrEmpty(mParentData.getFamilyGroup().getChildrens())){
                for (int j = 0; j < mParentData.getFamilyGroup().getChildrens().size(); j++){
//                    if (!listIsNullOrEmpty(mParentData.getChildrenList().get(j).getMentorList())){
//                        for (int i = 0; i < mParentData.getChildrenList().get(j).getMentorList().size(); i++){
//                            if (mParentData.getChildrenList().get(j).getMentorList().get(i) != null){
//                                //ModelParentData m = new ModelParentData();
//                                /*m.setModelType("MentorList");
//                                m.setChildId(mParentData.getChildrenList().get(j).getChildId());
//                                m.setMentorData(mParentData.getChildrenList().get(j).getMentorList().get(i));*/
//                                //ParentData.add(m);
//                            }
//                        }
//                    }
                    /*if (!listIsNullOrEmpty(mParentData.getChildrenList().get(j).getParentsLessons())){
                        for (int i = 0; i < mParentData.getChildrenList().get(j).getParentsLessons().size(); i++){
                            if (mParentData.getChildrenList().get(j).getParentsLessons().get(i) != null){
                                ModelParentData m = new ModelParentData();
                                m.setModelType("LessonsList");
                                m.setChildId(mParentData.getChildrenList().get(j).getChildId());
                                m.setParentLessonData(mParentData.getChildrenList().get(j).getParentsLessons().get(i));
                                ParentData.add(m);
                            }
                        }
                    }*/
                }
            }
        }
    }

    public void selectData(String ChildId) {
        onClear(SelectList);
        if (!listIsNullOrEmpty(ParentData)){
            /*for (int i = 0; i < ParentData.size(); i++){
                if (ParentData.get(i).getParentLessonData() != null)
                    if (ParentData.get(i).getChildId().equals(ChildId)) SelectList.add(ParentData.get(i));
            }
            for (int i = 0; i < ParentData.size(); i++){
                if (ParentData.get(i).getMentorData() != null)
                    if (ParentData.get(i).getChildId().equals(ChildId)) SelectList.add(ParentData.get(i));
            }*/
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        /*switch (SelectList.get(position).getModelType()) {
            case F.MentorList: return 1;
            case "LessonsList": return 2;
            case F.PrivateChat: return 3;
        }*/
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1://MentorList
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_chat_mentor, parent, false);
                return new vhMentor(view);
            case 2://LessonsList
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent_chat_mentor, parent, false);
                return new vhLesson(view);
            case 3://PrivateChat
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_parent_inter_form, parent, false);
                return new vhPrivateChat(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) return;
        /*else if (holder.getItemViewType() == 1){//MentorList
            ModelChildMentorList m = SelectList.get(position).getMentorData();

            String avatar = m.getMentorAvatar();
            if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).fit().centerInside().into(((vhMentor) holder).civAvatar);
            else ((vhMentor) holder).civAvatar.setBackgroundResource(R.drawable.img_default_avatar);

            ((vhMentor) holder).tvFullName.setText(m.getLessonName());

            if (m.getAverageGrade() != null && !m.getAverageGrade().isEmpty()){
                double averageGrade = Convert.Str2Double(m.getAverageGrade());
                ((vhMentor) holder).tvAverageGrade.setText(m.getAverageGrade());
                if (averageGrade >= 2 && averageGrade < 3) ((vhMentor) holder).tvAverageGrade.setBackground(activity.getResources().getDrawable(R.drawable.img_oval_red));
                else if (averageGrade >= 3 && averageGrade < 4) ((vhMentor) holder).tvAverageGrade.setBackground(activity.getResources().getDrawable(R.drawable.img_oval_yellow));
                else if (averageGrade >= 4 && averageGrade < 5) ((vhMentor) holder).tvAverageGrade.setBackground(activity.getResources().getDrawable(R.drawable.img_oval_green));
                else if (averageGrade == 5) ((vhMentor) holder).tvAverageGrade.setBackground(activity.getResources().getDrawable(R.drawable.img_oval_purple));
            }
            else{
                ((vhMentor) holder).tvAverageGrade.setText("-");
                ((vhMentor) holder).tvAverageGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_gray));
            }
            //if (m.get() != null) ((vhMentor) holder).ivLogoSponsor.setBackgroundResource(R.drawable.avatar_sponsor);

            if (m.getLastMessage() != null){
                if (m.getLastMessageType().equals("1")) ((vhMentor) holder).tvMessage.setText("Оценка " + m.getLastMessage());
                else ((vhMentor) holder).tvMessage.setText(m.getLastMessage());

                Date date = Convert.String2DateTime(m.getLastMessageDate());
                if (date != null){
                    if (date.getDate() == new Date().getDate()) ((vhMentor) holder).tvDate.setText(Convert.Date2ShortTime(date));
                    else ((vhMentor) holder).tvDate.setText(Convert.Date2ShortDate(date));
                }
                else ((vhMentor) holder).tvDate.setText("");

                if (m.getNoCheckCount() > 0) {
                        /*((vhInterForms) holder).tvNewMessageCount.setVisibility(View.VISIBLE);
                        ((vhInterForms) holder).tvNewMessageCount.setText(String.valueOf(m.getNoCheckCount()));
                    ((vhMentor) holder).tvMessage.setTextColor(activity.getResources().getColor(R.color.blue_sea));
                }
                else ((vhMentor) holder).tvMessage.setTextColor(activity.getResources().getColor(R.color.gray_default));
            }
            else {
                ((vhMentor) holder).tvMessage.setText("");
                ((vhMentor) holder).tvDate.setText("");
            }

            if (m.getNoCheckCount() > 0) {
                        /*((vhInterForms) holder).tvNewMessageCount.setVisibility(View.VISIBLE);
                        ((vhInterForms) holder).tvNewMessageCount.setText(String.valueOf(m.getNoCheckCount()));
                ((vhMentor) holder).tvMessage.setTextColor(activity.getResources().getColor(R.color.blue_sea));
            }
            else ((vhMentor) holder).tvMessage.setTextColor(activity.getResources().getColor(R.color.gray_default));
        }
        else if (holder.getItemViewType() == 2){
            ModelParentsLessons m = SelectList.get(position).getParentLessonData();

            String avatar = activity.login.loadUserDate(LoginKey.AVATAR);
            if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).fit().centerInside().into(((vhLesson) holder).civAvatar);
            else ((vhLesson) holder).civAvatar.setBackgroundResource(R.drawable.img_default_avatar);

            ((vhLesson) holder).tvFullName.setText(m.getLessonName());

            double averageGrade = m.getAverageGrade();
            if (averageGrade > 0){
                ((vhLesson) holder).tvAverageGrade.setText(String.valueOf(m.getAverageGrade()));
                if (averageGrade >= 2 && averageGrade < 3) ((vhLesson) holder).tvAverageGrade.setBackgroundResource(R.drawable.img_oval_red);
                else if (averageGrade >= 3 && averageGrade < 4) ((vhLesson) holder).tvAverageGrade.setBackgroundResource(R.drawable.img_oval_yellow);
                else if (averageGrade >= 4 && averageGrade < 5) ((vhLesson) holder).tvAverageGrade.setBackgroundResource(R.drawable.img_oval_green);
                else if (averageGrade == 5) ((vhLesson) holder).tvAverageGrade.setBackgroundResource(R.drawable.img_oval_purple);
            }
            else{
                ((vhLesson) holder).tvAverageGrade.setText("-");
                ((vhLesson) holder).tvAverageGrade.setBackground(activity.getResources().getDrawable(R.drawable.background_oval_gray));
            }

            if (m.getLastMessage() != null){
                if (m.getLastMessageType().equals("1")) ((vhLesson) holder).tvMessage.setText("Оценка " + m.getLastMessage());
                else ((vhLesson) holder).tvMessage.setText(m.getLastMessage());

                Date date = Convert.String2DateTime(m.getLastMessageDate());
                if (date != null){
                    if (date.getDate() == new Date().getDate()) ((vhLesson) holder).tvDate.setText(Convert.Date2ShortTime(date));
                    else ((vhLesson) holder).tvDate.setText(Convert.Date2ShortDate(date));
                }
                else ((vhMentor) holder).tvDate.setText("");

                /*if (m.getNoCheckCount() > 0) {
                        ((vhInterForms) holder).tvNewMessageCount.setVisibility(View.VISIBLE);
                        ((vhInterForms) holder).tvNewMessageCount.setText(String.valueOf(m.getNoCheckCount()))
                    ((vhMentor) holder).tvMessage.setTextColor(activity.getResources().getColor(R.color.blue_sea));
                }
                else ((vhMentor) holder).tvMessage.setTextColor(activity.getResources().getColor(R.color.gray_default));
            }
            else {
                ((vhMentor) holder).tvMessage.setText("");
                ((vhMentor) holder).tvDate.setText("");
            }
        }
        else if (holder.getItemViewType() == 3){
            ModelPrivateChat mPrivateChat = SelectList.get(position).getPrivateChatData();

            String avatar = mPrivateChat.getTargetAvatar();
            if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).fit().centerInside().into(((vhPrivateChat) holder).civAvatar);
            else ((vhPrivateChat) holder).civAvatar.setBackgroundResource(R.drawable.img_default_avatar);

            ((vhPrivateChat) holder).tvFullName.setText(mPrivateChat.getTargetFirstName() + " " + mPrivateChat.getTargetLastName());

            if (mPrivateChat.getTargetType().equals("Sponsor")) ((vhPrivateChat) holder).tvLogo.setBackgroundResource(R.drawable.img_icon_sponsor);
            else if (mPrivateChat.getTargetType().equals("Mentor")) ((vhPrivateChat) holder).tvLogo.setBackgroundResource(R.drawable.img_icon_mentor);
            else ((vhPrivateChat) holder).tvLogo.setVisibility(View.GONE);

            Date date = Convert.String2DateTime(mPrivateChat.getLastMessageDate());
            if (date != null){
                if (date.getDate() == new Date().getDate()) ((vhPrivateChat) holder).tvDate.setText(Convert.Date2ShortTime(date));
                else ((vhPrivateChat) holder).tvDate.setText(Convert.Date2ShortDate(date));
            }
            else ((vhPrivateChat) holder).tvDate.setText("");

            ((vhPrivateChat) holder).tvMessage.setText(mPrivateChat.getLastMessage());

            if (mPrivateChat.getNoCheckCount() > 0) {
                    /*((vhInterForms) holder).tvNewMessageCount.setVisibility(View.VISIBLE);
                    ((vhInterForms) holder).tvNewMessageCount.setText(String.valueOf(m.getNoCheckCount()));
                ((vhPrivateChat) holder).tvMessage.setTextColor(activity.getResources().getColor(R.color.blue_sea));
            }
            else ((vhPrivateChat) holder).tvMessage.setTextColor(activity.getResources().getColor(R.color.gray_default));
        }*/
    }

    class vhMentor extends RecyclerView.ViewHolder {
        CardView cvInfo;
        CircleImageView civAvatar, civAvatarSub;
        TextView tvFullName, tvDate, tvAverageGrade, tvMessage;
        RecyclerView recyclerView;

        vhMentor(@NonNull View itemView) {
            super(itemView);
            cvInfo = itemView.findViewById(R.id.cvInfo);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvAverageGrade = itemView.findViewById(R.id.tvAverageGrade);
            recyclerView = itemView.findViewById(R.id.rvGradesList);

            cvInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCLickListener == null) return;
                    onItemCLickListener.onMentorClick(0, SelectList.get(getAdapterPosition()));
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCLickListener == null) return;
                    onItemCLickListener.onMentorClick(1, SelectList.get(getAdapterPosition()));
                }
            });
        }
    }
    class vhLesson extends RecyclerView.ViewHolder {
        CardView cvInfo;
        CircleImageView civAvatar;
        TextView tvFullName, tvDate, tvAverageGrade, tvMessage;
        RecyclerView recyclerView;

        vhLesson(@NonNull View itemView) {
            super(itemView);
            cvInfo = itemView.findViewById(R.id.cvInfo);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvAverageGrade = itemView.findViewById(R.id.tvAverageGrade);
            recyclerView = itemView.findViewById(R.id.rvGradesList);

            cvInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCLickListener == null) return;
                    onItemCLickListener.onLessonrClick(0, SelectList.get(getAdapterPosition()));
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCLickListener == null) return;
                    onItemCLickListener.onLessonrClick(1, SelectList.get(getAdapterPosition()));
                }
            });
        }
    }
    class vhPrivateChat extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvMessage, tvDate, tvNewMessageCount;
        ImageView tvLogo;

        vhPrivateChat(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvNewMessageCount = itemView.findViewById(R.id.tvNewMessageCount);
            tvLogo = itemView.findViewById(R.id.tvLogo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemCLickListener != null) {
                        onItemCLickListener.onChatClick(SelectList.get(position));
                    }
                }
            });
        }
    }
}