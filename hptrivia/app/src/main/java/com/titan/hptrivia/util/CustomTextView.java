package com.titan.hptrivia.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ntessema on 6/3/14.
 */
public class CustomTextView extends TextView {

    private static final String TAG = CustomTextView.class.getSimpleName();

    public CustomTextView(Context context) {
        super(context);
        if (!this.isInEditMode()) setTypeface(Utils.getPotterTypeface(context));
    }

    // This is the one that's called
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!this.isInEditMode()) setTypeface(Utils.getPotterTypeface(context));
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!this.isInEditMode()) setTypeface(Utils.getPotterTypeface(context));
    }
}
