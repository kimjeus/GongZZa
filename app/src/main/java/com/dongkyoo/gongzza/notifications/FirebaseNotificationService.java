package com.dongkyoo.gongzza.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;

import com.dongkyoo.gongzza.MockData;
import com.dongkyoo.gongzza.R;
import com.dongkyoo.gongzza.chat.chattingRoom.ChatActivity;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.TokenApi;
import com.dongkyoo.gongzza.vos.Config;
import com.dongkyoo.gongzza.vos.Token;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 작성자 : 이동규
 * Firebase 푸시알림을 받기 위한 서비스
 */
public class FirebaseNotificationService extends FirebaseMessagingService {

    public static final String DEFAULT_CHANNEL_ID = "Default";
    private static final String TAG = "FbNotificationService";

    private static int getUniqueId() {
        return UUID.randomUUID().hashCode();
    }

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

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        int postId = Integer.parseInt(remoteMessage.getData().get("POST"));
        String senderId = remoteMessage.getData().get("SENDER");
        int id = Integer.parseInt(remoteMessage.getData().get("ID"));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String me = sharedPreferences.getString(Config.USER_ID, "");

        if (senderId.equals(me)) {
            return;
        }

        Intent intent = new Intent(getString(R.string.receive_message));
        intent.putExtra(Config.MESSAGE, message);
        intent.putExtra(Config.POST, postId);
        intent.putExtra(Config.USER, senderId);
        intent.putExtra(Config.CHAT_ID, id);
        sendBroadcast(intent);


        createNotificationChannel();

        intent = new Intent(getApplicationContext(), ChatActivity.class);
        intent.putExtra(Config.POST, postId);
        intent.putExtra(Config.USER, senderId);
        intent.putExtra(Config.CHAT_ID, id);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent , 0);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), DEFAULT_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManagerCompat.from(getApplicationContext()).notify(getUniqueId(), notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(DEFAULT_CHANNEL_ID, "기본",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("기본알림채널");
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
