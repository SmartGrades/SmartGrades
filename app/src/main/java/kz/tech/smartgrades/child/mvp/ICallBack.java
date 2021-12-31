package kz.tech.smartgrades.child.mvp;

import java.util.ArrayList;

import kz.tech.smartgrades.child.models.ModelChildData;
import kz.tech.smartgrades.child.models.ModelChildWardCoin;
import kz.tech.smartgrades.mentor.models.ModelDefaultMessages;
import kz.tech.smartgrades.parent.model.ModelInteractionToolSteps;
import kz.tech.smartgrades.sponsor.models.ModelDiscontCard;

public interface ICallBack {
    void onLoadChildWardCoins(ArrayList<ModelChildWardCoin> childWardCoinList, ArrayList<ModelDefaultMessages> modelDefaultChat);
    void onLoadChildWardSteps(ArrayList<ModelInteractionToolSteps> stepsList, ArrayList<ModelDefaultMessages> modelDefaultChat);
    void onLoadChildWardTime(int totalMinute);

    void ResultLoadChildData(ModelChildData modelChildData);

    void ResultAddAboutMe(boolean b);

    void ResultUpdateCartList(ArrayList<ModelDiscontCard> mUserCard);
}
