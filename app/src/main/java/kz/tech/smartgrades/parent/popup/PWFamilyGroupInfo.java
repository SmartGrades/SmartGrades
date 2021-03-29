package kz.tech.smartgrades.parent.popup;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.constraintlayout.widget.ConstraintLayout;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PWFamilyGroupInfo extends PopupWindow implements View.OnClickListener{

    public ParentActivity activity;
    private ConstraintLayout clFamilyGroupInfo;

    public PWFamilyGroupInfo(int width, int height, ParentActivity activity) {
        super(width, height);
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pw_family_group_info, null);
        setContentView(view);
        clFamilyGroupInfo = view.findViewById(R.id.clFamilyGroupInfo);
        clFamilyGroupInfo.setOnClickListener(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.clFamilyGroupInfo)
            dismiss();
    }
}
