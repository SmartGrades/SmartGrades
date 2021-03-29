package kz.tech.smartgrades.parents_module.settings.locality;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.smartgrades.root.ui.CustomLayout.CustomLinearLayout;
import kz.tech.smartgrades.root.ui.CustomLayout.CustomScrollView;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomView.CustomRecyclerView;

public class LocalityView extends FrameLayout {
    private static final String[] PARAM = {"layW:mPrt", "layH:mPrt"};
    private static final String[] PARAM_CONTAINER_SV = {"prt:FrmLay", "layW:mPrt", "layH:mPrt", "mrg:0,5,0,0"};
    private static final String[] PARAM_CONTAINER_LL = {"prt:ScrLay", "layW:mPrt", "layH:mPrt", "orn:ver"};

    private static final String[] PARAM_RV = {"prt:LinLay", "layW:mPrt", "layH:wCnt", "hfs:true", "layMan:llm"};

    private Context context;
    private RecyclerView recyclerView;
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
    public LocalityView(Context context, Resources res) {
        super(context);
        this.setLayoutParams(new FrameLayoutParams().getParams(context, PARAM));
        this.context = context;

        ScrollView scrollView = new CustomScrollView().onCustom(context, PARAM_CONTAINER_SV);
        this.addView(scrollView);

        LinearLayout linearLayout = new CustomLinearLayout().onCustom(context, PARAM_CONTAINER_LL);
        scrollView.addView(linearLayout);

        recyclerView = new CustomRecyclerView().onCustom(context, PARAM_RV);
        linearLayout.addView(recyclerView);
    }

    public LocalityView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LocalityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LocalityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
