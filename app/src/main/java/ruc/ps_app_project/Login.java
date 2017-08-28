package ruc.ps_app_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Login extends AppCompatActivity {

    String user = "";
    String url = "";
    Button btnLogin;
    EditText logEmail, logPassword;
    String validateEmail, validatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logEmail = (EditText)findViewById(R.id.log_email);
        logPassword = (EditText)findViewById(R.id.log_password);

        // Menu custom
        Toolbar mDetailToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mDetailToolBar);

        //Start login process

        btnLogin = (Button) findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
                if (user == "seler"){
                    url = "http://192.168.1.22:2222/users/login";
//                    System.out.println(url);
                }else{
                    url = "http://192.168.1.22:2222/posters/login";
                }
                login();

            }
        });

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
//        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_seller:
                user = "seler";
                break;
            case R.id.radio_buyer:
                user = "buyer";
                break;
        }
    }
    //-------------End checkbox----

    public  void login(){
        intialize();
        if(!validate()){
            Toast.makeText(Login.this,"Login has Failed.",Toast.LENGTH_SHORT).show();
        }else{
            loginSuccess();
        }
    }

    public boolean validate(){
        boolean valid = true;
        if (validatePassword.isEmpty() || validatePassword.length() < 5){
            logPassword.setError("Please Enter valid Password!");
            valid = false;
        }if (validateEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(validateEmail).matches()){
            logEmail.setError("Please Enter valid Email Address!");
            valid = false;
        }
        return valid;

    }

    public void intialize() {
        validateEmail = logEmail.getText().toString().trim();
        validatePassword = logPassword.getText().toString().trim();
    }

    public void loginSuccess(){

        RequestParams requestParams = new RequestParams();
        requestParams.add("email",String.valueOf(logEmail.getText().toString()));
        requestParams.add("password",String.valueOf(logPassword.getText().toString()));
//                    System.out.println(requestParams);
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                try {
                    String data = new String(responseBody, "UTF-8");
//                    Log.i("Data", data);
                    try {
                        JSONObject object = new JSONObject(data);
                        JSONArray objectUser = object.getJSONArray("data");
//                        JSONObject object = new JSONObject(data);
                        JSONObject objUser = objectUser.getJSONObject(0);
                        String username = objUser.getString("username");
                        String id = objUser.getString("id");
//                        Log.i("userdata" ,id);
                        String checkStatus = object.getString("status");
                        String aa = "";

                        if (checkStatus.equals("success")){
                            SharedPreferences pref = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("userId",id);
                            editor.putString("userName",username);
                            editor.commit();

//                            Intent goHome = new Intent(Login.this, HomeActivity.class);
//                            startActivity(goHome);
                            Toast.makeText(Login.this,"Login Success!!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Login.this,"Email address or Password incorrect!",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(Login.this,"Error",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

                try {
                    String data = new String(responseBody, "UTF-8");
//                                Toast.makeText(MainActivity.this,"Add fale", Toast.LENGTH_SHORT).show();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
                Toast.makeText(Login.this,"Login failse!!",Toast.LENGTH_SHORT).show();
            }


        });
//                Toast.makeText(Login.this,"Login Success.",Toast.LENGTH_SHORT).show();
    }


//});

}
