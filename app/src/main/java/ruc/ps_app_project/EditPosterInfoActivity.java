package ruc.ps_app_project;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditPosterInfoActivity extends AppCompatActivity {
    EditText seller_name, seller_mail, seller_phone, seller_add;
    String userLoginID;
    TextView btnUpdate,back_update;
    TextInputLayout TextInputConfirmPhone, TextInputAdd, TextInputUsername, TextInputEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_poster_info);

        seller_name = (EditText)findViewById(R.id.seller_name);
        seller_mail = (EditText)findViewById(R.id.seller_email);
        seller_phone = (EditText)findViewById(R.id.seller_phone);
        seller_add = (EditText)findViewById(R.id.seller_add);

        //-------------------Back--------------------------
        back_update = (TextView)findViewById(R.id.udpate_seller_back);
        back_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}
