package com.dongkyoo.gongzza;

import com.dongkyoo.gongzza.dtos.PostChatDto;
import com.dongkyoo.gongzza.network.ChatLogApi;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.vos.ChatLog;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ChatLogTest {

    @Test
    public void selectChatLogByPostAfterDatetime() throws Exception {
        ChatLogApi chatLogApi = Networks.retrofit.create(ChatLogApi.class);
        Call<List<ChatLog>> call = chatLogApi.selectChatLogByPostAfterDatetime(10,
                Utils.convertDateToString(new Date(0)));
        Response<List<ChatLog>> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
    }

    @Test
    public void sendChat() throws Exception {
        ChatLogApi chatLogApi = Networks.retrofit.create(ChatLogApi.class);
        Call<ChatLog> call = chatLogApi.sendChat(new ChatLog(
                10,
                "testId",
                "test",
                new Date()));
        Response<ChatLog> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
    }

    @Test
    public void selectPostChatListByUserAfterDatetime() throws Exception {
        ChatLogApi chatLogApi = Networks.retrofit.create(ChatLogApi.class);
        Call<List<PostChatDto>> call = chatLogApi.selectPostChatListByUserAfterDatetime("testId",
                Utils.convertDateToString(new Date(0)));
        Response<List<PostChatDto>> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
    }
}
