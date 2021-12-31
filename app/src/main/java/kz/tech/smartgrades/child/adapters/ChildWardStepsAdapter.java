package kz.tech.smartgrades.child.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.smartgrades.Convert;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;
import kz.tech.smartgrades.parent.model.ModelInteractionToolSteps;

public class ChildWardStepsAdapter extends RecyclerView.Adapter<ChildWardStepsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelInteractionToolSteps> arrayList;
    private ArrayList<ModelDefaultMessages> arrayMultiType;
    private RecyclerView[] recyclerView;

    private int windowWidth;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(RecyclerView rvDetailList, String stepName);
    }
    public void setOnItemClickListener(ChildWardStepsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private int getWindowWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvStepName.setText(arrayList.get(position).getTarget());
        holder.tvStepDone.setText(arrayList.get(position).getDone() + "/" + arrayList.get(position).getCount());
        Picasso.get().load(arrayList.get(position).getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civStepAvatar);
        Picasso.get().load(arrayList.get(position).getChildAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civChildAvatar);

        int getDone = Convert.Str2Int(arrayList.get(position).getDone());
        int getTotal = Convert.Str2Int(arrayList.get(position).getCount());
        holder.flChildAvatarPosition.animate().translationX((windowWidth / getTotal) * getDone);

        ArrayList<ModelDefaultMessages> modelDefaultChat = new ArrayList<>();
        for (int i = 0; i < arrayMultiType.size(); i++){
            if (arrayMultiType.get(i).getMessage().equals(arrayList.get(position).getTarget())) modelDefaultChat.add(arrayMultiType.get(i));
        }
        recyclerView[position] = holder.rvDetailList;
        ChildUsersListAdapter adapter = new ChildUsersListAdapter(context);
        recyclerView[position].setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter.updateData(modelDefaultChat);
        recyclerView[position].setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void onClear() {
        if (arrayList.size() > 0) arrayList.clear();
    }

    public void updateGroup(ArrayList<ModelInteractionToolSteps> arrayListe, ArrayList<ModelDefaultMessages> modelDefaultChat) {
        //onClear();
        this.arrayList = arrayListe;
        this.arrayMultiType = modelDefaultChat;
        notifyDataSetChanged();
    }

    public void updateData() {
        notifyItemRangeChanged(0, arrayList.size());
    }


    public ChildWardStepsAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();

        windowWidth = getWindowWidth();
        windowWidth -= windowWidth / 10;

        recyclerView = new RecyclerView[6];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ward_tool_child_step, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStepName;
        TextView tvStepDone;
        TextView tvStepsMoreDetails;
        ImageView civStepAvatar;
        FrameLayout flChildAvatarPosition;
        CircleImageView civChildAvatar;
        RecyclerView rvDetailList;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStepName = itemView.findViewById(R.id.tvStepName);
            tvStepDone = itemView.findViewById(R.id.tvStepDone);
            tvStepsMoreDetails = itemView.findViewById(R.id.tvStepsMoreDetails);
            civStepAvatar = itemView.findViewById(R.id.civStepAvatar);
            flChildAvatarPosition = itemView.findViewById(R.id.flChildAvatarPosition);
            civChildAvatar = itemView.findViewById(R.id.civMentorAvatar);
            rvDetailList = itemView.findViewById(R.id.rvDetailList);

            tvStepsMoreDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    //onItemClickListener.onItemClick(recyclerView[pos], arrayList.get(getAdapterPosition()).getTarget());
                    if (recyclerView[pos].getVisibility() == View.VISIBLE) recyclerView[pos].setVisibility(View.GONE);
                    else recyclerView[pos].setVisibility(View.VISIBLE);
                }
            });
        }
    }
}