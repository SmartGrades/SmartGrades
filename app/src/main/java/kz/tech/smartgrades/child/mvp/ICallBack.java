package kz.tech.smartgrades.child.mvp;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.child.models.ModelChildWardCoin;
import kz.tech.smartgrades.mentor.models.ModelDefaultChat;
import kz.tech.smartgrades.parent.model.ModelInteractionToolSteps;
import kz.tech.smartgrades.parent.model.ModelChildData2;
import kz.tech.smartgrades.sponsor.models.ModelUserCards;

public interface ICallBack {
    void onLoadChildWardCoins(ArrayList<ModelChildWardCoin> childWardCoinList, ArrayList<ModelDefaultChat> modelDefaultChat);
    void onLoadChildWardSteps(ArrayList<ModelInteractionToolSteps> stepsList, ArrayList<ModelDefaultChat> modelDefaultChat);
    void onLoadChildWardTime(int totalMinute);

    void ResultLoadChildData(ModelChildData modelChildData);

    void ResultAddAboutMe(boolean b);

    void ResultUpdateCartList(ArrayList<ModelUserCards> mUserCard);
}
