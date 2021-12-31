package kz.tech.smartgrades.parents_module.coins;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import kz.tech.smartgrades.P;
import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.ParentActivity;
import kz.tech.smartgrades.parents_module.coins.dialogs.ReportsDialog;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.root.models.ModelAllCoins;


public class CoinsFragment extends Fragment implements CoinsListenerClick {
    private ParentActivity activity;
    private CoinsView view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //initViews();
        return view;
    }
//    private void initViews() {
//        activity = (ParentActivity) getActivity();
//        view = new CoinsView(getActivity(), activity.language.getLanguage());
//        view.setOnItemClickListener(new CoinsView.OnItemClickListener() {
//            @Override
//            public void onCoinsClick(int time) {
//                onRequestCoinsChange(time);
//
//
//               // activity.presenter.onCoinsChange(time, activity.presenter.model.getIdChildFromChildList(activity.presenter.model.getChildPosition()));
//            }
//
//            @Override
//            public void onMessageClick(String msg) {
//                activity.onShowToast(msg);
//            }
//
//            @Override
//            public void onObolsClick(int numb) {
//                switch (numb) {
//                    case 2://  REPORTS
//                        onReportWeekTask();
//                        break;
//                    case 4://  BANK
//                        view.onSelectLL(3);
//                        activity.prefs.onSaveCurrentPageMainCoins(2);
//                        activity.view.onToolbarSelect(3, activity.language.getLanguage().getString(R.string.bank_text));
//                        break;
//                }
//
//            }
//        });
//        switch (activity.prefs.onLoadCurrentPageMainCoins()) {
//            case 0: view.onSelectLL(1); break;
//            case 1: view.onSelectLL(2); break;
//            case 2: view.onSelectLL(3); break;
//        }
//
//
//      //  Toast.makeText(getActivity(),"Fuck you " , Toast.LENGTH_SHORT).show();
//        String idChild = activity.presenter.model.getIdChildFromChildList(0);//  Get ID Child from MainModel
//        String totalCoins = activity.presenter.model.getCoinsChildDataList(idChild);//  Total Coins this Child select
//        if (totalCoins != null) {//  Check if List Empty, if Empty return NULL, if not, return Coins total
//            view.setTotalCoins(totalCoins);//  set Coins to CoinsView
//        }
//    }
//    public void onRequestCoinsChange(int coins) {
//        String id = activity.presenter.model.getCurrentChildID();
//        if (id != null) {
//            final int numbCoins = coins;
//            String idLogin = activity.login.loadUserDate(LoginKey.ID);
//            Query query = activity.fireBase.getFireBase(P.CHILD_DATA).child(idLogin).child(id).child("coins");
//            ValueEventListener valueEventListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    String totalCoinsDB = (String) dataSnapshot.getValue();
//                    int numb = Integer.parseInt(totalCoinsDB);
//                    numb += numbCoins;
//                    String result = String.valueOf(numb);
//                    dataSnapshot.getRef().setValue(result);
//                    for (int z = 0; z < activity.presenter.model.getChildDataList().size(); z++) {
//                        if (id.equals(activity.presenter.model.getChildDataList().get(z).getIdChild())) {
//                            activity.presenter.model.getChildDataList().get(z).setCoins(result);
//                        }
//                    }
//                    String totalCoins = activity.presenter.model.getCoinsChildDataList(id);//  Total Coins this Child select
//                    if (totalCoins != null) {//  Check if List Empty, if Empty return NULL, if not, return Coins total
//                        view.setTotalCoins(totalCoins);//  set Coins to CoinsView
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                }
//            };
//            query.addListenerForSingleValueEvent(valueEventListener);
//        }
//    }
    private void onReportWeekTask() {
        String name = activity.language.getLanguage().getString(R.string.reports_on) + " ";
        String avatar = "";
        String idChild = null;
        String idLogin = activity.login.loadUserDate(LoginKey.ID);

      /*  if (activity.presenter.model.getChildList() != null) {
            int lol = activity.presenter.model.getChildList().size();
            if (lol > 0) {
                String id = activity.presenter.model.getCurrentChildID();
                List<ModelChatListChild> list = activity.presenter.model.getChildList();
                for (int i = 0; i < list.size(); i++) {
                    if (id.equals(list.get(i).getIdChild())) {
                        name += list.get(i).getName();
                        avatar = list.get(i).getAvatar();
                        idChild = list.get(i).getIdChild();
                    }
                }
            }
        }*/
        String s1 = activity.date.getCurrentDate();
        String s2 = activity.date.getDateForReports(-1);
        String s3 = activity.date.getDateForReports(-2);
        String s4 = activity.date.getDateForReports(-3);
        String s5 = activity.date.getDateForReports(-4);
        String s6 = activity.date.getDateForReports(-5);
        String s7 = activity.date.getDateForReports(-6);


        ReportsDialog dialog = new ReportsDialog(getActivity(), R.style.CustomDialog2,
                activity.language.getLanguage(), name, avatar);
        dialog.show();

        if (idChild != null) {
            DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("Reports");

            dbr.child(idLogin).child(idChild).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                    if (dataSnapshot1.exists()) {

                        android.util.Log.e("TAG", "Reports: EXIST");
                    } else {
                        android.util.Log.e("TAG", "Reports: NOT EXIST");

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    android.util.Log.e("TAG", "Reports: ERROR");
                }
            });
        }


}

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (activity != null) { activity = null; }
        if (view != null) { view = null; }


    }


//    @Override
//    public void onChildIDClick(String id) {
//     //   if (main.presenter.model.getChildListSize() != null) {
//      //      main = null;
//     //   }
//
//
//        int v = 100;
//
//        int lol = activity.presenter.model.getChildListSize();
//        if (lol > 0) {
//            ModelAllCoins model =  activity.presenter.model.getAllCoinsById(id);
//            String coins = model.getCoins();
//            String coinsBank = model.getCoinsBank();
//            String coinsPiggy = model.getCoinsPiggy();
//            String coinsSpentToday = model.getCoinsSpentToday();
//            if (coins != null) {//  Check if List Empty, if Empty return NULL, if not, return Coins total
//                view.setTotalCoins(coins);//  set Coins to CoinsView
//            }
//            if (coinsBank != null) {//  Check if List Empty, if Empty return NULL, if not, return Coins total
//                view.setTotalCoinsBank(coinsBank);//  set Coins to CoinsView
//            }
//            if (coinsPiggy != null) {//  Check if List Empty, if Empty return NULL, if not, return Coins total
//                view.setTotalCoinsPiggy(coinsPiggy);//  set Coins to CoinsView
//            }
//            if (coinsSpentToday != null) {//  Check if List Empty, if Empty return NULL, if not, return Coins total
//                view.setTotalCoinsSpentToday(coinsSpentToday);//  set Coins to CoinsView
//            }
//        }
//    }

    @Override
    public void onChildIDClick(String id) {

    }

    @Override
    public void onBackClick() {
        view.onSelectLL(1);
        activity.prefs.onSaveCurrentPageMainCoins(0);
       // main.prefs.onSaveCurrentPage("ParentMain");
    }
}
