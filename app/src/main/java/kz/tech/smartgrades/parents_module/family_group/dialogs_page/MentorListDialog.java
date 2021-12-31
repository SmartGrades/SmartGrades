package kz.tech.smartgrades.parents_module.family_group.dialogs_page;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.authentication.fragments.registration_mentor.models.ModelMentor;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomFrameLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomRelativeLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomToolbar;
import kz.tech.smartgrades.root.ui.CustomView.CustomCircleImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomEditText;
import kz.tech.smartgrades.root.ui.CustomView.CustomImageView;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;
import kz.tech.smartgrades.root.var_resources.ColorsRGB;

public class MentorListDialog extends Dialog {
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_TOOLBAR = {"prt:FrmLay", "layW:mPrt", "layH:wCnt", "parH:50", "backC:WHITE"};
    private static final String[] PARAM_TOOLBAR_RL = {"prt:Toolbar", "layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_LEFT_BUTTON = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5"};
    private static final String[] PARAM_SEARCH = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "mrg:70,0,70,0", "pad:5,5,5,5",
            "txtC:BLACK", "grv:LCV", "txtS:18", "backC:0"};
    private static final String[] PARAM_AVATAR = {"prt:RelLay", "layW:mPrt", "layH:mPrt", "parW:50", "parH:50", "pad:5,5,5,5",
            "BordC:GRAY_THREE", "BordW:2", "img:avatar", "Rule:RIGHT"};

    private static final String[] PARAM_RV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "hfs:true", "layMan:llm", "mrg:0,55,0,0"};
    private List<ModelMentor> list;
    private Resources res;
    private Dialog dialog;
    private MentorListAdapter adapter;
    public MentorListAdapter getMentorListAdapter() {
        return adapter;
    }
    public MentorListDialog(@NonNull Context context) {
        super(context);
    }
    public MentorListDialog(Context context, int themeResId, Resources res, String avatar) {
        super(context, themeResId);
        this.res = res;
        dialog = this;
        list = new ArrayList<>();
        FrameLayout frameLayout = new CustomFrameLayout().onCustom(context, PARAM);
        this.setContentView(frameLayout);
        frameLayout.setBackgroundColor(ColorsRGB.WHITE);

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

        EditText etSearch = new CustomEditText().onCustom(context, PARAM_SEARCH, res.getString(R.string.search));
        rlToolbar.addView(etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int count) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                List<ModelMentor> filterArray = new ArrayList<>();

                String s = editable.toString();
                if (s.equals("")) {
                    adapter.removeData();
                    return;
                }
                for (ModelMentor m : list) {
                //    if (s.contains(m.getLogin())) {
                    int lol = s.length();
                    int kek = m.getLogin().length();
                    if (kek < lol) return;

                    String suka = s.substring(0, lol);
                    String fuck = m.getLogin().substring(0, lol);

                    android.util.Log.e("Tag", " Msg");

                    if (suka.equalsIgnoreCase(fuck)) {
                        filterArray.add(m);
                    }
                }
                if (filterArray.size() > 0) {
                    adapter.setData(filterArray);
                }

            }
             });


        CircleImageView civAvatar = new CustomCircleImageView().onCustom(context, PARAM_AVATAR);
        rlToolbar.addView(civAvatar);
        Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(civAvatar);


        adapter = new MentorListAdapter(context);

        RecyclerView recyclerView = new CustomRecyclerView().onCustom(context, PARAM_RV);
        frameLayout.addView(recyclerView);
        recyclerView.setAdapter(adapter);


    }
    public void updateData(List<ModelMentor> list) {
        this.list = list;

    }

    protected MentorListDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
//CustomDialog2