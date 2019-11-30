package com.dongkyoo.gongzza.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.main.MainActivity;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.UserApi;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.User;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        Networks.createRetrofit(this);
        userApi = Networks.retrofit.create(UserApi.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String id = sharedPreferences.getString(Config.USER_ID, null);
        String pw = sharedPreferences.getString(Config.PASSWORD, null);

        if (id != null && pw != null) {
            Call<User> call = userApi.login(id, pw);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        sharedPreferences.edit().putString(Config.USER_ID, response.body().getId()).apply();
                        sharedPreferences.edit().putString(Config.PASSWORD, pw).apply();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra(Config.USER, response.body());
                        startActivity(intent);
                        finish();
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "로그인 실패", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Snackbar.make(findViewById(android.R.id.content), "로그인 실패", Snackbar.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
        }


        Button signupBtn = (Button)findViewById(R.id.signupButton);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        Button findAccountBtn = (Button)findViewById(R.id.findAccount);
        findAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindAccountActivity.class);
                startActivity(intent);
            }
        });

        Button findPasswordBtn = (Button)findViewById(R.id.findPassword);
        findPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindPasswordActivity.class);
                startActivity(intent);
            }
        });

        EditText idEditText = findViewById(R.id.id_editText);
        EditText passwordEditText = findViewById(R.id.password_editText);

        Button loginBtn = (Button)findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(password)) {
                    return;
                }

                Call<User> call = userApi.login(id, password);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            sharedPreferences.edit().putString(Config.USER_ID, response.body().getId()).apply();
                            sharedPreferences.edit().putString(Config.PASSWORD, password).apply();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra(Config.USER, response.body());
                            startActivity(intent);
                            finish();
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "로그인 실패", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Snackbar.make(findViewById(android.R.id.content), "로그인 실패", Snackbar.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
            }
        });


    }
}
