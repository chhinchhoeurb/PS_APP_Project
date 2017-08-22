package ruc.ps_app_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmCodeActivity extends AppCompatActivity {
    Button btn_continue_reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);

        btn_continue_reset = (Button)findViewById(R.id.btn_continue_reset);
        btn_continue_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmCodeActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
