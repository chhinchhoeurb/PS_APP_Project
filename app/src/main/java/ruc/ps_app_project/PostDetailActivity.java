package ruc.ps_app_project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class PostDetailActivity extends AppCompatActivity {
    String postID;
    TextView poster,postDate,productName,productPrice,productDis,productDes,phone,email,address;
    Button btnLike,btnFav,btnCmt;
    ImageView posterProfile,postImage;
    TextView detail_back;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        poster = (TextView)findViewById(R.id.posterName);
        postDate = (TextView)findViewById(R.id.postDate);
        productName = (TextView)findViewById(R.id.pName);
        productPrice = (TextView)findViewById(R.id.pPrice);
        productDis = (TextView)findViewById(R.id.pDiscount) ;
        productDes = (TextView)findViewById(R.id.pDes) ;
        phone = (TextView)findViewById(R.id.cPhone);
        email = (TextView)findViewById(R.id.cMail);
        address = (TextView)findViewById(R.id.cAddress);

        btnLike = (Button)findViewById(R.id.btnlike);
        btnFav = (Button)findViewById(R.id.btnfavorite) ;
        btnCmt = (Button)findViewById(R.id.btncommentt);

        posterProfile = (ImageView)findViewById(R.id.detail_circle_image);
        postImage = (ImageView)findViewById(R.id.detailImage);

        // ----------------------get intent ==================================
        postID = getIntent().getStringExtra("postId");
        //Toast.makeText(PostDetailActivity.this,postID,Toast.LENGTH_LONG).show();

        //=============================back to home ===========================
        detail_back = (TextView)findViewById(R.id.back_postDetail);
        detail_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        //------------------------Start get data detail of post
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://192.168.1.14:1111/posts/postDetail/"+postID, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("my test","success");
                try {
                    String data = new String(responseBody, "UTF-8");
                    try {
                        JSONObject jsonObj = new JSONObject(data);
                        JSONArray jArray = jsonObj.getJSONArray("posts");
                        JSONObject objJson = jArray.getJSONObject(0);

                        poster.setText(objJson.getString("poster"));
                        postDate.setText(objJson.getString("created_at"));

                        productName.setText(objJson.getString("pos_title"));
                        productPrice.setText("$"+objJson.getString("price"));
                        productDis.setText(objJson.getString("discount")+"%");
                        productDes.setText(objJson.getString("pos_description"));
                        phone.setText(objJson.getString("pos_telephone"));
                        email.setText(objJson.getString("postermail"));
                        address.setText(objJson.getString("pos_address"));

                        btnLike.setText(objJson.getString("numlike"));
                        btnFav.setText(objJson.getString("numfavorite"));
                        btnCmt.setText(objJson.getString("numcmt"));

                        // profile poster
                        final String posterUrlImg = "http://192.168.1.14:1111/images/posters/"+objJson.getString("posterprofile");
                        loadProfile(posterUrlImg,posterProfile);
                        // post image
                        final String productUrlImg = "http://192.168.1.14:1111/images/posts/"+objJson.getString("pos_image");
                        loadProductImage(productUrlImg,postImage);




                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("my test","Fail");
                try {
                    String data = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });


        //--------------------------End detail post


    }

    // To load image of profile
    private void loadProfile(String url,ImageView imgView){
        Picasso.with(context)
                .load(url)
                .resize(800,800)
                .centerInside()// to zoom img
                //.centerCrop()
                .into(imgView);
    }

    // To load image of post
    private void loadProductImage(String url,ImageView imgView){
        Picasso.with(context)
                .load(url)
                .resize(1300,1200)
                .centerInside()// to zoom img
                //.centerCrop()
                .into(imgView);
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
    }


}
