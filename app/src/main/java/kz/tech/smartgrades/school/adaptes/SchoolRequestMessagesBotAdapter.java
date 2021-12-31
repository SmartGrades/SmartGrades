package kz.tech.smartgrades.school.adaptes;

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
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelSimilar;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class SchoolRequestMessagesBotAdapter extends RecyclerView.Adapter<SchoolRequestMessagesBotAdapter.ViewHolder>{

    private ArrayList<ModelSimilar> Similares;
    private SchoolActivity activity;
    private View oldView;

    private OnItemCLickListener onItemCLickListener;
    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener){
        this.onItemCLickListener = onItemCLickListener;
    }
    public interface OnItemCLickListener{
        void onItemClick(ModelSimilar similar);
    }

    public SchoolRequestMessagesBotAdapter(SchoolActivity activity){
        this.activity = activity;
        this.Similares = new ArrayList<>();
    }

    @Override
    public int getItemCount(){
        return Similares.size();
    }

    public void updateData(ArrayList<ModelSimilar> messages){
        onClear();
        this.Similares.addAll(messages);
        notifyDataSetChanged();
    }

    private void onClear(){
        if(Similares.size() > 0) Similares.clear();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_school_request_message_bot_similar, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSimilar m = Similares.get(position);
        if(!stringIsNullOrEmpty(m.getAvatar())) Picasso.get().load(m.getAvatar()).fit().centerCrop().into(holder.civAvatar);
        holder.tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        holder.tvClass.setText(m.getClassName());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvClass;
        ImageView ivChecked;
        ViewHolder(@NonNull View itemView){
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvClass = itemView.findViewById(R.id.tvClass);
            ivChecked = itemView.findViewById(R.id.ivChecked);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(oldView!=null){
                        ((ImageView)oldView.findViewById(R.id.ivChecked)).setImageDrawable(activity.getResources().getDrawable(R.drawable.checkbox_not_check));
                    }
                    oldView = itemView;
                    ivChecked.setImageDrawable(activity.getResources().getDrawable(R.drawable.checkbox_check));
                    if(onItemCLickListener!=null) onItemCLickListener.onItemClick(Similares.get(getAdapterPosition()));
                }
            });
        }
    }
}