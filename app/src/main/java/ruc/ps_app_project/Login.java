package ruc.ps_app_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Menu custom
        Toolbar mDetailToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mDetailToolBar);

    }


    // ---------------- start Overflow menu ---------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mMenuInflater = getMenuInflater();
        mMenuInflater.inflate(R.menu.menu_register, menu);
        return true;
    }


    /**
     * Menu overflow
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.toRegiter:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                Intent updateIntent = new Intent(Login.this,Register.class);
                startActivity(updateIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // ---------------- End Overflow menu ---------------------


    // ---------Checkbox-----------
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_seller:
                if (checked)
                    Toast.makeText(Login.this,"seller",Toast.LENGTH_LONG).show();
                break;
            case R.id.radio_buyer:
                if (checked)
                    Toast.makeText(Login.this,"buyer",Toast.LENGTH_LONG).show();
                break;
        }
    }
    //-------------End checkbox----
}
