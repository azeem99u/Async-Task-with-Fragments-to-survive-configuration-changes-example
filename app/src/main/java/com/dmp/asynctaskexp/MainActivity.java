package com.dmp.asynctaskexp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AsyncFragment.MyTaskHandler {
    private static final String FRAGMENT_TAG = "AsyncFragment";
    private TextView textView;

    private Button button;
    private boolean mTaskRunning ;
    private AsyncFragment asyncFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        FragmentManager manager = getFragmentManager();
        asyncFragment = (AsyncFragment) manager.findFragmentByTag(FRAGMENT_TAG);
        if (asyncFragment == null){
            asyncFragment = new AsyncFragment();
            manager.beginTransaction().add(asyncFragment,FRAGMENT_TAG).commit();
        }


        button.setOnClickListener(view -> {
            if (mTaskRunning && asyncFragment.myAsyncTask != null){
                asyncFragment.myAsyncTask.cancel(true);
                mTaskRunning = false;
                button.setText("Start Task Again");
            }else {
                button.setText("Stop Task");
                asyncFragment.runTask("movie 1","movie 2","movie 3","movie 4");
                mTaskRunning = true;

            }

        });


    }

    @Override
    public void handlerTask(String message) {
        message += "\n";
        textView.append(message);
    }

    @Override
    public void invoke() {
        mTaskRunning = false;
        button.setText("Start Task Again");
    }


}