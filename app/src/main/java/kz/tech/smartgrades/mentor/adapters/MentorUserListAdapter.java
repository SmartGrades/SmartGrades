package kz.tech.smartgrades.mentor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.mentor.models.ModelMentorChat;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.mentor.models.ModelMentorGroups;
import kz.tech.smartgrades.parent.model.ModelPrivateChat;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._System.getMaxLastGrades;


public class MentorUserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<ModelMentorGroups> MentorGroups;
    private ArrayList<ModelPrivateChat> PrivateChats;
    private ArrayList<ModelMentorChat> showList;

    private String MENTOR_ID;
    private String GROUP_ID;

    private int MAX_LAST_GRADES = 0;


    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(int i, ModelMentorChat m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public MentorUserListAdapter(Context context, String MENTOR_ID){
        this.context = context;
        this.MentorGroups = new ArrayList<>();
        this.showList = new ArrayList<>();
        this.PrivateChats = new ArrayList<>();
        this.MENTOR_ID = MENTOR_ID;

        MAX_LAST_GRADES = getMaxLastGrades(context);
    }

    private void onClear(ArrayList<?> datalist){
        if(datalist.size() > 0) datalist.clear();
    }
    public void updateData(ModelMentorData modelMentorData){
        if(!listIsNullOrEmpty(modelMentorData.getMentorGroups())){
            onClear(MentorGroups);
            this.MentorGroups.addAll(modelMentorData.getMentorGroups());
        }
        if(!listIsNullOrEmpty(modelMentorData.getChats())){
            onClear(PrivateChats);
            //this.PrivateChats.addAll(modelMentorData.getChats());
        }
    }
    public void selectList(String groupId){
        onClear(showList);
        if(groupId.equals(F.InterForm)){
            /*for (int i = 0; i < InterForms.size(); i++) {
                ModelInterFormOfMentoring mInterForm = InterForms.get(i);
                ModelMentorChat m = new ModelMentorChat();
                m.setModel(F.InterForm);
                m.setInterFormId(InterForms.get(i).getInterFormId());
                m.setChatId(InterForms.get(i).getChatId());

                m.setUserId(InterForms.get(i).getParentId());
                m.setAvatar(InterForms.get(i).getParentAvatar());
                m.setFirstName(InterForms.get(i).getParentFirstName());
                m.setLastName(InterForms.get(i).getParentLastName());

                m.setChildId(InterForms.get(i).getChildId());
                m.setSourceId(InterForms.get(i).getSourceId());

                m.setLastMessage(InterForms.get(i).getLastMessage());
                m.setLastMessageDate(InterForms.get(i).getLastMessageDate());
                m.setNoCheckCount(InterForms.get(i).getNoCheckCount());
                showList.add(m);
            }*/
            for(int i = 0; i < PrivateChats.size(); i++){
                ModelMentorChat m = new ModelMentorChat();
//                m.setModel(F.PrivateChat);
//                m.setUserId(PrivateChats.get(i).getTargetId());
//                m.setAvatar(PrivateChats.get(i).getTargetAvatar());
//                m.setFirstName(PrivateChats.get(i).getTargetFirstName());
//                m.setLastName(PrivateChats.get(i).getTargetLastName());
//                m.setType(PrivateChats.get(i).getTargetType());

                m.setChatId(PrivateChats.get(i).getChatId());
                m.setLastMessage(PrivateChats.get(i).getLastMessage());
                m.setLastMessageDate(PrivateChats.get(i).getLastMessageDate());
                m.setNoCheckCount(PrivateChats.get(i).getNoCheckCount());
                showList.add(m);
            }
        }
        else{
            GROUP_ID = groupId;
            for(int i = 0; i < MentorGroups.size(); i++){
                if(MentorGroups.get(i).getGroupId().equals(groupId)){
                    if(MentorGroups.get(i).getChildrenList() != null && MentorGroups.get(i).getChildrenList().size() > 0)
                        showList.addAll(MentorGroups.get(i).getChildrenList());
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        return showList.size();
    }
    @Override
    public int getItemViewType(int position){
        ModelMentorChat m = showList.get(position);
//        if(m.getModel() != null && (m.getModel().equals(F.InterForm) || m.getModel().equals(F.PrivateChat)))
//            return 1;
        return 0;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view;
        switch(viewType){
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.item_mentor_child_list, parent, false);
                return new vhChild(view);
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.item_chat_parent_inter_form, parent, false);
                return new vhInterForms(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        ModelMentorChat m = showList.get(position);
        if(holder.getItemViewType() == 0){
//            ((vhChild) holder).tvFullName.setText(m.getFirstName() + "\n" + m.getLastName());

//            String avatar = m.getAvatar();
//            if(avatar != null && !avatar.isEmpty())
//                Picasso.get().load(avatar).fit().centerInside().into(((vhChild) holder).civAvatar);
//            else
//                ((vhChild) holder).civAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));

//            if(m.getGrades() != null){
//                int TotalGrade = m.getGrades().size();
//                if(TotalGrade > 0){
//                    ((vhChild) holder).tvDate.setText(Convert.String2ShortTime(m.getGrades().get(m.getGrades().size() - 1).getDate()));
//                    ((vhChild) holder).tvDate.setVisibility(View.VISIBLE);
//
//                    ((vhChild) holder).tvGrade.setVisibility(View.VISIBLE);
//                    double AverageGrade = (double) Convert.Str2Double(m.getAverageGrade());
//                    ((vhChild) holder).tvGrade.setText(m.getAverageGrade());
//                    if(AverageGrade < 3)
//                        ((vhChild) holder).tvGrade.setBackgroundResource(R.drawable.background_oval_red);
//                    else if(AverageGrade >= 3 && AverageGrade < 4)
//                        ((vhChild) holder).tvGrade.setBackgroundResource(R.drawable.background_oval_yellow);
//                    else if(AverageGrade >= 4 && AverageGrade < 5)
//                        ((vhChild) holder).tvGrade.setBackgroundResource(R.drawable.background_oval_green);
//                    else if(AverageGrade == 5)
//                        ((vhChild) holder).tvGrade.setBackgroundResource(R.drawable.background_oval_purple);
//
//                    ChildListGradesListAdapter adapter = new ChildListGradesListAdapter(context);
//                    ((vhChild) holder).recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
//                    ((vhChild) holder).recyclerView.setAdapter(adapter);
//
//                    if(TotalGrade > MAX_LAST_GRADES){
//                        m.getGrades().get(m.getGrades().size() - 1).setGrade("...");
//                        //m.getGrades().get(m.getGrades().size() - 1).setType(-1);
//                    }
//                    adapter.updateData(m.getGrades());
//                    adapter.setOnItemClickListener(new ChildListGradesListAdapter.OnItemClickListener(){
//                        @Override
//                        public void onItemClick(){
//                            if(onItemClickListener != null)
//                                onItemClickListener.onItemClick(0, showList.get(holder.getAdapterPosition()));
//                        }
//                    });
//                    ((vhChild) holder).recyclerView.setVisibility(View.VISIBLE);
//                }
//                else{
//                    ((vhChild) holder).recyclerView.setVisibility(View.GONE);
//                    ((vhChild) holder).tvDate.setVisibility(View.GONE);
//                    ((vhChild) holder).tvGrade.setBackgroundResource(R.drawable.background_oval_gray);
//                    ((vhChild) holder).tvGrade.setText("-");
//                }
//            }
//            else{
//                ((vhChild) holder).recyclerView.setVisibility(View.GONE);
//                ((vhChild) holder).tvDate.setVisibility(View.GONE);
//                ((vhChild) holder).tvGrade.setBackgroundResource(R.drawable.background_oval_gray);
//                ((vhChild) holder).tvGrade.setText("-");
//            }
        }
        else if(holder.getItemViewType() == 1){
//            String avatar = m.getAvatar();
//            if(!stringIsNullOrEmpty(avatar))
//                Picasso.get().load(avatar).fit().centerInside().into(((vhInterForms) holder).civAvatar);
//            else
//                ((vhInterForms) holder).civAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));

//            ((vhInterForms) holder).tvFullName.setText(m.getFirstName() + " " + m.getLastName());

            Date date = Convert.String2DateTime(m.getLastMessageDate());
            if(date != null){
                if(date.getDate() == new Date().getDate())
                    ((vhInterForms) holder).tvDate.setText(Convert.Date2ShortTime(date));
                else ((vhInterForms) holder).tvDate.setText(Convert.Date2ShortDate(date));
            }
            else ((vhInterForms) holder).tvDate.setText("");

            ((vhInterForms) holder).tvMessage.setText(m.getLastMessage());
            if(m.getNoCheckCount() > 0){
                ((vhInterForms) holder).tvMessage.setTextColor(context.getResources().getColor(R.color.blue_sea));
            }
            else
                ((vhInterForms) holder).tvMessage.setTextColor(context.getResources().getColor(R.color.gray_default));
        }
    }

    class vhChild extends RecyclerView.ViewHolder{
        CardView cvInfo;
        CircleImageView civAvatar;
        TextView tvFullName, tvTitle, tvDate, tvGrade;
        RecyclerView recyclerView;

        vhChild(@NonNull View itemView){
            super(itemView);
            cvInfo = itemView.findViewById(R.id.cvInfo);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvGrade = itemView.findViewById(R.id.tvGrade);
            recyclerView = itemView.findViewById(R.id.rvGradesList);

            cvInfo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    onItemClickListener.onItemClick(1, showList.get(getAdapterPosition()));
                }
            });

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
//                    if(showList.get(pos).getType() != null && showList.get(pos).getType().equals("InterForm"))
//                        onItemClickListener.onItemClick(1, showList.get(pos));
//                    else onItemClickListener.onItemClick(0, showList.get(pos));
                }
            });
        }
    }
    class vhInterForms extends RecyclerView.ViewHolder{
        CircleImageView civAvatar;
        TextView tvFullName, tvMessage, tvDate, tvNewMessageCount;

        vhInterForms(@NonNull View itemView){
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvNewMessageCount = itemView.findViewById(R.id.tvNewMessageCount);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(1, showList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
