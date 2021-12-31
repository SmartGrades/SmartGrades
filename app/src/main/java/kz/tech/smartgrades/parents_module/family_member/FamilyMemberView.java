package kz.tech.smartgrades.parents_module.family_member;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomCardView;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;

public class FamilyMemberView extends FrameLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_CONTAINER_CV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:8,5,8,0",
            "Radius:8", "backC:WHITE"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:CardView", "layW:mPrt", "layH:wCnt",
            "orn:ver"};
    private static final String[] PARAM_FAMILY_MEMBER_AVATAR = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "parW:80", "parH:80",
            "GRAV:HOR"};
    private static final String[] PARAM_FAMILY_MEMBER_NAME = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:40",
            "txtC:BLACK", "GRAV:HOR", "grv:LCV", "pad:10,5,5,5"};
    private static final String[] PARAM_FAMILY_MEMBER_STATUS = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:40",
            "txtC:BLACK", "GRAV:HOR", "grv:LCV", "pad:10,5,5,5"};
    private static final String[] PARAM_FAMILY_MEMBER_PIN = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:40",
            "txtC:BLACK", "GRAV:HOR", "grv:LCV", "pad:10,5,5,5"};

    private Context context;
    private CircleImageView civAvatarFamilyMember;
    private TextView tvNameFamilyMember, tvStatusFamilyMember, tvQuickAccessCode;
    private Resources res;
    public FamilyMemberView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.context = context;
        this.res = res;
        CardView cardView = new CustomCardView().onCustom(context, PARAM_CONTAINER_CV);
        this.addView(cardView);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        cardView.addView(linearLayout);

        civAvatarFamilyMember = new CustomCircleImageView().onCustom(context, PARAM_FAMILY_MEMBER_AVATAR);
        linearLayout.addView(civAvatarFamilyMember);

        tvNameFamilyMember = new CustomTextView().onCustom(context, PARAM_FAMILY_MEMBER_NAME, null);
        linearLayout.addView(tvNameFamilyMember);

        tvStatusFamilyMember = new CustomTextView().onCustom(context, PARAM_FAMILY_MEMBER_STATUS, null);
        linearLayout.addView(tvStatusFamilyMember);

        tvQuickAccessCode = new CustomTextView().onCustom(context, PARAM_FAMILY_MEMBER_PIN, null);
        linearLayout.addView(tvQuickAccessCode);

    }
    public FamilyMemberView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public FamilyMemberView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FamilyMemberView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setData(String avatar, String name, String status, String pin) {
        if (avatar != null) { if (!avatar.equals("")) { Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatarFamilyMember); } }
        if (name != null) { if (!name.equals("")) {
            String firstText = res.getString(R.string.name) + ": ";
            int startIndex = 0;
            int endIndex = firstText.length();
            SpannableString spannableString = new SpannableString(firstText + name);
            //spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, 0);
            spannableString.setSpan(new ForegroundColorSpan(Color.rgb(152, 152, 152)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvNameFamilyMember.setText(spannableString);
        } }
        if (status != null) { if (!status.equals("")) {
            String selectStatus = "";
            switch (status) {
                case "Father": selectStatus = res.getString(R.string.father); break;
                case "Mother": selectStatus = res.getString(R.string.mother); break;
                case "Son": selectStatus = res.getString(R.string.son); break;
                case "Daughter": selectStatus = res.getString(R.string.daughter); break;
            }
            String firstText = res.getString(R.string.status) + ": ";
            int startIndex = 0;
            int endIndex = firstText.length();
            SpannableString spannableString = new SpannableString(firstText + selectStatus);
            //spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, 0);
            spannableString.setSpan(new ForegroundColorSpan(Color.rgb(152, 152, 152)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvStatusFamilyMember.setText(spannableString);
        } }
        if (pin != null) { if (!pin.equals("")) {
            String firstText = res.getString(R.string.quiack_access_code) + ": ";
            int startIndex = 0;
            int endIndex = firstText.length();
            SpannableString spannableString = new SpannableString(firstText + pin);
            //spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, 0);
            spannableString.setSpan(new ForegroundColorSpan(Color.rgb(152, 152, 152)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvQuickAccessCode.setText(spannableString);
        } }

    }
}
