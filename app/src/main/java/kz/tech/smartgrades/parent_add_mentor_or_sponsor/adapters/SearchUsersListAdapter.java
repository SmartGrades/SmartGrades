package kz.tech.smartgrades.parent_add_mentor_or_sponsor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.S;
import kz.tech.smartgrades.parent.model.ModelMentorSponsorRoom;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;

public class SearchUsersListAdapter extends RecyclerView.Adapter<SearchUsersListAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<ModelMentorSponsorRoom> dataList;
    private ArrayList<ModelMentorSponsorRoom> filterList;
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(ModelMentorSponsorRoom m);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SearchUsersListAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }

    public void updateData(ArrayList<ModelMentorSponsorRoom> list) {
        if (listIsNullOrEmpty(list)) return;
        dataList.clear();
        dataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_parent_serach, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelMentorSponsorRoom model = dataList.get(position);
        String avatar = model.getAvatar();
        if (avatar != null && !avatar.isEmpty()) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        else holder.civAvatar.setImageDrawable(context.getResources().getDrawable(R.drawable.img_default_avatar));

        holder.tvFullName.setText(model.getFirstName() + " " + model.getLastName());
        holder.tvLogin.setText(model.getLogin());
        if (model.getType().equals(S.INVESTOR))
            holder.ivLogo.setImageDrawable(context.getResources().getDrawable(R.drawable.img_icon_sponsor));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvFullName, tvLogin;
        ImageView ivLogo;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvLogin = itemView.findViewById(R.id.tvLogin);
            ivLogo = itemView.findViewById(R.id.ivLogo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onItemClick(dataList.get(position));
                    }
                }
            });
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) filterList = dataList;
                else {
                    ArrayList<ModelMentorSponsorRoom> filteredList = new ArrayList<>();
                    for (ModelMentorSponsorRoom row : dataList) {
                        if (row.getLogin().toLowerCase().contains(charString.toLowerCase())
                                || row.getPhoneOrMail().toLowerCase().contains(charString.toLowerCase())
                                || row.getFirstName().toLowerCase().contains(charString.toLowerCase())
                                || row.getLastName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filterList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<ModelMentorSponsorRoom>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
