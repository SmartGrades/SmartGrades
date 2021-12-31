package kz.tech.smartgrades.sponsor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.mentor.adapters.ChildListGradesListAdapter;
import kz.tech.smartgrades.parent.model.ModelPrivateChat;
import kz.tech.smartgrades.sponsor.models.ModelSponsorChildrenList;
import kz.tech.smartgrades.sponsor.models.ModelSponsorData;
import kz.tech.smartgrades.sponsor.models.ModelSponsorUsersListAdapter;


public class SponsorUsersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ModelSponsorUsersListAdapter> modelUsersListAdapters;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(ModelSponsorUsersListAdapter m);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public SponsorUsersListAdapter(Context context) {
        this.context = context;
        this.modelUsersListAdapters = new ArrayList<>();
    }


    @Override
    public int getItemCount() {
        return modelUsersListAdapters.size();
    }

    private void onClear() {
        if (modelUsersListAdapters.size() > 0) modelUsersListAdapters.clear();
    }

    public void updateData(ModelSponsorData SponsorData, boolean PrivateChat) {
        onClear();
        if (!PrivateChat){
            if (SponsorData.getChildrenList() != null){
                for (int i = 0; i < SponsorData.getChildrenList().size(); i++) {
                    ModelSponsorChildrenList childrenList = SponsorData.getChildrenList().get(i);
                    ModelSponsorUsersListAdapter m = new ModelSponsorUsersListAdapter();
                    m.setChatId(childrenList.getChatId());
                    m.setUserId(childrenList.getUserId());
                    m.setAvatar(childrenList.getAvatar());
                    m.setFirstName(childrenList.getFirstName());
                    m.setLastName(childrenList.getLastName());
                    m.setLessonId1(childrenList.getLessonId1());
                    m.setLessonId2(childrenList.getLessonId2());
                    m.setLessonId3(childrenList.getLessonId3());
                    m.setLessonId4(childrenList.getLessonId4());
                    m.setThresholdGrade(childrenList.getThresholdGrade());
                    m.setGradesLeft(childrenList.getGradesLeft());
                    m.setDaysLeft(childrenList.getDaysLeft());
                    m.setAverageGrade(childrenList.getAverageGrade());
                    m.setGrades(childrenList.getGrades());
                    modelUsersListAdapters.add(m);
                }
            }
        }
        else{
            if (SponsorData.getPrivateChats() != null){
                for (int i = 0; i < SponsorData.getPrivateChats().size(); i++) {
                    ModelPrivateChat mPrivateChat = SponsorData.getPrivateChats().get(i);
                    ModelSponsorUsersListAdapter m = new ModelSponsorUsersListAdapter();
                    m.setType("PrivateChat");
                    m.setChatId(mPrivateChat.getChatId());
                    m.setAvatar(mPrivateChat.getTargetAvatar());
                    m.setFirstName(mPrivateChat.getTargetFirstName());
                    m.setLastName(mPrivateChat.getTargetLastName());
                    m.setUserId(mPrivateChat.getTargetId());
                    m.setLastMessage(mPrivateChat.getLastMessage());
                    m.setLastMessageDate(mPrivateChat.getLastMessageDate());
                    m.setNoCheckCount(mPrivateChat.getNoCheckCount());
                    modelUsersListAdapters.add(m);
                }
            }
        }

        /*if (SponsorData.getInterFormSponsorToParent() != null){
            for (int i = 0; i < SponsorData.getInterFormSponsorToParent().size(); i++) {
                ModelInterFormSponsorToParent interactionForm = SponsorData.getInterFormSponsorToParent().get(i);
                ModelSponsorUsersListAdapter m = new ModelSponsorUsersListAdapter();
                m.setType("SponsorInterForm");
                m.setAnswer(interactionForm.getAnswer());
                m.setChatId(interactionForm.getChatId());
                m.setInterFormId(interactionForm.getIndex());
                m.setAvatar(interactionForm.getParentAvatar());
                m.setFirstName(interactionForm.getParentFirstName());
                m.setLastName(interactionForm.getParentLastName());
                m.setUserId(interactionForm.getParentId());
                m.setLastMessage(interactionForm.getChatLastMessage());
                m.setLastMessageDate(interactionForm.getChatLastMessageDate());
                m.setNoCheckCount(interactionForm.getNoCheckCount());
                modelUsersListAdapters.add(m);
            }
        }
        if (SponsorData.getInterFormParentToSponsor() != null){
            for (int i = 0; i < SponsorData.getInterFormParentToSponsor().size(); i++) {
                ModelInterFormParentToSponsor interactionForm = SponsorData.getInterFormParentToSponsor().get(i);
                ModelSponsorUsersListAdapter m = new ModelSponsorUsersListAdapter();
                m.setType("ParentInterForm");
                m.setAnswer(interactionForm.getAnswer());
                m.setChatId(interactionForm.getChatId());
                m.setInterFormId(interactionForm.getIndex());
                m.setAvatar(interactionForm.getParentAvatar());
                m.setFirstName(interactionForm.getParentFirstName());
                m.setLastName(interactionForm.getParentLastName());
                m.setUserId(interactionForm.getParentId());
                m.setLastMessage(interactionForm.getChatLastMessage());
                m.setLastMessageDate(interactionForm.getChatLastMessageDate());
                m.setNoCheckCount(interactionForm.getNoCheckCount());
                modelUsersListAdapters.add(m);
            }
        }*/

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        ModelSponsorUsersListAdapter m = modelUsersListAdapters.get(position);
        if (m.getType() != null && (m.getType().equals("ParentInterForm") || m.getType().equals("SponsorInterForm") || m.getType().equals("PrivateChat"))) return 1;
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0: view = LayoutInflater.from(context).inflate(R.layout.item_sponsor_chat_child, parent, false); return new vhChild(view);
            case 1: view = LayoutInflater.from(context).inflate(R.layout.item_chat_sponsor_inter_form, parent, false); return new vhInterForms(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelSponsorUsersListAdapter m = modelUsersListAdapters.get(position);
        if (holder.getItemViewType() == 0) {
            String avatar = m.getAvatar();
            if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(((vhChild) holder).civAvatar);
            else ((vhChild) holder).civAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));

            ((vhChild) holder).tvFullName.setText(m.getFirstName() + "\n" + m.getLastName());

            ((vhChild) holder).tvDaysLeft.setText(m.getDaysLeft());
            ((vhChild) holder).tvGradesLeft.setText(m.getGradesLeft());
            ((vhChild) holder).tvThresholdGrade.setText(m.getThresholdGrade());
            if (m.getAverageGrade() != null && !m.getAverageGrade().isEmpty()){
                ((vhChild) holder).tvAverageGrade.setText(m.getAverageGrade());
                double averageGrade = Convert.Str2Double(m.getAverageGrade());
                if (averageGrade < 3) ((vhChild) holder).tvAverageGrade.setBackground(context.getResources().getDrawable(R.drawable.background_oval_red));
                else if (averageGrade >= 3 && averageGrade < 4) ((vhChild) holder).tvAverageGrade.setBackground(context.getResources().getDrawable(R.drawable.background_oval_yellow));
                else if (averageGrade >= 4 && averageGrade < 5) ((vhChild) holder).tvAverageGrade.setBackground(context.getResources().getDrawable(R.drawable.background_oval_green));
                else if (averageGrade == 5) ((vhChild) holder).tvAverageGrade.setBackground(context.getResources().getDrawable(R.drawable.background_oval_purple));
            }
            else {
                ((vhChild) holder).tvAverageGrade.setBackground(context.getResources().getDrawable(R.drawable.background_oval_gray));
                ((vhChild) holder).tvAverageGrade.setText("-");
            }

            if (m.getGrades() != null){
                int TotalGrade = m.getGrades().size();
                if (TotalGrade > 0){
                    ChildListGradesListAdapter adapter = new ChildListGradesListAdapter(context);
                    ((vhChild) holder).rvGradesList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
                    ((vhChild) holder).rvGradesList.setAdapter(adapter);
                    if (TotalGrade > 5) {
                        m.getGrades().get(TotalGrade - 1).setGrade("...");
                        //m.getGrades().get(TotalGrade - 1).setType(-1);
                    }
                    adapter.updateData(m.getGrades());
                }
            }
        }
        else if (holder.getItemViewType() == 1){
            String avatar = m.getAvatar();
            if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(((vhInterForms) holder).civAvatar);
            else ((vhInterForms) holder).civAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));

            ((vhInterForms) holder).tvFullName.setText(m.getFirstName() + " " + m.getLastName());
            ((vhInterForms) holder).tvMessage.setText(m.getLastMessage());

            Date date = Convert.String2DateTime(m.getLastMessageDate());
            if (date != null){
                if (date.getDate() == new Date().getDate()) ((vhInterForms) holder).tvDate.setText(Convert.Date2ShortTime(date));
                else ((vhInterForms) holder).tvDate.setText(Convert.Date2ShortDate(date));
            }
            else ((vhInterForms) holder).tvDate.setText("");

            if (m.getNoCheckCount() > 0) {
                /*((vhInterForms) holder).tvNewMessageCount.setVisibility(View.VISIBLE);
                ((vhInterForms) holder).tvNewMessageCount.setText(String.valueOf(m.getNoCheckCount()));*/
                ((vhInterForms) holder).tvMessage.setTextColor(context.getResources().getColor(R.color.blue_sea));
            }
            else ((vhInterForms) holder).tvMessage.setTextColor(context.getResources().getColor(R.color.gray_default));
        }
    }


    class vhChild extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvDate, tvDaysLeft, tvGradesLeft, tvThresholdGrade, tvAverageGrade;
        RecyclerView rvGradesList;

        vhChild(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvDaysLeft = itemView.findViewById(R.id.tvDaysLeft);
            tvGradesLeft = itemView.findViewById(R.id.tvGradesLeft);
            tvGradesLeft = itemView.findViewById(R.id.tvGradesLeft);
            tvThresholdGrade = itemView.findViewById(R.id.tvThresholdGrade);
            tvAverageGrade = itemView.findViewById(R.id.tvAverageGrade);
            rvGradesList = itemView.findViewById(R.id.rvGradesList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onItemClickListener.onItemClick(modelUsersListAdapters.get(position));
                }
            });
        }
    }

    class vhInterForms extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvMessage, tvDate, tvNewMessageCount;

        vhInterForms(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvNewMessageCount = itemView.findViewById(R.id.tvNewMessageCount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(modelUsersListAdapters.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
