package br.com.buildin.attendance.service;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.List;

import br.com.buildin.attendance.model.FinishSessionForm;
import br.com.buildin.attendance.model.UserResponse;

/**
 * Created by samuelferreira on 29/12/16.
 */

public class AttendanceService {
    private static final String SERVICE_ENDPOINT = "http://localhost:8000";

    public static void loginOrCreateUser(String name, String password) {
        AndroidNetworking.post(SERVICE_ENDPOINT + "/seller/create")
                .addBodyParameter("name", "Afonso Jorge")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public static void addUserToQueue(String name) {
        AndroidNetworking.post(SERVICE_ENDPOINT + "/queue/add")
                .addBodyParameter("name", "Afonso Jorge")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public static void finishSession(FinishSessionForm form) {
        JSONObject jsonObject = new JSONObject(form);
        // TODO
        AndroidNetworking.post(SERVICE_ENDPOINT + "/queue/remove")
                .addJSONObjectBody(new JSONObject(form))
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public static void logoutUser(String name) {
        // TODO
    }

    public static List<UserResponse> listQueueUsers() {

        AndroidNetworking.get(SERVICE_ENDPOINT + "/sellers")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

        return null;
    }
}