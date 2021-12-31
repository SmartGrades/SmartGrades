package kz.tech.smartgrades.parent.adapters;

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
import kz.tech.smartgrades.auth.models.ModelUser;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.root.login.LoginKey;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;

public class ParentsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ParentActivity activity;
    private String PARENT_ID;

    private ArrayList<ModelUser> ParentsList;

    private OnItemCLickListener onItemCLickListener;
    public interface OnItemCLickListener {
        void onItemClick(ModelUser m);
    }
    public void setOnItemClickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public ParentsListAdapter(ParentActivity activity) {
        this.activity = activity;
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
        ParentsList = new ArrayList<>();
    }


    @Override
    public int getItemCount() {
        return ParentsList.size();
    }

    private void onClear(ArrayList<?> DataList) {
        if (DataList.size() > 0) DataList.clear();
    }

    public void updateData(ArrayList<ModelUser> parentsList) {
        onClear(ParentsList);
        if (!listIsNullOrEmpty(parentsList)){
            ParentsList.addAll(parentsList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parents_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelUser m = ParentsList.get(position);
        ((ViewHolder) holder).tvFullName.setText(m.getFirstName() + " " + m.getLastName());
        ((ViewHolder) holder).tvLogin.setText(m.getLogin());
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(((ViewHolder) holder).civAvatar);
        else ((ViewHolder) holder).civAvatar.setBackgroundResource(R.drawable.img_default_avatar);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvLogin, tvDate, tvAverageGrade, tvMessage;
        RecyclerView recyclerView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvLogin = itemView.findViewById(R.id.tvLogin);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvAverageGrade = itemView.findViewById(R.id.tvAverageGrade);
            recyclerView = itemView.findViewById(R.id.rvGradesList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemCLickListener == null) return;
                    onItemCLickListener.onItemClick(ParentsList.get(getAdapterPosition()));
                }
            });
        }
    }
}