package kz.tech.smartgrades.parents_module.family_group.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MentorsAdapter extends RecyclerView.Adapter<MentorsAdapter.ViewHolder> {

    private Context context;
    public MentorsAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
