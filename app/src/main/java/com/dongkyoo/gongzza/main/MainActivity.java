package com.dongkyoo.gongzza.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import com.dongkyoo.gongzza.MockData;
import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.all.AllFragment;
import com.dongkyoo.gongzza.board.BoardFragment;
import com.dongkyoo.gongzza.chat.chattingRoomList.ChattingRoomListFragment;
import com.dongkyoo.gongzza.course.CourseFragment;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.TokenApi;
import com.dongkyoo.gongzza.vos.ChatLog;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.Token;
import com.dongkyoo.gongzza.vos.User;
import com.google.android.material.tabs.TabLayout;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final int CODE_INSERT_COURSE = 100;
    private static final String TAG = "MainActivity";
    private MainTabAdapter adapter;
    private User me;

    private CourseFragment courseFragment;
    private ChattingRoomListFragment chattingRoomListFragment;

    private BroadcastReceiver chatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String senderId = intent.getStringExtra(Config.USER);
            String message = intent.getStringExtra(Config.MESSAGE);
            int postId = intent.getIntExtra(Config.POST, -1);
            int chatId = intent.getIntExtra(Config.CHAT_ID, -1);

            if (chattingRoomListFragment != null) {
                chattingRoomListFragment.receiveChat(new ChatLog(
                        chatId, postId, senderId, message, new Date()
                ));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //화면 전환 프래그먼트 선언 및 초기 화면 설정

        //화면 전환 프래그먼트 선언 및 초기 화면 설정

        Networks.createRetrofit(this);

        me = getIntent().getParcelableExtra(Config.USER);


        //registerToken();


        registerToken();

        courseFragment = CourseFragment.newInstance(me);
        chattingRoomListFragment = ChattingRoomListFragment.newInstance(me);


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        adapter = new MainTabAdapter(getSupportFragmentManager(),
                new Fragment[] {
                        courseFragment,
                        BoardFragment.newInstance(me),
                        chattingRoomListFragment,
                        new AllFragment()
                },
                getResources().getStringArray(R.array.tab_name_list)
        );

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager, true);
    }



    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment).commit();
    }
     /**
     * FCM(Firebase Cloud Message) 토큰 등록해주는 메소드, 푸시알림을 받으려면 토큰을 등록해줘야함
     */
    private void registerToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean tokenHasSent = sharedPreferences.getBoolean(Config.HAS_SENT, true);
        if (!tokenHasSent) {
            String token = sharedPreferences.getString(Config.TOKEN, null);
            if (token != null) {
                TokenApi tokenApi = Networks.retrofit.create(TokenApi.class);
                Call<Token> call = tokenApi.registerToken(new Token(me.getId(), token, new Date()));
                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.code() == 200) {
                            Log.i(TAG, "토큰 등록 성공!");
                            sharedPreferences.edit().putBoolean(Config.HAS_SENT, true).apply();
                        } else {
                            Log.e(TAG, "토큰 등록 실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Log.e(TAG, "토큰 등록 실패", t);
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_INSERT_COURSE) {
            courseFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(chatReceiver, new IntentFilter(getString(R.string.receive_message)));
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(chatReceiver);
    }
}
