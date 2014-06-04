package com.titan.hptrivia.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by ntessema on 6/3/14.
 */
public class CustomTextView extends TextView {

    private static final String TAG = CustomTextView.class.getSimpleName();

    public CustomTextView(Context context) {
        super(context);
        Log.i(TAG, "Single param constructor called");
        if (!this.isInEditMode()) setTypeface(Utils.getPotterTypeface(context));
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "2 param constructor called");
        if (!this.isInEditMode()) setTypeface(Utils.getPotterTypeface(context));
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.i(TAG, "3 param constructor called");
        if (!this.isInEditMode()) setTypeface(Utils.getPotterTypeface(context));
    }
}
