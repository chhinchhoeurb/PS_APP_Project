package ruc.ps_app_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class EditUserActivity extends AppCompatActivity {
    EditText user_name,user_mail,user_phone,user_add;
    String userLoginID;
    TextView btnUpdate,back_update;
    TextInputLayout TextInputConfirmPhone, TextInputAdd, TextInputUsername, TextInputEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        user_name = (EditText)findViewById(R.id.user_name);
        user_mail = (EditText)findViewById(R.id.user_email);
        user_phone = (EditText)findViewById(R.id.user_phone);
        user_add = (EditText)findViewById(R.id.user_add);

        //-------------------Back--------------------------
        back_update = (TextView)findViewById(R.id.udpate_user_back);
        back_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SharedPreferences prefUserLogin = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        userLoginID = prefUserLogin.getString("userId","");



        //------------------------Start get data old data of user
        AsyncHttpClient client = new AsyncHttpClient();
        // client.addHeader("header_key", "header value");

        client.get("http://192.168.56.1:1111/users/userProfile/"+userLoginID, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("test","success");
                try {
                    String data = new String(responseBody, "UTF-8");
                    try {
                        JSONObject jsonObj = new JSONObject(data);
                        JSONArray jArray = jsonObj.getJSONArray("posterProfile");
                        JSONObject obj = jArray.getJSONObject(0);

                        String userName = obj.getString("username");
                        String usereEmail = obj.getString("email");
                        String userPhone = obj.getString("phone");
                        String useradd = obj.getString("address");
                        String idInfo = obj.getString("id");

                        Log.i("ddd",idInfo);

                        user_name.setText(userName);
                        user_mail.setText(usereEmail);
                        user_phone.setText(userPhone);
                        user_add.setText(useradd);




                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("test data","Fail");
                try {
                    String data = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });

        //--------------------------End get old user ------------------------------


        //---------------------------start update user-----------------------------
        btnUpdate = (TextView) findViewById(R.id.btnUpdateUser);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputConfirmPhone = (TextInputLayout)findViewById(R.id.TextInputPhone);
                TextInputAdd = (TextInputLayout)findViewById(R.id.TextInputAdd);
                TextInputUsername = (TextInputLayout)findViewById(R.id.TextInputUserName);
                TextInputEmail = (TextInputLayout)findViewById(R.id.TextInputEmail);

                AsyncHttpClient client = new AsyncHttpClient();

                String newUserName = user_name.getText().toString();
                String newEmail = user_mail.getText().toString();
                String newPhone = user_phone.getText().toString();
                String newAdd = user_add.getText().toString();

                Boolean checkData = false;
                if(user_name.length()== 0){
                    showMsgError(TextInputUsername, user_name, "User name is required!");
                }else{
                    hideMsgError(TextInputUsername, user_name,2);
                }
                if(user_mail.length()==0){
                    showMsgError(TextInputEmail, user_mail,"Email is required!");
                }else {
                    checkData = true;
                  hideMsgError(TextInputEmail, user_mail,2);
                }
                if(!emailValidator(user_mail.getText().toString())){
                    showMsgError(TextInputEmail, user_mail,"Email is invalid!");
                    checkData = true;
                }else {
                    hideMsgError(TextInputEmail, user_mail,2);
                }

                if(user_phone.length()==0){
                    showMsgError(TextInputConfirmPhone, user_phone,"Phone is required!");
                }else {
                    checkData = true;
                    hideMsgError(TextInputConfirmPhone, user_phone,2);
                }


                if(user_add.length()==0){
                    showMsgError(TextInputAdd, user_add,"Address is required!");
                }else {
                    checkData = true;
                    hideMsgError(TextInputAdd, user_add,2);
                }


                if(checkData.equals(false)){
                    RequestParams requestParams = new RequestParams();
                    requestParams.add("username", newUserName);
                    requestParams.add("email",newEmail);
                    requestParams.add("phone",newPhone);
                    requestParams.add("address",newAdd);

                    client.post("http://192.168.56.1:1111/users/updateUserInfo/"+userLoginID, requestParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                String data = new String(responseBody, "UTF-8");
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    String sms = jsonObject.getString("status");
                                    if(sms.equals("success")){
                                        Toast.makeText(EditUserActivity.this,"success",Toast.LENGTH_LONG).show();
                                        Intent backProfileUser = new Intent(EditUserActivity.this,RegisterProfile.class);
                                        startActivity(backProfileUser);
                                    }
                                    if(sms.equals("fail")){
                                        showMsgError(TextInputEmail, user_mail,"This email is already used!");
                                    }


                                } catch (Throwable t) {
                                    t.printStackTrace();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            try {
                                Toast.makeText(EditUserActivity.this,"fail",Toast.LENGTH_LONG).show();
                                String data = new String(responseBody, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (NullPointerException e){
                                e.printStackTrace();
                            }
                        }
                    });



            }else {
                Toast.makeText(EditUserActivity.this, "Update is fail", Toast.LENGTH_LONG).show();

            }
            }
        });



        //---------------------------End update user-----------------------------




    }

    /*
* Set error message
* */
    public static void showMsgError(TextInputLayout textInputLayout, EditText editText, String errText){
        textInputLayout.setError(errText);
        errorStyle(editText, Color.RED);
    }
    /*
    * Hide error message
    * */
    public static void hideMsgError(TextInputLayout textInputLayout, EditText editText, int color){
        textInputLayout.setError("");
        editText.setTextColor(Color.BLACK);
        errorStyle(editText, color);
    }
    /*Text message error style of Edited text */
    public static void errorStyle(EditText edt, int color){
        Drawable drawable = edt.getBackground(); // get current EditText drawable
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP); // change the drawable color

        if(Build.VERSION.SDK_INT > 16) {
            edt.setBackground(drawable); // set the new drawable to EditText
        }else{
            edt.setBackgroundDrawable(drawable); // use setBackgroundDrawable because setBackground required API 16
        }
    }
    //Is valid email
    public  boolean emailValidator(final String mailAddress) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();

    }


    //===========Back method==========
    public void onBackPressed()
    {
        super.onBackPressed();  // optional depending on your needs
    }




}
