package com.example.webapis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String CHUCK_NORIS_WEB_API = "http://api.icndb.com/jokes/random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickJocks(View view) {

        Ion.with(this)
                .load(CHUCK_NORIS_WEB_API)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    public void onCompleted(Exception e, String result) {
                        //Log.v("ion", result);
                        processJsonJoke(result);
                    }
                });
    }

    private void processJsonJoke(String jsonJock){
         try{
             JSONObject result = new JSONObject(jsonJock);
             String joke = result.getJSONObject("value").getString("joke");
             TextView jokeTextView = findViewById(R.id.tv_joke);
             jokeTextView.setText(joke);
         } catch (JSONException jsonE){
            Log.v("processJsonJoke", jsonE.getMessage());
         }

    }

    public void onClickCats(View view) {
    }
}



         /*
        {
          "type": "success",
          "value": {
            "id": 114,
            "joke": "Chuck Norris doesn't believe in Germany.",
            "categories": [

            ]
          }
        }
         */
