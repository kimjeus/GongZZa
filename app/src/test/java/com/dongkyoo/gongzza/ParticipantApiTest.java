package com.dongkyoo.gongzza;

import com.dongkyoo.gongzza.dtos.PostDto;
import com.dongkyoo.gongzza.network.Networks;
import com.dongkyoo.gongzza.network.ParticipantApi;
import com.dongkyoo.gongzza.network.PostApi;
import com.dongkyoo.gongzza.vos.Participant;
import com.dongkyoo.gongzza.vos.Post;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ParticipantApiTest {

    public static Participant insertParticipant() throws Exception {
        ParticipantApi participantApi = Networks.retrofit.create(ParticipantApi.class);
        Call<Participant> call = participantApi.insertParticipant(MockData.getMockParticipant());
        Response<Participant> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        return response.body();
    }

    @Test
    public void deleteParticipant() throws Exception {
        Post post = PostApiTest.insertPost();
        ParticipantApi participantApi = Networks.retrofit.create(ParticipantApi.class);

        Call<Boolean> call = participantApi.deleteParticipant(post.getId(), MockData.getMockUser().getId());
        Response<Boolean> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
        assertTrue(response.body());
    }

    @Test
    public void selectParticipantListByPostId() throws Exception {
        Post post = PostApiTest.insertPost();
        ParticipantApi participantApi = Networks.retrofit.create(ParticipantApi.class);

        Call<List<Participant>> call = participantApi.selectParticipantListByPostId(post.getId());
        Response<List<Participant>> response = call.execute();
        assertEquals(200, response.code());
        assertNotNull(response.body());
    }
}
