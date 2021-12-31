package kz.tech.smartgrades.parent.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.adapters.InteractionToolStepsToysAdapter;

public class DialogInteractionToolStepsToys extends Dialog {

    public interface OnItemClickListener {
        void onItemClick(int image);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private Dialog dialog;

    public DialogInteractionToolStepsToys(@NonNull Context context) {
        super(context);
    }

    public DialogInteractionToolStepsToys(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        dialog = this;

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_interaction_tool_steps_toys, null, false);
        this.setContentView(view);

        ImageView ivBack = view.findViewById(R.id.ivBack);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        RecyclerView recyclerView = view.findViewById(R.id.rvGradesList);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 5));
        InteractionToolStepsToysAdapter adapter = new InteractionToolStepsToysAdapter(context);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new InteractionToolStepsToysAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int image) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(image);
                    dialog.dismiss();
                    onItemClickListener = null;
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    protected DialogInteractionToolStepsToys(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
