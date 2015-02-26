package com.titan.hptrivia.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.titan.hptrivia.model.Question;
import com.titan.hptrivia.model.Quiz;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ntessema on 5/30/14.
 */
public class Utils {

    public static final String TAG = Utils.class.getSimpleName();
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

    public static Typeface getRegularTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/IMPERATOR.TTF");
    }

    public static Typeface getRegularBoldTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/IMPERATOR_BOLD.TTF");
    }

    public class CONSTANTS {
        public static final int MILLIS_PER_QUESTION = 10000;
        public static final int MILLIS_UPDATE_FREQUENCY = 1000;
    }

    public static Bitmap convertToCircularBitmap(Bitmap bitmap) {

        // Create a blank bitmap
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * Stores an image to internal memory. Requires method {getOutputMediaFile}.
     * @param context
     * @param image
     * @param id
     */
    public static String storeImage(Context context, Bitmap image, String id) {

        String TAG = "Utils.storeImage";

        File pictureFile = Utils.getOutputMediaFile(context, id);
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return "";
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
        return pictureFile.getPath();
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(Context context, String id){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + context.getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        String mImageName="IMG_" + id + ".png";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public static int getVersionCode(Context context) {
        int v = 0;
        try {
            v = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // Huh? Really?
        }
        return v;
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static Quiz getQuizFromFullJson(String fullJson) {
        Quiz quiz = null;

        try {
            JSONObject responseJSON = new JSONObject(fullJson.trim());
            JSONObject quizJSON = new JSONObject(responseJSON.getString("content")/*.replace("\\\"", "\"")*/);

            ArrayList<Question> questionList = new ArrayList<Question>();
            Iterator<?> iterator = quizJSON.keys();

            while( iterator.hasNext() ){
                String key = (String)iterator.next();
                if(quizJSON.get(key) instanceof JSONObject ){

                    JSONObject questionJSON = (JSONObject) quizJSON.get(key);
                    questionList.add(new Question(questionJSON));
                }
            }

            quiz = new Quiz(questionList);
            Log.d(TAG, "Just created a Quiz with " + quiz.size() + " elements");
        } catch (JSONException e) {
            Log.e(TAG, "getQuizJsonFromFullJson() failed. Error: " + e);
        }
        return quiz;
    }

}
