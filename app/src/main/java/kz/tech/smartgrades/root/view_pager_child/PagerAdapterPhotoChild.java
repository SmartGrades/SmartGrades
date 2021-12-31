package kz.tech.smartgrades.root.view_pager_child;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.models.ModelChild;

public class PagerAdapterPhotoChild extends PagerAdapter {
    private Context context;
    private List<ModelChild> listChild;
    public PagerAdapterPhotoChild(Context context) {
        this.context = context;
   /*     listChild = new ArrayList<>();
        listChild.add(new ModelChatListChild("1", "John", "z7771"));
        listChild.add(new ModelChatListChild("2", "Monica", "z7772"));
        listChild.add(new ModelChatListChild("3", "Connor", "z7773"));
        listChild.add(new ModelChatListChild("4", "Sarah", "z7774"));*/
    }
    @Override
    public int getCount() {
        if (listChild == null) {
            return 0;
        } else {
            return listChild.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ListViewPhotoChild viewImage = new ListViewPhotoChild(context);
        container.addView(viewImage);
        if (viewImage.imageView != null) {
        /*    switch (listChild.get(position).getAvatar()) {
                case "1": viewImage.imageView.setImageResource(R.drawable.boy1); break;
                case "2": viewImage.imageView.setImageResource(R.drawable.girl1); break;
                case "3": viewImage.imageView.setImageResource(R.drawable.boy2); break;
                case "4": viewImage.imageView.setImageResource(R.drawable.girl2); break;
            }*/
            Picasso.get().load(listChild.get(position).getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(viewImage.imageView);
        }
        if (viewImage.textView != null) {
            viewImage.textView.setText(listChild.get(position).getName());
        }
        return viewImage;

    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
    public void onUpdateDate(List<ModelChild> listChild) {
        this.listChild = listChild;
        notifyDataSetChanged();
    }
}
