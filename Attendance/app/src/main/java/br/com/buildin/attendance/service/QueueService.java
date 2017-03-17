package br.com.buildin.attendance.service;

import java.util.List;

import br.com.buildin.attendance.model.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by samuelferreira on 15/03/17.
 */

public interface QueueService {

    @GET("sellers")
    Call<List<UserResponse>> listQueuedUsers();
}
