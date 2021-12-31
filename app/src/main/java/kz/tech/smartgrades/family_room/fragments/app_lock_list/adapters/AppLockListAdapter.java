package kz.tech.smartgrades.family_room.fragments.app_lock_list.adapters;

import android.content.Context;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import kz.tech.esparta.R;
import kz.tech.smartgrades.family_room.fragments.app_lock_list.ItemsAppLockList;
import kz.tech.smartgrades.root.var_resources.VarID;


public class AppLockListAdapter extends RecyclerView.Adapter<AppLockListAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onRemoveAllViewsClick();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private Context context;
    private boolean isChecked = false;
    private boolean isStandardList = false;
    private List<ApplicationInfo> arrayList;

    public AppLockListAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<ApplicationInfo>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new ItemsAppLockList(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ApplicationInfo applicationInfo = arrayList.get(position);
        if (applicationInfo != null) {
            if (position == 0) {
                holder.vTop.setVisibility(View.VISIBLE);
            }
            Drawable icon = context.getPackageManager().getApplicationIcon(applicationInfo);
            CharSequence name = context.getPackageManager().getApplicationLabel(applicationInfo);
          //  long timeInstall = context.getPackageManager().getApp
            long installed = 0;
            try {
               installed = context.getPackageManager().getPackageInfo(applicationInfo.packageName, 0).firstInstallTime;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            Date installTime = new Date( installed );
            holder.ivIcon.setImageDrawable(icon);
            holder.tvText.setText(name);
            if (!isChecked) {//          STANDARD
                holder.ivCheckBox.setOnClickListener(null);
                if (!isStandardList) {
                    holder.ivCheckBox.setImageResource(R.mipmap.lock);
                } else {
                    if (isStandardListBool2(applicationInfo.packageName)) {
                        holder.ivCheckBox.setImageResource(R.mipmap.unlock);
                    } else {
                        holder.ivCheckBox.setImageResource(R.mipmap.lock);
                    }
                }
            } else {//         CUSTOMIZABLE
                String packageNameApp = applicationInfo.packageName;
                SharedPreferences settings = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);

                int checkedApp = settings.getInt(packageNameApp, 1);
                switch (checkedApp) {
                    case 1: holder.ivCheckBox.setImageResource(R.mipmap.unlock); break;
                    case 2: holder.ivCheckBox.setImageResource(R.mipmap.lock); break;
                    case 3: holder.ivCheckBox.setImageResource(R.mipmap.lock_new); break;
                }


            }
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon, ivCheckBox;
        TextView tvText;
        View vTop;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(VarID.ID_IV_APP_LOCK_LIST_ICON);
            tvText = (TextView) itemView.findViewById(VarID.ID_TV_APP_LOCK_LIST_TEXT);
            ivCheckBox = (ImageView) itemView.findViewById(VarID.ID_IV_APP_LOCK_LIST_CHECK_BOX);
            vTop = (View) itemView.findViewById(VarID.ID_IV_APP_LOCK_LIST_TOP);
            ivCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isChecked) {
                        int position = getAdapterPosition();

                        SharedPreferences settings = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                        int checkedApp = settings.getInt(arrayList.get(position).packageName, 1);

                        SharedPreferences.Editor editPackNameChecked = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE).edit();
                        SharedPreferences.Editor editPackName = context.getSharedPreferences("PREFS_APP_LOCK_LIST", Context.MODE_PRIVATE).edit();

                        if (checkedApp == 1) {
                            ivCheckBox.setImageResource(R.mipmap.lock);
                            editPackNameChecked.putInt(arrayList.get(position).packageName, 2);
                            editPackNameChecked.apply();

                            editPackName.putString(arrayList.get(position).packageName, arrayList.get(position).packageName);
                            editPackName.apply();
                        } else if (checkedApp == 2 || checkedApp == 3) {
                            ivCheckBox.setImageResource(R.mipmap.unlock);
                            editPackNameChecked.remove(arrayList.get(position).packageName);
                            editPackNameChecked.apply();

                            editPackName.remove(arrayList.get(position).packageName);
                            editPackName.apply();
                        }
                    }
                }
            });
        }
    }
    /*
      if (checkedApp == 1) {
                            ivCheckBox.setImageResource(R.mipmap.unlock);
                            editPackNameChecked.remove(arrayList.get(position).packageName);
                            editPackNameChecked.apply();

                            editPackName.remove(arrayList.get(position).packageName);
                            editPackName.apply();
                        } else if (checkedApp == 2 || checkedApp == 3) {
                            ivCheckBox.setImageResource(R.mipmap.lock);
                            editPackNameChecked.putBoolean(arrayList.get(position).packageName, true);
                            editPackNameChecked.apply();

                            editPackName.putString(arrayList.get(position).packageName, arrayList.get(position).packageName);
                            editPackName.apply();
                        }
     */

    public void setData(String select) {
        isStandardList = false;
        arrayList.clear();
        if (onItemClickListener != null) {
            onItemClickListener.onRemoveAllViewsClick();
        }
        switch (select) {
            case "standardList":
                isStandardList = true;
                isChecked = false;
                onStandardList();
                break;
            case "customizableList":
                isChecked = true;
                onCustomizableList();
                break;
        }
        notifyDataSetChanged();
    }
    ////////       CUSTOMIZABLE      ////////////////
    private void onCustomizableList() {
        SharedPreferences settings = context.getSharedPreferences("PREFS_APP_LOCK_TIME_INSTALL", Context.MODE_PRIVATE);
        boolean checkedApp = settings.getBoolean("firstInstall", false);
        long timeInstall = settings.getLong("timeInstall", 0);

        SharedPreferences.Editor editor = context.getSharedPreferences("PREFS_APP_LOCK_TIME_INSTALL", Context.MODE_PRIVATE).edit();



        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);

        for (PackageInfo packageInfo : packageList) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            arrayList.add(applicationInfo);
            if (!checkedApp) {
                if (settings.getLong("timeInstall", 0) < packageInfo.firstInstallTime) {
                    editor.putLong("timeInstall", packageInfo.firstInstallTime);
                    editor.apply();
                }
            } else {
                if (settings.getLong("timeInstall", 0) < packageInfo.firstInstallTime) {
                    SharedPreferences.Editor editPackNameChecked = context.getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE).edit();
                    SharedPreferences.Editor editPackName = context.getSharedPreferences("PREFS_APP_LOCK_LIST", Context.MODE_PRIVATE).edit();

                    editPackNameChecked.putInt(applicationInfo.packageName, 3);
                    editPackNameChecked.apply();

                    editPackName.putString(applicationInfo.packageName, applicationInfo.packageName);
                    editPackName.apply();
                }
            }

        }
        if (packageList.size() > 0) {
            editor.putBoolean("firstInstall", true);
            editor.apply();
        }
    }
    /////////        STANDARD        ////////////////
    private void onStandardList() {
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> packageList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (PackageInfo packageInfo : packageList) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (isStandardListBool(packageInfo)) {
                arrayList.add(applicationInfo);
            }
        }
        for (PackageInfo packageInfo : packageList) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (!isStandardListBool(packageInfo)) {
                arrayList.add(applicationInfo);
            }
        }
    }
    private boolean isStandardListBool(PackageInfo packageInfo) {
        String[] arrPackages = {"com.whatsapp", "org.telegram.messenger", "kz.tech.esparta", "com.facebook.orca"};
        for (int i = 0; i < arrPackages.length; i++) {
            if (packageInfo.packageName.equals(arrPackages[i])) {
               return true;
            }
        }
        return false;
    }
    private boolean isStandardListBool2(String strPackage) {
        String[] arrPackages = {"com.whatsapp", "org.telegram.messenger", "kz.tech.esparta", "com.facebook.orca"};
        for (int i = 0; i < arrPackages.length; i++) {
            if (strPackage.equals(arrPackages[i])) {
                return true;
            }
        }
        return false;
    }


    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
                : false;
    }
    private boolean isSystemPackage1(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) ? false
                : true;
    }
    private boolean isSystemPackage2(PackageInfo pkgInfo) {
        return ((pkgInfo.packageName.contains("com.android.systemui"))) ? false
                : true;
    }
    private boolean isExceptionPackage(PackageInfo packageInfo) {
        if (packageInfo.packageName.contains("com.whatsapp")) {
            return true;
        } else if (packageInfo.packageName.contains("org.telegram.messenger")) {
            return true;
        } else if (packageInfo.packageName.contains("kz.tech.esparta")) {
            return true;
        }
        return false;
    }
}
/*

        servicePackageList.add("com.facebook.katana");         //facebook
        servicePackageList.add("com.facebook.orca");           //facebook Messenger
        servicePackageList.add("com.google.android.youtube");  //youtube
        servicePackageList.add("com.twitter.android");         //twitter
        servicePackageList.add("com.google.android.gms");      //GooglePlay
 */