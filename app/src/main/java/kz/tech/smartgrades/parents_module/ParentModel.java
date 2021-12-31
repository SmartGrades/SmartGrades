package kz.tech.smartgrades.parents_module;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.parents_module.mvp.ICallBack;
import kz.tech.smartgrades.parents_module.mvp.IModel;
import kz.tech.smartgrades.root.db.IFamilyDao;
import kz.tech.smartgrades.root.db.TableFamilyList;
import kz.tech.smartgrades.root.db.TableFamilyRoom;
import kz.tech.smartgrades.root.firebase.IFireBase;
import kz.tech.smartgrades.root.models.ModelAllCoins;
import kz.tech.smartgrades.root.models.ModelChild;
import kz.tech.smartgrades.root.models.ModelContract;

public class ParentModel implements IModel {

//    public void updateDateParentByIdContract(String idContract, String dateParent) {
//        for (int i = 0; i < childDataList.size(); i++) {
//            int countList = childDataList.get(i).getContracts().size();
//            for (int j = 0; j < countList; j++) {
//                if (childDataList.get(i).getContracts().get(j).getIdContract().equals(idContract)) {
//                    childDataList.get(i).getContracts().get(j).setDateParent(dateParent);
//                }
//            }
//        }
//    }
//    public void updateCheckParentByIdContract(String idContract, String checkParent) {
//        for (int i = 0; i < childDataList.size(); i++) {
//            int countList = childDataList.get(i).getContracts().size();
//            for (int j = 0; j < countList; j++) {
//                if (childDataList.get(i).getContracts().get(j).getIdContract().equals(idContract)) {
//                    childDataList.get(i).getContracts().get(j).setCheckParent(checkParent);
//                }
//            }
//        }
//    }
//    public void updateCheckChildByIdContract(String idContract, String checkChild) {
//        for (int i = 0; i < childDataList.size(); i++) {
//            int countList = childDataList.get(i).getContracts().size();
//            for (int j = 0; j < countList; j++) {
//                if (childDataList.get(i).getContracts().get(j).getIdContract().equals(idContract)) {
//                    childDataList.get(i).getContracts().get(j).setCheckChild(checkChild);
//                }
//            }
//        }
//    }
    ///////////      INSERT CONTRACT      ////////////
//    public void insertContractInChildDataList(ModelContract modelContract) {
//        for (int i = 0; i < childDataList.size(); i++) {
//            if (childDataList.get(i).getIdChild().equals(modelContract.getIdChild())) {
//                int subArrayList = childDataList.get(i).getContracts().size();
//                for (int s = 0; s < subArrayList; s++) {
//                    String idChild = childDataList.get(i).getContracts().get(s).getIdChild();
//                    if (modelContract.getIdChild().equals(idChild)) {
//                        childDataList.get(i).getContracts().add(modelContract);
//                        android.util.Log.e("TAG", " Insert: Contract position "+ String.valueOf(s));
//                        return;
//                    }
//                }
//            }
//        }
//    }
//    public List<ModelContract> getContractsChildDataList(String idChild) {
//        List<ModelContract> list = new ArrayList<>();
//        for (int i = 0; i < childDataList.size(); i++) {
//            if (idChild.equals(childDataList.get(i).getIdChild())) {
//
//                if (childDataList.get(i).getContracts().size() > 0) {
//                    int subArrayList = childDataList.get(i).getContracts().size();
//                    for (int s = 0; s < subArrayList; s++) {
//                        String avatar = childDataList.get(i).getContracts().get(s).getAvatar();
//                        String checkChild = childDataList.get(i).getContracts().get(s).getCheckChild();
//                        String checkParent = childDataList.get(i).getContracts().get(s).getCheckParent();
//                        String countSteps = childDataList.get(i).getContracts().get(s).getCountSteps();
//                        String dateChild = childDataList.get(i).getContracts().get(s).getDateChild();
//                        String dateParent = childDataList.get(i).getContracts().get(s).getDateParent();
//                        String description = childDataList.get(i).getContracts().get(s).getDescription();
//                        String id_Child = childDataList.get(i).getContracts().get(s).getIdChild();
//                        String idContract = childDataList.get(i).getContracts().get(s).getIdContract();
//                        String idParent = childDataList.get(i).getContracts().get(s).getIdParent();
//                        String purposeChild = childDataList.get(i).getContracts().get(s).getPurposeChild();
//                        String title = childDataList.get(i).getContracts().get(s).getTitle();
//                        list.add(new ModelContract(avatar, checkChild, checkParent, countSteps, dateChild,
//                                dateParent, description, id_Child, idContract, idParent, purposeChild, title));
//                    }
//                }
//            }
//        }
//        return list;
//
//    }
    //  ChildData
    public void onClearChildDataList() {
        childDataList.clear();
    }
    public List<ModelChildData> getChildDataList() {
        return childDataList;
    }
//    public void updateCountStepsByIdContract(String idContract, String countSteps) {
//        for (int i = 0; i < childDataList.size(); i++) {
//            int countList = childDataList.get(i).getContracts().size();
//            for (int j = 0; j < countList; j++) {
//                if (childDataList.get(i).getContracts().get(j).getIdContract().equals(idContract)) {
//                    childDataList.get(i).getContracts().get(j).setCountSteps(countSteps);
//                }
//            }
//        }
//    }
    ////////             CHILD POSITION          //////////////////////
    private int childPosition = 0;
    public int getChildPosition() {
        return childPosition;
    }
    public void setChildPosition(int childPosition) {
        this.childPosition = childPosition;
    }
    /////////         GET ALL TYPE COINS BY ID IN CHILD DATA LIST      /////////////
//    public ModelAllCoins getAllCoinsById(String id) {
//        ModelAllCoins modelAllCoins = new ModelAllCoins();
//        for (int i = 0; i < childDataList.size(); i++) {
//            if (id.equals(childDataList.get(i).getIdChild())) {
//                modelAllCoins.setCoins(childDataList.get(i).getCoins());
//                modelAllCoins.setCoinsBank(childDataList.get(i).getCoinsBank());
//                modelAllCoins.setCoinsPiggy(childDataList.get(i).getCoinsPiggy());
//                modelAllCoins.setCoinsSpentToday(childDataList.get(i).getCoinsSpentToday());
//            }
//        }
//        return modelAllCoins;
//    }
    private static final String TAG = "TAG";
    ///////          GET COINS BY ID        ////////////
//    public String getCoinsChildDataList(String idChild) {
//        String result = null;
//        for (int i = 0; i < childDataList.size(); i++) {
//            if (idChild.equals(childDataList.get(i).getIdChild())) {
//                result = childDataList.get(i).getCoins();
//            }
//        }
//        return result;
//    }
    ///////       CURRENT CHILD ID        //////////
    private String currentChildID;
    public String getCurrentChildID() {
        return currentChildID;
    }
    public void setCurrentChildID(String currentChildID) {
        this.currentChildID = currentChildID;
    }
    public void onClearChildList() {
        childList.clear();
    }
    public List<ModelChild> getChildList() {
        return childList;
    }
    public int getChildListSize() {
        return childList.size();
    }
    //  public String getCurrentChildID(int position) {
    //   return childList.get(position).getIdChild();
    //  }
    public String getIdChildFromChildList(int position) {
        if (childList.size() == 0) return null;
        return childList.get(position).getIdChild();
    }
    private List<TableFamilyList> familyLists;
    private List<TableFamilyRoom> familyRooms;
    private List<ModelChild> childList;
    private List<ModelChildData> childDataList;
    private IFireBase fireBase;
    private IFamilyDao familyDao;
    private long timeStamp = 0;


    public List<TableFamilyRoom> getFamilyRooms() {
        return familyRooms;
    }

    public ParentModel(IFireBase fireBase, IFamilyDao familyDao) {
        this.fireBase = fireBase;
        this.familyDao = familyDao;
        this.familyLists = new ArrayList<>();
        this.familyRooms = new ArrayList<>();
        this.childList = new ArrayList<>();
        this.childDataList = new ArrayList<>();
        familyDao.getFamilyListByID("0").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<TableFamilyList>() {
                    @Override
                    public void onSuccess(TableFamilyList familyList) {
                        timeStamp = Long.parseLong(familyList.login);
                        Log.e("Tag", "Good");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Tag", "Good");
                    }
                });
        Log.e("Tag", "Good");

    }

    @Override
    public void onRequestFamilyList(String path, String id, ICallBack callBack) {
        if (id == null) return;
        if (familyLists.size() > 0) { familyLists.clear(); }
        fireBase.getFireBase(path).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isTimeStamp = false;
                if (dataSnapshot.exists()) {
                    Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        String key = (String) ((DataSnapshot) i.next()).getKey();
                        String value = dataSnapshot.child(key).getValue(String.class);
                        if (key != null && value != null) {
                            if (key.equals("0")) {
                                long result = Long.parseLong(value);
                                if (result > timeStamp) {
                                    isTimeStamp = true;

                                /*    Single.fromCallable(() -> {   familyDao.deleteAllFamilyList();
                                        return true;} )
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe();

                                    Single.fromCallable(() -> {   familyDao.deleteAllFamilyRoom();
                                        return true;} )
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe();*/


                                }
                            }
                        }
                        if (isTimeStamp) { familyLists.add(new TableFamilyList(key, value)); }
                        Log.e("Tag", "Good");
                    }
                    if (isTimeStamp) {
                       Single.fromCallable(() -> {   familyDao.insertFamilyList(familyLists);
                           return true;} )
                               .subscribeOn(Schedulers.io())
                               .observeOn(AndroidSchedulers.mainThread())
                               .subscribe();
                        //Load Family members then save to DB
                        callBack.onResponseFamilyList(true);
                    } else {
                        callBack.onResponseFamilyList(false);
                    }
                } else {
                    callBack.onMessageResult("Error: Family Room NOT EXIST");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callBack.onMessageResult("Error: Server OFF");
            }
        });
    }

    @Override
    public void onRequestFamilyRoom(String path, ICallBack callBack) {
        if (familyLists.size() > 0) {
            for (int j = 0; j < familyLists.size(); j++) {
                if (j > 0) {
                    int q = j;
                    final String login = familyLists.get(j).login;
                    fireBase.getFireBase(path).child(login)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Iterator i = dataSnapshot.getChildren().iterator();
                                if (dataSnapshot.getChildrenCount() == 10) {
                                    while (i.hasNext()) {
                                        TableFamilyRoom model = new TableFamilyRoom();
                                        model.setAvatar((String) ((DataSnapshot) i.next()).getValue());
                                        model.setData(null);
                                        model.setIdFamilyRoom((String) ((DataSnapshot) i.next()).getValue());
                                        model.setLogin(login);
                                        model.setMail((String) ((DataSnapshot) i.next()).getValue());
                                        model.setName((String) ((DataSnapshot) i.next()).getValue());
                                        model.setPassword((String) ((DataSnapshot) i.next()).getValue());
                                        model.setPin((String) ((DataSnapshot) i.next()).getValue());
                                        model.setTimestamp((String) ((DataSnapshot) i.next()).getValue());
                                        model.setTitle((String) ((DataSnapshot) i.next()).getValue());
                                        model.setTypeAccount((String) ((DataSnapshot) i.next()).getValue());
                                        model.setIdLogin((String) ((DataSnapshot) i.next()).getValue());

                                        familyRooms.add(model);
                                        Single.fromCallable(() -> {   familyDao.insertFamilyRoom(model);
                                            return true;} )
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe();
                                    }
                                    if (q == familyLists.size()-1) {
                                        Log.e("Tag", "Good");
                                    }
                                    Log.e("Tag", "Good");
                                }
                            } else {
                                callBack.onMessageResult("Error: Family Room NOT EXIST");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            callBack.onMessageResult("Error: Server OFF");
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onLoadStorage(ICallBack callBack) {
        familyDao.getAllFamilyRoomList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<TableFamilyRoom>>() {
                    @Override
                    public void onSuccess(List<TableFamilyRoom> familyList) {
                        familyRooms.addAll(familyList);
                        if (childList.size() > 0) { childList.clear(); }
                        for (int j = 0; j < familyRooms.size(); j++) {
                            String child = familyRooms.get(j).getTitle();
                            if (child != null) {
                                if (child.equals("Son") || child.equals("Daughter")) {
                                    childList.add(new ModelChild(familyRooms.get(j).getAvatar(),
                                            familyRooms.get(j).getName(), familyRooms.get(j).getIdLogin()));
                                }
                            }
                        }
                        callBack.onLoadChildList();
                        Log.e("Tag", "Good");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Tag", "Good");
                    }
                });
    }

    @Override
    public void onRequestChildData(String path, String id, ICallBack callBack) {

    }


    ///////////////          CHILD DATA               ///////////////////////
//    @Override
//    public void onRequestChildData(String path, String id, ICallBack callBack) {
//        if (childDataList.size() > 0) { childDataList.clear(); }
//        fireBase.getFireBase(path).child(id).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
//                if (dataSnapshot1.exists()) {
//                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()){
//                        Iterator z = dataSnapshot2.getChildren().iterator();
//                        String coins = (String) ((DataSnapshot) z.next()).getValue();
//                        String coinsBank = (String) ((DataSnapshot) z.next()).getValue();
//                        String coinsPiggy = (String) ((DataSnapshot) z.next()).getValue();
//                        String coinsSpentToday = (String) ((DataSnapshot) z.next()).getValue();
//                        Object contracts = (Object) ((DataSnapshot) z.next()).getValue();
//                        String idChilds = (String) ((DataSnapshot) z.next()).getValue();
//                        ArrayList<ModelContract> contractsList1 = new ArrayList<>();
//                        if (contracts instanceof HashMap) {
//                            HashMap hashMap1 = (HashMap) contracts;
//                            ArrayList<ModelContract> contractsList2 = new ArrayList<>(hashMap1.values());
//                            for (int w = 0; w < contractsList2.size(); w++) {
//                                HashMap hashMap2 = new HashMap((Map) contractsList2.get(w));
//                                String titles = String.valueOf(hashMap2.get("title"));
//                                if (titles != null) {
//                                    if (!titles.equals("0")) {
//                                        String avatar = String.valueOf(hashMap2.get("avatar"));
//                                        String checkChild = String.valueOf(hashMap2.get("checkChild"));
//                                        String checkParent = String.valueOf(hashMap2.get("checkParent"));
//                                        String countSteps = String.valueOf(hashMap2.get("countSteps"));
//                                        String dateChild = String.valueOf(hashMap2.get("dateChild"));
//                                        String dateParent = String.valueOf(hashMap2.get("dateParent"));
//                                        String description = String.valueOf(hashMap2.get("description"));
//                                        String idChild = String.valueOf(hashMap2.get("idChild"));
//                                        String idContract = String.valueOf(hashMap2.get("idContract"));
//                                        String idParent = String.valueOf(hashMap2.get("idParent"));
//                                        String purposeChild = String.valueOf(hashMap2.get("purposeChild"));
//                                        String title = String.valueOf(hashMap2.get("title"));
//                                        contractsList1.add(new ModelContract(avatar, checkChild, checkParent, countSteps,
//                                                dateChild, dateParent, description, idChild, idContract, idParent, purposeChild, title));
//                                    }
//                                }
//                            }
//                        } else if (contracts instanceof ArrayList) {
//                            android.util.Log.e(TAG, "ChildData: Contracts empty");
//                        }
//                        childDataList.add(new ModelChildData(coins, coinsBank, coinsPiggy, coinsSpentToday, contractsList1, idChilds));
//                        android.util.Log.e(TAG, "ChildData: Contracts added");
//                    }
//                    android.util.Log.e(TAG, "ChildData: EXIST");
//                   // if (n == 1) { callBack.onResponseChildData();}
//                 //   else if  (n == 2) { callBack.onResponseSignIn(n); }
//                } else {
//                    android.util.Log.e(TAG, "ChildData: NOT EXIST");
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                android.util.Log.e(TAG, "ChildData: ERROR");
//            }
//        });
//    }
}
