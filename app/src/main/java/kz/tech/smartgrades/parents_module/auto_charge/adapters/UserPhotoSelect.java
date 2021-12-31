package kz.tech.smartgrades.parents_module.auto_charge.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.children_time.SelectAvatarTimeCharge;
import kz.tech.smartgrades.root.models.ModelChild;

public class UserPhotoSelect extends PagerAdapter {
    private Context context;
    private List<ModelChild> listChild;
    public UserPhotoSelect(Context context) {
        this.context = context;
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
        SelectAvatarTimeCharge viewImage = new SelectAvatarTimeCharge(context);
        container.addView(viewImage);
        if (viewImage.imageView != null) {
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
