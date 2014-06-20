package com.titan.hptrivia.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.titan.hptrivia.R;
import com.titan.hptrivia.model.NewQuizListener;
import com.titan.hptrivia.model.Quiz;
import com.titan.hptrivia.model.QuizPersister;
import com.titan.hptrivia.network.RestClientImpl;
import com.titan.hptrivia.util.CustomTextView;
import com.titan.hptrivia.util.Utils;


public class HomeActivity extends ActionBarActivity implements NewQuizListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private QuizPersister quizPersister;
    private RestClientImpl restClient;

    // UI elements
    private Button buttonStartQuiz;
    private SignInButton buttonGoogleSignIn;

    // Google+ auth
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;

    /* Track whether the sign-in button has been clicked so that we know to resolve
    * all issues preventing sign-in without waiting.
    */
    private boolean mSignInClicked = false;

    /* Store the connection result from onConnectionFailed callbacks so that we can
    * resolve them when the user clicks sign-in.
    */
    private ConnectionResult mConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_home_new);

        // Google+
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();


        quizPersister = QuizPersister.getInstance();
        restClient = new RestClientImpl(getApplicationContext());

        inflateXML();
        setTitleBarFont();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void inflateXML() {

        // set title font
        TextView titleText = ((TextView) findViewById(R.id.textView_appTitle));
        titleText.setTypeface(Utils.getPotterTypeface(getApplicationContext()));
        titleText.setTextSize(80);

        buttonStartQuiz = (Button) findViewById(R.id.buttonStartQuiz);
        if (!quizPersister.hasQuiz())
            buttonStartQuiz.setBackgroundColor(getResources().getColor(R.color.gray));
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setProgressBarIndeterminateVisibility(true);
                restClient.generateNewQuiz(5);
            }
        });

        // Google+ sign in button
        buttonGoogleSignIn = (SignInButton) findViewById(R.id.sign_in_button);
        buttonGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.sign_in_button
                        && !mGoogleApiClient.isConnecting()) {
                    if (mSignInClicked) {
                        resolveSignInError();
                    } else {
                        mGoogleApiClient.connect();
                        mSignInClicked = true;
                    }
                }
            }
        });
    }

    private void setTitleBarFont() {
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_title, null);

        // if you need to customize anything else about the text, do it here.
        ((CustomTextView)v.findViewById(R.id.customTitle)).setText(this.getTitle());

        // assign the view to the actionbar
        this.getSupportActionBar().setCustomView(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_home_settings:
                Utils.makeShortToast(getApplicationContext(), "Home Settings");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);
        quizPersister.addNewQuizListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        quizPersister.removeNewQuizListener(this);

//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
    }

    @Override
    public void onNewQuizStored(Quiz quiz) {
    //    buttonStartQuiz.setActivated(true);
    //    buttonStartQuiz.setBackgroundColor(getResources().getColor(R.color.blue));
        setProgressBarIndeterminateVisibility(false);
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected called");

        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String personName = currentPerson.getDisplayName();
            Toast.makeText(getApplicationContext(), "connected as " + personName, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "personName = " + personName);
            Log.d(TAG, "img url: " + currentPerson.getImage().getUrl());

            String personGooglePlusProfile = currentPerson.getUrl();
        } else Log.e(TAG, "current person == NULL");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "onConnectionSuspended called");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed");
        Log.e(TAG, "error code " + connectionResult.getErrorCode());
        Log.e(TAG, "connectionResult resolution: " + connectionResult.getResolution());
        Log.e(TAG, "connectionResult.toString" + connectionResult.toString());

        // handle different issues: http://goo.gl/WkezlF

        if (!mIntentInProgress) {
            // Store the ConnectionResult so that we can use it later when the user clicks
            // 'sign-in'.
            mConnectionResult = connectionResult;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

/*        if (!mIntentInProgress && connectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
            //    startIntentSenderForResult(connectionResult.getResolution().getIntentSender(),
            //            RC_SIGN_IN, null, 0, 0, 0);
                connectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (android.content.IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }*/
    //    Log.e(TAG, "connection failed: (error code " + connectionResult.getErrorCode()
    //            + "), \ntoString" + connectionResult.toString() + ",\nresolution.toString: " + connectionResult.getResolution().toString());
    }

    /* A helper method to resolve the current ConnectionResult error. */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
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
}
