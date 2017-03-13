package com.qwash.user.ui.fragment.PrepareOrder;

/**
 * Created by Amay on 2/23/2017.
 */
public class TypeVehicle {
    private final int typeVehicle;
    private final int childTypeVehicle;

    public TypeVehicle(int TypeVehicle, int childTypeVehicle) {
        this.typeVehicle =TypeVehicle;
        this.childTypeVehicle=childTypeVehicle;
    }
    public int getTypeVehicle() {
        return typeVehicle;
    }

    public int getChildTypeVehicle() {
        return childTypeVehicle;
    }
}
