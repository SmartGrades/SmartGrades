package kz.tech.smartgrades.child.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.child.adapters.ChildExtractCardListAdapter;
import kz.tech.smartgrades.child.bottom_sheet_dialog.ChildBSDExtract;
import kz.tech.smartgrades.child.bottom_sheet_dialog.ChildBSDRenameCard;
import kz.tech.smartgrades.parent.adapters.BankListAdapter;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.models.ModelPrivateAccount;
import kz.tech.smartgrades.sponsor.models.ModelDiscontCard;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_AddDiscontCard;
import static kz.tech.smartgrades._Web.func_RemoveDiscontCard;

public class ChildCashFragment extends Fragment implements View.OnClickListener, ChildExtractCardListAdapter.OnItemCLickListener, BankListAdapter.OnItemCLickListener {

    private ModelPrivateAccount mPrivateAccount;
    private ChildActivity activity;
    private String CHILD_ID;

    private TextView tvChildMoney;
    private ImageView ivNav;

    private ConstraintLayout clNoCards;
    private TextView tvNoCards;
    private CardView cvBind;
    private TextView tvBind;

    private ConstraintLayout clBindCard;
    private TextView tvBindCard;
    private TextView tvCardNumber;
    private CardView cvCardNumber;
    private EditText etEnterCardNumber;
    private TextView tvCardName;
    private CardView cvCardName;
    private EditText etEnterCardName;
    private CardView cvBindCard;
    private TextView tvBindCardBtn;

    private ScrollView svMyCards;
    private TextView tvMyCard;

    private RecyclerView rvBankCards;

    private TextView tvAddNewCard;

    private ConstraintLayout clSuccessExtract;
    private TextView tvSuccessExtract;
    private ImageView ivSuccessExtract;
    private TextView tvSuccessExtractText;
    private CardView cvGoHome;
    private TextView tvGoHome;

    private TextView tvEnterBankName;
    private RecyclerView rvBankList;
    private BankListAdapter bankListAdapter;
    private ArrayList<String> bankList;

    private ChildExtractCardListAdapter adapter;

    public ChildCashFragment() {
        // Required empty public constructor
    }

    public static ChildCashFragment newInstance(ModelPrivateAccount PrivateAccount) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", (Parcelable) PrivateAccount);
        ChildCashFragment fragment = new ChildCashFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ChildActivity) getActivity();
        CHILD_ID = activity.login.loadUserDate(LoginKey.ID);
        mPrivateAccount = activity.getPrivateAccount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_cash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setTargetInfo();
        checkCards();
    }

    private void checkCards() {
        if (listIsNullOrEmpty(mPrivateAccount.getCards())) {
            svMyCards.setVisibility(View.GONE);
            clBindCard.setVisibility(View.GONE);
            clSuccessExtract.setVisibility(View.GONE);
            clNoCards.setVisibility(View.VISIBLE);
        } else {
            clBindCard.setVisibility(View.GONE);
            clSuccessExtract.setVisibility(View.GONE);
            clNoCards.setVisibility(View.GONE);
            svMyCards.setVisibility(View.VISIBLE);
        }
    }

    public void updateCardList() {
        adapter.updateData(activity.getPrivateAccount().getCards());
    }

    private void initViews(View view) {
        tvChildMoney = view.findViewById(R.id.tvChildMoney);
        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);

        clNoCards = view.findViewById(R.id.clNoCards);
        tvNoCards = view.findViewById(R.id.tvNoCards);
        cvBind = view.findViewById(R.id.cvBind);
        cvBind.setOnClickListener(this);
        tvBind = view.findViewById(R.id.tvBind);

        clBindCard = view.findViewById(R.id.clBindCard);
        tvBindCard = view.findViewById(R.id.tvEnterCode);
        clBindCard.setClickable(false);
        tvCardNumber = view.findViewById(R.id.tvCardNumber);
        cvCardNumber = view.findViewById(R.id.cvCode);
        etEnterCardNumber = view.findViewById(R.id.etEnterCardNumber);
        tvCardName = view.findViewById(R.id.tvCardName);
        cvCardName = view.findViewById(R.id.cvCardName);
        etEnterCardName = view.findViewById(R.id.etEnterCardName);
        cvBindCard = view.findViewById(R.id.cvBindCard);
        cvBindCard.setOnClickListener(this);
        cvBindCard.setClickable(false);
        tvBindCardBtn = view.findViewById(R.id.tvBindCardBtn);

        svMyCards = view.findViewById(R.id.svMyCards);
        tvMyCard = view.findViewById(R.id.tvMyCard);

        rvBankCards = view.findViewById(R.id.rvBankCards);

        tvAddNewCard = view.findViewById(R.id.tvAddNewCard);

        clSuccessExtract = view.findViewById(R.id.clSuccessExtract);
        tvSuccessExtract = view.findViewById(R.id.tvSuccessExtract);
        ivSuccessExtract = view.findViewById(R.id.ivSuccessExtract);
        tvSuccessExtractText = view.findViewById(R.id.tvSuccessExtractText);
        cvGoHome = view.findViewById(R.id.cvGoHome);
        cvGoHome.setOnClickListener(this);
        tvGoHome = view.findViewById(R.id.tvGoHome);

        rvBankCards = view.findViewById(R.id.rvBankCards);
        tvAddNewCard = view.findViewById(R.id.tvAddNewCard);
        tvAddNewCard.setOnClickListener(this);

        tvEnterBankName = view.findViewById(R.id.tvEnterBankName);
        tvEnterBankName.setOnClickListener(this);
        //_____
        rvBankList = view.findViewById(R.id.rvBankList);
        rvBankList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        bankListAdapter = new BankListAdapter(activity);
        bankListAdapter.setOnItemCLickListener(this);
        rvBankList.setAdapter(bankListAdapter);
        bankList = new ArrayList<>();
        bankList.add("Kaspi Bank");
        bankList.add("Альфа-Банк");
        bankList.add("Halyk Bank");
        bankList.add("Сбербанк");
        bankList.add("Jysan Bank");
        bankList.add("ForteBank");
        bankList.add("Банк ЦентрКредит");
        bankList.add("Евразийский банк");
        bankList.add("RBK Bank");
        bankList.add("Нурбанк");
        bankList.add("АТФБанк");
        bankList.add("Другой банк");
        //_____

        adapter = new ChildExtractCardListAdapter(activity);
        rvBankCards.setAdapter(adapter);
        adapter.setOnItemCLickListener(this);
        rvBankCards.setLayoutManager(new LinearLayoutManager(activity));
        if (!listIsNullOrEmpty(activity.getPrivateAccount().getCards()))
            adapter.updateData(activity.getPrivateAccount().getCards());
        setTextWatcher();
    }

    private void setTextWatcher() {
        etEnterCardNumber.addTextChangedListener(new TextWatcher() {
            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
                isOk();
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

        etEnterCardName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        etEnterCardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) { isOk(); }
        });
    }

    private void isOk() {
        if ((etEnterCardNumber.getText().length() == 19) && !etEnterCardName.getText().toString().isEmpty()
                && (bankList.contains(tvEnterBankName.getText().toString()))) {
            cvBindCard.setCardBackgroundColor(activity.getResources().getColor(R.color.blue_sea));
            cvBindCard.setClickable(true);
        } else {
            cvBindCard.setCardBackgroundColor(activity.getResources().getColor(R.color.gray_average_dark));
            cvBindCard.setClickable(false);
        }
    }

    private void setTargetInfo() {
        if (!stringIsNullOrEmpty(mPrivateAccount.getCurrentSum())) {
            tvChildMoney.setText("₸ " + mPrivateAccount.getCurrentSum());
        } else tvChildMoney.setText("₸ 0");
    }

    private void bindCard() {
        activity.alert.hideKeyboard(activity);
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, CHILD_ID);
        jsonData.addProperty(F.CardNumber, etEnterCardNumber.getText().toString().replace("-", ""));
        jsonData.addProperty(F.UserName, etEnterCardName.getText().toString());
        jsonData.addProperty(F.Bank, tvEnterBankName.getText().toString());

        String SOAP = SoapRequest(func_AddDiscontCard, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) { }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = _Web.XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.alert.onToast(answer.getMessage());
                            if (answer.isSuccess()) {
                                activity.presenter.onUpdateCartList();
                                activity.updatePresenter();
                                clearET();
                                clBindCard.setVisibility(View.GONE);
                                svMyCards.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }
        });
    }

    public void onBack() {
        activity.alert.hideKeyboard(activity);
        if (clNoCards.getVisibility() == View.VISIBLE || svMyCards.getVisibility() == View.VISIBLE) {
            activity.onRemoveFragment();
        } else if (clBindCard.getVisibility() == View.VISIBLE) {
            clBindCard.setVisibility(View.GONE);
            checkCards();
        }
    }

    private void openBindWindow() {
        clNoCards.setVisibility(View.GONE);
        svMyCards.setVisibility(View.GONE);
        clBindCard.setVisibility(View.VISIBLE);
        clSuccessExtract.setVisibility(View.GONE);
    }

    public boolean canClose() {
        return clNoCards.getVisibility() != View.GONE || svMyCards.getVisibility() != View.GONE;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivNav:
                onBack();
                break;
            case R.id.cvBindCard:
                bindCard();
                break;
            case R.id.cvBind:
            case R.id.tvAddNewCard:
                openBindWindow();
                break;
            case R.id.tvEnterBankName:
                onShowList();
                break;
            case R.id.cvGoHome:
                activity.onRemoveFragment();
                break;
        }
    }

    private void onShowList() {
        activity.alert.hideKeyboard(activity);
        rvBankList.setVisibility(View.VISIBLE);
        bankListAdapter.updateData(bankList);
    }

    private void clearET() {
        etEnterCardNumber.setText("");
        etEnterCardName.setText("");
    }

    @Override
    public void onExtractClick(ModelDiscontCard mCard) {
        ChildBSDExtract dialog = new ChildBSDExtract(activity, mCard);
        dialog.show();
    }

    @Override
    public void onOptionClick(ModelDiscontCard mCard, View itemView) {
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        View popUpView = getLayoutInflater().inflate(R.layout.pw_bank_card_options, null);
        PopupWindow popupWindow = new PopupWindow(popUpView, width, height, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) popupWindow.setElevation(20);
        popupWindow.showAsDropDown(itemView, 0, 0);
        TextView tvRenameCard = popUpView.findViewById(R.id.tvRenameCard);
        TextView tvRemoveCard = popUpView.findViewById(R.id.tvRemoveCard);
        tvRenameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChildBSDRenameCard dialog = new ChildBSDRenameCard(activity, mCard);
                dialog.show();
                popupWindow.dismiss();
            }
        });
        tvRemoveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(F.UserId, CHILD_ID);
                jsonObject.addProperty(F.Id, mCard.getId());
                String SOAP = SoapRequest(func_RemoveDiscontCard, jsonObject.toString());
                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                Request request = new Request.Builder().url(URL).post(body).build();
                HttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(final Call call, IOException e) { }
                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String result = XMLToJson(response.body().string());
                            ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    activity.alert.onToast(answer.getMessage());
                                    if (answer.isSuccess()) {
                                        activity.updatePresenter();
                                    }
                                }
                            });
                        }
                    }
                });
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void onClick(String bank) {
        tvEnterBankName.setText(bank);
        rvBankList.setVisibility(View.GONE);
        isOk();
    }

    public void onSuccessExtract() {
        clNoCards.setVisibility(View.GONE);
        svMyCards.setVisibility(View.GONE);
        clBindCard.setVisibility(View.GONE);
        clSuccessExtract.setVisibility(View.VISIBLE);
    }
}