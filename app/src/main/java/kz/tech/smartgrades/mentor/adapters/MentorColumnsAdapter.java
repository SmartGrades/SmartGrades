package kz.tech.smartgrades.mentor.adapters;

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
import kz.tech.smartgrades.F;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.models.ModelMentorClassesColumn;
import kz.tech.smartgrades.mentor.models.ModelMentorData;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolClassMoveDragListener;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
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
import static kz.tech.smartgrades._Web.func_MentorDeleteColumn;

public class MentorColumnsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        View.OnLongClickListener{

    private MentorActivity activity;
    private String MENTOR_ID;

    private ArrayList<ModelMentorClassesColumn> mColumns;

    private FrameLayout flDelete;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onAddClassClick(ModelMentorClassesColumn m);
        void onEditColumnClick(ModelMentorClassesColumn m);
        void onAddColumnClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public ModelMentorClassesColumn getItem(int tag){
        return mColumns.get(tag);
    }
    public MentorColumnsAdapter(MentorActivity activity, FrameLayout flDelete){
        this.activity = activity;
        this.mColumns = new ArrayList<>();
        this.flDelete = flDelete;
        MENTOR_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    public void updateData(ModelMentorData mMentorData){
        onClear();
        if(mMentorData!=null)
            if(!listIsNullOrEmpty(mMentorData.getColumns()))
                mColumns.addAll(mMentorData.getColumns());
        mColumns.add(new ModelMentorClassesColumn());
        notifyDataSetChanged();
    }
    public void setData(ArrayList<ModelMentorClassesColumn> DataList){
        onClear();
        if(!listIsNullOrEmpty(DataList)) this.mColumns.addAll(DataList);
        this.mColumns.add(new ModelMentorClassesColumn());
        notifyDataSetChanged();
    }
    private void onClear(){
        if(mColumns.size() > 0) mColumns.clear();
    }

    public ArrayList<ModelMentorClassesColumn> getColumnsList(){
        return mColumns;
    }
    public ModelMentorClassesColumn getColumnForClass(ModelSchoolClass mClass){
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
                jsonData.addProperty(F.MentorId, MENTOR_ID);
                jsonData.addProperty(F.Id, mColumns.get(pos).getId());

                String SOAP = SoapRequest(func_MentorDeleteColumn, jsonData.toString());
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
                                    if(answer.isSuccess()) activity.presenter.onStartPresenter();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        if(holder.getItemViewType() != 0) return;
        ModelMentorClassesColumn m = mColumns.get(position);

        if(!stringIsNullOrEmpty(m.getSchoolId()))
            ((vhColumn) holder).tvAddClass.setVisibility(View.GONE);
        else ((vhColumn) holder).tvAddClass.setVisibility(View.VISIBLE);

        ((vhColumn) holder).tvColumnName.setText(m.getName());
        if(!listIsNullOrEmpty(m.getClasses()) || mColumns.size() <= 2) ((vhColumn) holder).ivDelete.setVisibility(View.GONE);
        else ((vhColumn) holder).ivDelete.setVisibility(View.VISIBLE);

        MentorClassAdapter adapter = new MentorClassAdapter(activity, flDelete, mColumns, position, null, null);
        ((vhColumn) holder).rvClass.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        ((vhColumn) holder).rvClass.setAdapter(adapter);
        adapter.setOnItemClickListener(activity);
        adapter.updateData();

        if(!listIsNullOrEmpty(m.getClasses())){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((vhColumn) holder).rvClass.getLayoutParams();
            if(m.getClasses().size() >= 7) layoutParams.bottomMargin += DpToPx(activity, 50);
            else layoutParams.bottomMargin = 0;
            ((vhColumn) holder).rvClass.setLayoutParams(layoutParams);
        }
        else {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((vhColumn) holder).rvClass.getLayoutParams();
            layoutParams.bottomMargin = 0;
            ((vhColumn) holder).rvClass.setLayoutParams(layoutParams);
        }

        ((vhColumn) holder).itemView.setOnLongClickListener(this);
        ((vhColumn) holder).itemView.setTag(position);
        ((vhColumn) holder).clColumn.setOnDragListener(new SchoolClassMoveDragListener());
        ((vhColumn) holder).rvClass.setOnDragListener(new SchoolClassMoveDragListener());
    }

    @Override
    public int getItemCount(){
        return mColumns.size();
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
            return new vhColumn(LayoutInflater.from(activity).inflate(R.layout.item_mentor_columns, parent, false));
        return new vhAddColumn(LayoutInflater.from(activity).inflate(R.layout.item_school_add_column, parent, false));
    }

    class vhColumn extends RecyclerView.ViewHolder{
        CardView cvColumn;
        ConstraintLayout clColumn;
        TextView tvColumnName, tvAddClass;
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

            tvAddClass.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onAddClassClick(mColumns.get(getAdapterPosition()));
                }
            });
            ivEdit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onEditColumnClick(mColumns.get(getAdapterPosition()));
                }
            });
            ivDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(!listIsNullOrEmpty(mColumns.get(getAdapterPosition()).getClasses())) return;
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
