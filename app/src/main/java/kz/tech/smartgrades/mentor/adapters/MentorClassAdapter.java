package kz.tech.smartgrades.mentor.adapters;

import android.app.AlertDialog;
import android.content.ClipData;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.mentor.models.ModelMentorClass;
import kz.tech.smartgrades.mentor.models.ModelMentorClassesColumn;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolClassMoveDragListener;
import kz.tech.smartgrades.school.models.ModelSchoolLesson;
import kz.tech.smartgrades.school.models.ModelSchoolStudent;
import kz.tech.smartgrades.school.models.ModelSchoolTeacher;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._System.Vibrator;
import static kz.tech.smartgrades._System.getLocationOnScreen;
import static kz.tech.smartgrades._Web.ContentType_XML;
import static kz.tech.smartgrades._Web.HttpClient;
import static kz.tech.smartgrades._Web.SoapRequest;
import static kz.tech.smartgrades._Web.URL;
import static kz.tech.smartgrades._Web.XMLToJson;
import static kz.tech.smartgrades._Web.func_SchoolDeleteClass;

public class MentorClassAdapter extends RecyclerView.Adapter<MentorClassAdapter.ViewHolder> implements View.OnLongClickListener{

    private MentorActivity activity;
    private String SCHOOL_ID;

    private ArrayList<ModelMentorClass> mClasses;
    private ModelMentorClass selectClass;

    private ArrayList<ModelSchoolStudent> Students;
    private ArrayList<ModelSchoolTeacher> Teachers;
    private ArrayList<ModelSchoolLesson> Lessons;

    private FrameLayout flDelete;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onSelectClass(ModelMentorClass m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    private ArrayList<ModelMentorClassesColumn> mColumns;
    private int PosColumn;
    private ModelMentorClassesColumn mColumn;
    public ArrayList<ModelMentorClassesColumn> getColumns(){
        return mColumns;
    }

    private boolean AddedItem = false;
    private int PosAddedItem = 0;
    public boolean isAddedItem(){
        return AddedItem;
    }
    public void setAddedItem(boolean addedItem){
        AddedItem = addedItem;
    }
    public int getPosAddedItem(){
        return PosAddedItem;
    }
    public void setPosAddedItem(int posAddedItem){
        this.PosAddedItem = posAddedItem;
    }
    public int getPosColumn(){
        return PosColumn;
    }

    public ModelMentorClass getSelectClass(){
        return selectClass;
    }
    public void setSelectClass(ModelMentorClass selectClass){
        this.selectClass = selectClass;
    }

    public MentorClassAdapter(MentorActivity activity, FrameLayout flDelete, ArrayList<ModelMentorClassesColumn> mColumns, int position,
                              ArrayList<ModelSchoolStudent> students, ArrayList<ModelSchoolLesson> lessons){
        this.activity = activity;
        this.mColumns = mColumns;
        this.PosColumn = position;
        this.flDelete = flDelete;
        Students = students;
        Lessons = lessons;
        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    public void updateData(){
        mColumn = mColumns.get(PosColumn);
        if(!listIsNullOrEmpty(mColumn.getClasses())) mClasses = mColumn.getClasses();
        else mClasses = new ArrayList<>();
        notifyItemRangeChanged(0, mClasses.size());
    }
    public void updateData(ArrayList<ModelMentorClass> mClasses){
        onClear();
        if(!listIsNullOrEmpty(mClasses)) this.mClasses.addAll(mClasses);
        notifyDataSetChanged();
    }

    private void onClear(){
        if(!listIsNullOrEmpty(mClasses)) mClasses.clear();
    }
    public ArrayList<ModelMentorClass> getClassesList(){
        return mClasses;
    }
    public ModelMentorClassesColumn getColumn(){
        return mColumn;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelMentorClass m = mClasses.get(position);

        holder.tvClassName.setText(m.getName());

        if(!listIsNullOrEmpty(m.getStudents())){
            holder.tvTitleNotStudents.setVisibility(View.GONE);
            holder.ivStudentsCount.setVisibility(View.VISIBLE);
            holder.tvStudentsCount.setVisibility(View.VISIBLE);
            holder.tvStudentsCount.setText(String.valueOf(m.getStudents().size()));
        }
        else {
            holder.tvTitleNotStudents.setVisibility(View.VISIBLE);
            holder.ivStudentsCount.setVisibility(View.GONE);
            holder.tvStudentsCount.setVisibility(View.GONE);
        }

        if(!stringIsNullOrEmpty(m.getSchoolId())){
            holder.itemView.setTag(position);
            holder.itemView.setOnLongClickListener(this);
            holder.itemView.setOnDragListener(new SchoolClassMoveDragListener(flDelete));
            if(AddedItem && PosAddedItem == position) holder.itemView.setVisibility(View.INVISIBLE);
            else holder.itemView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public boolean onLongClick(View view){
        Vibrator(activity);
        //flDelete.setVisibility(View.VISIBLE);
        view.setVisibility(View.INVISIBLE);
        int pos = (int)view.getTag();
        setSelectClass(mClasses.get(pos));
        ClipData data = ClipData.newPlainText("", "" );
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            view.startDragAndDrop(data, shadowBuilder, view, 0);
        else view.startDrag(data, shadowBuilder, view, 0);
        return true;
    }

    private void onDeleteClass(int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View view = activity.getLayoutInflater().inflate(R.layout.ad_school_remove_or_moveclass, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvDescription = view.findViewById(R.id.tvDescription);
        TextView tvCancel = view.findViewById(R.id.tvCancel);
        TextView tvOk = view.findViewById(R.id.tvOk);

        tvTitle.setText("Удалить класс");
        tvDescription.setText("Удалить класс " + mClasses.get(pos).getName() + " из столбца " + mColumn.getName() + "?");
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
                jsonData.addProperty("ColumnId", mColumn.getId());
                jsonData.addProperty("Id", mColumn.getClasses().get(pos).getId());

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
    private void onMoveClass(int pos){
        /*if(mColumns.size() > 1){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View view = activity.getLayoutInflater().inflate(R.layout.pw_school_move_class, null);
            builder.setView(view);
            AlertDialog adColumnsList = builder.create();
            adColumnsList.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            adColumnsList.show();

            RecyclerView rvColumnsList = view.findViewById(R.id.rvColumnsList);
            SchoolColumnsListAdapter adapter = new SchoolColumnsListAdapter(activity);
            rvColumnsList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            rvColumnsList.setAdapter(adapter);
            adapter.updateData(activity.getSchoolData().getClassessColumns());
            adapter.setOnItemClickListener(new SchoolColumnsListAdapter.OnItemClickListener(){
                @Override
                public void onSelectColumn(ModelSchoolClassesColumn m){
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    View view = activity.getLayoutInflater().inflate(R.layout.ad_school_remove_or_moveclass, null);
                    builder.setView(view);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    alertDialog.show();

                    TextView tvTitle = view.findViewById(R.id.tvTitle);
                    TextView tvDescription = view.findViewById(R.id.tvDescription);
                    TextView tvCancel = view.findViewById(R.id.tvCancel);
                    TextView tvOk = view.findViewById(R.id.tvOk);

                    tvTitle.setText("Переместить класс");
                    tvDescription.setText("Переместить класс " + mClasses.get(pos).getName() + " в " + mColumn.getName() + "?");
                    tvOk.setText("Переместить");

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
                            adColumnsList.dismiss();

                            JsonObject jsonData = new JsonObject();
                            jsonData.addProperty("SchoolId", SCHOOL_ID);
                            jsonData.addProperty("ColumnId", m.getId());
                            jsonData.addProperty("Id", mColumn.getClasses().get(pos).getId());

                            String SOAP = SoapRequest(func_SchoolMoveClass, jsonData.toString());
                            RequestBody body = RequestBody.create(ContentType_XML, SOAP);
                            Request request = new Request.Builder().url(URL).post(body).build();
                            HttpClient.newCall(request).enqueue(new Callback(){
                                @Override
                                public void onFailure(final Call call, IOException e){
                                }
                                @Override
                                public void onResponse(Call call, final Response response) throws IOException{
                                    if(response.isSuccessful()){
                                        String result = XMLReader(response.body().string());
                                        activity.runOnUiThread(new Runnable(){
                                            @Override
                                            public void run(){
                                                ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                                activity.alert.onToast(answer.getMessage());
                                                if(answer.isSuccess())
                                                    activity.presenter.onStartPresenter();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View view = activity.getLayoutInflater().inflate(R.layout.pw_school_notification, null);
            builder.setView(view);
            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialog.show();

            ((TextView) view.findViewById(R.id.tvOk)).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    alertDialog.dismiss();
                }
            });
        }*/
    }

    @Override
    public int getItemCount(){
        return mClasses.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_mentor_class, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvClassName;
        TextView tvTitleNotLessons, tvTitleNotStudents;
        ImageView ivMenu, ivStudentsCount;
        TextView tvStudentsCount;

        ViewHolder(@NonNull View view){
            super(view);
            tvClassName = view.findViewById(R.id.tvClassName);
            ivMenu = view.findViewById(R.id.ivMenu);
            tvTitleNotLessons = view.findViewById(R.id.tvTitleNotLessons);
            tvTitleNotStudents = view.findViewById(R.id.tvTitleNotStudents);
            ivStudentsCount = view.findViewById(R.id.ivStudentsCount);
            tvStudentsCount = view.findViewById(R.id.tvStudentsCount);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(onItemClickListener != null)
                        onItemClickListener.onSelectClass(mClasses.get(getAdapterPosition()));
                }
            });
            ivMenu.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    FrameLayout viewGroup = (FrameLayout) activity.findViewById(R.id.llSortChangePopup);
                    LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
                    View layout = layoutInflater.inflate(R.layout.pw_mentor_menu_class, viewGroup);
                    PopupWindow popupWindow = new PopupWindow(activity);
                    popupWindow.setContentView(layout);
                    popupWindow.setWidth(FrameLayout.LayoutParams.WRAP_CONTENT);
                    popupWindow.setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    Point p = getLocationOnScreen(ivMenu);
                    popupWindow.showAtLocation(layout, Gravity.NO_GRAVITY, p.x - 150, p.y);

                    LinearLayout llDelete = layout.findViewById(R.id.llDelete);
                    LinearLayout llRename = layout.findViewById(R.id.llRename);

                    llDelete.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            popupWindow.dismiss();
                            onDeleteClass(getAdapterPosition());
                        }
                    });
                    llRename.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            popupWindow.dismiss();
                            onMoveClass(getAdapterPosition());
                        }
                    });
                }
            });
        }
    }
}
