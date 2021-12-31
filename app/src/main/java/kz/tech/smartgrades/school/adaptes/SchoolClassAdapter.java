package kz.tech.smartgrades.school.adaptes;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.auth.models.ModelAnswer;
import kz.tech.smartgrades.root.login.LoginKey;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.SchoolClassMoveDragListener;
import kz.tech.smartgrades.school.bottom_sheet_dialogs.BSDRenameClass;
import kz.tech.smartgrades.school.models.ModelSchoolClass;
import kz.tech.smartgrades.school.models.ModelSchoolClassesColumn;
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
import static kz.tech.smartgrades._Web.func_SchoolMoveClass;


public class SchoolClassAdapter extends RecyclerView.Adapter<SchoolClassAdapter.ViewHolder> implements View.OnLongClickListener{

    private SchoolActivity activity;
    private String SCHOOL_ID;

    private ArrayList<ModelSchoolClass> mClasses;
    private ModelSchoolClass selectClass;

    private ArrayList<ModelSchoolStudent> Students;
    private ArrayList<ModelSchoolTeacher> Teachers;
    private ArrayList<ModelSchoolLesson> Lessons;

    private FrameLayout flDelete;

    private OnItemClickListener onItemClickListener;

    private ArrayList<ModelSchoolClassesColumn> mColumns;
    private int PosColumn;
    private ModelSchoolClassesColumn mColumn;
    public ArrayList<ModelSchoolClassesColumn> getColumns(){
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

    public ModelSchoolClass getSelectClass(){
        return selectClass;
    }
    public void setSelectClass(ModelSchoolClass selectClass){
        this.selectClass = selectClass;
    }

    public interface OnItemClickListener{
        void onSelectClass(ModelSchoolClass m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public SchoolClassAdapter(SchoolActivity activity, FrameLayout flDelete, ArrayList<ModelSchoolClassesColumn> mColumns, int position,
                              ArrayList<ModelSchoolStudent> students, ArrayList<ModelSchoolTeacher> teachers, ArrayList<ModelSchoolLesson> lessons){
        this.activity = activity;
        this.mColumns = mColumns;
        this.PosColumn = position;
        this.flDelete = flDelete;
        Students = students;
        Teachers = teachers;
        Lessons = lessons;

        SCHOOL_ID = activity.login.loadUserDate(LoginKey.ID);
    }

    public void updateData(){
        mColumn = mColumns.get(PosColumn);
        if(!listIsNullOrEmpty(mColumn.getClasses())) mClasses = mColumn.getClasses();
        else mClasses = new ArrayList<>();
        notifyItemRangeChanged(0, mClasses.size());
    }
    public void updateData(ArrayList<ModelSchoolClass> mClasses){
        onClear();
        if(!listIsNullOrEmpty(mClasses)) this.mClasses.addAll(mClasses);
        notifyDataSetChanged();
    }

    private void onClear(){
        if(!listIsNullOrEmpty(mClasses)) mClasses.clear();
    }
    public ArrayList<ModelSchoolClass> getClassesList(){
        return mClasses;
    }
    public ModelSchoolClassesColumn getColumn(){
        return mColumn;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelSchoolClass m = mClasses.get(position);
        String name = m.getName();
        if (name.length() > 33) name = name.substring(0, 30) + "...";
        holder.tvClassName.setText(name);
        holder.tvTitle.setVisibility(View.GONE);

        holder.itemView.setTag(position);
        holder.itemView.setOnLongClickListener(this);
        holder.itemView.setOnDragListener(new SchoolClassMoveDragListener(activity, flDelete));

        if(AddedItem && PosAddedItem == position) holder.itemView.setVisibility(View.INVISIBLE);
        else {
            holder.itemView.setVisibility(View.VISIBLE);

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
            if(!listIsNullOrEmpty(m.getTeachers())){
                holder.tvTitleNotTeachers.setVisibility(View.GONE);
                holder.civAvatar1.setVisibility(View.VISIBLE);
                String avatar = m.getTeachers().get(0).getAvatar();
                if(!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(holder.civAvatar1);
                if(m.getTeachers().size() > 1){
                    holder.civAvatar2.setVisibility(View.VISIBLE);
                    avatar = m.getTeachers().get(1).getAvatar();
                    if(!stringIsNullOrEmpty(avatar)) Picasso.get().load(avatar).placeholder(R.drawable.img_default_avatar).fit().centerCrop().into(holder.civAvatar2);
                }
                if(m.getTeachers().size() > 2){
                    holder.tvTeachersCount.setVisibility(View.VISIBLE);
                    holder.tvTeachersCount.setText("+ еще " + (m.getTeachers().size() - 2));
                }
            }
            else holder.tvTitleNotTeachers.setVisibility(View.VISIBLE);

            if(!listIsNullOrEmpty(m.getLessons())){
                if(!listIsNullOrEmpty(m.getLessons())) holder.tvTitleNotLessons.setVisibility(View.GONE);
                else holder.tvTitleNotLessons.setVisibility(View.VISIBLE);
            }
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
        if(mColumns.size() > 1){
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
                    tvDescription.setText("Переместить класс " + mClasses.get(pos).getName() + " в " + m.getName() + "?");
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
                                        String result = XMLToJson(response.body().string());
                                        activity.runOnUiThread(new Runnable(){
                                            @Override
                                            public void run(){
                                                ModelAnswer answer = new Gson().fromJson(result, ModelAnswer.class);
                                                activity.alert.onToast(answer.getMessage());
                                                if(answer.isSuccess())
                                                    activity.updatePresenter();
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
        }
    }
    private void onRenameClass(ModelSchoolClass m) {
        BSDRenameClass dialog = new BSDRenameClass(activity, m, mColumn.getId());
        dialog.show();
    }

    @Override
    public int getItemCount(){
        return mClasses.size();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_school_class, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvClassName, tvTitle;
        TextView tvTitleNotLessons, tvTitleNotStudents, tvTitleNotTeachers;
        ImageView ivMenu, ivStudentsCount;
        TextView tvStudentsCount, tvTeachersCount;
        CircleImageView civAvatar1, civAvatar2;

        ViewHolder(@NonNull View view){
            super(view);
            tvClassName = view.findViewById(R.id.tvClassName);
            tvTitle = view.findViewById(R.id.tvTitle);
            ivMenu = view.findViewById(R.id.ivMenu);
            tvTitleNotLessons = view.findViewById(R.id.tvTitleNotLessons);
            tvTitleNotStudents = view.findViewById(R.id.tvTitleNotStudents);
            tvTitleNotTeachers = view.findViewById(R.id.tvTitleNotTeachers);
            ivStudentsCount = view.findViewById(R.id.ivStudentsCount);
            tvStudentsCount = view.findViewById(R.id.tvStudentsCount);
            civAvatar1 = view.findViewById(R.id.civAvatar1);
            civAvatar2 = view.findViewById(R.id.civAvatar2);
            tvTeachersCount = view.findViewById(R.id.tvTeachersCount);

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
                    View layout = layoutInflater.inflate(R.layout.pw_school_menu_class, viewGroup);
                    PopupWindow popupWindow = new PopupWindow(activity);
                    popupWindow.setContentView(layout);
                    popupWindow.setWidth(FrameLayout.LayoutParams.WRAP_CONTENT);
                    popupWindow.setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    Point p = getLocationOnScreen(ivMenu);
                    popupWindow.showAtLocation(layout, Gravity.NO_GRAVITY, p.x - 150, p.y);

                    LinearLayout llDelete = layout.findViewById(R.id.llDelete);
                    LinearLayout llMove = layout.findViewById(R.id.llMove);
                    LinearLayout llRename = layout.findViewById(R.id.llRename);

                    llDelete.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            popupWindow.dismiss();
                            onDeleteClass(getAdapterPosition());
                        }
                    });
                    llMove.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            popupWindow.dismiss();
                            onMoveClass(getAdapterPosition());
                        }
                    });
                    llRename.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            popupWindow.dismiss();
                            onRenameClass(mClasses.get(getAdapterPosition()));
                        }
                    });
                }
            });
        }
    }
}
