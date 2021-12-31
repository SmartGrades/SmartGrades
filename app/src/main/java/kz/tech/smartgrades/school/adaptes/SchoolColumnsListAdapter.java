package kz.tech.smartgrades.school.adaptes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._Web.SoapRequest;


public class SchoolColumnsListAdapter extends RecyclerView.Adapter<SchoolColumnsListAdapter.ViewHolder>{

    private SchoolActivity activity;
    private String SCHOOL_ID;

    private ArrayList<ModelSchoolClassesColumn> Columns;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onSelectColumn(ModelSchoolClassesColumn m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public SchoolColumnsListAdapter(SchoolActivity activity){
        this.activity = activity;
        this.Columns = new ArrayList<>();
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    public void updateData(ArrayList<ModelSchoolClassesColumn> Columns){
        onClear();
        if(!listIsNullOrEmpty(Columns)) this.Columns.addAll(Columns);
        notifyDataSetChanged();
    }
    private void onClear(){
        if(Columns.size() > 0) Columns.clear();
    }


    @Override
    public int getItemCount(){
        return Columns.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_school_column_list, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolClassesColumn m = Columns.get(position);
        holder.tvTitle.setText(m.getName());
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ViewHolder(@NonNull View view){
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onSelectColumn(Columns.get(getAdapterPosition()));
                }
            });
        }
    }
}
