package com.titan.hptrivia.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ntessema on 6/28/14.
 */
public class RegularTextView extends TextView {

    private static String TAG = RegularButton.class.getSimpleName();

    public RegularTextView(Context context) {
        super(context);
        if (!this.isInEditMode()) setTypeface(Utils.getRegularTypeface(context));
    }

    public RegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!this.isInEditMode()) setTypeface(Utils.getRegularTypeface(context));
    }

    public RegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!this.isInEditMode()) setTypeface(Utils.getRegularTypeface(context));

    }
}
