package kz.tech.smartgrades.parents_module.settings.locality;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.var_resources.VarID;

public class LocalityAdapter extends RecyclerView.Adapter<LocalityAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(String language);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private Context context;
    private ArrayList<LocalityModel> arrayList;
    private Resources res;
    public LocalityAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new LocalityItemList(context));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocalityModel model = arrayList.get(position);
        if (position == 0) {
            holder.vTop.setVisibility(View.VISIBLE);
        }
        if (model.getImage() != 0) {
            holder.ivImage.setImageResource(model.getImage());
        }
        if (model.getText() != null) {
            holder.tvText.setText(model.getText());
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        View vTop, vBottom;
        TextView tvText;
        ImageView ivImage;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            vTop = (View) itemView.findViewById(VarID.ID_V_TOP_LOCALITY);
            vBottom = (View) itemView.findViewById(VarID.ID_V_BOTTOM_LOCALITY);
            tvText = (TextView) itemView.findViewById(VarID.ID_TV_TEXT_LOCALITY);
            ivImage = (ImageView) itemView.findViewById(VarID.ID_IV_IMAGE_LOCALITY);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(arrayList.get(position).getLanguage());
                    }
                }
            });
        }
    }
    public void setData(Resources res) {
        String[] languages = res.getStringArray(R.array.languages);
        arrayList.add(new LocalityModel(R.mipmap.usa_flag, languages[0], "en"));
        arrayList.add(new LocalityModel(R.mipmap.ru_flag, languages[1], "ru"));
        notifyDataSetChanged();
    }
    public void changeLanguage(Resources res) {
        String[] languages = res.getStringArray(R.array.languages);
        for (int x = 0; x < arrayList.size(); x++) {
            arrayList.get(x).setText(languages[x]);
            notifyItemChanged(x);
        }
    }
}