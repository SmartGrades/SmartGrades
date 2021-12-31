package kz.tech.smartgrades.school.adaptes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;


public class SchoolChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ModelDefaultMessages> arrayList;
    private SchoolActivity activity;

    public SchoolChatAdapter(SchoolActivity activity) {
        this.activity = activity;
        this.arrayList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void updateData(ArrayList<ModelDefaultMessages> arrayList2) {
        onClear();
        this.arrayList.addAll(arrayList2);
        notifyDataSetChanged();
    }

    private void onClear() {
        if (arrayList.size() > 0) arrayList.clear();
    }

    @Override
    public int getItemViewType(int position) {
        String id = activity.login.loadUserDate(LoginKey.ID);
        ModelDefaultMessages m = arrayList.get(position);

        /*if (arrayList.get(position).getMessageType().equals("message")) {
            if (m.getSourceId().equals(id)) return 1;
            else return 2;
        }
        else*/ return 3;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1://Mentor message
                return new vhMentorMsg(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_chat_msg, parent, false));
            case 2://School message
                return new vhSchooltMsg(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_msg_target, parent, false));
            case 3://Date
                return new vhMentorMsg(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_date, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String id = activity.login.loadUserDate(LoginKey.ID);
        ModelDefaultMessages m = arrayList.get(position);
        if (m == null) return;
        /*if (arrayList.get(position).getMessageType().equals("message")) {
            if (m.getSourceId().equals(id)) {
                ((vhMentorMsg)holder).tvMessage.setText(m.getMessage());
                ((vhMentorMsg)holder).tvDate.setText(m.getDate());
            }
            else {
                ((vhSchooltMsg)holder).tvMessage.setText(m.getMessage());
                ((vhSchooltMsg)holder).tvDate.setText(m.getDate());
            }
        }
        else{
            ((vhDate)holder).tvDate.setText(m.getDate());
        }*/
    }

    class vhSchooltMsg extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvMessage, tvDate;
        vhSchooltMsg(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvMessage = itemView.findViewById(R.id.tvTransactionDate);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
        }
    }
    class vhMentorMsg extends RecyclerView.ViewHolder {
        TextView tvMessage, tvDate, tvData;
        vhMentorMsg(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvTransactionDate);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvData = itemView.findViewById(R.id.tvData);
        }
    }
    class vhDate extends RecyclerView.ViewHolder {
        TextView tvDate;
        vhDate(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
        }
    }

}