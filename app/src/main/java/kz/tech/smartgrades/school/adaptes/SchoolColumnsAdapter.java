package kz.tech.smartgrades.school.adaptes;

import android.app.AlertDialog;
import android.content.ClipData;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
import kz.tech.smartgrades.school.models.ModelSchoolData;
import kz.tech.smartgrades.school.SchoolClassMoveDragListener;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.Convert.DpToPx;
import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._System.Vibrator;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_SchoolDeleteColumn;

public class SchoolColumnsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnLongClickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;

    private ArrayList<ModelSchoolClassesColumn> mColumns;
    private ArrayList<ModelSchoolClassesColumn> filterList;
    private OnItemClickListener onItemClickListener;

    private ArrayList<ModelSchoolStudent> Students;
    private ArrayList<ModelSchoolTeacher> Teachers;
    private ArrayList<ModelSchoolLesson> Lessons;

    private FrameLayout flDelete;

    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(mColumns);
        else for(ModelSchoolClassesColumn _classColumn : mColumns) {
            if (!listIsNullOrEmpty(_classColumn.getClasses())) {
                for (ModelSchoolClass _class : _classColumn.getClasses()) {
                    if (!stringIsNullOrEmpty(_class.getName()) && _class.getName().toLowerCase().contains(toString.toLowerCase())) {
                        filterList.add(_classColumn);
                        break;
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onAddClassClick(ModelSchoolClassesColumn m);
        void onEditColumnClick(ModelSchoolClassesColumn m);
        void onAddColumnClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public ModelSchoolClassesColumn getItem(int tag){
        return mColumns.get(tag);
    }
    public SchoolColumnsAdapter(SchoolActivity activity, FrameLayout flDelete){
        this.activity = activity;
        this.mColumns = new ArrayList<>();
        filterList = new ArrayList<>();
        this.flDelete = flDelete;
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    public void updateData(ModelSchoolData mSchoolData){
        onClear();
        if(mSchoolData != null){
            this.mColumns = new ArrayList<>();
            if(!listIsNullOrEmpty(mSchoolData.getClassessColumns()))
                mColumns.addAll(mSchoolData.getClassessColumns());
            mColumns.add(new ModelSchoolClassesColumn());
            filterList.addAll(mColumns);

            if(!listIsNullOrEmpty(mSchoolData.getStudents())){
                Students = new ArrayList<>();
                Students.addAll(mSchoolData.getStudents());
            }
            if(!listIsNullOrEmpty(mSchoolData.getTeachers())){
                Teachers = new ArrayList<>();
                Teachers.addAll(mSchoolData.getTeachers());
            }
            if(!listIsNullOrEmpty(mSchoolData.getLessons())){
                Lessons = new ArrayList<>();
                Lessons.addAll(mSchoolData.getLessons());
            }

            notifyItemRangeChanged(0, mColumns.size());
        }
    }
    public void setData(ArrayList<ModelSchoolClassesColumn> DataList){
        onClear();
        if(!listIsNullOrEmpty(DataList))
            this.mColumns.addAll(DataList);
        this.mColumns.add(new ModelSchoolClassesColumn());
        notifyDataSetChanged();
    }
    private void onClear(){
        if(mColumns.size() > 0) mColumns.clear();
        if(filterList.size() > 0) filterList.clear();
    }

    public ArrayList<ModelSchoolClassesColumn> getColumnsList(){
        return mColumns;
    }
    public ModelSchoolClassesColumn getColumnForClass(ModelSchoolClass mClass){
        for(int column = 0; column < mColumns.size(); column++){
            if(mColumns.get(column).getClasses() != null){
                for(int Class = 0; Class < mColumns.get(column).getClasses().size(); Class++){
                    if(mColumns.get(column).getClasses().get(Class).getId().equals(mClass.getId()))
                        return mColumns.get(column);
                }
            }
        }
        return null;
    }

    @Override
    public boolean onLongClick(View view){
        Vibrator(activity);
        //flDelete.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            view.startDragAndDrop(data, shadowBuilder, view, 0);
        else view.startDrag(data, shadowBuilder, view, 0);
        return true;
    }
    private void onMenuClick(int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View adView = activity.getLayoutInflater().inflate(R.layout.ad_school_remove_or_moveclass, null);
        builder.setView(adView);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        TextView tvTitle = adView.findViewById(R.id.tvTitle);
        TextView tvDescription = adView.findViewById(R.id.tvDescription);
        TextView tvCancel = adView.findViewById(R.id.tvCancel);
        TextView tvOk = adView.findViewById(R.id.tvOk);

        tvTitle.setText("Удалить столбец");
        tvDescription.setText("Удалить столбец " + mColumns.get(pos).getName() + "?");
        tvOk.setText("Удалить");

        tvCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();

                JsonObject jsonData = new JsonObject();
                jsonData.addProperty("SchoolId", SCHOOL_ID);
                jsonData.addProperty("Id", mColumns.get(pos).getId());

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
                            activity.runOnUiThread(new Runnable(){
                                @Override
                                public void run(){
                                    ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                    activity.alert.onToast(answer.getMessage());
                                    if(answer.isSuccess()) activity.updatePresenter();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public void UpdateColumns(){
        for(ModelSchoolClassesColumn _column : mColumns){
            if(!stringIsNullOrEmpty(_column.getName()) && _column.getName().equals("Temporary")){
                if(listIsNullOrEmpty(_column.getClasses())){
                    mColumns.remove(_column);
                    filterList.remove(_column);
                    break;
                }
            }
        }
        notifyItemRangeChanged(0, mColumns.size());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        if(holder.getItemViewType() != 0) return;
        ModelSchoolClassesColumn m = filterList.get(position);

        if(m.getName().equals("Temporary")){
            ((vhColumn) holder).tvColumnName.setText("Новые классы (Временный)");
            ((vhColumn) holder).clColumn.setBackgroundColor(activity.getResources().getColor(R.color.orange));
            ((vhColumn) holder).ivDelete.setVisibility(View.GONE);
            ((vhColumn) holder).ivEdit.setVisibility(View.GONE);
            ((vhColumn) holder).tvAddClass.setVisibility(View.GONE);
            ((vhColumn) holder).tvLabel.setVisibility(View.VISIBLE);
        }
        else {
            ((vhColumn) holder).clColumn.setBackgroundColor(activity.getResources().getColor(R.color.gray_disabled));
            ((vhColumn) holder).tvColumnName.setText(m.getName());
            if(!listIsNullOrEmpty(m.getClasses())) ((vhColumn) holder).ivDelete.setVisibility(View.GONE);

            ((vhColumn) holder).itemView.setOnLongClickListener(this);
            ((vhColumn) holder).itemView.setTag(position);
            ((vhColumn) holder).clColumn.setOnDragListener(new SchoolClassMoveDragListener());

            ((vhColumn) holder).tvLabel.setVisibility(View.GONE);
            ((vhColumn) holder).ivDelete.setVisibility(View.VISIBLE);
            ((vhColumn) holder).ivEdit.setVisibility(View.VISIBLE);
            ((vhColumn) holder).tvAddClass.setVisibility(View.VISIBLE);

            if (!listIsNullOrEmpty(filterList) && filterList.size() == 2) ((vhColumn) holder).ivDelete.setVisibility(View.GONE);
        }

        SchoolClassAdapter adapter = new SchoolClassAdapter(activity, flDelete, mColumns, position, Students, Teachers, Lessons);
        ((vhColumn) holder).rvClass.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        ((vhColumn) holder).rvClass.setAdapter(adapter);
        adapter.updateData();
        adapter.setOnItemClickListener(activity);

        if(!listIsNullOrEmpty(m.getClasses())){
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) ((vhColumn) holder).tvLabel.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) ((vhColumn) holder).rvClass.getLayoutParams();
            if(m.getClasses().size() >= 5) {
                layoutParams.bottomMargin = DpToPx(activity, 70);
                layoutParams2.bottomMargin = DpToPx(activity, 70);
            }
            else {
                layoutParams.bottomMargin = 0;
                layoutParams2.bottomMargin = 0;
            }
            ((vhColumn) holder).tvLabel.setLayoutParams(layoutParams);
            ((vhColumn) holder).rvClass.setLayoutParams(layoutParams2);
        }
        else {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) ((vhColumn) holder).tvLabel.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) ((vhColumn) holder).rvClass.getLayoutParams();
            layoutParams.bottomMargin = 0;
            layoutParams2.bottomMargin = 0;
            ((vhColumn) holder).tvLabel.setLayoutParams(layoutParams);
            ((vhColumn) holder).rvClass.setLayoutParams(layoutParams2);
        }

        ((vhColumn) holder).rvClass.setOnDragListener(new SchoolClassMoveDragListener());
    }

    @Override
    public int getItemCount(){
        return filterList.size();
    }
    @Override
    public int getItemViewType(int position){
        if(position < mColumns.size() - 1) return 0;
        return 1;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        if(viewType == 0)
            return new vhColumn(LayoutInflater.from(activity).inflate(R.layout.item_school_columns, parent, false));
        return new vhAddColumn(LayoutInflater.from(activity).inflate(R.layout.item_school_add_column, parent, false));
    }

    class vhColumn extends RecyclerView.ViewHolder{
        CardView cvColumn;
        ConstraintLayout clColumn;
        TextView tvColumnName, tvAddClass, tvLabel;
        ImageView ivEdit;
        ImageView ivDelete;
        RecyclerView rvClass;
        vhColumn(@NonNull View view){
            super(view);
            cvColumn = view.findViewById(R.id.cvColumn);
            clColumn = view.findViewById(R.id.clColumn);
            tvColumnName = view.findViewById(R.id.tvColumnName);
            tvAddClass = view.findViewById(R.id.tvAddClass);
            rvClass = view.findViewById(R.id.rvClass);
            ivEdit = view.findViewById(R.id.ivEdit);
            ivDelete = view.findViewById(R.id.ivDelete);
            tvLabel = view.findViewById(R.id.tvLabel);

            tvAddClass.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onAddClassClick(filterList.get(getAdapterPosition()));
                }
            });
            ivEdit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onEditColumnClick(filterList.get(getAdapterPosition()));
                }
            });
            ivDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //if(!listIsNullOrEmpty(filterList.get(getAdapterPosition()).getClasses())) return;
                    onMenuClick(getAdapterPosition());
                }
            });
        }
    }
    class vhAddColumn extends RecyclerView.ViewHolder{
        vhAddColumn(@NonNull View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onAddColumnClick();
                }
            });
        }
    }
}
