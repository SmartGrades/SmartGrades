package kz.tech.smartgrades.mentor.dialog;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import kz.tech.esparta.R;
import kz.tech.smartgrades.MSG_TYPE;

import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;


public class BSDGrading extends BottomSheetDialog implements View.OnClickListener{

    private Context context;

    private ImageView ivBack;
    private TextView tvTitle;

    private TextView[] TV = new TextView[4];
    private LinearLayout[] LL = new LinearLayout[5];

    private ImageView ivOpenChat;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onGradeClick(int type, String grade);
        void onChatClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }


    public BSDGrading(@NonNull Context context, String Title, String type){
        super(context);
        this.context = context;

        View view = getLayoutInflater().inflate(R.layout.bsd_mentor_set_grade, null, false);
        setContentView(view);

        initViews(view);

        if(!stringIsNullOrEmpty(Title)) tvTitle.setText(Title);
        else tvTitle.setVisibility(View.GONE);

        if(type.equals("Parent")){
            LL[0].setVisibility(View.GONE);
            LL[1].setVisibility(View.GONE);
            LL[2].setVisibility(View.GONE);
            LL[3].setVisibility(View.GONE);
        }
    }

    private void initViews(View view){
        ivOpenChat = view.findViewById(R.id.ivOpenChat);
        ivOpenChat.setOnClickListener(this);
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        tvTitle = view.findViewById(R.id.tvTitle);

        LL[0] = view.findViewById(R.id.llLate);
        LL[0].setOnClickListener(this);
        LL[1] = view.findViewById(R.id.llWasAbsentCause);
        LL[1].setOnClickListener(this);
        LL[2] = view.findViewById(R.id.llWasAbsentNonCause);
        LL[2].setOnClickListener(this);
        LL[3] = view.findViewById(R.id.llSick);
        LL[3].setOnClickListener(this);

//        TV[0] = view.findViewById(R.id.tvTwo);
//        TV[0].setOnClickListener(this);
//        TV[1] = view.findViewById(R.id.tvThree);
//        TV[1].setOnClickListener(this);
//        TV[2] = view.findViewById(R.id.tvFour);
//        TV[2].setOnClickListener(this);
//        TV[3] = view.findViewById(R.id.tvFive);
//        TV[3].setOnClickListener(this);
    }

    private void onBack(){
        dismiss();
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivBack:
                onBack();
                break;
            case R.id.ivOpenChat:
                onOpenChat();
                break;

            case R.id.llLate:
                onClickItem(MSG_TYPE.SKIP_LATE, MSG_TYPE.SKIP_LATE_MSG);
                break;
            case R.id.llWasAbsentCause:
                onClickItem(MSG_TYPE.SKIP_WAC, MSG_TYPE.SKIP_WAC_MSG);
                break;
            case R.id.llWasAbsentNonCause:
                onClickItem(MSG_TYPE.SKIP_WANC, MSG_TYPE.SKIP_WANC_MSG);
                break;
            case R.id.llSick:
                onClickItem(MSG_TYPE.SKIP_SICK, MSG_TYPE.SKIP_SICK_MSG);
                break;

//            case R.id.tvTwo:
//                onClickItem(1, "2");
//                break;
//            case R.id.tvThree:
//                onClickItem(1, "3");
//                break;
//            case R.id.tvFour:
//                onClickItem(1, "4");
//                break;
//            case R.id.tvFive:
//                onClickItem(1, "5");
//                break;
        }
    }
    private void onOpenChat(){
        if(onItemClickListener == null) return;
        onItemClickListener.onChatClick();
    }

    private void onClickItem(int type, String grade){
        if(onItemClickListener == null) return;
        onItemClickListener.onGradeClick(type, grade);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        View v = getCurrentFocus();
        if(v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")){
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if(x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()){
                final InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                View focus = getCurrentFocus();
                if(focus != null){
                    im.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                }
            }

        }
        return super.dispatchTouchEvent(ev);
    }
}