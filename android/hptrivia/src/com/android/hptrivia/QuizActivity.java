package com.android.hptrivia;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	TextView textViewQuestionNumber;
	TextView textViewQuestion;
	Button buttonA;
	Button buttonB;
	Button buttonC;
	Button buttonD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_question);

		inflateXML();
		addClickListeners();
	}

	private void inflateXML() {
		textViewQuestionNumber = (TextView) findViewById(R.id.textView_question_num);
		textViewQuestion = (TextView) findViewById(R.id.textView_question);
		buttonA = (Button) findViewById(R.id.button_answer1);
		buttonB = (Button) findViewById(R.id.button_answer2);
		buttonC = (Button) findViewById(R.id.button_answer3);
		buttonD = (Button) findViewById(R.id.button_answer4);
	}

	private void addClickListeners() {
		OnClickListener cl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button thisButton = (Button) v;
				Toast.makeText(getApplicationContext(),
						"you selected " + thisButton.getText().toString(),
						Toast.LENGTH_SHORT).show();
			}
		};

		buttonA.setOnClickListener(cl);
		buttonB.setOnClickListener(cl);
		buttonC.setOnClickListener(cl);
		buttonD.setOnClickListener(cl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

}
