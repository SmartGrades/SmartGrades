package kz.tech.smartgrades.mentor_module.coins.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kz.tech.esparta.R;
import kz.tech.smartgrades.mentor_module.coins.list_view.ListItemCoinsChild;
import kz.tech.smartgrades.mentor_module.coins.list_view.ListItemCoinsView;
import kz.tech.smartgrades.mentor_module.coins.models.ModelChildList;

public class CoinsChildAdapter extends RecyclerView.Adapter<CoinsChildAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(String idLogin, String idChild, int n, int coins);
        void onMsgClick(String msg);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    private List<ModelChildList> list;
    private Context context;
    private String dateToday;
    public CoinsChildAdapter(Context context, String dateToday) {
        this.context = context;
        this.list = new ArrayList<>();
        this.dateToday = dateToday;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(new ListItemCoinsChild(context));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder.llCoins.getChildCount() > 0) { holder.llCoins.removeAllViewsInLayout(); }
        if (holder.llContracts.getChildCount() > 0) { holder.llContracts.removeAllViewsInLayout(); }
        int lol = (holder.llContainer.getVisibility() == View.VISIBLE)?1:0;// 1 = vis, 0 gone
        if (lol == 1) {
            holder.llContainer.setVisibility(View.GONE);
            holder.vLine.setVisibility(View.VISIBLE);
            holder.tvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        onTodayCoins(holder, 1, 0);

        ModelChildList m = list.get(position);

        SharedPreferences settings = context.getSharedPreferences(m.getIdChild(), 0);
        String date = settings.getString("date", null);
        String todayCoins = settings.getString("todayCoins", null);
        if (date != null) {
            if (date.equals(dateToday)) {
                onTodayCoins(holder, 2, Integer.parseInt(todayCoins));
            }
        }

        if (m.getName() != null) {
            String name = m.getName() + "   ";
            holder.tvName.setText(name);
        }
        if (m.getAvatar() != null) {
            Picasso.get().load(m.getAvatar()).placeholder(R.drawable.img_default_avatar).fit().centerInside().into(holder.civAvatar);
        }
        String accrualOfCoins = m.getAccrualOfCoins();
        if (accrualOfCoins != null) {
            if (!accrualOfCoins.equals("")) {
                String[] from_to = accrualOfCoins.split(":");
                int from_minus = Integer.parseInt(from_to[0]);
                from_to[0] = getReplaceMinus(from_to[0]);
                int from = Integer.parseInt(from_to[0]);
                int to = Integer.parseInt(from_to[1]);

                for (int j = 0; j < from; j++) {
                    String t = "-" + String.valueOf(j+1);
                    int obols = j+1;
                    ListItemCoinsView coins = new ListItemCoinsView(context, 1, t);
                    holder.llCoins.addView(coins, 0);
                    coins.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (onItemClickListener != null) {
                                int lol = ((int)(10*obols)*20)/10;
                                onItemClickListener.onItemClick(m.getIdLogin(), m.getIdChild(),1, lol);
                            }
                            String strCoins = getTodayCoins(m.getIdChild());
                            int checkCount = 0;
                            if (strCoins != null) {
                                checkCount = Integer.parseInt(strCoins);
                            }
                            if (from_minus > checkCount-obols) {
                                onItemClickListener.onMsgClick("LIMIT");
                                return;
                            }
                            if (date != null) {
                                if (date.equals(dateToday)) {
                                    int coinsLOL = checkCount - obols;
                                    onTodayCoins(holder, 2, coinsLOL);
                                    onTodayCoinsPrefs(m.getIdChild(), String.valueOf(coinsLOL));
                                }
                            } else {
                                int coinsLOL = checkCount - obols;
                                onTodayCoins(holder, 2, coinsLOL);
                                onTodayCoinsPrefs(m.getIdChild(), String.valueOf(coinsLOL));
                            }
                        }
                    });
                }

                for (int i = 0; i < to; i++) {
                    String t = "+" + String.valueOf(i+1);
                    int obols = i+1;
                    ListItemCoinsView coins = new ListItemCoinsView(context, 2, t);
                    holder.llCoins.addView(coins);
                    coins.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (onItemClickListener != null) {
                                int lol = ((int)(10*obols)*20)/10;
                                onItemClickListener.onItemClick(m.getIdLogin(), m.getIdChild(),2, lol);
                                String strCoins = getTodayCoins(m.getIdChild());
                                int checkCount = 0;
                                if (strCoins != null) {
                                    checkCount = Integer.parseInt(strCoins);
                                }
                                if (to < obols + checkCount) {
                                    onItemClickListener.onMsgClick("LIMIT");
                                    return;
                                }
                                if (date != null) {
                                    if (date.equals(dateToday)) {
                                        int coinsLOL = checkCount + obols;
                                        onTodayCoins(holder, 2, coinsLOL);
                                        onTodayCoinsPrefs(m.getIdChild(), String.valueOf(coinsLOL));
                                    } else {
                                        int coinsLOL = Integer.parseInt(strCoins) + obols;
                                        onTodayCoins(holder, 2, coinsLOL);
                                        onTodayCoinsPrefs(m.getIdChild(), String.valueOf(coinsLOL));
                                    }
                                } else {
                                    int coinsLOL = checkCount + obols;
                                    onTodayCoins(holder, 2, coinsLOL);
                                    onTodayCoinsPrefs(m.getIdChild(), String.valueOf(coinsLOL));
                                }
                            }
                        }
                    });
                }
            }
        }
        String accrualOfCoinsSuper = m.getAccrualOfCoinsSuper();

        String accrualOfOffset = m.getAccrualOfOffset();
        if (accrualOfOffset != null) {
            if (!accrualOfOffset.equals("")) {
                String[] from_to = accrualOfOffset.split(":");
                if(from_to[0].startsWith("-")){
                    from_to[0] = from_to[0].replace("-", "");
                }
                int from = Integer.parseInt(from_to[0]);
                int to = Integer.parseInt(from_to[1]);

              /*  for (int j = 0; j < from; j++) {
                    String t = "+" + String.valueOf(j+1);
                    ListItemCoinsView coins = new ListItemCoinsView(context, 3, t);
                    holder.llContracts.addView(coins);
                }*/

                for (int i = 0; i < to; i++) {
                    String t = "";
                    ListItemCoinsView coins = new ListItemCoinsView(context, 3, t);
                    holder.llContracts.addView(coins);
                }
            }

        }

        String accrualOfOffsetSuper = m.getAccrualOfOffsetSuper();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civAvatar;
        TextView tvName, tvTodayCoins;
        View vLine;
        ImageView ivTodayCoins;
        FrameLayout flTodayCoins;
        LinearLayout llContainer, llCoins, llContracts;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = (CircleImageView) itemView.findViewById((int)400101);
            tvName = (TextView) itemView.findViewById((int)400102);
            vLine = (View) itemView.findViewById((int)400103);
            flTodayCoins = (FrameLayout) itemView.findViewById((int)400104);
            ivTodayCoins = (ImageView) itemView.findViewById((int)400105);
            tvTodayCoins = (TextView) itemView.findViewById((int)400106);
            llContainer = (LinearLayout) itemView.findViewById((int)400108);
            llCoins = (LinearLayout) itemView.findViewById((int)400109);
            llContracts = (LinearLayout) itemView.findViewById((int)400110);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int lol = (llContainer.getVisibility() == View.VISIBLE)?1:0;// 1 = vis, 0 gone
                    if (lol == 0) {
                        llContainer.setVisibility(View.VISIBLE);
                        vLine.setVisibility(View.GONE);
                        tvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.red_pick, 0);
                    } else if (lol == 1) {
                        llContainer.setVisibility(View.GONE);
                        vLine.setVisibility(View.VISIBLE);
                        tvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }

                }
            });
        }
    }
    private String getReplaceMinus(String s) {
        if(s.startsWith("-")){
            s = s.replace("-", "");
        }
        return s;
    }
    private String getTodayCoins(String idChild) {
        SharedPreferences settings = context.getSharedPreferences(idChild, 0);
        return settings.getString("todayCoins", null);
    }
    private void onTodayCoinsPrefs(String idChild, String coins) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(idChild, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("date", dateToday);
        editor.putString("todayCoins", coins);
        editor.apply();
    }
    private void onTodayCoins(ViewHolder vh, int n, int coins) {
        switch (n) {
            case 1:
                vh.flTodayCoins.setVisibility(View.GONE);
                vh.ivTodayCoins.setImageResource(0);
                vh.tvTodayCoins.setText("");
                break;
            case 2:
                vh.flTodayCoins.setVisibility(View.VISIBLE);
                if (coins == 0) {
                    vh.flTodayCoins.setVisibility(View.GONE);
                    vh.ivTodayCoins.setImageResource(0);
                    vh.tvTodayCoins.setText("");
                } else if (coins > 0) {
                    vh.ivTodayCoins.setImageResource(R.mipmap.green_monet);
                    vh.tvTodayCoins.setText("+" + String.valueOf(coins));
                } else if (coins < 0) {
                    vh.ivTodayCoins.setImageResource(R.mipmap.red_monet);
                    vh.tvTodayCoins.setText(String.valueOf(coins));
                }
                break;
        }
    }
    public void updateData(List<ModelChildList> childLists) {
        if (list.size() > 0) {
            int n = list.size();
            for (int i = 0; i < n; i++) {
                notifyItemRemoved(n);
            }
        }
     //   if (list.size() > 0) { list.clear(); }
        list = childLists;
        notifyDataSetChanged();
    }
}
