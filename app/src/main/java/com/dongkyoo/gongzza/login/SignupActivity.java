package com.dongkyoo.gongzza.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.UserApi;
import com.dongkyoo.gongzza.vos.AuthMail;
import com.dongkyoo.gongzza.vos.User;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    UserApi userApi = Networks.retrofit.create(UserApi.class);

    DatePicker Birth;
    private  String birthString;
    private Date birthDate;
    private  boolean isChanged = false;
    private  boolean isComplete = false;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_signup);

        Networks.createRetrofit(this);

        //객체성성
        User user = new User();
        AuthMail authMail = new AuthMail();

        EditText nameEditText = (EditText)findViewById(R.id.inputName);
        EditText emailEditText = (EditText)findViewById((R.id.inputEmailCheck));
        EditText passwordEditText = (EditText)findViewById((R.id.inputPassword));
        EditText pwConfirmEditText = (EditText)findViewById((R.id.inputPasswordConfirm));
        EditText idEditText = (EditText)findViewById(R.id.inputId);

        Birth = (DatePicker)findViewById(R.id.inputBirth);
        //현재날짜로 초기화
        Birth.init(Birth.getYear(), Birth.getMonth(), Birth.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                birthString = String.format("%d-%d-%d", Birth.getYear(), Birth.getMonth() + 1, Birth.getDayOfMonth());
                birthDate = Date.valueOf(birthString);
                isChanged = true;
            }
        });

        findViewById(R.id.emailConfirmButton).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                builder.setTitle("인증 이메일이 발송되었습니다!");
                builder.setMessage("등록된 이메일로 접속하여 메일을 확인하셔야 가입이 완료됩니다.");
                builder.setPositiveButton("확인",null);

                AlertDialog alertDialog = builder.create();

                String id = idEditText.getText().toString();
                String email = emailEditText.getText().toString();

                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(email))
                    return;

                //서버와 통신
                Call<AuthMail> call = userApi.sendAuthenticateEmail(id, email);
                call.enqueue(new Callback<AuthMail>() {

                    @Override
                    public void onResponse(Call<AuthMail> call, Response<AuthMail> response) {
                        if (response.isSuccessful()) {
                            alertDialog.show();
                        }
                        else {
                            Toast.makeText(SignupActivity.this, "이미 인증된 이메일입니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<AuthMail> call, Throwable t) {
                        Toast.makeText(SignupActivity.this, "서버와 통신에 실패하였습니다. 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "이메일 전송 실패", t);
                    }
                });
            }
        });

        findViewById(R.id.signupedButton).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String pwConfirm = pwConfirmEditText.getText().toString();
                String id = idEditText.getText().toString();

                if(TextUtils.isEmpty(name)) {
                    Toast.makeText(SignupActivity.this, "이름을 입력하세요",Toast.LENGTH_SHORT).show();
                    nameEditText.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(SignupActivity.this, "Email을 입력하세요",Toast.LENGTH_SHORT).show();
                    emailEditText.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(id)) {
                    Toast.makeText(SignupActivity.this, "ID를 입력하세요", Toast.LENGTH_SHORT).show();
                    idEditText.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(SignupActivity.this, "Password를 입력하세요",Toast.LENGTH_SHORT).show();
                    passwordEditText.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(pwConfirm)) {
                    Toast.makeText(SignupActivity.this, "Password Confirm을 입력하세요",Toast.LENGTH_SHORT).show();
                    pwConfirmEditText.requestFocus();
                    return;
                }
                else if(!password.equals(pwConfirm)) {
                    Toast.makeText(SignupActivity.this, "비밀번호가 일치하지 않습니다",Toast.LENGTH_SHORT).show();
                    pwConfirmEditText.setText("");
                    pwConfirmEditText.requestFocus();
                    return;
                }

                if (isChanged){
                    user.setBirthday(birthDate);
                    isComplete = true;
                }
                else
                    Toast.makeText(SignupActivity.this, "생년월일을 입력해 주세요.",Toast.LENGTH_SHORT).show();

                if(isComplete){
                    //모든 데이터 입력 했을 시
                    //서버에 데이터 전송
                    Call<User> call = userApi.signUp(new User(
                            id, name, password, birthDate, 1, email
                    ));
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            //@POST("/users")
                            //Call<User> signUp(@Body User user);
                            if (response.code() == 200) {
                                Toast.makeText(SignupActivity.this, "회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(SignupActivity.this, "이메일 인증이 필요합니다",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(SignupActivity.this, "서버와 통신에 실패하였습니다. 다시 시도해 주세요.",Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "가입 실패", t);
                        }
                    });

                }
            }
        });
    }
}
