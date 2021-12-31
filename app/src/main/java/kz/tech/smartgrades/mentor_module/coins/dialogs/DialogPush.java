package kz.tech.smartgrades.mentor_module.coins.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor_module.MentorActivity;
import kz.tech.smartgrades.mentor_module.coins.models.ModelChildList;
import kz.tech.smartgrades.mentor_module.coins.models.ModelMentorPushList;
import kz.tech.smartgrades.parents_module.family_group.dialogs_page.interaction_form.ModelChildArray;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomTextView;
import kz.tech.smartgrades.root.ui.CustomView.CustomView;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class DialogPush extends Dialog {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        //void onAddOrRefuseClick(String a, List<ModelChildArray> l, String d, String m, String n);
        void onAddClick(List<ModelChildList> list);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_LEFT_BUTTON = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5"};
    private static final String[] PARAM_TITLE = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:56,0,56,0", "pad:5,5,5,5",
            "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5",
            "BordC:GRAY_THREE", "BordW:2", "img:avatar", "Rule:RIGHT"};

    private static final String[] PARAM_CONTAINER_SV = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:0,55,0,0"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:ScrLay", "layW:mPrt", "layH:wCnt", "orn:ver"};

    private static final String[] PARAM_CONTAINER_FL_1 = {"prt:LinLay", "layW:mPrt", "layH:wCnt"};
    private static final String[] PARAM_CONTAINER_FL_2 = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:0,40,0,0", "backC:WHITE"};

    private static final String[] PARAM_DATE_CREATE = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:40", "txtC:BLACK", "grv:CHCV", "txtS:18"};
    private static final String[] PARAM_PHOTO = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:40", "parH:40", "mrg:5,5,0,0", "GRAV:TOP_LEFT"};
    private static final String[] PARAM_NAME = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "mrg:0,50,0,0",
            "parW:50", "parH:20",  "GRAV:TOP_LEFT", "txtC:BLACK", "grv:CHCV"};
    private static final String[] PARAM_MSG = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:50,0,0,0",
            "GRAV:TOP_RIGHT", "txtC:BLACK", "grv:CHCV", "txtS:14", "pad:5,5,5,5"};



    private static final String[] PARAM_CHILD_CONTAINER_LL = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "mrg:60,50,0,0", "orn:ver"};
    private static final String[] PARAM_CHILD_CONTAINER_FL = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "parH:50"};
    private static final String[] PARAM_CHILD_PHOTO = {"prt:FrmLay", "layW:wCnt", "layH:wCnt", "parW:40", "parH:40", "GRAV:LEFT_VER"};
    private static final String[] PARAM_CHILD_NAME = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:40", "mrg:50,0,0,0",
            "GRAV:VER", "grv:LCV", "txtC:GRAY_THREE", "pad:5,5,5,5"};
    private static final String[] PARAM_CHILD_TITLE_FORM = {"prt:FrmLay", "layW:mPrt", "layH:wCnt",
            "txtC:BLACK", "grv:CHCV", "txtS:14", "pad:5,5,5,5" };
    private static final String[] PARAM_VIEW_LINE = {"prt:LinLay", "layW:mPrt", "layH:mPrt", "parH:1", "backC:GRAY_THREE"};

    private static final String[] PARAM_CHILD_CONTAINER_LL_LL = {"prt:LinLay", "layW:mPrt", "layH:wCnt",
            "parH:30", "orn:hor", "WeiSum:2"};
    private static final String[] PARAM_CHILD_TEXT_GRAY = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1",
            "grv:LCV", "txtC:GRAY_THREE"};
    private static final String[] PARAM_CHILD_TEXT_BLACK = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1",
            "grv:LCV", "txtC:BLACK",  "pad:20,0,0,0"};
    private static final String[] PARAM_SUPER_TEXT_GRAY = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1",
            "grv:LCV", "txtC:GRAY_THREE",  "pad:20,0,0,0"};
    private static final String[] PARAM_CHILD_BUTTONS = {"prt:LinLay", "layW:wCnt", "layH:wCnt", "mrg:0,20,0,20",
            "parH:40", "orn:hor", "WeiSum:2", "GRAV:RIGHT"};
    private static final String[] PARAM_CHILD_REFUSE = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", //"mrg:0,0,10,0",
            "txtC:RED_ONE", "grv:CHCV", "txtS:14"};
    private static final String[] PARAM_CHILD_ADD = {"prt:LinLay", "layW:0", "layH:mPrt", "wei:1", "mrg:0,0,20,0",
            "txtC:WHITE", "grv:CHCV", "backR:RED_ONE", "txtS:14"};
    private Context context;
    private Resources res;
    private Dialog dialog;
    private LinearLayout linearLayout;
    private List<ModelMentorPushList> arrayList;
    private String avatar, name;
    private MentorActivity activity;
    public DialogPush(@NonNull Context context) {
        super(context);
    }
    public DialogPush(Context c, int themeResId, Resources res, String avatar, String name,
                      List<ModelMentorPushList> list, MentorActivity activity) {
        super(c, themeResId);
        this.context = c;
        this.res = res;
        dialog = this;
        arrayList = list;
        this.avatar = avatar;
        this.name = name;
        this.activity = activity;


        FrameLayout frameLayout = new CustomFrameLayout().onCustom(context, PARAM);
        this.setContentView(frameLayout);

        frameLayout.setBackgroundColor(ColorsRGB.GRAY_TWO);

        Toolbar toolbar = new CustomToolbar().onCustom(context, PARAM_TOOLBAR);
        frameLayout.addView(toolbar);
        RelativeLayout rlToolbar = new CustomRelativeLayout().onCustom(context, PARAM_TOOLBAR_RL);
        toolbar.addView(rlToolbar);
        ImageView ivLeftButton = new CustomImageView().onCustom(context, PARAM_LEFT_BUTTON, R.mipmap.red_arrow_left);
        rlToolbar.addView(ivLeftButton);
        ivLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        TextView tvTitle = new CustomTextView().onCustom(context, PARAM_TITLE, res.getString(R.string.form_of_interaction));
        rlToolbar.addView(tvTitle);

        CircleImageView civAvatar = new CustomCircleImageView().onCustom(context, PARAM_AVATAR);
        rlToolbar.addView(civAvatar);
        Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);

        ScrollView scrollView = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        frameLayout.addView(scrollView);


        linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        scrollView.addView(linearLayout);

        String p = "parent";
        String m = "mentor";

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getType().equals(p)) {
                onParentAction(arrayList.get(i));
            } else if (arrayList.get(i).getType().equals(m)) {
                onMentorAction(arrayList.get(i));
            }

        }
    }

    protected DialogPush(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    private void onParentAction(ModelMentorPushList m) {
        ///////////           1              //////////////////
        FrameLayout fl1 = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL_1);
        linearLayout.addView(fl1);

        TextView tvDate = new CustomTextView().onCustom(context, PARAM_DATE_CREATE, m.getDate());
        fl1.addView(tvDate);

        ///////////           2              //////////////////
        FrameLayout fl2 = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL_2);
        fl1.addView(fl2);

        CircleImageView civAvatarP = new CustomCircleImageView().onCustom(context, PARAM_PHOTO);
        fl2.addView(civAvatarP);
        Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatarP);

        TextView tvNameP = new CustomTextView().onCustom(context, PARAM_NAME, m.getName());
        fl2.addView(tvNameP);

        TextView tvMsg = new CustomTextView().onCustom(context, PARAM_MSG, m.getMsg());
        fl2.addView(tvMsg);


        ///////////           3              //////////////////
        LinearLayout ll1 = new CustomLinearLayout().onCustom(context, PARAM_CHILD_CONTAINER_LL);
        fl2.addView(ll1);

        //List<ModelChildArray> list2 = m.getChildList();

        for (int i = 0; i < m.getChildList().size(); i++) {
           // Object o = m.getChildList().get(i);
            /*HashMap hashMap = (HashMap);
            String avatar = String.valueOf(hashMap.get("avatar"));
            String name = String.valueOf(hashMap.get("name"));*/

            FrameLayout flChild = new CustomFrameLayout().onCustom(context, PARAM_CHILD_CONTAINER_FL);
            ll1.addView(flChild);

            CircleImageView civAvatarC = new CustomCircleImageView().onCustom(context, PARAM_CHILD_PHOTO);
            flChild.addView(civAvatarC);
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatarC);

            TextView tvNameC = new CustomTextView().onCustom(context, PARAM_CHILD_NAME,name);
            flChild.addView(tvNameC);
        }


        View v1 = new CustomView().onCustom(context, PARAM_VIEW_LINE);
        ll1.addView(v1);


        TextView tvInteractionForm = new CustomTextView().onCustom(context,
                PARAM_CHILD_TITLE_FORM, res.getString(R.string.form_of_interaction) + ":");
        ll1.addView(tvInteractionForm);

        if (m.getAccrualOfCoins() != null) {
            LinearLayout llCoins = new CustomLinearLayout().onCustom(context, PARAM_CHILD_CONTAINER_LL_LL);
            ll1.addView(llCoins);

            String[] arr = m.getAccrualOfCoins().split(":");
            String textNum1 = arr[0];
            String textNum2 = arr[1];

            String textResult = res.getString(R.string.from) + " " + textNum1 +
                    " " + res.getString(R.string.to) + " " + textNum2;

            TextView tvCoins1 = new CustomTextView().onCustom(context, PARAM_CHILD_TEXT_GRAY, res.getString(R.string.accrual_of_coins));
            llCoins.addView(tvCoins1);

            TextView tvCoins2 = new CustomTextView().onCustom(context, PARAM_CHILD_TEXT_BLACK, textResult.toLowerCase());
            llCoins.addView(tvCoins2);

            if (m.getAccrualOfCoinsSuper() != null) {
                LinearLayout llCoinsSuper = new CustomLinearLayout().onCustom(context, PARAM_CHILD_CONTAINER_LL_LL);
                ll1.addView(llCoinsSuper);

                TextView tvCoinsSuper1 = new CustomTextView().onCustom(context, PARAM_SUPER_TEXT_GRAY, res.getString(R.string.super_achievement));
                llCoinsSuper.addView(tvCoinsSuper1);

                TextView tvCoinsSuper2 = new CustomTextView().onCustom(context, PARAM_CHILD_TEXT_BLACK, m.getAccrualOfCoinsSuper());
                llCoinsSuper.addView(tvCoinsSuper2);
            }

        }//  PARAM_SUPER_TEXT_GRAY
        //  super_achievement
        if (m.getAccrualOfOffset() != null) {
            LinearLayout llOffset = new CustomLinearLayout().onCustom(context, PARAM_CHILD_CONTAINER_LL_LL);
            ll1.addView(llOffset);

            String[] arr = m.getAccrualOfOffset().split(":");
            String textNum1 = arr[0];
            String textNum2 = arr[1];

            String textResult = res.getString(R.string.from) + " " + textNum1 +
                    " " + res.getString(R.string.to) + " " + textNum2;

            TextView tvOffset1 = new CustomTextView().onCustom(context, PARAM_CHILD_TEXT_GRAY, res.getString(R.string.accrual_of_offset));
            llOffset.addView(tvOffset1);

            TextView tvOffset2 = new CustomTextView().onCustom(context, PARAM_CHILD_TEXT_BLACK, textResult.toLowerCase());
            llOffset.addView(tvOffset2);


            if (m.getAccrualOfOffsetSuper() != null) {
                LinearLayout llOffsetSuper = new CustomLinearLayout().onCustom(context, PARAM_CHILD_CONTAINER_LL_LL);
                ll1.addView(llOffsetSuper);

                TextView tvOffsetSuper1 = new CustomTextView().onCustom(context, PARAM_SUPER_TEXT_GRAY, res.getString(R.string.super_achievement));
                llOffsetSuper.addView(tvOffsetSuper1);

                TextView tvOffsetSuper2 = new CustomTextView().onCustom(context, PARAM_CHILD_TEXT_BLACK, m.getAccrualOfOffsetSuper());
                llOffsetSuper.addView(tvOffsetSuper2);

            }
        }


        LinearLayout llButtons = new CustomLinearLayout().onCustom(context, PARAM_CHILD_BUTTONS);
        ll1.addView(llButtons);

        TextView tvRefuse = new CustomTextView().onCustom(context, PARAM_CHILD_REFUSE, res.getString(R.string.refuse));
        llButtons.addView(tvRefuse);
        tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    String msg = res.getString(R.string.you_refused_to_add_a_ward);
                    String date = activity.iDate.getCurrentDate();
                    //onItemClickListener.onAddOrRefuseClick(avatar, list2, date, msg, name);
                    m.setMsg(msg);
                    m.setDate(date);

                    //   fl1.removeAllViewsInLayout();
                    //linearLayout.removeView(fl1);

                    onMentorAction(m);
                }
            }
        });

        TextView tvAdd = new CustomTextView().onCustom(context, PARAM_CHILD_ADD, res.getString(R.string.add));
        llButtons.addView(tvAdd);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    List<ModelChildList> list3 = new ArrayList<>();
                    for (int z = 0; z < m.getChildList().size(); z++) {
                        Object o = m.getChildList().get(z);
                        HashMap hashMap = (HashMap) o;
                        String avatar = String.valueOf(hashMap.get("avatar"));
                        String idChild = String.valueOf(hashMap.get("id"));
                        String name = String.valueOf(hashMap.get("name"));
                        list3.add(new ModelChildList(m.getAccrualOfCoins(), m.getAccrualOfCoinsSuper(),
                                m.getAccrualOfOffset(), m.getAccrualOfOffsetSuper(), avatar,
                                idChild, m.getIdLogin(),  name));
                    }
                    onItemClickListener.onAddClick(list3);

                    String msg = res.getString(R.string.you_added_a_ward);
                    String date = activity.iDate.getCurrentDate();
                    //onItemClickListener.onAddOrRefuseClick(avatar, list2, date, msg, name);
                    m.setMsg(msg);
                    m.setDate(date);

                 //   fl1.removeAllViewsInLayout();
                    //linearLayout.removeView(fl1);
                    onMentorAction(m);
                }
            }
        });

    }

    private void onMentorAction(ModelMentorPushList m) {
        ///////////           1              //////////////////
        FrameLayout fl1 = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL_1);
        linearLayout.addView(fl1);

        TextView tvDate = new CustomTextView().onCustom(context, PARAM_DATE_CREATE, m.getDate());
        fl1.addView(tvDate);

        ///////////           2              //////////////////
        FrameLayout fl2 = new CustomFrameLayout().onCustom(context, PARAM_CONTAINER_FL_2);
        fl1.addView(fl2);

        CircleImageView civAvatarM = new CustomCircleImageView().onCustom(context, PARAM_PHOTO);
        fl2.addView(civAvatarM);
        Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatarM);

        TextView tvNameM = new CustomTextView().onCustom(context, PARAM_NAME, name);
        fl2.addView(tvNameM);

        TextView tvMsg = new CustomTextView().onCustom(context, PARAM_MSG, m.getMsg());
        fl2.addView(tvMsg);

        ///////////           3              //////////////////
        LinearLayout ll1 = new CustomLinearLayout().onCustom(context, PARAM_CHILD_CONTAINER_LL);
        fl2.addView(ll1);



        for (int i = 0; i < m.getChildList().size(); i++) {
            Object o = m.getChildList().get(i);
            HashMap hashMap = (HashMap) o;
            String avatar = String.valueOf(hashMap.get("avatar"));
            String name = String.valueOf(hashMap.get("name"));

            FrameLayout flChild = new CustomFrameLayout().onCustom(context, PARAM_CHILD_CONTAINER_FL);
            ll1.addView(flChild);

            CircleImageView civAvatarC = new CustomCircleImageView().onCustom(context, PARAM_CHILD_PHOTO);
            flChild.addView(civAvatarC);
            Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatarC);

            TextView tvNameC = new CustomTextView().onCustom(context, PARAM_CHILD_NAME,name);
            flChild.addView(tvNameC);
        }

    }
}
