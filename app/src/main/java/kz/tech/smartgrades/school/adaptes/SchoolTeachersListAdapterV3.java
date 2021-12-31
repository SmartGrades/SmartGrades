package kz.tech.smartgrades.school.adaptes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.Convert;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.school.models.ModelTeachersList;
import kz.tech.smartgrades.school.models.ModelTeachersListLesson;

import static kz.tech.smartgrades.GET.listIsNullOrEmpty;
import static kz.tech.smartgrades.GET.stringIsNullOrEmpty;
import static kz.tech.smartgrades._Web.SoapRequest;


public class SchoolTeachersListAdapterV3 extends RecyclerView.Adapter<SchoolTeachersListAdapterV3.ViewHolder>{

    private SchoolActivity activity;
    private ArrayList<ModelTeachersList> mTeachers;
    private ArrayList<ModelTeachersList> filterList;
    private String classId;

    private OnItemClickListener onItemClickListener;

    public void filter(String toString){
        filterList.clear();
        if(stringIsNullOrEmpty(toString)) filterList.addAll(mTeachers);
        else for(ModelTeachersList _class : mTeachers)
            if((_class.getFirstName() + " " + _class.getLastName()).toLowerCase().contains(toString.toLowerCase()))
                filterList.add(_class);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(ModelTeachersList m);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }


    public SchoolTeachersListAdapterV3(SchoolActivity activity){
        this.activity = activity;
        this.mTeachers = new ArrayList<>();
        filterList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        ModelTeachersList m = filterList.get(position);
        String avatar = m.getAvatar();
        if (!stringIsNullOrEmpty(avatar)) Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        else holder.civAvatar.setImageDrawable(activity.getResources().getDrawable(R.drawable.img_default_avatar));
        holder.tvFullName.setText(m.getFirstName() + " " + m.getLastName());

        if (stringIsNullOrEmpty(m.getUserId())) {
            holder.tvNotActive.setVisibility(View.VISIBLE);
        }

        LessonsTagAdapterV3 adapter = new LessonsTagAdapterV3();
        holder.rvLessonsList.setAdapter(adapter);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(activity);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setAlignItems(AlignItems.CENTER);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        holder.rvLessonsList.setLayoutManager(flexboxLayoutManager);
//        if (m.isAddedToClass()) {
//            holder.cvAdd.setVisibility(View.GONE);
//            holder.ivEdit.setVisibility(View.VISIBLE);
//        } else {
//            holder.cvAdd.setVisibility(View.VISIBLE);
//            holder.ivEdit.setVisibility(View.GONE);
//        }
//        if (m.isSelected()) {
//            adapter.setEdit(true);
//            holder.clBtn.setVisibility(View.GONE);
//            holder.tvNotActive.setBackground(null);
//            holder.tvNotActive.setText(R.string.Select_lesson);
//            holder.tvNotActive.setTextColor(activity.getResources().getColor(R.color.green_background));
//        } else {
//            adapter.setEdit(false);
//            holder.clBtn.setVisibility(View.VISIBLE);
//        }
//        adapter.setOnItemCLickListener(new LessonsTagAdapterV3.OnItemCLickListener() {
//            @Override
//            public void onSelectLessonForClassClick(ModelTeachersListLesson m) {
//                holder.clBtn.setVisibility(View.VISIBLE);
//                holder.tvNotActive.setBackground(activity.getResources().getDrawable(R.drawable.background_square_red_v2));
//                holder.tvNotActive.setTextColor(activity.getResources().getColor(R.color.white));
//                holder.tvNotActive.setText(R.string.not_active);
//                holder.tvNotActive.setPadding((int) Convert.DpToPx(5), (int)Convert.DpToPx(0), (int)Convert.DpToPx(5), (int)Convert.DpToPx(0));
//            }
//
//            @Override
//            public void onSelectLessonForDeleteClick(ModelTeachersListLesson m) {
//
//            }
//        });
        adapter.UpdateData(m.getLessons());
    }
    @Override
    public int getItemCount(){
        return filterList.size();
    }
    private void onClear(){
        if(mTeachers.size() > 0) mTeachers.clear();
        if(filterList.size() > 0) filterList.clear();
    }

    public void updateData(ArrayList<ModelTeachersList> list){
        onClear();
        if(!listIsNullOrEmpty(list)) {
            mTeachers.addAll(list);
            filterList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public ArrayList<ModelTeachersList> getMTeachers() {
        return mTeachers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_school_teacher_list_v3, parent, false));
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout container;
        CircleImageView civAvatar;
        ImageView ivEdit;
        TextView tvFullName, tvNotActive;
        RecyclerView rvLessonsList;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            container = itemView.findViewById(R.id.container);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            civAvatar = itemView.findViewById(R.id.civAvatar);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvNotActive = itemView.findViewById(R.id.tvNotActive);
            rvLessonsList = itemView.findViewById(R.id.rvLessonsList);

//            clBtn.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v){
//                    isEdited = true;
//                    // проверка на ацепт (забрать из адаптера уроки и вставить в эту модель)
//                    // проверка на первый выбор
//                    filterList.get(getAdapterPosition()).setSelected(!filterList.get(getAdapterPosition()).isSelected());
//                    for (ModelTeachersListLesson _lesson : filterList.get(getAdapterPosition()).getLessons()) {
//                        if (_lesson.isAddedToClass()) {
//                            filterList.get(getAdapterPosition()).setAddedToClass(true);
//                            break;
//                        }
//                    }
//                    notifyDataSetChanged();
//                }
//            });

            civAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener == null) return;
                    onItemClickListener.onItemClick(filterList.get(getAdapterPosition()));
                }
            });

            tvFullName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener == null) return;
                    onItemClickListener.onItemClick(filterList.get(getAdapterPosition()));
                }
            });
        }
    }
}
