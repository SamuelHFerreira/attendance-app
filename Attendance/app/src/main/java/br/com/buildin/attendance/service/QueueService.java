package br.com.buildin.attendance.service;

import java.util.List;

import br.com.buildin.attendance.model.FinishSessionForm;
import br.com.buildin.attendance.model.UserResponse;
import br.com.buildin.attendance.service.body.QueueBody;
import br.com.buildin.attendance.service.body.LoginBody;
import br.com.buildin.attendance.service.body.StartQueueBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by samuelferreira on 15/03/17.
 */

public interface QueueService {

    @GET("seller")
    Call<List<UserResponse>> listQueuedUsers();

    @POST("seller")
    Call<Void> createOrLogin(@Body LoginBody loginBody);

    @PUT("seller/{sellerId}")
    Call<Void> updateSeller(@Path("sellerId") String sellerId, @Body LoginBody loginBody);

    // TODO get queue data

    @POST("queue/seller")
    Call<Void> addUserToQueue(@Body QueueBody queueBody);

    @DELETE("queue/seller/{sellerId}")
    Call<Void> removeUserOfQueue(@Path("sellerId") String sellerId);

    @POST("attendance")
    Call<Void> startAttendance(@Body StartQueueBody attendanceId);

    // body un...
//    @DELETE("attendance")
//    Call<Void> finishSession(@Body FinishSessionForm form);

    @DELETE("attendance/{attendanceId}")
    Call<Void> logout(@Path("attendanceId") Long attendanceId);

}