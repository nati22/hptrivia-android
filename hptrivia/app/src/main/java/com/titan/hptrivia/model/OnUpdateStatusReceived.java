package com.titan.hptrivia.model;

/**
 * Created by ntessema on 8/16/14.
 */
public interface OnUpdateStatusReceived {
    /**
     *  Hey
     */
    void onUpdateStatusReceived (boolean x);

    void onLastUpdateTimeReceived (String y, String z);
}