package br.com.buildin.attendance.service;

import android.view.View;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.List;

import br.com.buildin.attendance.model.FinishSessionForm;
import br.com.buildin.attendance.model.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by samuelferreira on 29/12/16.
 */

public class AttendanceService {
    private static final String LOG = "AttendanceService";
    public static final String SERVICE_ENDPOINT = "http://horadavez.com.br:80";

    private static final String CREATE_USER_ENDPOINT = "/seller/create";
    private static final String QUEUE_ADD_ENDPOINT = "/queue/add";
    private static final String FINISH_ATTENDANCE_ENDPOINT = "/close-attendance";
    private static final String SELLERS_ENDPOINT = "/sellers";
    private static final String START_ATTENDANCE_ENDPOINT = "/start-attendance";
    private static final String ATTENDANCE_DETAIL_ENDPOINT = "/attendance/by-user";

    public static void loginOrCreateUser(String name, String password) {
//        AndroidNetworking.post(SERVICE_ENDPOINT + CREATE_USER_ENDPOINT)
//                .addBodyParameter("name", "Afonso Jorge")
//                .setTag("test")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.v(LOG, "sucess login:" + response.toString());
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        Log.v(LOG, "error: " + error.toString());
//                    }
//                });
    }

    public static void startAttendance(Long attendanceId) {
//        AndroidNetworking.post(SERVICE_ENDPOINT + START_ATTENDANCE_ENDPOINT)
//                .addBodyParameter("id", attendanceId.toString())
//                .setTag("test")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.v(LOG, "sucess started attendance:" + response.toString());
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        Log.v(LOG, "error: " + error.toString());
//                    }
//                });
    }

    public static void addUserToQueue(String name) {
//        AndroidNetworking.post(SERVICE_ENDPOINT + QUEUE_ADD_ENDPOINT)
//                .addBodyParameter("name", "Afonso Jorge")
//                .setTag("test")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//                        Log.v(LOG, "sucess added user to queue:" + response.toString());
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                        Log.v(LOG, "error: " + error.toString());
//                    }
//                });
    }

    public static void finishSession(FinishSessionForm form) {
        JSONObject jsonObject = objectToJSONObject(form);

        // TODO
//        AndroidNetworking.post(SERVICE_ENDPOINT + FINISH_ATTENDANCE_ENDPOINT)
//                .addJSONObjectBody(jsonObject)
//                .setTag("test")
//                .addJSONObjectBody(jsonObject)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//                        Log.v(LOG, "sucess finish:" + response.toString());
//                    }
//
//                    @Override
//                    public void onError(ANError error) {
//                        Log.v(LOG, "error finish: " + error.toString());
//                    }
//                });
    }

    public static void logoutUser(String name) {
        // TODO
    }

    public static List<UserResponse> listQueueUsers(View view) {
//        QueueService service = ApiRetrofitClient.getClient().create(QueueService.class);
//        List<UserResponse> responseResult = null;
//
//        Call<List<UserResponse>> call = service.listQueuedUsers();
//        Callback<List<UserResponse>> callback = new Callback<List<UserResponse>>() {
//            @Override
//            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
//                responseResult = response.body();
//
//            }
//
//            @Override
//            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
//                //Handle failure
//            }
//        };
//        call.enqueue(callback);

    return null;


//        AndroidNetworking.get(SERVICE_ENDPOINT + SELLERS_ENDPOINT)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONArray(new JSONArrayRequestListener() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // do anything with response
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                    }
//                });



//        return null;
    }

    public static JSONObject objectToJSONObject(Object object) {
        JSONObject result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = new JSONObject(mapper.writeValueAsString(object));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }
}