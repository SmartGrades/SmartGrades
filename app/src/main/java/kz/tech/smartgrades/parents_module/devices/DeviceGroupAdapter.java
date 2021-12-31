package kz.tech.smartgrades.parents_module.devices;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kz.tech.esparta.R;
import kz.tech.smartgrades.root.db.TableFamilyRoom;
import kz.tech.smartgrades.root.models.ModelDevice;
import kz.tech.smartgrades.root.var_resources.DimensionDP;
import kz.tech.smartgrades.root.var_resources.VarID;


public class DeviceGroupAdapter extends RecyclerView.Adapter<DeviceGroupAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemLongClick(String idDevice, int position);
        void onItemClick(ModelDevice modelDevice);
        void onRenameClick(ModelDevice modelDevice);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private Context context;
    private List<ModelDevice> arrayList;
    private Resources res;
    private List<TableFamilyRoom> familyRoomList;
    public DeviceGroupAdapter(Context context) {
        this.context = context;
        this.arrayList = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new DeviceGroupItemList(context));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelDevice modelDevice = arrayList.get(position);
        if (position == 0) {
            holder.v1.setVisibility(View.VISIBLE);
        }
        if (modelDevice.getTypeDevice() != null) {
            switch (modelDevice.getTypeDevice()) {
                case "SmartPhone": holder.ivDeviceIcon.setImageResource(R.mipmap.smartphone); break;
                case "Tablet": holder.ivDeviceIcon.setImageResource(R.mipmap.planshet); break;
                case "iPad": holder.ivDeviceIcon.setImageResource(R.mipmap.ipod); break;
                case "iPhone": holder.ivDeviceIcon.setImageResource(R.mipmap.iphone); break;
            }
        }
        if (modelDevice.getNameDevice() != null) {
            String firstText = modelDevice.getNameDevice();
            String secondFirst = res.getString(R.string.change_dot);
            int startIndex = 0;
            int endIndex = firstText.length();
            SpannableString spannableString = new SpannableString(firstText + "\n" + secondFirst);
            spannableString.setSpan(new ForegroundColorSpan(Color.rgb(0, 0, 0)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvDeviceName.setText(spannableString);
        }
        if (modelDevice.getIdUser() != null) {
            for (int i = 0; i < familyRoomList.size(); i++) {
                if (modelDevice.getIdUser().equals(familyRoomList.get(i).getIdLogin())) {
                    if (modelDevice.getLockStatus() != null) {
                        switch (modelDevice.getLockStatus()) {
                            case "user":
                                holder.ivLockStatus.setPadding(0, 0, 0, 0);
                                Picasso.get().load(familyRoomList.get(i).getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.ivLockStatus);
                                break;
                            case "lock":
                                int numbLock = DimensionDP.sizeDP(10, context);
                                holder.ivLockStatus.setPadding(numbLock, numbLock, numbLock, numbLock);
                                holder.ivLockStatus.setImageResource(R.mipmap.lock);
                                break;
                            case "unlock":
                                int numbUnlock = DimensionDP.sizeDP(10, context);
                                holder.ivLockStatus.setPadding(numbUnlock, numbUnlock, numbUnlock, numbUnlock);
                                holder.ivLockStatus.setImageResource(R.mipmap.unlock);
                                break;
                        }
                    }
                    if (familyRoomList.get(i).getTitle().equals("Son") || familyRoomList.get(i).getTitle().equals("Daughter")) {
                        if (modelDevice.getOnlineStatus().equals("true")) {
                            holder.ivOnlineStatus.setImageResource(R.mipmap.green_dot);
                        } else if (modelDevice.getOnlineStatus().equals("false")) {
                            holder.ivOnlineStatus.setImageResource(R.mipmap.red_dot);
                        }
                        holder.ivChangeUser.setImageResource(R.mipmap.arrow_oval);
                        holder.tvUserName.setText(familyRoomList.get(i).getName());
                    } else if (familyRoomList.get(i).getTitle().equals("Father") || familyRoomList.get(i).getTitle().equals("Mother")) {
                        if (familyRoomList.get(i).getTitle().equals("Father")) {
                            holder.tvUserName.setText(res.getString(R.string.father));
                        } else if (familyRoomList.get(i).getTitle().equals("Mother")) {
                            holder.tvUserName.setText(res.getString(R.string.mother));
                        }
                        holder.ivChangeUser.setVisibility(View.GONE);
                    }
                }
            }
        }
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        View v1;
        ImageView ivDeviceIcon, ivLockStatus, ivOnlineStatus, ivChangeUser;
        TextView tvDeviceName, tvUserName;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            v1 = (View)itemView.findViewById(VarID.ID_V_TOP_DEVICE);
            ivDeviceIcon = (ImageView)itemView.findViewById(VarID.ID_IV_DEVICE_ICON_DEVICE);
            tvDeviceName = (TextView)itemView.findViewById(VarID.ID_TV_DEVICE_NAME_DEVICE);
            ivLockStatus = (ImageView)itemView.findViewById(VarID.ID_IV_LOCK_STATUS_DEVICE);
            ivOnlineStatus = (ImageView)itemView.findViewById(VarID.ID_IV_ONLINE_STATUS_DEVICE);
            tvUserName = (TextView)itemView.findViewById(VarID.ID_TV_USER_NAME_DEVICE);
            ivChangeUser = (ImageView)itemView.findViewById(VarID.ID_IV_CHANGE_USER_DEVICE);

            tvDeviceName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onRenameClick(arrayList.get(position));
                    }
                }
            });

            ivChangeUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onItemClick(arrayList.get(position));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onItemLongClick(arrayList.get(position).getIdDevice(), position);
                    }
                    return false;
                }
            });
        }
    }
    public void setData(List<ModelDevice> arrayList, List<TableFamilyRoom> familyRoomList, Resources res) {
        this.arrayList.clear();
        this.arrayList = arrayList;
        this.familyRoomList = familyRoomList;
        this.res = res;
        notifyDataSetChanged();
    }
    public void removeData(int position) {
        arrayList.remove(position);
        notifyDataSetChanged();
    }
    public void updateData(String idChild, ModelDevice modelDevice) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getIdDevice().equals(modelDevice.getIdDevice())) {
                arrayList.get(i).setIdUser(idChild);
                arrayList.get(i).setLockStatus(modelDevice.getLockStatus());
                notifyItemChanged(i);
                return;
            }
       }
    }
    public void updateDataLockUnlock(String statusLock, ModelDevice modelDevice) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getIdDevice().equals(modelDevice.getIdDevice())) {
                arrayList.get(i).setLockStatus(statusLock);
                notifyItemChanged(i);
                return;
            }
        }
    }
    public void updateRenameDevice(String deviceName, ModelDevice modelDevice) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getIdDevice().equals(modelDevice.getIdDevice())) {
                arrayList.get(i).setNameDevice(deviceName);
                notifyItemChanged(i);
                return;
            }
        }
    }
}
