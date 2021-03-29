package kz.tech.smartgrades.parent.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.root.var_resources.DimensionDP;


public class DialogInteractionToolStepsPage extends Dialog implements View.OnClickListener {

    public interface OnItemClickListener {
        void onItemClick(int count, ImageView img, String steps, String target);
        void onBackClick();
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private Dialog dialog;
    private Context context;

    private int childSelect = 0;

    private ParentActivity activity;

    private FrameLayout flAvatarToys;
    private ImageView ivAvatarToys;
    private TextView tvSelectChild;
    private EditText etSelectTarget;
    private EditText etSelectStepsText;
    private EditText etSelectStepsCount;
    private Button btnSendToChild;
    private TextView tvBottom;


    private boolean isSelectTarget = false;
    private boolean isSelectStepsText = false;
    private boolean isSelectStepsCount = false;

    public DialogInteractionToolStepsPage(@NonNull Context context) {
        super(context);
    }

    public DialogInteractionToolStepsPage(@NonNull Context context, int themeResId, ParentActivity activity,  String fullName) {
        super(context, themeResId);
        this.context = context;
        this.activity = activity;
        dialog = this;
        dialog.setCanceledOnTouchOutside(true);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_interaction_tool_steps_page, null, false);
        this.setContentView(view);

        ConstraintLayout frame = view.findViewById(R.id.frame);
        frame.setOnClickListener(this);

        ImageView ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);

        TextView tvTitle = view.findViewById(R.id.tvTitle);

        flAvatarToys = view.findViewById(R.id.flAvatarToys);
        flAvatarToys.setOnClickListener(this);
        ivAvatarToys = view.findViewById(R.id.ivAvatarToys);
        ivAvatarToys.setOnClickListener(this);

        tvSelectChild = view.findViewById(R.id.tvSelectChild);
        tvSelectChild.setText(fullName);

        etSelectTarget = view.findViewById(R.id.etSelectTarget);
        etSelectTarget.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                //      adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Toast.makeText(getApplicationContext(),"before text change",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                //   Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
                if (arg0.length() == 0) {
                    isSelectTarget = false;
                    onTernarButton();
                } else if (arg0.length() > 0) {
                    isSelectTarget = true;
                    onTernarButton();
                }
            }
        });

        etSelectStepsText = view.findViewById(R.id.etSelectStepsText);
        etSelectStepsText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                //      adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Toast.makeText(getApplicationContext(),"before text change",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                //   Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
                if (arg0.length() == 0) {
                    isSelectStepsText = false;
                    onTernarButton();
                } else if (arg0.length() > 0) {
                    isSelectStepsText = true;
                    onTernarButton();
                }
            }
        });

        etSelectStepsCount = view.findViewById(R.id.etSelectStepsCount);
        etSelectStepsCount.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                //      adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                //  Toast.makeText(getApplicationContext(),"before text change",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                //   Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
                if (arg0.length() == 0) {
                    isSelectStepsCount = false;
                    onTernarButton();
                } else if (arg0.length() > 0) {
                    isSelectStepsCount = true;
                    onTernarButton();
                }
            }
        });
        etSelectStepsCount.setFilters(new android.text.InputFilter[]{filters});


        btnSendToChild = view.findViewById(R.id.btnSendToChild);
        btnSendToChild.setOnClickListener(this);

        tvBottom = view .findViewById(R.id.tvBottom);

    }

    private InputFilter filters = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //noinspection EmptyCatchBlock
            try {
                int input = Integer.parseInt(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length()));
                if (isInRange(0, 365, input)) {
                    return null;
                } else {
                    activity.alert.onAlertDialogSolo(activity,"","Ошибка!\n" +
                            "\n" +
                            "Количество шагов\n" +
                            "не должно превышать 365.", "OK");
                }
            } catch (NumberFormatException nfe) { }
            return "";
        }
        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    };

    protected DialogInteractionToolStepsPage(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void onTernarButton() {
        boolean isActive;
        if (isSelectTarget && isSelectStepsText && isSelectStepsCount) {
            isActive = true;
        } else {
            isActive = false;
        }
        tvBottom.setText(isActive ? "" : activity.getResources().getString(R.string.steps_page_bottom_text));
        btnSendToChild.setBackgroundResource(isActive ?
                R.drawable.btn_background_purple :
                R.drawable.btn_background_purple_alpha);
        btnSendToChild.setEnabled(isActive);
        btnSendToChild.setPadding(DimensionDP.sizeDP(50, activity), 0, DimensionDP.sizeDP(50, activity),0);
    }

    private void onTernarAvatar(boolean isVisible) {
        flAvatarToys.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        ivAvatarToys.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void onBack() {
        if (onItemClickListener != null) {
            onItemClickListener.onBackClick();
        }
        dialog.dismiss();
    }

    private void onSelectAvatarToys() {
        DialogInteractionToolStepsToys dialogAvatar
                = new DialogInteractionToolStepsToys(context, R.style.CustomDialog2);
        dialogAvatar.show();
        dialogAvatar.setOnItemClickListener(new DialogInteractionToolStepsToys.OnItemClickListener() {
            @Override
            public void onItemClick(int image) {
                if (image > 0) {
                    ivAvatarToys.setImageResource(image);
                    onTernarAvatar(true);
                }
            }
        });
    }

    private void onSendToChild() {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(
                    Integer.parseInt(etSelectStepsCount.getText().toString()),
                            ivAvatarToys,
                            etSelectStepsText.getText().toString(),
                            etSelectTarget.getText().toString());
            onBack();
        }
     }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.frame:
               // activity.alert.hideSoftKeyboard(activity);
                break;
            case R.id.ivBack:
                onBack();
                break;
            case R.id.flAvatarToys:
                onSelectAvatarToys();
                break;
            case R.id.ivAvatarToys:
                onSelectAvatarToys();
                break;
            case R.id.btnSendToChild:
                onSendToChild();
                break;
        }
    }




    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom()) {
                final InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                View focus = getCurrentFocus();
                if (focus != null) {
                    im.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                }
            }

        }
        return super.dispatchTouchEvent(ev);
    }


}