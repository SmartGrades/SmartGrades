package kz.tech.smartgrades.school.adaptes;

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
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.school.models.ModelUsersList;

public class SearchUsersAdapter extends RecyclerView.Adapter<SearchUsersAdapter.ViewHolder>
        implements Filterable {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ModelUsersList m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private Context context;
    private ArrayList<ModelUsersList> usersList;
    private ArrayList<ModelUsersList> usersListFilter;

    public SearchUsersAdapter(Context context) {
        this.context = context;
        this.usersList = new ArrayList<>();
        this.usersListFilter = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_parent_serach, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelUsersList model = usersList.get(position);
        Picasso.get().load(model.getAvatar()).fit().centerInside().into(holder.civAvatar);
        holder.tvFullName.setText(model.getFullName());
        holder.tvLogin.setText(model.getLogin());
        if (!model.getType().equals("Mentor")) holder.ivLogo.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void updateData(List<ModelUsersList> list) {
        usersList.addAll(list);
        notifyDataSetChanged();
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
                        onItemClickListener.onItemClick(usersList.get(getAdapterPosition()));
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
                if (charString.isEmpty()) {
                    usersListFilter = usersList;
                } else {
                    ArrayList<ModelUsersList> filteredList = new ArrayList<>();
                    for (ModelUsersList row : usersList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getFullName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    usersListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = usersListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                usersListFilter = (ArrayList<ModelUsersList>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
