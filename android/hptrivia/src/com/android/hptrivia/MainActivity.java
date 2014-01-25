package com.android.hptrivia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView textViewTitle;
	Button buttonStartQuiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get XML assets
		textViewTitle = (TextView) findViewById(R.id.textView_app_title);
		buttonStartQuiz = (Button) findViewById(R.id.button_start_quiz);

		// set title font
		Typeface titleFont = Typeface.createFromAsset(getAssets(), "harryp.ttf");
		textViewTitle.setTypeface(titleFont);

		// set click listener
		buttonStartQuiz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, QuizActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
