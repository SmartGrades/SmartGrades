package kz.tech.smartgrades.parents_module.coins.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.parents_module.coins.ContainerForImage;
import kz.tech.smartgrades.root.models.ModelChild;

public class ChildPagerAdapter extends PagerAdapter {
    private Context context;
    private List<ModelChild> listChild;
    public ChildPagerAdapter(Context context) {
        this.context = context;
        listChild = new ArrayList<>();
        listChild.add(new ModelChild("1", "John", "z7771"));
        listChild.add(new ModelChild("2", "Monica", "z7772"));
        listChild.add(new ModelChild("3", "Connor", "z7773"));
        listChild.add(new ModelChild("4", "Sarah", "z7774"));

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
        ContainerForImage viewImage = new ContainerForImage(context);
        container.addView(viewImage);
        if (viewImage.imageView != null) {
            switch (listChild.get(position).getAvatar()) {
                case "1": viewImage.imageView.setImageResource(R.drawable.boy1); break;
                case "2": viewImage.imageView.setImageResource(R.drawable.girl1); break;
                case "3": viewImage.imageView.setImageResource(R.drawable.boy2); break;
                case "4": viewImage.imageView.setImageResource(R.drawable.girl2); break;
            }
           // Picasso.with(context).load(listChild.get(position).getAvatar()).fit().centerInside().into(viewImage.imageView);
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
