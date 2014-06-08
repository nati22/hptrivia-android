package com.titan.hptrivia.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.titan.hptrivia.R;

public class ResultsActivity extends ActionBarActivity {

    private ResultsAdapter adapter;
    private ListView listViewResults;
//    private ArrayList<Question> arrayListQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // get views
        listViewResults = (ListView) findViewById(R.id.listViewResults);

        adapter = new ResultsAdapter(getApplicationContext());
        listViewResults.setAdapter(adapter);
        listViewResults.setEmptyView(findViewById(android.R.id.empty));

    }


    private class ResultsAdapter extends BaseAdapter {

        private final String TAG = ResultsAdapter.class.getSimpleName();

        public ResultsAdapter(Context context) {
            Log.d(TAG, "constructor called");
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
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
}
