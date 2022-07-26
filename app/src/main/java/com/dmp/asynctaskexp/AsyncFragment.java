package com.dmp.asynctaskexp;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AsyncFragment extends Fragment {

    public MyAsyncTask myAsyncTask;
    private MyTaskHandler myTaskHandler;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void runTask(String... strings){
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(strings);
    }


    public interface MyTaskHandler{
        void handlerTask(String message);
        void invoke();
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MyTaskHandler){
            myTaskHandler= (MyTaskHandler) context;
        }
    }

    class MyAsyncTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            for (String s:strings) {
                if (isCancelled()){
                    publishProgress("task cancelled");
                    break;
                }
                SystemClock.sleep(1000);
                publishProgress(s);

            }
            return "Download Completed";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            myTaskHandler.handlerTask(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            myTaskHandler.handlerTask("Result: "+s);
            myTaskHandler.invoke();

        }

        @Override
        protected void onCancelled() {
            myTaskHandler.handlerTask("Task cancelled");
        }

        @Override
        protected void onCancelled(String s) {
            myTaskHandler.handlerTask("Task has been cancelled: "+s);
        }
    }
}
