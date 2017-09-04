package ruc.ps_app_project;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class PosterProfile extends AppCompatActivity {
    Button btnPost;
    ListView simpleList;
    int flags[] = {R.drawable.back, R.drawable.back};
    String countryList[] = {"Camboida","India"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_profile);



//        simpleList = (ListView) findViewById(R.id.listViewPoster);
//        PosterAdapter customAdapter = new PosterAdapter(getApplicationContext(), countryList, flags);
//        simpleList.setAdapter(customAdapter);


    }





}
