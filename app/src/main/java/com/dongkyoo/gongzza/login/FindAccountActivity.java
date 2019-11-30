package com.dongkyoo.gongzza.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dongkyoo.gongzza.R;

public class FindAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_find_account);

        findViewById(R.id.signupedButton).setOnClickListener(new View.OnClickListener(){
            EditText findAccountEtName = (EditText)findViewById(R.id.inputName);

            @Override
            public void onClick(View v) {
                if(findAccountEtName.getText().toString().length() == 0){
                    Toast.makeText(FindAccountActivity.this, "이름을 입력하세요",Toast.LENGTH_SHORT).show();
                    findAccountEtName.requestFocus();
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
