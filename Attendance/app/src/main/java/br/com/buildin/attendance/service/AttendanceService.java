package br.com.buildin.attendance.service;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import br.com.buildin.attendance.model.FinishSessionForm;

/**
 * Created by samuelferreira on 29/12/16.
 */

public class AttendanceService {
    private static final String SERVICE_ENDPOINT = "http://localhost:8000";

    public static void loginOrCreateUser(String name, String password) {
        AndroidNetworking.post(SERVICE_ENDPOINT + "/seller/create")
                .addBodyParameter("firstname", "Amit")
                .addBodyParameter("lastname", "Shekhar")
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
        // TODO
    }

    public static void finishSession(FinishSessionForm form) {
        // TODO
    }

    public static void logoutUser(String name) {
        // TODO
    }
}
