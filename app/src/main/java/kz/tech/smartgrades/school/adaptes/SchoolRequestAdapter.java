package kz.tech.smartgrades.school.adaptes;

import android.annotation.SuppressLint;
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
import kz.tech.smartgrades.child.models.ModelInterForm;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelSchoolRequest;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class SchoolRequestAdapter extends RecyclerView.Adapter<SchoolRequestAdapter.ViewHolder>{

    private ArrayList<ModelInterForm> InterForms;
    private SchoolActivity activity;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemClick(ModelInterForm m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public SchoolRequestAdapter(SchoolActivity activity){
        this.activity = activity;
        this.InterForms = new ArrayList<>();
    }

    @Override
    public int getItemCount(){
        return InterForms.size();
    }

    private void onClear(){
        if(InterForms.size() > 0) InterForms.clear();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<ModelInterForm> InterForms){
        onClear();
        if(!listIsNullOrEmpty(InterForms)) this.InterForms.addAll(InterForms);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolRequestAdapter.ViewHolder holder, int position){
        ModelInterForm m = InterForms.get(position);

        if(!stringIsNullOrEmpty(m.getSourceAvatar()))
            Picasso.get().load(m.getSourceAvatar()).fit().centerCrop().into(holder.civAvatar);
        String name = m.getSourceFirstName() + " " + m.getSourceLastName();
        if (!stringIsNullOrEmpty(name))
            holder.tvName.setText(name);
        else holder.tvName.setText(m.getSourceLogin());

        if(m.getType().equals("ParentToSchool"))
            holder.tvTitle.setText("Заявка на добавление ученика");
        else if(m.getType().equals("MentorToSchool"))
            holder.tvTitle.setText("Заявка на добавление учителя");

        holder.tvDate.setText(m.getTimeStamp());
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
                    onItemClickListener.onItemClick(InterForms.get(getAdapterPosition()));
                }
            });
        }
    }
}