package kz.tech.smartgrades.mentor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelMentorClass;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._Web.SoapRequest;

public class MentorClassListAdapter extends RecyclerView.Adapter<MentorClassListAdapter.ViewHolder>{

    private ArrayList<ModelMentorClass> mClasses;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onSelectClass(ModelMentorClass m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public MentorClassListAdapter(){
        mClasses = new ArrayList<>();
    }

    public void updateData(ArrayList<ModelMentorClass> mClasses){
        onClear();
        if(!listIsNullOrEmpty(mClasses)) this.mClasses.addAll(mClasses);
        notifyDataSetChanged();
    }
    private void onClear(){
        if(!listIsNullOrEmpty(mClasses)) mClasses.clear();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelMentorClass m = mClasses.get(position);
        holder.tvName.setText(m.getName());
    }

    @Override
    public int getItemCount(){
        return mClasses.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentor_select_class_for_inter_form, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;

        ViewHolder(@NonNull View view){
            super(view);
            tvName = view.findViewById(R.id.tvName);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onSelectClass(mClasses.get(getAdapterPosition()));
                }
            });
        }
    }
}
