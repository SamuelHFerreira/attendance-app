package br.com.buildin.attendance.service;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import br.com.buildin.attendance.MainActivity;
import br.com.buildin.attendance.adapter.ActiveUserAdapter;
import br.com.buildin.attendance.model.ActiveUser;
import br.com.buildin.attendance.model.FinishSessionForm;
import br.com.buildin.attendance.model.UserResponse;
import br.com.buildin.attendance.service.body.LoginBody;
import br.com.buildin.attendance.service.body.QueueBody;
import br.com.buildin.attendance.service.body.StartQueueBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by samuelferreira on 29/12/16.
 */
public class AttendanceService {

    private Context workingContext;
    private KProgressHUD workingHud;
    private QueueService service;

    private static final String LOG = "AttendanceService";

    private AttendanceService(Context coworkingContextntext) {
        this.workingContext = coworkingContextntext;
        this.service = ApiRetrofitClient.getClient().create(QueueService.class);
    }

    public static AttendanceService instance(Context context) {
        return new AttendanceService(context);
    }

    public boolean loginOrCreateUser(String name, String password) {
        Call<Void> call = this.service.createOrLogin(new LoginBody(name, password));
        try {
            Response<Void> response = call.execute();
            if (response.body() != null)
                Log.v(LOG, "sucess login:" + response.body().toString());
            return response.isSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void startAttendance(Long attendanceId, final ActiveUserAdapter adapter, final View view, final Integer positionItem) {
        Call<Void> call = this.service.startAttendance(new StartQueueBody(attendanceId));
        Callback<Void> callback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.body() != null) {
                    Log.v(LOG, "sucess started attendance:" + response.body().toString());
                }
                adapter.getItem(positionItem).setOnAttendance(true);
                adapter.swichStartButtonBackground(view, positionItem);
                stopDefaultLoading();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //Handle failure
                Log.v(LOG, "error: " + t.toString());
                stopDefaultLoading();
            }

        };
        createDefaultLoading();
        call.enqueue(callback);
    }

    public void addUserToQueue(Long sellerId) {
        Call<Void> call = this.service.addUserToQueue(new QueueBody(sellerId));
        Callback<Void> callback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.body() != null) {
                    Log.v(LOG, "sucess added user to queue:" + response.body().toString());
                }
                stopDefaultLoading();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //Handle failure
                stopDefaultLoading();
            }

        };
        createDefaultLoading();
        call.enqueue(callback);
    }

    public void finishSession(FinishSessionForm form) {
        Call<Void> call = this.service.removeUserOfQueue(form.getDescription());
        Callback<Void> callback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.v(LOG, "sucess finish:");
                }
                stopDefaultLoading();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //Handle failure
                stopDefaultLoading();
            }

        };
        createDefaultLoading();
        call.enqueue(callback);
    }

    public void logoutUser(Long sellerId) {
        Call<Void> call = this.service.removeUserOfQueue(sellerId.toString());
        Callback<Void> callback = new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.v(LOG, "sucess on remove");
                }
                stopDefaultLoading();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                //Handle failure
                stopDefaultLoading();
            }

        };
        createDefaultLoading();
        call.enqueue(callback);

    }

    public void listQueueUsers(final ListView listview, final List<ActiveUser> activeUsers, final ActiveUserAdapter adapter) {
        Call<List<UserResponse>> call = this.service.listQueuedUsers();
        Callback<List<UserResponse>> callback = new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                if (response.body() != null)
                    for (UserResponse userResponse : response.body()) {
                        activeUsers.add(new ActiveUser(userResponse.getName(), System.currentTimeMillis(), userResponse.getId(), false));
                    }
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
                        final String item = (String) parent.getItemAtPosition(position);
                        view.animate().setDuration(2000).alpha(0)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        activeUsers.remove(item);
                                        adapter.notifyDataSetChanged();
                                        view.setAlpha(1);
                                    }
                                });
                    }
                });
                stopDefaultLoading();
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                //Handle failure
                stopDefaultLoading();
            }

        };
        createDefaultLoading();
        call.enqueue(callback);
    }

    private void createDefaultLoading() {
        workingHud = KProgressHUD.create(workingContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    private void stopDefaultLoading() {
        workingHud.dismiss();
    }
}