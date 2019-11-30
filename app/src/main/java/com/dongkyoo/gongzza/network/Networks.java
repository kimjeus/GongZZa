package com.dongkyoo.gongzza.network;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Networks {

    public static String SERVER_URL = "http://n09app.cafe24.com/gongzza/";
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(client)
            .addConverterFactory(
                    JacksonConverterFactory.create(
//                            new ObjectMapper().setDateFormat(
//                                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssSSS")
//                            )
                    )
            )
            .build();

    static {
        try {
            InputStream is = new FileInputStream("src/main/assets/server.properties");
            Properties properties = new Properties();
            properties.load(is);
            SERVER_URL = properties.getProperty("serverUrl");
            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .client(client)
                    .addConverterFactory(
                            JacksonConverterFactory.create(
//                                    new ObjectMapper().setDateFormat(
//                                            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssSSS")
//                                    )
                            )
                    )
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Retrofit createRetrofit(Context context) {
        try {
            InputStream is = context.getAssets().open("server.properties");
            Properties properties = new Properties();
            properties.load(is);
            SERVER_URL = properties.getProperty("serverUrl");
            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .client(client)
                    .addConverterFactory(
                            JacksonConverterFactory.create(
//                                    new ObjectMapper().setDateFormat(
//                                            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssSSS")
//                                    )
                            )
                    )
                    .build();
            return retrofit;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
