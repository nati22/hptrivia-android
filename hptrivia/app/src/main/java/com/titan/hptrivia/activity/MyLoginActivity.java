package com.titan.hptrivia.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.titan.hptrivia.R;
import com.titan.hptrivia.network.RestClientImpl;
import com.titan.hptrivia.util.Keys;
import com.titan.hptrivia.util.Utils;

import java.io.InputStream;

public class MyLoginActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MyLoginActivity.class.getSimpleName();

    private SharedPreferences prefs;
    private RestClientImpl restClient;

    // UI
    private SignInButton buttonGoogleSignIn;
    /* This dialog is displayed while registering the user on my servers */
    private ProgressDialog pDialog;

    // Google+ auth
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents. */
    private boolean mIntentInProgress;

    /* Track whether the sign-in button has been clicked so that we know to resolve
    * all issues preventing sign-in without waiting. */
    private boolean mSignInClicked = false;

    /* Store the connection result from onConnectionFailed callbacks so that we can
    * resolve them when the user clicks sign-in.
    */
    private ConnectionResult mConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

    //    tryToAutoLogin();

        restClient = new RestClientImpl(this);

        // Google+
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        buttonGoogleSignIn = (SignInButton) findViewById(R.id.sign_in_button);
        buttonGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mGoogleApiClient.isConnecting()) {
                    if (mSignInClicked) {
                        if (!mGoogleApiClient.isConnected()) resolveSignInError();
                    } else {
                        mGoogleApiClient.connect();
                        mSignInClicked = true;
                    }
                }
            }
        });

        // set title font
        TextView titleText = ((TextView) findViewById(R.id.textView_loginTitle));
        titleText.setTypeface(Utils.getPotterTypeface(getApplicationContext()));
        titleText.setTextSize(80);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "connected");

        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

            String personName = currentPerson.getDisplayName();
            String personGooglePlusProfile = currentPerson.getUrl();
            String personPhotoURL = currentPerson.getImage().getUrl();

            // TODO store info in shared prefs
            prefs.edit().putString(Keys.PREFS.GOOGLE_IMG_URL.name(), personPhotoURL).commit();

            // get image
        //    new DownloadImageTask((ImageView) findViewById(R.id.profile_pic)).execute(personPhotoURL);


            // try to create new user
            restClient.createNewUser(currentPerson.getId(),
                    currentPerson.getName().getGivenName(),
                    currentPerson.getName().getFamilyName());

        } else Log.e(TAG, "current person == NULL");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult cResult) {
        Log.e(TAG, "connection failed");
        Log.e(TAG, "connection result = " + cResult.getResolution().toString());
        Log.e(TAG, "connection error code= " + cResult.getErrorCode());

        // handle different issues: http://goo.gl/WkezlF
        if (!mIntentInProgress) {
            // Store the ConnectionResult so that we can use it later when the user clicks
            // 'sign-in'.
            mConnectionResult = cResult;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }

    private void tryToAutoLogin() {
        if (prefs.getBoolean(Keys.PREFS.AUTO_LOGIN.name(), false)) {
            Log.d(TAG, "Auto-logging in...");

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  // remove MyLoginActivity from backstack
            startActivity(intent);

            finish();
        }
    }

    /* A helper method to resolve the current ConnectionResult error. */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        Log.d(TAG, "onActivityResult called");
        if (requestCode == RC_SIGN_IN) {
/*            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }*/
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView img;

        public DownloadImageTask(ImageView img) {
            this.img = img;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            // write image to storage

            // store location in SharedPrefs
            img.setImageBitmap(Utils.convertToCircularBitmap(result));

            Toast.makeText(getApplicationContext(), "Image downloaded", Toast.LENGTH_SHORT).show();
        }
    }

}
