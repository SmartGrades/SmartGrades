package kz.tech.smartgrades.school.adaptes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelRequestMessages;
import kz.tech.smartgrades.school.models.ModelSimilar;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class SchoolRequestMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<ModelRequestMessages> Messages;
    private SchoolActivity activity;
    private String ID;
    private ModelSimilar selectSimilar;

    public SchoolRequestMessagesAdapter(SchoolActivity activity){
        this.activity = activity;
        this.Messages = new ArrayList<>();
        ID = activity.login.loadUserDate(LoginKey.ID);
    }

    @Override
    public int getItemCount(){
        return Messages.size();
    }

    public void updateData(ArrayList<ModelRequestMessages> messages){
        onClear();
        this.Messages.addAll(messages);
        notifyDataSetChanged();
    }

    private void onClear(){
        if(Messages.size() > 0) Messages.clear();
    }

    @Override
    public int getItemViewType(int position){
        ModelRequestMessages m = Messages.get(position);
        if(m.getSourceId().equals(ID)){
            switch(m.getType()){
                case 12: return 1;
            }
        }
        else {
            switch(m.getType()){
                case 12: return 1;
            }
        }
        return 0;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        switch(viewType){
            case 1:
                return new vhParentMsg(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_request_message, parent, false));
            case 2:
                return new vhSchooltMsg(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_chat_msg_target, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        ModelRequestMessages m = Messages.get(position);

        switch(holder.getItemViewType()){
            case 1:
                if(!stringIsNullOrEmpty(m.getSourceAvatar())) Picasso.get().load(m.getSourceAvatar()).fit().centerCrop().into(((vhParentMsg)holder).civAvatar);
                if(!stringIsNullOrEmpty(m.getChildAvatar())) Picasso.get().load(m.getChildAvatar()).fit().centerCrop().into(((vhParentMsg)holder).civChildAvatar);

                ((vhParentMsg)holder).tvDate.setText(m.getDate());
                ((vhParentMsg)holder).tvMessage.setText(m.getContent());
                ((vhParentMsg)holder).tvChildFullName.setText(m.getChildFirstName() + " " + m.getChildLastName());
                ((vhParentMsg)holder).tvClass.setText(m.getClassName());

                if(!listIsNullOrEmpty(m.getSimilares())){
                    ((vhParentMsg)holder).clDataBot.setVisibility(View.VISIBLE);
                    ((vhParentMsg)holder).tvDateBot.setText(m.getDate());
                    SchoolRequestMessagesBotAdapter adapter = new SchoolRequestMessagesBotAdapter(activity);
                    ((vhParentMsg)holder).rvSimiliar.setAdapter(adapter);
                    ((vhParentMsg)holder).rvSimiliar.setLayoutManager(new LinearLayoutManager(activity));
                    adapter.updateData(m.getSimilares());
                    adapter.setOnItemCLickListener(new SchoolRequestMessagesBotAdapter.OnItemCLickListener(){
                        @Override
                        public void onItemClick(ModelSimilar similar){
                            selectSimilar = similar;
                            ((vhParentMsg)holder).btnUnite.setEnabled(true);
                            ((vhParentMsg)holder).btnUnite.setBackgroundResource(R.drawable.background_oval_blue_sea);
                            ((vhParentMsg)holder).btnUnite.setPadding(Convert.DpToPx(activity, 20), 0, Convert.DpToPx(activity, 20), 0);

                        }
                    });
                }

                break;
        }
    }

    class vhSchooltMsg extends RecyclerView.ViewHolder{
        CircleImageView civAvatar;
        TextView tvFullName, tvMessage, tvDate;
        vhSchooltMsg(@NonNull View itemView){
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvMessage = itemView.findViewById(R.id.tvTransactionDate);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
        }
    }
    class vhParentMsg extends RecyclerView.ViewHolder{
        ConstraintLayout clDataBot;
        CircleImageView civAvatar, civChildAvatar;
        TextView tvDate, tvMessage, tvChildFullName, tvClassLabel, tvClass,  tvDateBot, tvMessageBot;
        RecyclerView rvSimiliar;
        Button btnAdd, btnUnite;
        vhParentMsg(@NonNull View itemView){
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            civChildAvatar = itemView.findViewById(R.id.civMentorAvatar);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvChildFullName = itemView.findViewById(R.id.tvMentorFullName);
            tvClassLabel = itemView.findViewById(R.id.tvClassLabel);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvDateBot = itemView.findViewById(R.id.tvDateBot);
            tvMessageBot = itemView.findViewById(R.id.tvMessageBot);
            rvSimiliar = itemView.findViewById(R.id.rvSimiliar);
            clDataBot = itemView.findViewById(R.id.clDataBot);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnUnite = itemView.findViewById(R.id.btnUnite);

            btnAdd.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    activity.alert.onToast("Функция добавления еще не активирована");
                }
            });
            btnUnite.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    activity.alert.onToast("Функция объединения еще не активирована");
                }
            });
        }
    }
}