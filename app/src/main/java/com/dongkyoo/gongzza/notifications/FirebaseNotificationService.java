package com.dongkyoo.gongzza.notifications;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.dongkyoo.gongzza.MockData;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.TokenApi;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.Token;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 작성자 : 이동규
 * Firebase 푸시알림을 받기 위한 서비스
 */
public class FirebaseNotificationService extends FirebaseMessagingService {

    private static final String TAG = "FbNotificationService";

    public FirebaseNotificationService() {
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        // 새 토큰 저장
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String previousToken = sharedPreferences.getString(Config.TOKEN, null);
        if (previousToken == null || !previousToken.equals(s)) {
            editor.putString(Config.TOKEN, s);
            editor.putBoolean(Config.HAS_SENT, false);
        }

        editor.apply();

        String userId = sharedPreferences.getString(Config.USER_ID, null);
        if (userId != null) {
            TokenApi tokenApi = Networks.retrofit.create(TokenApi.class);
            Call<Token> call = tokenApi.registerToken(new Token(userId, s, new Date()));
            call.enqueue(new Callback<Token>() {
                @Override
                public void onResponse(Call<Token> call, Response<Token> response) {
                    if (response.code() == 200) {
                        Log.i(TAG, "토큰 등록 성공!");
                        editor.putBoolean(Config.HAS_SENT, true);
                        editor.apply();
                    } else {
                        Log.i(TAG, "토큰 등록 실패");
                    }
                }

                @Override
                public void onFailure(Call<Token> call, Throwable t) {
                    Log.i(TAG, "토큰 등록 실패", t);
                }
            });
        }
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


    }
}
