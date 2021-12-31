package kz.tech.smartgrades.school.adaptes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;


public class SchoolClassListAdapterV2 extends RecyclerView.Adapter<SchoolClassListAdapterV2.ViewHolder>{

    private Context context;
    private ArrayList<ModelSchoolClass> mClasses;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(ModelSchoolClass m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public SchoolClassListAdapterV2(Context context){
        this.context = context;
        this.mClasses = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolClass m = mClasses.get(position);
        holder.tvText.setText(m.getName());
    }
    @Override
    public int getItemCount(){
        return mClasses.size();
    }
    private void onClear(){
        if(mClasses.size() > 0) mClasses.clear();
    }

    public void updateData(ArrayList<ModelSchoolClass> list){
        onClear();
        if(!listIsNullOrEmpty(list)) mClasses.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvText;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onItemClick(mClasses.get(getAdapterPosition()));
                }
            });
        }
    }
}
