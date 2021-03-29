package kz.tech.smartgrades.school.adaptes;

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
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelSchoolRequest;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class SchoolRequestAdapter extends RecyclerView.Adapter<SchoolRequestAdapter.ViewHolder>{

    private ArrayList<ModelSchoolRequest> requests;
    private SchoolActivity activity;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(ModelSchoolRequest m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public SchoolRequestAdapter(SchoolActivity activity){
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
    public void updateData(ArrayList<ModelSchoolRequest> requests){
        onClear();
        this.requests.addAll(requests);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolRequestAdapter.ViewHolder holder, int position){
        ModelSchoolRequest m = requests.get(position);
        String avatar = m.getSourceAvatar();
        if(!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).fit().centerCrop().into(holder.civAvatar);
        if(m.getSourceType() != null && m.getSourceType().equals("Mentor")){
            if(!stringIsNullOrEmpty(m.getSourceFirstName() + m.getSourceLastName()))
                holder.tvName.setText(m.getSourceFirstName() + " " + m.getSourceLastName());
            else holder.tvName.setVisibility(View.GONE);
            if(!stringIsNullOrEmpty(m.getTitle())) holder.tvTitle.setText(m.getTitle());
            else holder.tvTitle.setText("Заявка на добавление учителя");
        }
        else {
            if(!stringIsNullOrEmpty(m.getParentFirstName() + m.getParentLastName()))
                holder.tvName.setText(m.getParentFirstName() + " " + m.getParentLastName());
            else holder.tvName.setVisibility(View.GONE);
            if(!stringIsNullOrEmpty(m.getTitle())) holder.tvTitle.setText(m.getTitle());
            else holder.tvTitle.setText("Заявка на добавление ребенка");
        }

        if(!stringIsNullOrEmpty(m.getLastMessage())) holder.tvLastMassage.setText(m.getLastMessage());
        else holder.tvLastMassage.setVisibility(View.GONE);
        if(!stringIsNullOrEmpty(m.getLastMessageDate())) holder.tvDate.setText(m.getLastMessageDate());
        else holder.tvDate.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public SchoolRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new SchoolRequestAdapter.ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_school_request, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civAvatar;
        TextView tvName, tvTitle, tvLastMassage, tvDate;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvLastMassage = itemView.findViewById(R.id.tvLastMassage);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener == null) return;
                    onItemClickListener.onItemClick(requests.get(getAdapterPosition()));
                }
            });
        }
    }
}