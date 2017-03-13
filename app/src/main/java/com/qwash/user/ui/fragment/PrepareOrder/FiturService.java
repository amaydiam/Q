package com.qwash.user.ui.fragment.PrepareOrder;

/**
 * Created by Amay on 2/23/2017.
 */
public class FiturService {


    private boolean isPerfurmed = false;
    private boolean isInteriorVaccum = false;
    private boolean isWaterless = false;

    public FiturService(boolean isPerfurmed, boolean isInteriorVaccum, boolean isWaterless) {
        this.isPerfurmed = isPerfurmed;
        this.isInteriorVaccum = isInteriorVaccum;
        this.isWaterless = isWaterless;
    }

    public boolean isPerfurmed() {
        return isPerfurmed;
    }

    public boolean isInteriorVaccum() {
        return isInteriorVaccum;
    }

    public boolean isWaterless() {
        return isWaterless;
    }
}
