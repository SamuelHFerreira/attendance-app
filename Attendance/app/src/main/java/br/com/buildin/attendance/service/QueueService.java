package br.com.buildin.attendance.service;

import java.util.List;

import br.com.buildin.attendance.model.FinishSessionForm;
import br.com.buildin.attendance.model.UserResponse;
import br.com.buildin.attendance.service.body.LoginBody;
import br.com.buildin.attendance.service.body.StartQueueBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by samuelferreira on 15/03/17.
 */

public interface QueueService {

    @GET("sellers")
    Call<List<UserResponse>> listQueuedUsers();

    @POST("seller/create")
    Call<Void> createOrLogin(@Body LoginBody loginBody);

    @POST("start-attendance")
    Call<Void> startAttendance(@Body StartQueueBody attendanceId);

    @POST("queue/add")
    Call<Void> addUserToQueue(String name);

    @DELETE("queue")
    Call<Void> removeUserOfQueue(Long attendanceId);

    @POST("close-attendance")
    Call<Void> finishSession(FinishSessionForm form);

    @GET("seller/to-leave/{attendanceId}")
    Call<Void> pauseAttendanceQueue(@Path("attendanceId") Long attendanceId);

//    private static final String ATTENDANCE_DETAIL_ENDPOINT = "/attendance/by-user";
}