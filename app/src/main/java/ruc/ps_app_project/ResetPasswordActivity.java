package ruc.ps_app_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText resetPass,conFirmPass,verifyCode;
    Intent intent;
    String email,newPass,conPass;
    Button resetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        resetPass = (EditText)findViewById(R.id.reset_new_pass);
        conFirmPass= (EditText)findViewById(R.id.reset_con_pass);
        verifyCode = (EditText)findViewById(R.id.con_code);

        resetPassword = (Button)findViewById(R.id.btn_reset_pass);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPass = resetPass.getText().toString();
                conPass = conFirmPass.getText().toString();

                Log.i("first",newPass);
                Log.i("second",conPass);

                if(newPass.equals(conPass)){
                    intent = getIntent();
                    email = intent.getStringExtra("extraEmail");
                    Log.i("email",email);

                    RequestParams requestParams = new RequestParams();
                    requestParams.add("email",String.valueOf(email));
                    requestParams.add("confirmcode",String.valueOf(verifyCode.getText().toString()));
                    requestParams.add("password",String.valueOf(conFirmPass.getText().toString()));

                    AsyncHttpClient resetPass = new AsyncHttpClient();
                    resetPass.post("http://192.168.1.22:2222/users/resetForgotPass", requestParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                String data = new String(responseBody, "UTF-8");
                                Log.i("data",data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    String sms = jsonObject.getString("status");
                                    Log.i("message", sms);
                                    if (sms.equals("success")){
                                        Intent intent = new Intent(ResetPasswordActivity.this, Login.class);
                                        startActivity(intent);
                                        Toast.makeText(ResetPasswordActivity.this,"changed!",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(ResetPasswordActivity.this,"not change!!",Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                    Toast.makeText(ResetPasswordActivity.this,"Match!!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ResetPasswordActivity.this,"Password Not Match!",Toast.LENGTH_SHORT).show();
                }
//
            }
        });
    }
}
