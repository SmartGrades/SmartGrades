package kz.tech.smartgrades.mentor;

import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.adaptes.SchoolClassAdapter;
import kz.tech.smartgrades.school.adaptes.SchoolColumnsAdapter;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.view.Gravity.CENTER;
import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_SchoolDeleteClass;
import static kz.tech.smartgrades._Web.func_SchoolDeleteColumn;
import static kz.tech.smartgrades._Web.func_SchoolMoveClassv2;

public class MentorClassMoveDragListener implements View.OnDragListener{

    private MentorActivity activity;

    private final int clColumnId = R.id.clColumn;
    private final int cvColumnId = R.id.cvColumn;
    private final int rvColumnsId = R.id.rvColumns;
    private final int cvClassId = R.id.cvClass;
    private final int clClassId = R.id.clClass;
    private final int rvClassId = R.id.rvClass;

    private final int tvDelete = R.id.tvDelete;
    private final int vMoveLeft = R.id.vMoveListLeft;
    private final int vMoveRight = R.id.vMoveListRight;

    private Timer timer;
    private int MoveType = 0;

    private FrameLayout flDelete;

    private ModelSchoolClass mClass;
    private int posSource, posTarget;
    private RecyclerView rvSource, rvTarget, rvColumns, rvClass;
    private SchoolClassAdapter adapterSource, adapterTarget;
    private SchoolColumnsAdapter adapterColumns;
    private ArrayList<ModelSchoolClass> mClassListSource, mClassListTarget;
    private ArrayList<ModelSchoolClassesColumn> mColumns;
    private ModelSchoolClassesColumn mColumn;
    private ViewGroup.LayoutParams layoutParamter;

    private Callbackk callbackk;
    interface Callbackk{
        void callback(ModelAnswer answer);
    }

    public MentorClassMoveDragListener(FrameLayout flDelete){
        this.flDelete = flDelete;
    }
    public MentorClassMoveDragListener(MentorActivity activity, FrameLayout flDelete){
        this.activity = activity;
        this.flDelete = flDelete;
    }
    public MentorClassMoveDragListener(RecyclerView rvColumns){
        this.rvColumns = rvColumns;
    }

    private void onDrop(ArrayList<ModelSchoolClassesColumn> mColumns){
        String SOAP = SoapRequest(func_SchoolMoveClassv2, new Gson().toJson(mColumns));
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = XMLToJson(response.body().string());
                }
            }
        });
    }
    private void deleteClass(Callbackk callbackk, String SchoolId, String ColumnId, String Id){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("SchoolId", SchoolId);
        jsonData.addProperty("ColumnId", ColumnId);
        jsonData.addProperty("Id", Id);
        String SOAP = SoapRequest(func_SchoolDeleteClass, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            callbackk.callback(answer);
                        }
                    });
                }
            }
        });
    }
    private void deleteColumn(Callbackk callbackk, String SchoolId, String ColumnId){
        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("SchoolId", SchoolId);
        jsonData.addProperty("Id", ColumnId);
        String SOAP = SoapRequest(func_SchoolDeleteColumn, jsonData.toString());
        RequestBody body = RequestBody.create(ContentType_XML, SOAP);
        Request request = new Request.Builder().url(URL).post(body).build();
        HttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(final Call call, IOException e){
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException{
                if(response.isSuccessful()){
                    String result = XMLToJson(response.body().string());
                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                    activity.runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            callbackk.callback(answer);
                        }
                    });
                }
            }
        });
    }
    private void onClear(View vSource){//CardView
        try{
            View rvView = (RecyclerView) vSource.getParent();
            if(rvView == null) return;
            rvView = (ConstraintLayout) rvView.getParent();
            if(rvView == null) return;
            rvView = (CardView) rvView.getParent();
            if(rvView == null) return;
            rvView = (RecyclerView) rvView.getParent();
            if(rvView == null) return;

            rvColumns = (RecyclerView) rvView;
            adapterColumns = (SchoolColumnsAdapter) rvColumns.getAdapter();
            mColumns = adapterColumns.getColumnsList();

            rvSource = (RecyclerView) vSource.getParent();
            if(rvSource == null) return;
            adapterSource = (SchoolClassAdapter) rvSource.getAdapter();
            mClass = adapterSource.getSelectClass();

            for(int _column = 0; _column < mColumns.size(); _column++){
                if(!listIsNullOrEmpty(mColumns.get(_column).getClasses())){
                    for(int _class = 0; _class < mColumns.get(_column).getClasses().size(); _class++){
                        if(mColumns.get(_column).getClasses().get(_class).getId().equals(mClass.getId())){
                            if(rvColumns.findViewHolderForAdapterPosition(_column) != null){
                                rvClass = rvColumns.findViewHolderForAdapterPosition(_column).itemView.findViewById(R.id.rvClass);
                                if(rvClass != null){
                                    adapterTarget = (SchoolClassAdapter) rvClass.getAdapter();
                                    if(adapterTarget.isAddedItem()){
                                        adapterTarget.setAddedItem(false);
                                        mClassListTarget = adapterTarget.getClassesList();
                                        mClassListTarget.remove(adapterTarget.getPosAddedItem());
                                        adapterTarget.notifyItemRemoved(adapterTarget.getPosAddedItem());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception ignored){
        }
    }

    @Override
    public boolean onDrag(View vTarget, DragEvent event){
        View vSource = (View) event.getLocalState();
        switch(event.getAction()){

            case DragEvent.ACTION_DRAG_ENTERED:
                if(vTarget.getId() == tvDelete){
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    flDelete.findViewById(R.id.tvDelete).setLayoutParams(layoutParams);
                }
                else if(vTarget.getId() == vMoveLeft || vTarget.getId() == vMoveRight){
                    if(vTarget.getId() == vMoveRight) MoveType = 1;
                    timer = new Timer();
                    timer.schedule(new TimerScrollTask(), 0, 5);
                    break;
                }
                else{
                    if(vSource.getId() == cvColumnId && vTarget.getId() == clColumnId){
                        rvColumns = (RecyclerView) vSource.getParent();
                        if(rvColumns != null){
                            posSource = (int) vSource.getTag();
                            posTarget = (int) ((CardView) vTarget.getParent()).getTag();
                            vSource.findViewById(clColumnId).setTag(posTarget);
                            vTarget.setTag(posSource);
                            adapterColumns = (SchoolColumnsAdapter) rvColumns.getAdapter();
                            mColumns = adapterColumns.getColumnsList();
                            Collections.swap(mColumns, posSource, posTarget);
                            adapterColumns.notifyItemMoved(posSource, posTarget);
                            if(posSource > posTarget) rvColumns.scrollToPosition(posTarget);
                            else rvColumns.scrollToPosition(posSource);
                        }
                    }
                    else if(vSource.getId() == cvClassId){
                        switch(vTarget.getId()){

                            /*case rvColumnsId:
                                onClear(vSource);
                                layoutParamter = vSource.getLayoutParams();
                                layoutParamter.height = 0;
                                vSource.setLayoutParams(layoutParamter);
                                break;*/

                            case clColumnId:
                                rvSource = (RecyclerView) vSource.getParent();
                                rvTarget = vTarget.findViewById(R.id.rvClass);
                                if(rvSource != null && rvTarget != null && rvSource != rvTarget){
                                    adapterTarget = (SchoolClassAdapter) rvTarget.getAdapter();
                                    if(!adapterTarget.isAddedItem() && listIsNullOrEmpty(adapterTarget.getClassesList())){
                                        adapterTarget.setAddedItem(true);
                                        adapterTarget.setPosAddedItem(0);

                                        adapterSource = (SchoolClassAdapter) rvSource.getAdapter();
                                        mClass = adapterSource.getSelectClass();

                                        mClassListTarget = adapterTarget.getClassesList();
                                        mClassListTarget.add(mClass);
                                        adapterTarget.notifyItemInserted(0);
                                    }
                                }
                                break;

                            case cvClassId:
                                if(vTarget.getVisibility() == View.INVISIBLE) return true;
                                rvSource = (RecyclerView) vSource.getParent();
                                rvTarget = (RecyclerView) vTarget.getParent();
                                if(rvSource == rvTarget){
                                    if(vSource.getLayoutParams().height == 0){
                                        layoutParamter = vSource.getLayoutParams();
                                        layoutParamter.height = layoutParamter.WRAP_CONTENT;
                                        vSource.setLayoutParams(layoutParamter);

                                        posSource = (int) vSource.getTag();
                                        posTarget = (int) vTarget.getTag();
                                        adapterSource = (SchoolClassAdapter) rvSource.getAdapter();
                                        mClassListSource = adapterSource.getClassesList();
                                        adapterSource.notifyItemMoved(posTarget, posSource);
                                        Collections.swap(mClassListSource, posSource, posTarget);
                                        vSource.setTag(posTarget);
                                        vTarget.setTag(posSource);
                                    }
                                    else{
                                        posSource = (int) vSource.getTag();
                                        posTarget = (int) vTarget.getTag();
                                        adapterSource = (SchoolClassAdapter) rvSource.getAdapter();
                                        mClassListSource = adapterSource.getClassesList();
                                        adapterSource.notifyItemMoved(posTarget, posSource);
                                        Collections.swap(mClassListSource, posSource, posTarget);
                                        vSource.setTag(posTarget);
                                        vTarget.setTag(posSource);
                                    }
                                }
                                else{
                                    adapterTarget = (SchoolClassAdapter) rvTarget.getAdapter();
                                    if(!adapterTarget.isAddedItem()){
                                        adapterTarget.setAddedItem(true);
                                        posTarget = (int) vTarget.getTag();
                                        adapterTarget.setPosAddedItem(posTarget);

                                        mClassListTarget = adapterTarget.getClassesList();
                                        rvSource = (RecyclerView) vSource.getParent();
                                        if(rvSource != null){
                                            adapterSource = (SchoolClassAdapter) rvSource.getAdapter();
                                            mClass = adapterSource.getSelectClass();
                                            mClassListTarget.add(posTarget, mClass);
                                            adapterTarget.notifyItemInserted(posTarget);
                                            adapterTarget.notifyItemRangeChanged(0, mClassListTarget.size());
                                        }
                                    }
                                    else{
                                        adapterTarget.setPosAddedItem(posTarget);
                                        if(adapterTarget.getClassesList().size() > 0){
                                            posSource = adapterTarget.getPosAddedItem();
                                            posTarget = (int) vTarget.getTag();

                                            vTarget.setTag(posSource);
                                            mClassListTarget = adapterTarget.getClassesList();
                                            Collections.swap(mClassListTarget, posSource, posTarget);
                                            adapterTarget.notifyItemMoved(posSource, posTarget);
                                            //adapterTarget.notifyItemRangeChanged(0, mClassListTarget.size());
                                        }
                                    }
                                }
                                break;
                        }
                    }
                }
                break;

            case DragEvent.ACTION_DRAG_EXITED:
                switch(vTarget.getId()){
                    case tvDelete:
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, CENTER);
                        flDelete.findViewById(R.id.tvDelete).setLayoutParams(layoutParams);
                        break;
                    case vMoveLeft:
                    case vMoveRight:
                        if(timer != null){
                            timer.cancel();
                            timer = null;
                        }
                        break;

                    case clColumnId:
                        if(vSource.getId() == cvClassId){
                            rvSource = (RecyclerView) vSource.getParent();
                            rvTarget = vTarget.findViewById(rvClassId);
                            if(rvSource == rvTarget){
                                layoutParamter = vSource.getLayoutParams();
                                layoutParamter.height = 0;
                                vSource.setLayoutParams(layoutParamter);
                            }
                            else{
                                adapterTarget = (SchoolClassAdapter) rvTarget.getAdapter();
                                if(adapterTarget.isAddedItem()){
                                    adapterTarget.setAddedItem(false);
                                    mClassListTarget = adapterTarget.getClassesList();
                                    mClassListTarget.remove(adapterTarget.getPosAddedItem());
                                    adapterTarget.notifyItemRemoved(adapterTarget.getPosAddedItem());
                                    adapterTarget.notifyItemRangeChanged(0, mClassListTarget.size());
                                }
                            }
                        }
                        break;
                }
                break;

            case DragEvent.ACTION_DROP:
                if(vTarget.getId() == vMoveLeft || vTarget.getId() == vMoveRight){
                    if(timer != null){
                        timer.cancel();
                        timer = null;
                    }
                    return false;
                }
                else if(vTarget.getId() == tvDelete){
                    if(vSource.getId() == cvColumnId){
                        rvColumns = (RecyclerView) vSource.getParent();
                        adapterColumns = (SchoolColumnsAdapter) rvColumns.getAdapter();
                        ArrayList<ModelSchoolClassesColumn> columnsList = adapterColumns.getColumnsList();
                        ModelSchoolClassesColumn mColumn = columnsList.get((int) vSource.getTag());
                        callbackk = new Callbackk(){
                            @Override
                            public void callback(ModelAnswer answer){
                                activity.alert.onToast(answer.getMessage());
                                if(answer.isSuccess()){
                                    columnsList.remove(mColumn);
                                    adapterColumns.notifyItemRemoved((int) vSource.getTag());
                                    adapterColumns.notifyItemRangeChanged(0, columnsList.size());
                                }
                            }
                        };
                        deleteColumn(callbackk, activity.login.loadUserDate(LoginKey.ID), mColumn.getId());
                    }
                    else if(vSource.getId() == cvClassId){
                        RecyclerView rvSource = (RecyclerView) vSource.getParent();
                        SchoolClassAdapter adapterSource = (SchoolClassAdapter) rvSource.getAdapter();
                        ModelSchoolClass mClass = adapterSource.getSelectClass();
                        ArrayList<ModelSchoolClass> ClassList = adapterSource.getClassesList();
                        ModelSchoolClassesColumn mColumnSource = adapterSource.getColumn();

                        callbackk = new Callbackk(){
                            @Override
                            public void callback(ModelAnswer answer){
                                activity.alert.onToast(answer.getMessage());
                                if(answer.isSuccess()){
                                    ClassList.remove(mClass);
                                    adapterSource.notifyItemRemoved((int) vSource.getTag());
                                    adapterSource.notifyItemRangeChanged(0, ClassList.size());
                                }
                            }
                        };
                        deleteClass(callbackk, activity.login.loadUserDate(LoginKey.ID), mColumnSource.getId(), mClass.getId());
                    }
                    flDelete.setVisibility(View.GONE);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, CENTER);
                    flDelete.findViewById(R.id.tvDelete).setLayoutParams(layoutParams);
                }
                else if(vSource.getId() == cvColumnId) vSource.setVisibility(View.VISIBLE);
                else if(vSource.getId() == cvClassId){
                    /*try{
                        Thread.sleep(100);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }*/
                    switch(vTarget.getId()){
                        case clColumnId:
                            rvTarget = vTarget.findViewById(rvClassId);
                            break;
                        case rvClassId:
                            rvTarget = (RecyclerView) vTarget;
                            break;
                        case cvClassId:
                            rvTarget = (RecyclerView) vTarget.getParent();
                            break;
                        case rvColumnsId:
                            return false;
                    }
                    rvSource = (RecyclerView) vSource.getParent();
                    adapterSource = (SchoolClassAdapter) rvSource.getAdapter();
                    adapterTarget = (SchoolClassAdapter) rvTarget.getAdapter();
                    if(rvSource != rvTarget && adapterTarget.isAddedItem()){
                        posSource = (int) vSource.getTag();

                        mClassListSource = adapterSource.getClassesList();
                        mClassListSource.remove(posSource);
                        adapterSource.notifyItemRemoved(posSource);
                        adapterSource.notifyItemRangeChanged(0, mClassListSource.size());

                        mClassListTarget = adapterTarget.getClassesList();
                        mClassListTarget.remove(adapterTarget.getPosAddedItem());
                        adapterTarget.setAddedItem(false);
                        mClassListTarget.add(adapterSource.getSelectClass());
                        adapterTarget.notifyItemRangeChanged(0, mClassListTarget.size());

                        mClass = adapterSource.getSelectClass();
                        mColumn = adapterTarget.getColumn();
                        if(listIsNullOrEmpty(mColumn.getClasses()))
                            mColumn.setClasses(new ArrayList<>());
                        adapterTarget.getColumn().getClasses().add(adapterTarget.getPosAddedItem(), mClass);

                        ArrayList<ModelSchoolClassesColumn> list = adapterTarget.getColumns();
                        onDrop(list);
                        return true;
                    }
                    else{
                        layoutParamter = vSource.getLayoutParams();
                        layoutParamter.height = layoutParamter.WRAP_CONTENT;
                        vSource.setLayoutParams(layoutParamter);
                        vSource.setVisibility(View.VISIBLE);
                    }
                }

            /*else if(vSource == vTarget){
                    vSource.setVisibility(View.VISIBLE);
                    rvSource = (RecyclerView) vSource.getParent();
                    adapterSource = (SchoolClassAdapter) rvSource.getAdapter();
                    onDrop(adapterSource.getColumns());
                    return true;
                }
                else if(vTarget.getId() == rvColumnId){
                    rvColumns = (RecyclerView) vTarget;
                    adapterColumns = (SchoolColumnsAdapter) rvColumns.getAdapter();
                    onDrop(adapterColumns.getColumnsList());
                }
                else if(vTarget.getId() == cvColumnId){
                    View SourceColumn = (RecyclerView) vSource.getParent();
                    if(SourceColumn == null) return true;
                    SourceColumn = (ConstraintLayout) SourceColumn.getParent();
                    if(SourceColumn == null) return true;
                    SourceColumn = (CardView) SourceColumn.getParent();
                    if(SourceColumn == null) return true;

                    if(SourceColumn != vTarget){
                        posSource = (int) vSource.getTag();
                        rvSource = (RecyclerView) vSource.getParent();
                        adapterSource = (SchoolClassAdapter) rvSource.getAdapter();
                        mClassListSource = adapterSource.getClassesList();
                        mClassListSource.remove(posSource);
                        if(listIsNullOrEmpty(mClassListSource)){
                            rvColumns = (RecyclerView) (((CardView) ((ConstraintLayout) ((RecyclerView) vSource.getParent()).getParent()).getParent())).getParent();
                            if(rvColumns != null){
                                adapterColumns = (SchoolColumnsAdapter) rvColumns.getAdapter();
                                adapterColumns.notifyItemChanged(adapterSource.getPosColumn());
                            }
                        }
                        else{
                            adapterSource.notifyItemRemoved(posSource);
                            adapterSource.notifyItemRangeChanged(0, mClassListSource.size());
                        }

                        rvTarget = vTarget.findViewById(R.id.rvClass);
                        adapterTarget = (SchoolClassAdapter) rvTarget.getAdapter();
                        adapterTarget.setAddedItem(false);
                        mClassListTarget = adapterTarget.getClassesList();
                        adapterTarget.notifyItemRangeChanged(0, mClassListTarget.size());
                        rvColumns = (RecyclerView) (((CardView) ((ConstraintLayout) ((RecyclerView) vSource.getParent()).getParent()).getParent())).getParent();
                        if(rvColumns != null){
                            adapterColumns = (SchoolColumnsAdapter) rvColumns.getAdapter();
                            adapterColumns.notifyItemChanged(adapterTarget.getPosColumn());
                        }
                    }
                    else{
                        layoutParamter = vSource.getLayoutParams();
                        layoutParamter.height = layoutParamter.WRAP_CONTENT;
                        vSource.setLayoutParams(layoutParamter);
                        vSource.setVisibility(View.VISIBLE);
                    }
                    onDrop(adapterSource.getColumns());
                }
                else if(vTarget.getId() == cvClassId){
                    rvSource = (RecyclerView) vSource.getParent();
                    rvTarget = (RecyclerView) vTarget.getParent();
                    if(rvSource != rvTarget){
                        posSource = (int) vSource.getTag();
                        adapterSource = (SchoolClassAdapter) rvSource.getAdapter();
                        mClassListSource = adapterSource.getClassesList();
                        mClassListSource.remove(posSource);
                        if(listIsNullOrEmpty(mClassListSource)){
                            rvColumns = (RecyclerView) (((CardView) ((ConstraintLayout) ((RecyclerView) vSource.getParent()).getParent()).getParent())).getParent();
                            if(rvColumns != null){
                                adapterColumns = (SchoolColumnsAdapter) rvColumns.getAdapter();
                                adapterColumns.notifyItemChanged(adapterSource.getPosColumn());
                            }
                        }
                        else{
                            adapterSource.notifyItemRemoved(posSource);
                            adapterSource.notifyItemRangeChanged(0, mClassListSource.size());
                        }
                        adapterTarget = (SchoolClassAdapter) rvTarget.getAdapter();
                        adapterTarget.setAddedItem(false);
                        mClassListTarget = adapterTarget.getClassesList();
                        adapterTarget.notifyItemRangeChanged(0, mClassListTarget.size());
                        onDrop(adapterSource.getColumns());
                    }
                    else{
                        layoutParamter = vSource.getLayoutParams();
                        layoutParamter.height = layoutParamter.WRAP_CONTENT;
                        vSource.setLayoutParams(layoutParamter);
                        vSource.setVisibility(View.VISIBLE);
                    }
                }

                */
                break;

            case DragEvent.ACTION_DRAG_ENDED:
                if(flDelete != null && flDelete.getVisibility() == View.VISIBLE){
                    flDelete.setVisibility(View.GONE);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, CENTER);
                    flDelete.findViewById(R.id.tvDelete).setLayoutParams(layoutParams);
                }
                if(!event.getResult() && vSource.getId() == cvClassId){
                    layoutParamter = vSource.getLayoutParams();
                    layoutParamter.height = layoutParamter.WRAP_CONTENT;
                    vSource.setLayoutParams(layoutParamter);
                    vSource.setVisibility(View.VISIBLE);
                    onClear(vSource);
                }
                break;
        }
        return true;
    }

    class TimerScrollTask extends TimerTask{
        public void run(){
            if(rvColumns == null) return;
            rvColumns.post(new Runnable(){
                @Override
                public void run(){
                    if(MoveType == 0) rvColumns.scrollBy(-10, 0);
                    else rvColumns.scrollBy(10, 0);
                }
            });
        }
    }
}