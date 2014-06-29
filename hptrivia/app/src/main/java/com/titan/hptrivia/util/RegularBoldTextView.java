package com.titan.hptrivia.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ntessema on 6/28/14.
 */
public class RegularBoldTextView extends TextView {

    private static String TAG = RegularButton.class.getSimpleName();

    public RegularBoldTextView(Context context) {
        super(context);
        if (!this.isInEditMode()) setTypeface(Utils.getRegularBoldTypeface(context));
    }

    public RegularBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!this.isInEditMode()) setTypeface(Utils.getRegularBoldTypeface(context));
    }

    public RegularBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!this.isInEditMode()) setTypeface(Utils.getRegularBoldTypeface(context));

    }
}

