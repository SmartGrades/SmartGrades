package kz.tech.smartgrades.sponsor.adapters;

import android.content.Context;
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
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.sponsor.models.ModelSponsorAddChild;


public class SponsorChildSchoolsListAdapterAdapter extends RecyclerView.Adapter<SponsorChildSchoolsListAdapterAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelSchoolData> schoolList;

    public SponsorChildSchoolsListAdapterAdapter(Context context) {
        this.context = context;
        this.schoolList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sponsor_school, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelSchoolData m = schoolList.get(position);
        holder.tvSchoolName.setText(m.getName());
        holder.tvClass.setText(m.getSchoolClass());
    }

    @Override
    public int getItemCount() {
        return schoolList.size();
    }

    private void onClear() {
        if (schoolList.size() > 0) schoolList.clear();
    }

    public void updateData(ArrayList<ModelSchoolData> list) {
        onClear();
        schoolList.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSchoolName;
        TextView tvClass;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSchoolName = itemView.findViewById(R.id.tvSchoolName);
            tvClass = itemView.findViewById(R.id.tvClass);
        }
    }
}
