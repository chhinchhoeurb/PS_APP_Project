package ruc.ps_app_project;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PosterProfile extends AppCompatActivity {
    Button btnPost, btn_cancel,btn_change_pro, btn_view_pro;
    TextView poster_name,back;
    ListView listViewPosterPost;
    ImageView cover, profile;
    List<String> ID = new ArrayList<>();
    List<String> POSTER_ID = new ArrayList<>();
    List<String> PROFILE = new ArrayList<>();
    List<String> POSTIMAGE = new ArrayList<>();
    List<String> USERNAME = new ArrayList<>();
    List<String> NUMLIKE = new ArrayList<>();
    List<String> NUMCMT = new ArrayList<>();
    List<String> NUMFAV = new ArrayList<>();
    List<String> DESCRIPTION = new ArrayList<>();
    List<String> DATETIME = new ArrayList<>();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_profile);
        context = PosterProfile.this;

        back = (TextView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //==========================for profile==============================================
        poster_name = (TextView)findViewById(R.id.textView_username);
        cover = (ImageView)findViewById(R.id.imageView_cover);
        profile = (ImageView)findViewById(R.id.imageView_profile);

        //===========================get sharedPreference====================================
        SharedPreferences preferLogin = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String userId = preferLogin.getString("userId","");
        final String userName = preferLogin.getString("userName","");
//        Toast.makeText(PosterProfile.this, userName, Toast.LENGTH_LONG).show();

        //============================data of poster==========================================
        final AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("apikey", "123");
        client.get("http://192.168.1.6:8888/posters/posterProfile/"+userId, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String data = new String(responseBody, "UTF8");
                    //Log.i("data", data);
                    try {
                        JSONObject obj = new JSONObject(data);
                        //String status = obj.getString("status");
                        JSONObject poster_data= obj.getJSONObject("posterProfile");

                        String username = poster_data.getString("username");
                        String profiles = poster_data.getString("image");
                        String covers = poster_data.getString("cover");
                        //set text
                        poster_name.setText(username);

                        // profile poster
                        final String posterUrlImg = "http://192.168.1.6:8888/images/posters/"+profiles;
                        loadProfile(posterUrlImg,profile);
                        // post image
                        final String productUrlImg = "http://192.168.1.6:8888/images/posters/"+covers;
                        loadProductImage(productUrlImg,cover);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
//==============================================for all favorite post=====================================
        final AsyncHttpClient clients = new AsyncHttpClient();
        clients.addHeader("apikey", "123");
        clients.get("http://192.168.1.6:8888/posters/viewPosterPost/"+userId, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String data = new String(responseBody, "UTF8");
                    //Log.i("data", data);
                    try {
                        Toast.makeText(PosterProfile.this, "yes",Toast.LENGTH_SHORT).show();
                        JSONObject jsonObj = new JSONObject(data);
                        JSONArray user_data = jsonObj.getJSONArray("posterpost");
                        //Loop all info
                        for(int i = 0; i <= jsonObj.length(); i++){
                            JSONObject poster_data= user_data.getJSONObject(i);
                            String post_id = poster_data.getString("id");
                            String poster_id = poster_data.getString("posters_id");
                            String image_pro = poster_data.getString("image");
                            String image_pos = poster_data.getString("pos_image");
                            String username = poster_data.getString("username");
                            String description = poster_data.getString("pos_description");
                            String dateTime = poster_data.getString("created_at");
                            String likes = poster_data.getString("numlike");
                            String cmts = poster_data.getString("numcmt");
                            String favs = poster_data.getString("numfavorite");
                            //add  each info in to list array
                            ID.add(post_id);
                            POSTER_ID.add(poster_id);
                            POSTIMAGE.add(image_pos);
                            PROFILE.add(image_pro);
                            DESCRIPTION.add(description);
                            USERNAME.add(username);
                            DATETIME.add(dateTime);
                            NUMCMT.add(cmts);
                            NUMFAV.add(favs);
                            NUMLIKE.add(likes);
                        }
                    }catch (JSONException e){
                        Toast.makeText(PosterProfile.this, "no",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                listViewPosterPost = (ListView)findViewById(R.id.listViewPosterPost);
                PosterAdapter customAdapter = new PosterAdapter(getApplicationContext(), USERNAME,DATETIME ,DESCRIPTION,PROFILE, POSTIMAGE ,NUMLIKE,NUMFAV,NUMCMT);
                listViewPosterPost.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        //=================================for view profile =========================================
        profile = (ImageView)findViewById(R.id.imageView_profile);
        profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openDialog();

            }
        });


    }


    public void openDialog() {
        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.activity_dialog);
        dialog.show();
//        btn_cancel = (Button)findViewById(R.id.btn_cancel);
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
    }







    //============================ To load image of profile==============================================
    private void loadProfile(String url,ImageView imgView){
        Picasso.with(context)
                .load(url)
                .resize(800,800)
                .centerInside()// to zoom img
                //.centerCrop()
                .into(imgView);
    }

    //============================== To load image of post================================================
    private void loadProductImage(String url,ImageView imgView){
        Picasso.with(context)
                .load(url)
                .resize(2000,2000)
                .centerInside()// to zoom img
                //.centerCrop()
                .into(imgView);
    }

}
