package kz.tech.smartgrades.child.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.parent.model.ModelChat;
import kz.tech.smartgrades.root.login.LoginKey;


public class ChildUserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ChildActivity activity;
    private String CHILD_ID;
    //private boolean SponsorFilter;

    private ModelChat mChat;

    private ArrayList<ModelChildData> ChildData;
    private ArrayList<ModelChildData> SelectList;

    /*private ModelChildData modelChildData;
    private ModelChildSponsorData mSponsorData;*/


    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ModelChildData mChildData);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ChildUserListAdapter(ChildActivity activity, boolean SponsorFilter) {
        this.activity = activity;
        CHILD_ID = activity.login.loadUserDate(LoginKey.ID);

        this.ChildData = new ArrayList<>();
        this.SelectList = new ArrayList<>();
    }


    @Override
    public int getItemCount() {
        return SelectList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ModelChildData mChildData) {
        onClear(ChildData);
//        if (!listIsNullOrEmpty(mChildData.getMentorList())){
//            for (int j = 0; j < mChildData.getMentorList().size(); j++){
//                ModelChildData m = new ModelChildData();
//                m.setModel(F.MentorList);
//                m.setMentorData(mChildData.getMentorList().get(j));
//                SelectList.add(m);
//            }
//        }
//        if (!listIsNullOrEmpty(mChildData.getPrivateChats())){
//            for (int j = 0; j < mChildData.getPrivateChats().size(); j++){
//                ModelChildData m = new ModelChildData();
//                m.setModel(F.PrivateChat);
//                m.setPrivateChat(mChildData.getPrivateChats().get(j));
//                SelectList.add(m);
//            }
//        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
//        switch (SelectList.get(position).getModel()) {
//            case F.MentorList: return 1;
//            case F.PrivateChat: return 2;
//        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(activity).inflate(R.layout.item_child_mentor_list, parent, false);
                return new vhMentor(view);
            case 2:
                view = LayoutInflater.from(activity).inflate(R.layout.item_chat_parent_inter_form, parent, false);
                return new vhInterForms(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) return;

//        if (holder.getItemViewType() == 1) {
//            ModelChildMentorList m = SelectList.get(position).getMentorData();
//
//            String avatar = m.getMentorAvatar();
//            if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerInside().into(((vhMentor) holder).civAvatar);
//            else ((vhMentor) holder).civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
//
//            ((vhMentor) holder).tvFullName.setText(m.getLessonName());
//            // ((vhMentor) holder).ivSponsorLogo.setVisibility(SponsorFilter ? View.VISIBLE : View.GONE);
//            if (m.getGrades() != null){
//                int TotalGrade = m.getGrades().size();
//                if (TotalGrade > 0) {
//                    ((vhMentor) holder).tvAverageGrade.setText(m.getAverageGrade());
//                    double AverageGrade = Convert.Str2Double(m.getAverageGrade());
//                    if (AverageGrade < 3)
//                        ((vhMentor) holder).tvAverageGrade.setBackgroundResource(R.drawable.background_oval_red);
//                    else if (AverageGrade >= 3 && AverageGrade < 4)
//                        ((vhMentor) holder).tvAverageGrade.setBackgroundResource(R.drawable.background_oval_yellow);
//                    else if (AverageGrade >= 4 && AverageGrade < 5)
//                        ((vhMentor) holder).tvAverageGrade.setBackgroundResource(R.drawable.background_oval_green);
//                    else ((vhMentor) holder).tvAverageGrade.setBackgroundResource(R.drawable.background_oval_purple);
//
//                    if (m.getLastMessageType().equals("1"))
//                        ((vhMentor) holder).tvTitle.setText("Оценка " + m.getLastMessage());
//                    else {
//                        if (m.getLastMessage().length() <= 10)
//                            ((vhMentor) holder).tvTitle.setText(m.getLastMessage());
//                        else ((vhMentor) holder).tvTitle.setText(m.getLastMessage().substring(0, 10));
//                    }
//
//                    Date date = Convert.String2DateTime(m.getLastMessageDate());
//                    if (date != null) {
//                        if (date.getDate() == new Date().getDate())
//                            ((vhMentor) holder).tvDate.setText(Convert.Date2ShortTime(date));
//                        else ((vhMentor) holder).tvDate.setText(Convert.Date2ShortDate(date));
//                    }
//                    else ((vhMentor) holder).tvDate.setText("");
//
//                    if (m.getNoCheckCount() > 0) {
//                        ((vhMentor) holder).tvTitle.setTextColor(activity.getResources().getColor(R.color.blue_sea));
//                    } else
//                        ((vhMentor) holder).tvTitle.setTextColor(activity.getResources().getColor(R.color.gray_default));
//
//                    ChildListGradesListAdapter adapter = new ChildListGradesListAdapter(activity);
//                    ((vhMentor) holder).recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true));
//                    ((vhMentor) holder).recyclerView.setAdapter(adapter);
//
//                    if (TotalGrade > MAX_LAST_GRADES) {
//                        m.getGrades().get(m.getGrades().size() - 1).setData("...");
//                        //m.getGrades().get(m.getGrades().size() - 1).setType(-1);
//                    }
//                    adapter.updateData(m.getGrades());
//                    ((vhMentor) holder).recyclerView.setVisibility(View.VISIBLE);
//
//                }
//            }
//        }
//        else {
//            ModelPrivateChat m = SelectList.get(position).getPrivateChat();
//
//            String avatar = m.getTargetAvatar();
//            if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerInside().into(((vhInterForms) holder).civAvatar);
//            else((vhInterForms) holder).civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
//
//            ((vhInterForms) holder).tvFullName.setText(m.getTargetFirstName() + " " + m.getTargetLastName());
//
//            Date date = Convert.String2DateTime(m.getLastMessageDate());
//            if (date != null){
//                if (date.getDate() == new Date().getDate()) ((vhInterForms) holder).tvDate.setText(Convert.Date2ShortTime(date));
//                else ((vhInterForms) holder).tvDate.setText(Convert.Date2ShortDate(date));
//            }
//            else ((vhInterForms) holder).tvDate.setText("");
//
//            ((vhInterForms) holder).tvMessage.setText(m.getLastMessage());
//            if (m.getNoCheckCount() > 0) {
//                ((vhInterForms) holder).tvMessage.setTextColor(activity.getResources().getColor(R.color.blue_sea));
//            }
//            else ((vhInterForms) holder).tvMessage.setTextColor(activity.getResources().getColor(R.color.gray_default));
//        }
    }

    class vhMentor extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvDate, tvAverageGrade, tvTitle;
        RecyclerView recyclerView;
        ImageView ivSponsorLogo;

        vhMentor(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            ivSponsorLogo = itemView.findViewById(R.id.ivSponsorLogo);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvAverageGrade = itemView.findViewById(R.id.tvAverageGrade);
            recyclerView = itemView.findViewById(R.id.rvGradesList);
            tvTitle = itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onItemClickListener.onItemClick(SelectList.get(position));
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
                    onItemClickListener.onItemClick(SelectList.get(position));
                }
            });
        }
    }
}
