package com.example.rohan.projectmajor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_password);
        editText=findViewById(R.id.pass);
        button=findViewById(R.id.adminpass);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= editText.getText().toString();
                if (s.equals("qwerty"))
                {
                    startActivity(new Intent(PasswordActivity.this, AdminMap.class));
                }
                else
                {
                    editText.setText("");
                    //Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    Toast.makeText(PasswordActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
