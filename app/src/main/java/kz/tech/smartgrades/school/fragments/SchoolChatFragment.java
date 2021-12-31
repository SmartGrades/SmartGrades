package kz.tech.smartgrades.school.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades._Firebase;
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.adaptes.SchoolChatAdapter;
import kz.tech.smartgrades.school.models.ModelMentorGradesList;
import kz.tech.smartgrades.school.models.ModelUsersList;

public class SchoolChatFragment extends Fragment implements View.OnClickListener {

    private SchoolActivity activity;
    private ImageView ivBack;
    private CircleImageView civAvatar;
    private TextView tvTitle;
    private RecyclerView recyclerView;
    private SchoolChatAdapter schoolChatAdapter;
    private ModelUsersList modelUsersList;


    public static SchoolChatFragment newInstance(ModelUsersList modelUserList) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("model", modelUserList);
        SchoolChatFragment fragment = new SchoolChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SchoolActivity) getActivity();
        if (getArguments() != null) modelUsersList = getArguments().getParcelable("model");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_default_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if (modelUsersList != null) onLoadWardOnAddition();
    }

    private void initViews(View view) {
        ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        tvTitle = view.findViewById(R.id.tvTitle);
        civAvatar = view.findViewById(R.id.civAvatar);
        recyclerView = view.findViewById(R.id.rvChatAdapter);
        schoolChatAdapter = new SchoolChatAdapter(activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(schoolChatAdapter);
    }


    private void onLoadWardOnAddition() {
        Picasso.get().load(modelUsersList.getAvatar()).fit().centerInside().into(civAvatar);
        tvTitle.setText(modelUsersList.getFullName());
        String chatId = modelUsersList.getId();

        DatabaseReference drChats = new _Firebase().getRefChats();
        drChats.child(chatId).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<ModelMentorGradesList> MentorGradesLists = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();
                        ModelDefaultMessages valueList = dataSnapshot.child(key).getValue(ModelDefaultMessages.class);
                        if (valueList.getSourceId().equals(modelUsersList.getId())){




                        }
                        //modelMultiTypes.add(valueList);
                    }
                    /*if (valueList.getSourceId().equals(modelUsersList.getId())){
                        ModelMentorGradesList m = new ModelMentorGradesList();
                        m.setMentorId(modelUsersList.getId());
                        m.set(modelUsersList.getId());
                    }*/

                    /*DatabaseReference dbrUsers = new _Firebase().getRefUsers();
                    dbrUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String userId = snapshot.getKey();
                                for (int i = 0; i < modelMultiTypes.size(); i++){
                                    if (modelMultiTypes.get(i).getSourceId().equals(userId));
                                    ModelUserList valueUserList = dataSnapshot.child(userId).getValue(ModelUserList.class);
                                    modelMultiTypes.get(i).setAvatar(valueUserList.getAvatar());
                                    modelMultiTypes.get(i).setFullName(valueUserList.getFullName());
                                }
                                schoolChatAdapter.updateData(modelMultiTypes);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });*/
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void onBack() {
        activity.onRemoveFragment();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBack();
                break;
        }
    }
}
