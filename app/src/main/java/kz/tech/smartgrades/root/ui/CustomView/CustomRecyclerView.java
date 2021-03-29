package kz.tech.smartgrades.root.ui.CustomView;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import kz.tech.smartgrades.root.tools.GetColorInText;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.FrameLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.LinearLayoutParams;
import kz.tech.smartgrades.root.ui.CustomLayoutParams.RelativeLayoutParams;

public class CustomRecyclerView {
    public RecyclerView onCustom(Context context, String[] paramsList) {
        RecyclerView recyclerView = new RecyclerView(context);
        int gridColumn = 0;
        for (int i = 0; paramsList.length > i; i++) {//  LayoutParams
            String[] data = paramsList[i].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "prt"://  Parent
                    switch (data[1]) {//  VALUE
                        case "LinLay": recyclerView.setLayoutParams(new LinearLayoutParams().getParams(context, paramsList)); break;
                        case "RelLay": recyclerView.setLayoutParams(new RelativeLayoutParams().getParams(context, paramsList)); break;
                        case "FrmLay": recyclerView.setLayoutParams(new FrameLayoutParams().getParams(context, paramsList)); break;
                    }
                    break;
            }
        }
        for (int j = 0; paramsList.length > j; j++) {//  TextView
            String[] data = paramsList[j].split(":");//  position 0 = key, 1 = value
            switch (data[0]) {//  KEY
                case "layMan"://  LayoutManager
                    switch (data[1]) {//  VALUE
                        case "glm"://  GridLayoutManager
                            RecyclerView.LayoutManager glm = new androidx.recyclerview.widget.GridLayoutManager(context, gridColumn);
                            recyclerView.setLayoutManager(glm);
                            break;
                        case "llm"://  LinearLayoutManager
                            RecyclerView.LayoutManager llm = new androidx.recyclerview.widget.LinearLayoutManager(context,
                                    androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(llm);
                            break;

                    }
                    break;
                case "backC"://  RippleDrawable
                    recyclerView.setBackgroundColor(GetColorInText.getIntColor(data[1]));
                    break;
                case "GridC"://  GridColumn
                    gridColumn = Integer.parseInt(data[1]);//  Count COLUMN
                    break;
            }
        }


        return recyclerView;
    }
}
