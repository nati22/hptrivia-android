package com.titan.hptrivia.util;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by ntessema on 5/30/14.
 */
public class Utils {

    /**
     * Returns true IFF this device is connected to the internet, either through
     * WiFi, 3G or 4G.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();

        return activeNetworkInfo != null;
    }

    public static void makeShortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static Typeface getPotterTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/HARRYP.TTF");
    }

}
