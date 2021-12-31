package kz.tech.smartgrades.parent.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.model.ModelChildData2;

public class DialogInteractionToolTemporaryCoinsBank extends Dialog implements View.OnClickListener {


    public interface OnItemClickListener {
        void onItemClick(int count, ImageView img, String steps, String target);
        void onBackClick();
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private ParentActivity activity;

    private Dialog dialog;
    private Context context;

    private ModelChildData2 m;

    public DialogInteractionToolTemporaryCoinsBank(@NonNull Context context) {
        super(context);
    }

    public DialogInteractionToolTemporaryCoinsBank(@NonNull Context context, int themeResId, ParentActivity activity, ModelChildData2 m) {
        super(context, themeResId);
        this.context = context;
        this.activity = activity;
        dialog = this;
      //  dialog.setCanceledOnTouchOutside(true);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_interaction_tool_temporary_coins_bank, null, false);
        this.setContentView(view);

        LinearLayout frame1 = view.findViewById(R.id.frame1);

        ScrollView frame2 = view.findViewById(R.id.frame2);

        ImageView ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        TextView tvTitle = view.findViewById(R.id.tvTitle);

        CircleImageView civAvatar = view.findViewById(R.id.civAvatar);
        TextView tvLogin = view.findViewById(R.id.tvLogin);

        if (m.getLogin() != null) {
            tvLogin.setText(m.getLogin());
        }
        if (m.getAvatar() != null) {
            Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);
        }
    }

    protected DialogInteractionToolTemporaryCoinsBank(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }



    private void onBack() {
        if (onItemClickListener != null) {
            onItemClickListener.onBackClick();
        }
        dialog.dismiss();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
        }
    }
}
