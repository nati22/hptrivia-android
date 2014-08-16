package com.titan.hptrivia.util;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.titan.hptrivia.R;

/**
 * Created by ntessema on 8/16/14.
 */
public class UpdateNeededDialog extends DialogFragment {

    private static final String TAG = UpdateNeededDialog.class.getSimpleName();

    private Button mButton;

    public UpdateNeededDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_needed, container);
        mButton = (Button) view.findViewById(R.id.update_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = "market://details?id=" + Utils.getPackageName(getActivity());
                Log.d(TAG, "url = " + url);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        getDialog().setTitle("Update required");
        setCancelable(false);
        return view;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(TAG, "onCancel");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        Log.d(TAG, "onDismisss");
    }
}
