package kz.tech.smartgrades;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.root.firebase.FireBaseDevices;
import kz.tech.smartgrades.root.models.ModelAllCoins;
import kz.tech.smartgrades.root.models.ModelChild;
import kz.tech.smartgrades.root.models.ModelContract;
import kz.tech.smartgrades.root.models.ModelDevice;
import kz.tech.smartgrades.root.models.ModelFamilyRoom;
import kz.tech.smartgrades.root.mvp.ICallBack;
import kz.tech.smartgrades.root.mvp.IModel;
import kz.tech.smartgrades.root.fragments.FragmentSelect;

public class MainModel implements IModel {
    private static final String TAG = "TAG";
    //  FIRE BASE
    private DatabaseReference dbrFamilyRoom, dbrChildData, dbrDevices;
    public DatabaseReference getDbrFamilyRoom() {
        return dbrFamilyRoom;
    }
    public DatabaseReference getChildDataDBR() {
        return dbrChildData;
    }
    //  LIST
    public List<ModelFamilyRoom> familyRoomList;
    public List<ModelFamilyRoom> getFamilyList() {
        return familyRoomList;
    }
    public void addFamilyRoom(ModelFamilyRoom modelFamilyRoom) {
        familyRoomList.add(modelFamilyRoom);
    }


    private List<ModelChild> childList;
    private List<ModelChildData> childDataList;


    private ModelContract modelContract;
    public ModelContract getModelContract() {
        return modelContract;
    }
    public void setModelContract(ModelContract modelContract) {
        this.modelContract = modelContract;
    }

    ////////             CHILD POSITION          //////////////////////
    private int childPosition = 0;
    public int getChildPosition() {
        return childPosition;
    }
    public void setChildPosition(int childPosition) {
        this.childPosition = childPosition;
    }

    ///////       CURRENT CHILD ID        //////////
    private String currentChildID;
    public String getCurrentChildID() {
        return currentChildID;
    }
    public void setCurrentChildID(String currentChildID) {
        this.currentChildID = currentChildID;
    }

    //  Constructor for init objects BODY
    public MainModel() {
        //  init all FireBasePath
        dbrFamilyRoom = new _Firebase().getRefFamilyRoom();//  FamilyRoom
        dbrChildData = new _Firebase().getRefChildData();//  ChildData
        dbrDevices = new FireBaseDevices().getDevices();//  Devices
        //  init all ArrayList
        familyRoomList = new ArrayList<>();
        childList = new ArrayList<>();
        childDataList = new ArrayList<>();
    }
    ///////////////////           FamilyRoom            //////////////////////////////
    public List<ModelFamilyRoom> getModelFamilyRoomList() {
       return familyRoomList;
    }
    public void setModelFamilyRoomList(List<ModelFamilyRoom> familyRoomList) {
        this.familyRoomList = familyRoomList;

    }

    public List<ModelFamilyRoom> getFamilyRoomList() {
        return familyRoomList;
    }
    public void onClearFamilyRoomList() {
        familyRoomList.clear();
    }
    private String idSelectFamilyMember;
    public String getIdSelectFamilyMember() {
        if (idSelectFamilyMember != null) {
            return idSelectFamilyMember;
        }
        return null;
    }
    public void setIdSelectFamilyMember(String idSelectFamilyMember) {
        this.idSelectFamilyMember = idSelectFamilyMember;
    }
    public ModelFamilyRoom getSelectModelFamilyMember(String idMember) {
        ModelFamilyRoom modelFamilyRoom = null;
        for (int i = 0; i < familyRoomList.size(); i++) {
            if (familyRoomList.get(i).getId().equals(idMember)) {
                modelFamilyRoom = familyRoomList.get(i);
            }
        }
        return modelFamilyRoom;
    }
    /////////////////////            ModelFamilyGroup               //////////////////
    private ModelFamilyRoom modelFamilyRoom;
    public ModelFamilyRoom getModelFamilyRoom() {
        return modelFamilyRoom;
    }
    public void setModelFamilyRoom(ModelFamilyRoom modelFamilyRoom) {
        this.modelFamilyRoom = modelFamilyRoom;
    }
    ///////////////////                Child              ////////////////////////
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

    ///////////      INSERT CONTRACT      ////////////
//    public void insertContractInChildDataList(ModelContract modelContract) {
//        for (int i = 0; i < childDataList.size(); i++) {
//            if (childDataList.get(i).getId().equals(modelContract.getIdChild())) {
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
    ///////////      UPDATE CONTRACT      ////////////
//    public void updateContractInChildDataList(ModelContract modelContract) {
//        for (int i = 0; i < childDataList.size(); i++) {
//            if (childDataList.get(i).getIdChild().equals(modelContract.getIdChild())) {
//                int subArrayList = childDataList.get(i).getContracts().size();
//                for (int s = 0; s < subArrayList; s++) {
//                    String idContract = childDataList.get(i).getContracts().get(s).getIdContract();
//                    if (modelContract.getIdContract().equals(idContract)) {
//                        childDataList.get(i).getContracts().set(s, modelContract);
//                        android.util.Log.e("TAG", " Update: Contract position "+ String.valueOf(s));
//                    }
//                }
//            }
//        }
//    }
//    public void deleteContractInCildDataList(ModelContract modelContract) {
//        for (int i = 0; i < childDataList.size(); i++) {
//            if (childDataList.get(i).getIdChild().equals(modelContract.getIdChild())) {
//                int subArrayList = childDataList.get(i).getContracts().size();
//                for (int s = 0; s < subArrayList; s++) {
//                    String idContract = childDataList.get(i).getContracts().get(s).getIdContract();
//                    if (modelContract.getIdContract().equals(idContract)) {
//                        childDataList.get(i).getContracts().remove(s);
//                        android.util.Log.e("TAG", " Delete: Contract position "+ String.valueOf(s));
//                    }
//                }
//            }
//        }
//    }
    //  ChildData
//    public void onClearChildDataList() {
//        childDataList.clear();
//    }
//    public List<ModelChildData> getChildDataList() {
//        return childDataList;
//    }
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

//    public List<ModelContract> getContractsChildDataList(String idChild) {
//        List<ModelContract> list = new ArrayList<>();
//        for (int i = 0; i < childDataList.size(); i++) {
//            if (idChild.equals(childDataList.get(i).getIdChild())) {
//
//                if (childDataList.get(i).getContracts().size() > 0) {
//                    int subArrayList = childDataList.get(i).getContracts().size();
//                  for (int s = 0; s < subArrayList; s++) {
//                      String avatar = childDataList.get(i).getContracts().get(s).getAvatar();
//                      String checkChild = childDataList.get(i).getContracts().get(s).getCheckChild();
//                      String checkParent = childDataList.get(i).getContracts().get(s).getCheckParent();
//                      String countSteps = childDataList.get(i).getContracts().get(s).getCountSteps();
//                      String dateChild = childDataList.get(i).getContracts().get(s).getDateChild();
//                      String dateParent = childDataList.get(i).getContracts().get(s).getDateParent();
//                      String description = childDataList.get(i).getContracts().get(s).getDescription();
//                      String id_Child = childDataList.get(i).getContracts().get(s).getIdChild();
//                      String idContract = childDataList.get(i).getContracts().get(s).getIdContract();
//                      String idParent = childDataList.get(i).getContracts().get(s).getIdParent();
//                      String purposeChild = childDataList.get(i).getContracts().get(s).getPurposeChild();
//                      String title = childDataList.get(i).getContracts().get(s).getTitle();
//                      list.add(new ModelContract(avatar, checkChild, checkParent, countSteps, dateChild,
//                              dateParent, description, id_Child, idContract, idParent, purposeChild, title));
//                  }
//                }
//            }
//        }
//        return list;
//
//    }
    //  Contract

    @Override
    public androidx.fragment.app.Fragment getFragment(String fragment) {
        return new FragmentSelect().getFragment(fragment);
    }

    @Override
    public void onRequestFamilyRoom(ICallBack callBack, String id) {
        if (id == null) return;
        if (familyRoomList.size() > 0) { familyRoomList.clear(); }
        dbrFamilyRoom.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        String key = (String) ((DataSnapshot) i.next()).getKey();
                        ModelFamilyRoom modelFamilyRoom = dataSnapshot.child(key).getValue(ModelFamilyRoom.class);
                        familyRoomList.add(modelFamilyRoom);
                    }
                    onFamilyRoomListToChildList();
                    callBack.onResponseFamilyRoom();
                } else {
                    callBack.onMessageCallBack("Error: Family Room NOT EXIST");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callBack.onMessageCallBack("Error: Server OFF");
            }
        });
    }

    @Override
    public void onRequestChildData(ICallBack callBack, String id, int n) {

    }

    public void onFamilyRoomListToChildList() {
        if (childList.size() > 0) { childList.clear(); }
        for (int j = 0; j < familyRoomList.size(); j++) {
            String child = familyRoomList.get(j).getLogin();
            if (child != null) {
                if (child.equals("Son") || child.equals("Daughter")) {
                    /*childList.add(new ModelChild(familyRoomList.get(j).get(),
                            familyRoomList.get(j).getName(), familyRoomList.get(j).getZId()));*/
                }
            }
        }
    }

    ///////////////          CHILD DATA               ///////////////////////
//    @Override
//    public void onRequestChildData(ICallBack callBack, String id, int n) {
//        if (childDataList.size() > 0) { childDataList.clear(); }
//        dbrChildData.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
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
//                                        }
//                                    }
//                                }
//                            } else if (contracts instanceof ArrayList) {
//                                android.util.Log.e(TAG, "ChildData: Contracts empty");
//                            }
//                            childDataList.add(new ModelChildData(coins, coinsBank, coinsPiggy, coinsSpentToday, contractsList1, idChilds));
//                            android.util.Log.e(TAG, "ChildData: Contracts added");
//                        }
//                    android.util.Log.e(TAG, "ChildData: EXIST");
//                    if (n == 1) { callBack.onResponseChildData();}
//                    else if  (n == 2) { callBack.onResponseSignIn(n); }
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
    ///////////////          CHILD DATA               ///////////////////////


    ///////////////          DEVICE               ///////////////////////
    @Override
    public void onDeviceCheckIfExist(ModelDevice modelDevice, String serialNumberOrIMEI, String id, String idAccount) {
        Query query = dbrDevices.child(id).child(serialNumberOrIMEI);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ModelDevice m = dataSnapshot.getValue(ModelDevice.class);
                    if (!m.getIdUser().equals(idAccount)) {
                        dataSnapshot.getRef().child("idUser").setValue(idAccount);
                    }
                    dataSnapshot.getRef().child("onlineStatus").setValue("true");
                    android.util.Log.e("TAG", "DeviceCheck: EXIST");
                } else {
                    dbrDevices.child(id).child(serialNumberOrIMEI).setValue(modelDevice);
                    android.util.Log.e("TAG", "DeviceCheck: NOT EXIST");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                android.util.Log.e("TAG", "DeviceCheck: ERROR");
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void onRequestMentor(ICallBack callBack, String id) {
        DatabaseReference dbrMentor = FirebaseDatabase.getInstance().getReference("Mentors");
        dbrMentor.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String avatar = null, idM = null, login =null, name = null;
                if (dataSnapshot.exists()) {
                    Iterator i = dataSnapshot.getChildren().iterator();
                    while (i.hasNext()) {
                        avatar = (String) ((DataSnapshot) i.next()).getValue();
                        idM = (String) ((DataSnapshot) i.next()).getValue();
                        login = (String) ((DataSnapshot) i.next()).getValue();
                        name = (String) ((DataSnapshot) i.next()).getValue();
                    }
                    if (avatar != null && name != null) {
                        callBack.onResponseMentors(avatar, name);
                    }
                } else {
                    android.util.Log.e(TAG, "Mentors: ERROR");
                   // callBack.onMessageCallBack("Error: Family Room NOT EXIST");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
               // callBack.onMessageCallBack("Error: Server OFF");
            }
        });
    }

    ///////////////          DEVICE               ///////////////////////




}
