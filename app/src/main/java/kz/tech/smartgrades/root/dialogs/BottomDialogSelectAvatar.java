package kz.tech.smartgrades.root.dialogs;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.smartgrades.root.adapters.AvatarAdapter;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;

public class BottomDialogSelectAvatar extends BottomSheetDialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onImageClick(int imageAvatar);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private BottomSheetDialog dialog;
    private static final String[] param = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "orn:ver"};
    private static final String[] paramRV = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "mrg:8,8,8,0", "parH:220", "hfs:true", "GridC:4", "layMan:glm"};
    public BottomDialogSelectAvatar(@NonNull Context context, CircleImageView civAvatar, String status) {
        super(context);
        dialog = this;
        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, param);
        this.setContentView(linearLayout);
        RecyclerView recyclerView = new CustomRecyclerView().onCustom(context, paramRV);
        linearLayout.addView(recyclerView);


        AvatarAdapter adapter = new AvatarAdapter(context);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AvatarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int image) {
                civAvatar.setImageResource(image);
                if (onItemClickListener != null) {
                    onItemClickListener.onImageClick(image);
                }
                if (dialog != null) {
                    dialog.cancel();
                    dialog = null;
                }

            }
        });
        switch (status) {
            case "Father": adapter.setData(1); break;
            case "Mother": adapter.setData(2); break;
            case "Son": adapter.setData(3); break;
            case "Daughter": adapter.setData(4); break;
        }


    }
    public BottomDialogSelectAvatar(@NonNull Context context, int theme) {
        super(context, theme);
    }
    protected BottomDialogSelectAvatar(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

}
