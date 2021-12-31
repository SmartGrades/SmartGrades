package kz.tech.smartgrades.parent.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.F;
import kz.tech.smartgrades._Web;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent.adapters.BankListAdapter;
import kz.tech.smartgrades.parent.adapters.TabCardPagerFragmentAdapter;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.sponsor.models.ModelPaymentResponse;
import kz.tech.smartgrades.sponsor.models.ModelPrivateAccount;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.cloudpayments.sdk.cp_card.CPCard;
import ru.cloudpayments.sdk.three_ds.ThreeDsDialogFragment;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.func_AddDiscontCard;
import static kz.tech.smartgrades._Web.func_Replenish;

public class ParentCashFragment extends Fragment implements View.OnClickListener, BankListAdapter.OnItemCLickListener{

    private ModelPrivateAccount mPrivateAccount;
    private ParentActivity activity;
    private String PARENT_ID;

    private TextView tvMoney;

    private ConstraintLayout clNoCards;
    private CardView cvBind;

    private NestedScrollView clBindCard;
    private EditText etEnterCardNumber;
    private EditText etEnterCardDate;
    private EditText etEnterCVV;
    private EditText etEnterCardName;
    private EditText etEnterSum;
    private TextView tvEnterBankName;
    private RecyclerView rvBankList;
    private CardView cvBindCard;

    private ConstraintLayout clMyCards;
    private RecyclerView rvBankCards;
    private TextView tvAddNewCard;

    private ConstraintLayout clSuccessExtract;
    private CardView cvGoHome;
    private ImageView ivNav;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private BankListAdapter bankListAdapter;
    private ArrayList<String> bankList;

    private TabCardPagerFragmentAdapter tabCardPagerFragmentAdapter;

    public ParentCashFragment(){
        // Required empty public constructor
    }

    public static ParentCashFragment newInstance(ModelPrivateAccount PrivateAccount){
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", PrivateAccount);
        ParentCashFragment fragment = new ParentCashFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.activity = (ParentActivity) getActivity();
        PARENT_ID = activity.login.loadUserDate(LoginKey.ID);
        if(getArguments() != null) mPrivateAccount = getArguments().getParcelable("model");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parent_cash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setTargetInfo();
    }
    private void initViews(View view){
        tvMoney = view.findViewById(R.id.tvChildMoney);
        clNoCards = view.findViewById(R.id.clNoCards);
        cvBind = view.findViewById(R.id.cvBind);
        cvBind.setOnClickListener(this);
        clBindCard = view.findViewById(R.id.clBindCard);
        clBindCard.setClickable(false);
        etEnterCardNumber = view.findViewById(R.id.etEnterCardNumber);
        etEnterCardDate = view.findViewById(R.id.etEnterCardDate);
        etEnterCVV = view.findViewById(R.id.etEnterCVV);
        etEnterCardName = view.findViewById(R.id.etEnterCardName);

        cvBindCard = view.findViewById(R.id.cvBindCard);
        cvBindCard.setOnClickListener(this);
        cvBindCard.setClickable(false);
        cvBind.setOnClickListener(this);
        clMyCards = view.findViewById(R.id.clMyCards);
        //clReplenish = view.findViewById(R.id.clReplenish);
        //clReplenish.setOnClickListener(this);
        //clExtract = view.findViewById(R.id.clExtract);
        //clExtract.setOnClickListener(this);
        rvBankCards = view.findViewById(R.id.rvBankCards);
        clSuccessExtract = view.findViewById(R.id.clSuccessExtract);
        cvGoHome = view.findViewById(R.id.cvGoHome);
        cvGoHome.setOnClickListener(this);

        ivNav = view.findViewById(R.id.ivNav);
        ivNav.setOnClickListener(this);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        etEnterSum = view.findViewById(R.id.etEnterSum);
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

        setCardsAdapter();
        setTextWatcher();
    }

    private void setCardsAdapter() {
        tabCardPagerFragmentAdapter = new TabCardPagerFragmentAdapter(activity.getSupportFragmentManager(), activity);
        viewPager.setAdapter(tabCardPagerFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setTargetInfo(){
        if(mPrivateAccount.getCurrentSum() != null){
            tvMoney.setText("₸ " + mPrivateAccount.getCurrentSum());
        }
        else tvMoney.setText("₸ 0.00");
        checkCards();
    }
    private void checkCards(){
        if(listIsNullOrEmpty(mPrivateAccount.getCards())){
            clMyCards.setVisibility(View.GONE);
            clBindCard.setVisibility(View.GONE);
            clSuccessExtract.setVisibility(View.GONE);
            clNoCards.setVisibility(View.VISIBLE);
        }
        else{
            clBindCard.setVisibility(View.GONE);
            clSuccessExtract.setVisibility(View.GONE);
            clNoCards.setVisibility(View.GONE);
            clMyCards.setVisibility(View.VISIBLE);
        }
    }

    private void setTextWatcher(){
        etEnterCardNumber.addTextChangedListener(new TextWatcher(){
            private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '-';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                // noop
            }

            @Override
            public void afterTextChanged(Editable s){
                if(!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)){
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
                isOk();
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider){
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for(int i = 0; i < s.length(); i++){ // check that every element is right
                    if(i > 0 && (i + 1) % dividerModulo == 0){
                        isCorrect &= divider == s.charAt(i);
                    }
                    else{
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider){
                final StringBuilder formatted = new StringBuilder();

                for(int i = 0; i < digits.length; i++){
                    if(digits[i] != 0){
                        formatted.append(digits[i]);
                        if((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)){
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size){
                char[] digits = new char[size];
                int index = 0;
                for(int i = 0; i < s.length() && index < size; i++){
                    char current = s.charAt(i);
                    if(Character.isDigit(current)){
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });
        etEnterCardDate.addTextChangedListener(new TextWatcher(){
            private static final int TOTAL_SYMBOLS = 5; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 4; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 3; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = '/';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                // noop
            }

            @Override
            public void afterTextChanged(Editable s){
                if(!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)){
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
                isOk();
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider){
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for(int i = 0; i < s.length(); i++){ // check that every element is right
                    if(i > 0 && (i + 1) % dividerModulo == 0){
                        isCorrect &= divider == s.charAt(i);
                    }
                    else{
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider){
                final StringBuilder formatted = new StringBuilder();

                for(int i = 0; i < digits.length; i++){
                    if(digits[i] != 0){
                        formatted.append(digits[i]);
                        if((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)){
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size){
                char[] digits = new char[size];
                int index = 0;
                for(int i = 0; i < s.length() && index < size; i++){
                    char current = s.charAt(i);
                    if(Character.isDigit(current)){
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });
        etEnterCVV.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                isOk();
            }
        });
        etEnterCardName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        etEnterCardName.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
            }
            @Override
            public void afterTextChanged(Editable editable){
                isOk();
            }
        });
    }

    private void isOk(){
        if((etEnterCardNumber.getText().length() == 19)
                && !etEnterCardName.getText().toString().isEmpty()
                && (etEnterCVV.getText().toString().length() == 3)
                && (etEnterCardDate.getText().toString().length() == 5)
                && (bankList.contains(tvEnterBankName.getText().toString()))){
            cvBindCard.setCardBackgroundColor(activity.getResources().getColor(R.color.blue_sea));
            cvBindCard.setClickable(true);
        }
        else{
            cvBindCard.setCardBackgroundColor(activity.getResources().getColor(R.color.gray_average_dark));
            cvBindCard.setClickable(false);
        }
    }

    private void bindCard(){
        CPCard card;
        try {
            card = new CPCard(etEnterCardNumber.getText().toString().replace("-", ""),
                    etEnterCardDate.getText().toString().replace("/", ""),
                    etEnterCVV.getText().toString());
        } catch (IllegalArgumentException e) {
            activity.alert.onToast(getString(R.string.Card_number_is_not_correct));
            return;
        }
        String cardCryptogram = null;
        try {
            cardCryptogram = card.cardCryptogram("pk_6641d376b1bce563542d5269b00a3");
        } catch (Exception e) {
            activity.alert.onToast(getString(R.string.Data_entry_error_please_try_again));
            return;
        }

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty(F.UserId, PARENT_ID);
        jsonData.addProperty(F.CardNumber, etEnterCardNumber.getText().toString().replace("-", ""));
        jsonData.addProperty(F.UserName, etEnterCardName.getText().toString());
        jsonData.addProperty(F.UserId, PARENT_ID);
        jsonData.addProperty(F.Cryptogram, cardCryptogram);
        jsonData.addProperty(F.Bank, tvEnterBankName.getText().toString());

        String SOAP = SoapRequest(func_AddDiscontCard, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = _Web.XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            activity.alert.onToast(answer.getMessage());
                            if(answer.isSuccess()){
                                activity.updatePresenter();

                                String sum = etEnterSum.getText().toString().trim();
                                if (Convert.Str2Int(sum) <= 0){
                                    activity.alert.onToast(activity.getString(R.string.The_amount_is_incorrect));
                                    return;
                                }
                                JsonObject jsonData = new JsonObject();
                                jsonData.addProperty(F.UserId, PARENT_ID);
                                jsonData.addProperty(F.AccountId, activity.getMParentData().getPrivateAccount().getAccountId());
                                jsonData.addProperty(F.Id, answer.getCardId());
                                jsonData.addProperty(F.Sum, sum);

                                String SOAP = SoapRequest(func_Replenish, jsonData.toString());
                                RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                                Request request = new Request.Builder().url(URL).post(body).build();
                                HttpClient.newCall(request).enqueue(new Callback() {
                                    @Override
                                    public void onFailure(final Call call, IOException e) { }
                                    @Override
                                    public void onResponse(Call call, final Response response) throws IOException {
                                        if (response.code() >= 200 && response.code() <= 300) {
                                            String xml = response.body().string();
                                            String result = _Web.XMLToJson(xml);
                                            activity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (result.equals("0")) {
                                                        activity.alert.onToast(activity.getString(R.string.Error_please_try_again));
                                                    }
                                                    else {
                                                        ModelPaymentResponse m = new Gson().fromJson(result, ModelPaymentResponse.class);
                                                        if (m!=null){
                                                            if (m.isSuccess() && stringIsNullOrEmpty(m.getModel().getAcsUrl()))
                                                                activity.alert.onToast(m.getModel().getCardHolderMessage());
                                                            else if (!m.isSuccess() && !stringIsNullOrEmpty(m.getModel().getAcsUrl()))
                                                                ThreeDsDialogFragment.newInstance(m.getModel().getAcsUrl(),m.getModel().getTransactionId(),m.getModel().getPaReq()).show(activity.getSupportFragmentManager(), "3DS");
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });

                                clBindCard.setVisibility(View.GONE);
                                clMyCards.setVisibility(View.VISIBLE);
                                clearET();
                            }
                        }
                    });
                }
            }
        });
    }

    public void onBack(){
        activity.alert.hideKeyboard(activity);
        if(clNoCards.getVisibility() == View.VISIBLE || clMyCards.getVisibility() == View.VISIBLE){
            activity.onBackPressed();
        }
        else if(clBindCard.getVisibility() == View.VISIBLE){
            clBindCard.setVisibility(View.GONE);
            checkCards();
        }
    }

    public void openBindWindow(){
        clNoCards.setVisibility(View.GONE);
        clMyCards.setVisibility(View.GONE);
        clBindCard.setVisibility(View.VISIBLE);
        clSuccessExtract.setVisibility(View.GONE);
    }

    private void clearET() {
        etEnterCardNumber.setText("");
        etEnterCardDate.setText("");
        etEnterCVV.setText("");
        etEnterCardName.setText("");
        etEnterSum.setText("");
        tvEnterBankName.setText(activity.getResources().getString(R.string.Select_from_the_list));
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.ivNav:
                onBack();
                break;
            case R.id.cvBindCard:
                if (!stringIsNullOrEmpty(etEnterSum.getText().toString()) && Convert.Str2Double(etEnterSum.getText().toString()) >= 100) {
                    bindCard();
                } else {
                    activity.alert.onToast("Сумма пополнения должна составлять минимум 100 тг");
                }
                break;
            case R.id.cvBind:
                openBindWindow();
                break;
            case R.id.cvGoHome:
                activity.onBackPressed();
                break;
            case R.id.tvEnterBankName:
                onShowList();
                break;
        }
    }

    private void onShowList() {
        activity.alert.hideKeyboard(activity);
        rvBankList.setVisibility(View.VISIBLE);
        bankListAdapter.updateData(bankList);
    }

    public void updateCards() {
        tabCardPagerFragmentAdapter.updateCards();
    }

    public void onSuccessExtract() {
        clNoCards.setVisibility(View.GONE);
        clMyCards.setVisibility(View.GONE);
        clBindCard.setVisibility(View.GONE);
        clSuccessExtract.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String bank) {
        tvEnterBankName.setText(bank);
        rvBankList.setVisibility(View.GONE);
        isOk();
    }
}