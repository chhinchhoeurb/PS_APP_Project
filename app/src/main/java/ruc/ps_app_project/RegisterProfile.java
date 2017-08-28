package ruc.ps_app_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class RegisterProfile extends AppCompatActivity {

    GridView simpleGrid;
    int logos[] = {R.drawable.postprofile, R.drawable.postprofile, R.drawable.postprofile, R.drawable.postprofile,
            R.drawable.postprofile, R.drawable.postprofile};
    String productListItem[] = {"Camboida","India","B","C","D","E"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);

        simpleGrid = (GridView) findViewById(R.id.simpleGridView); // init GridView

        // Create an object of CustomAdapter and set Adapter to GirdView
        RegisterAdapter customAdapter = new RegisterAdapter(getApplicationContext(), productListItem,logos);
        simpleGrid.setAdapter(customAdapter);

        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                //Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                //  intent.putExtra("IMAGE", logos[position]); // put image data in Intent
                // startActivity(intent); // start Intent
                Toast.makeText(RegisterProfile.this,"Popup Image",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
