package com.example.webapis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String CHUCK_NORIS_WEB_API = "http://api.icndb.com/jokes/random";
    private static final String THE_CAT_API = "https://api.thecatapi.com/api/images/get?format=json&results_per_page=6";

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
        Ion.with(this)
                .load(THE_CAT_API)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    public void onCompleted(Exception e, String result) {
                        //Log.v("ion", result);
                        processJsonCat(result);
                    }
                });
    }

    private void processJsonCat(String result){
        GridLayout gridLayout = findViewById(R.id.gl_images);
        gridLayout.removeAllViews();
        try{
            JSONArray jsArray = new JSONArray(result);
            for (int i=0; i<jsArray.length(); i++){
                String imgUrl = jsArray.getJSONObject(i).getString("url");
                loadImage(imgUrl);
            }

        } catch (JSONException jsEx){
            Log.v("processJsonCat", jsEx.getMessage());
        }
    }

    private void loadImage(String url){
        ImageView img = new ImageView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        img.setLayoutParams(params);
        GridLayout gridLayout = findViewById(R.id.gl_images);
        gridLayout.addView(img);

        Picasso.get()
                .load(url)
                .resize(550, 800)
                .centerCrop()
                .into(img);
    }
}



         /* Json format -------------------
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


         /* XML format CAT -------------------
         <response>
            <data>
                <images>
                    <image>
                        <id>4g6</id>
                        <url>
                          https://s3.us-west-2.amazonaws.com/cdn2.thecatapi.com/images/4g6.gif
                        </url>
                        <source_url>https://thecatapi.com/?image_id=4g6</source_url>
                    </image>
                </images>
            </data>
        </response>
          */
         /* Json format CAT -----------------
{
  "response": {
    "data": {
      "images": {
        "image": [
          { "url": "http://27.media.tumblr.com/tumblr_m27fatL9PD1qzex9io1_500.jpg",
            "id": "sm",
            "source_url": "http://thecatapi.com/?id=sm" },
          { "url": "http://25.media.tumblr.com/tumblr_m1o0ai1nI31qzex9io1_500.jpg",
            "id": "5tv",
            "source_url": "http://thecatapi.com/?id=5tv" },
          { "url": "http://25.media.tumblr.com/tumblr_m18j7miQoo1r94vvxo1_500.jpg",
            "id": "b0f",
            "source_url": "http://thecatapi.com/?id=b0f" }
        ]
      }
    }
  }
}
          */