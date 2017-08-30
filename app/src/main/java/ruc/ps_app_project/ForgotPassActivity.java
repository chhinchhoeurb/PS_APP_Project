package ruc.ps_app_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ForgotPassActivity extends AppCompatActivity {
    Button btn_continue;
    EditText email;
    String extraEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        email = (EditText)findViewById(R.id.for_email);

        btn_continue = (Button)findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams requestParams = new RequestParams();
                requestParams.add("email",String.valueOf(email.getText().toString()));
                extraEmail = email.getText().toString();
                    AsyncHttpClient getEmail = new AsyncHttpClient();
                    getEmail.post("http://192.168.1.22:2222/users/sendMail", requestParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                String data = new String(responseBody, "UTF-8");
                                System.out.println(data);
                                try {
                                    JSONObject objectData = new JSONObject(data);
                                    String checkStatus = objectData.getString("status");
//                                    System.out.println(email);
                                   if (checkStatus.equals("success")){
//                                       System.out.println(email);
//                                       System.out.println(data);
                                       Toast.makeText(ForgotPassActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(ForgotPassActivity.this, ResetPasswordActivity.class);
                                       intent.putExtra("extraEmail",extraEmail);
                                       startActivity(intent);
                                   }else{
                                       Toast.makeText(ForgotPassActivity.this,"Not Success!",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ForgotPassActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                        }
                    });
//                }else{
//                    Toast.makeText(ForgotPassActivity.this,"Please check your E-mail again!",Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }
}
