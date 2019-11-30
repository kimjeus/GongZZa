package com.dongkyoo.gongzza.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dongkyoo.gongzza.R;

public class FindPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find_password);

        findViewById(R.id.signupedButton).setOnClickListener(new View.OnClickListener(){
            EditText findPasswordEtName = (EditText)findViewById(R.id.inputName);
            EditText findPasswordEtId = (EditText)findViewById((R.id.inputId));

            @Override
            public void onClick(View v) {
                if(findPasswordEtId.getText().toString().length() == 0){
                    Toast.makeText(FindPasswordActivity.this, "Email을 입력하세요",Toast.LENGTH_SHORT).show();
                    findPasswordEtId.requestFocus();
                    return;
                }
                else if(findPasswordEtName.getText().toString().length() == 0){
                    Toast.makeText(FindPasswordActivity.this, "이름을 입력하세요",Toast.LENGTH_SHORT).show();
                    findPasswordEtName.requestFocus();
                    return;
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
