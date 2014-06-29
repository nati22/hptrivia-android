package com.titan.hptrivia.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by ntessema on 6/28/14.
 */
public class RegularButton extends Button {

    private static String TAG = RegularButton.class.getSimpleName();

    public RegularButton(Context context) {
        super(context);
        if (!this.isInEditMode()) setTypeface(Utils.getRegularTypeface(context));
    }

    public RegularButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!this.isInEditMode()) setTypeface(Utils.getRegularTypeface(context));
    }

    public RegularButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!this.isInEditMode()) setTypeface(Utils.getRegularTypeface(context));

    }
}
