package ruc.ps_app_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpGet;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ListView simpleList;
    private Spinner spinner;
    List<String> users;
    List<String> postId, postDesc,postPro,postImage,dateAndTime,numeLike,numCmt,numFav;
    ListView homeListView;

    TextView registerAction,loginAction;;
    View nav_view ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // -------------------------List view--------------------------
        homeListView = (ListView)findViewById(R.id.simpleListView);
        //Event on ListView



        homeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Intent detailIntent = new Intent(HomeActivity.this,PostDetailActivity.class);
                detailIntent.putExtra("postId",postId.get(position).toString());
               // detailIntent.putExtra("userPostId",USERPOSTID.get(position).toString());
                startActivity(detailIntent);
            }
        });

        //-----------drawer bar----------------------
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //---------------------------Register---------------------------------
        View headerview = navigationView.getHeaderView(0);
        registerAction = (TextView) headerview.findViewById(R.id.action_register);
        registerAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(HomeActivity.this,Register.class);
                startActivity(regIntent);
            }
        });

        //--------------------------------Login--------------------------------------
        loginAction = (TextView) headerview.findViewById(R.id.action_login);
        loginAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(HomeActivity.this,Login.class);
                startActivity(regIntent);
            }
        });


        //------------------------------------start Spinner-------------------------------------


        // Spinner Drop down elements
        final List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        Spinner spinner = (Spinner) navigationView.getMenu().findItem(R.id.nav_categories).getActionView();
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,categories));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HomeActivity.this, categories.get(position),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //----------------------------------End spinner----------------------------------------

        users = new ArrayList<String>();
        postDesc = new ArrayList<String>();
        postPro = new ArrayList<String>();
        postImage = new ArrayList<String>();
        dateAndTime = new ArrayList<String>();
        numeLike = new ArrayList<String>();
        numCmt = new ArrayList<String>();
        numFav = new ArrayList<String>();
        postId = new ArrayList<String>();
        //------------------------Start get data all of post----------------------





        //------------------------End get data all of post----------------------

        // call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute("http://192.168.1.14:1111/posts/viewAllPost");


    }



    // To get API url
    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONArray jArray = jsonObj.getJSONArray("data");

                for(int i=0; i < jArray.length(); i++){
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    String name = jsonObject.getString("username");
                    String description = jsonObject.getString("pos_description");
                    String postIds = jsonObject.getString("id");
                    String postProfile = jsonObject.getString("image");
                    String postImg = jsonObject.getString("pos_image");
                    String dateTime = jsonObject.getString("created_at");
                    String likes = jsonObject.getString("numlike");
                    String cmts = jsonObject.getString("numcmt");
                    String favs = jsonObject.getString("numfavorite");

                    users.add(name);
                    postDesc.add(description);
                    postPro.add(postProfile);
                    postImage.add(postImg);
                    dateAndTime.add(dateTime);
                    numeLike.add(likes);
                    numCmt.add(cmts);
                    numFav.add(favs);
                    postId.add(postIds);
                    Log.i("name",postId.toString());



                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "not data!", Toast.LENGTH_LONG).show();

            }

            HomeAdapter homeList = new HomeAdapter(getApplicationContext(),users,dateAndTime,postDesc,postPro,postImage,numeLike,numFav,numCmt);
            homeListView.setAdapter(homeList);

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_categories) {

        } else if (id == R.id.nav_manage_favorite) {

        } else if (id == R.id.nav_manage_profile) {

        } else if (id == R.id.nav_manage_post) {

        } else if (id == R.id.nav_change_password) {
           // Intent intent = new Intent(HomeActivity.this,ChangePasswordActivity.class);
            //startActivity(intent);
        } else if (id == R.id.nav_Logout){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}