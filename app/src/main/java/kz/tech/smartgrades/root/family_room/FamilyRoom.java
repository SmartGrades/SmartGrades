package kz.tech.smartgrades.root.family_room;

import kz.tech.smartgrades.root.models.ModelFamilyRoom;

public class FamilyRoom implements IFamily {

    public FamilyRoom() {}

    private ModelFamilyRoom m;


    @Override
    public void setFamilyRoom(ModelFamilyRoom m) {
        this.m = m;
    }

    @Override
    public ModelFamilyRoom getFamilyRoom() {
        return m;
    }
}
