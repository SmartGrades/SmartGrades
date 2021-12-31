package kz.tech.smartgrades.mentor_module.coins;

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

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kz.tech.smartgrades.mentor_module.MentorActivity;
import kz.tech.smartgrades.mentor_module.coins.adapters.CoinsChildAdapter;
import kz.tech.smartgrades.mentor_module.coins.models.ModelChildList;
import kz.tech.smartgrades.mentor_module.coins.models.ModelGroups;
import kz.tech.smartgrades.mentor_module.coins.models.ModelMentorPushList;
import kz.tech.smartgrades.root.login.LoginKey;


public class CoinsFragment extends Fragment implements CoinsListenerClick {
    private MentorActivity activity;
    private CoinsView view;
    private CoinsChildAdapter adapter;
    private List<ModelMentorPushList> arrayList;
    private List<ModelGroups> groupsList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return view;
    }
    private void initViews() {
        activity = (MentorActivity) getActivity();
        view = new CoinsView(getActivity(), activity.language.getLanguage());
        adapter = new CoinsChildAdapter(getActivity(), activity.iDate.getCurrentDate());
        arrayList = new ArrayList<>();
        groupsList = new ArrayList<>();

        view.getCoinsChild().setAdapter(adapter);


        view.setOnItemClickListener(new CoinsView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (groupsList.size() > 0) {
                    adapter.updateData(groupsList.get(position).getChildList());
                    view.onTabSelect(position);
                }
            }
            @Override
            public void onSelectViewClick(int n) {
                switch (n) {
                    case 2:
                        view.onSelectFL(2);
                       // activity.view.onToolbarMentorSelect(2, null);
                        break;
                }
            }
            @Override
            public void onAddGroupsClick(String name) {
                onAddGroups(name);
            }
            @Override
            public void onRemoveGroupClick(String idGroup) {
                onRemoveGroup(idGroup);
            }

            @Override
            public void onPushClick() {
                /*if (arrayList.size() > 0) {
                    String avatar = activity.prefs.getMentorData("avatar");
                    String name = activity.prefs.getMentorData("name");
                    DialogPush dialog = new DialogPush(getActivity(), R.style.CustomDialog2, activity.language.getLanguage(),
                            avatar, name,  arrayList, activity);
                    dialog.show();
                    dialog.setOnItemClickListener(new DialogPush.OnItemClickListener() {
                        @Override
                        public void onAddOrRefuseClick(String a, List<ModelChildArray> l, String d, String m, String n) {
                            //onAddOrRefuseMsg(a, l, d, m, n);
                        }
                        @Override
                        public void onAddClick(List<ModelChildList> list) {
                            if (groupsList.size() == 1) {
                                for (int i = 0; i < list.size(); i++) {
                                    onAddChildToGroup(list.get(i), groupsList.get(0).getIdGroup());
                                }
                                activity.onShowToast(activity.language.getLanguage().getString(R.string.child_added));
                            } else if (groupsList.size() > 1) {
                                BottomDialogSelectGroup dialog2 =
                                        new BottomDialogSelectGroup(getActivity(), activity.language.getLanguage(), groupsList);
                                dialog2.show();
                                dialog2.setOnItemClickListener(new BottomDialogSelectGroup.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(ModelGroups m) {
                                        for (int j = 0; j < list.size(); j++) {
                                            onAddChildToGroup(list.get(j), m.getIdGroup());
                                        }
                                        activity.onShowToast(activity.language.getLanguage().getString(R.string.child_added));
                                    }
                                });
                            }


                        }
                    });
                }*/
            }
        });

        adapter.setOnItemClickListener(new CoinsChildAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String idLogin, String idChild, int n, int coins) {
                onRequestCoinsChange(idLogin, idChild, n, coins);
            }

            @Override
            public void onMsgClick(String msg) {

            }
        });
        onLoadAllGroups();


        view.onEnablePushAnimation();
    }
    private void onAddChildToGroup(ModelChildList m, String idGroup) {
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("MentorGroups");
        String generateId = dbr.push().getKey();
        String idLogin = activity.login.loadUserDate(LoginKey.ID);


        dbr.child(idLogin).child(idGroup).child("childList").child(generateId).setValue(m);
    }
    private void onAddGroups(String name) {
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("MentorGroups");
        String generateId = dbr.push().getKey();
        String idLogin = activity.login.loadUserDate(LoginKey.ID);


        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("childList", "");
        hashMap.put("idGroup", generateId);
        hashMap.put("name", name);



        dbr.child(idLogin).child(generateId).setValue(hashMap);

        List<ModelChildList> list = new ArrayList<>();

        groupsList.add(new ModelGroups(list, generateId, name));

        int n = groupsList.size()-1;
        view.onChildrenGroups(name, generateId, n);

        activity.alert.hideKeyboard(activity);

    }
    private void onLoadAllGroups() {
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("MentorGroups");
        String idLogin = activity.login.loadUserDate(LoginKey.ID);
        dbr.child(idLogin).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                        long num = ds2.getChildrenCount();
                        if (num == 3) {
                            List<ModelChildList> list = new ArrayList<>();
                            Iterator i = ds2.getChildren().iterator();
                            Object o = (Object) ((DataSnapshot) i.next()).getValue();//  childList
                            String s1 = (String) ((DataSnapshot) i.next()).getValue();//  idGroup
                            String s2 = (String) ((DataSnapshot) i.next()).getValue();//  name
                            if (o instanceof ArrayList) {
                                android.util.Log.e("TAG", "its  ArrayList");
                            }
                            if (o instanceof HashMap) {
                                HashMap hashMap = (HashMap) o;
                                Iterator itr = hashMap.entrySet().iterator();
                                while(itr.hasNext()) {
                                    Map.Entry pair = (Map.Entry) itr.next();
                                    HashMap hm2 = (HashMap) pair.getValue();
                                    String t1 = String.valueOf(hm2.get("accrualOfCoins"));
                                    String t2 = String.valueOf(hm2.get("accrualOfCoinsSuper"));
                                    String t3 = String.valueOf(hm2.get("accrualOfOffset"));
                                    String t4 = String.valueOf(hm2.get("accrualOfOffsetSuper"));
                                    String t5 = String.valueOf(hm2.get("avatar"));
                                    String t6 = String.valueOf(hm2.get("idChild"));
                                    String t7 = String.valueOf(hm2.get("idLogin"));
                                    String t8 = String.valueOf(hm2.get("name"));
                                    list.add(new ModelChildList(t1, t2, t3, t4, t5, t6, t7, t8));
                                }
                                android.util.Log.e("TAG", "its  HashMap");
                            }
                            groupsList.add(new ModelGroups(list, s1, s2));
                        }
                    }
                    for (int j = 0; j < groupsList.size(); j++) {
                        view.onChildrenGroups(groupsList.get(j).getName(), groupsList.get(j).getIdGroup(), j);
                    }

                    if (groupsList.size() > 0) {
                     //   view.getCoinsChild().setItemViewCacheSize(0);
                        adapter.updateData(groupsList.get(0).getChildList());
                        view.onTabSelect(0);
                    }
                    onLoadPush();
               //     android.util.Log.e("TAG", "MentorGroups: EXIST");
                } else {
                    android.util.Log.e("TAG", "MentorGroups: NOT EXIST");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                android.util.Log.e("TAG", "MentorGroups: ERROR");
            }
        });
    }
    private void onLoadPush() {
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("InteractionForm");
        String idLogin = activity.login.loadUserDate(LoginKey.ID);

        dbr.child(idLogin).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //List<ModelChildArray> list2 = new ArrayList<>();
                    String p = "parent";
                    String m = "mentor";
                    for (DataSnapshot ds2 : dataSnapshot.getChildren()){
                        long num = ds2.getChildrenCount();
                        if (num == 10) {
                            Iterator i = ds2.getChildren().iterator();
                            while (i.hasNext()) {
                                String a1 = (String) ((DataSnapshot) i.next()).getValue();//  accrualOfCoins
                                String a2 = (String) ((DataSnapshot) i.next()).getValue();//  accrualOfCoinsSuper
                                String a3 = (String) ((DataSnapshot) i.next()).getValue();//  accrualOfOffset
                                String a4 = (String) ((DataSnapshot) i.next()).getValue();//  accrualOfOffsetSuper
                                Object o1 = (Object) ((DataSnapshot) i.next()).getValue();//  childList
                                if (o1 instanceof HashMap) {
                                    HashMap hashMap = (HashMap) o1;
                                    Iterator itr = hashMap.entrySet().iterator();
                                    while(itr.hasNext()) {
                                        Map.Entry pair = (Map.Entry) itr.next();
                                        System.out.println("Key = " + pair.getKey() + ", Value = " + pair.getValue());
                                    }
                                } else if (o1 instanceof ArrayList) {
                                    //list2 = (ArrayList) o1;

                                    android.util.Log.e("TAG ", "ArrayList");
                                }
                                String d1 = (String) ((DataSnapshot) i.next()).getValue();//  dateOfCreation
                                String id1 = (String) ((DataSnapshot) i.next()).getValue();//  idLogin
                                String s1 = (String) ((DataSnapshot) i.next()).getValue();//  parentAvatar
                                String s2 = (String) ((DataSnapshot) i.next()).getValue();//  parentMessage
                                String s3 = (String) ((DataSnapshot) i.next()).getValue();//  parentName

                                android.util.Log.e("TAG ", "HashMap");

                                //arrayList.add(new ModelMentorPushList(a1, a2, a3, a4, s1, list2, d1, id1, s2, s3, p));
                            }

                        } else if (num == 5){
                            Iterator i = ds2.getChildren().iterator();
                            String e = "";
                            while (i.hasNext()) {
                                String s1 = (String) ((DataSnapshot) i.next()).getValue();//  avatar
                                Object o = (Object) ((DataSnapshot) i.next()).getValue();//  childList
                                if (o instanceof HashMap) {
                                    android.util.Log.e("TAG ", "HashMap FUCK YOU");
                                } else if (o instanceof ArrayList) {
                                    //list2 = (ArrayList) o;

                                    android.util.Log.e("TAG ", "ArrayList");
                                }
                                String d1 = (String) ((DataSnapshot) i.next()).getValue();//  date
                                String s2 = (String) ((DataSnapshot) i.next()).getValue();//  msg
                                String s3 = (String) ((DataSnapshot) i.next()).getValue();//  name

                                //arrayList.add(new ModelMentorPushList(e, e, e, e, s1, list2, d1, e, s2, s3, m));
                            }
                        }

                        android.util.Log.e("TAG ", "String");
                    }

                  //  Iterator i = dataSnapshot.getChildren().iterator();

                        android.util.Log.e("TAG ", "String");

//here
                    //     android.util.Log.e("TAG", "MentorGroups: EXIST");
                } else {
                    android.util.Log.e("TAG", "MentorGroups: NOT EXIST");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                android.util.Log.e("TAG", "MentorGroups: ERROR");
            }
        });
    }
    /*private void onAddOrRefuseMsg(String a, List<ModelChildArray> l, String d, String m, String n) {
        String idLogin = activity.login.loadUserDate(LoginKey.ID);

        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("InteractionForm");
        String generationID = dbr.push().getKey();

        ModelAddOrRefuseMsg model = new ModelAddOrRefuseMsg();
        model.setAvatar(a);
        model.setChildList(l);
        model.setDate(d);
        model.setMsg(m);
        model.setName(n);
        dbr.child(idLogin).child(generationID).setValue(model);
    }*/


    private void onRemoveGroup(String idGroup) {
        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("MentorGroups");
        String idLogin = activity.login.loadUserDate(LoginKey.ID);

        dbr.child(idLogin).child(idGroup).removeValue();
    }
    public void onRequestCoinsChange(String idLogin, String idChild, int n, int coins) {
            final int numbCoins = coins;
            DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("ChildData");
            Query query = dbr.child(idLogin).child(idChild).child("coins");
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String totalCoinsDB = (String) dataSnapshot.getValue();
                    int numb = Integer.parseInt(totalCoinsDB);
                    switch (n) {
                        case 1: numb -= numbCoins; break;
                        case 2: numb += numbCoins; break;
                    }
                    String result = String.valueOf(numb);
                    dataSnapshot.getRef().setValue(result);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            query.addListenerForSingleValueEvent(valueEventListener);
    }
    @Override
    public void onBackClick() {
      //  activity.view.onToolbarMentorSelect(1, null);
        view.onSelectFL(1);
    }
}
