package br.com.buildin.attendance;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.buildin.attendance.adapter.ActiveUserAdapter;
import br.com.buildin.attendance.model.ActiveUser;
import br.com.buildin.attendance.model.UserResponse;
import br.com.buildin.attendance.service.ApiRetrofitClient;
import br.com.buildin.attendance.service.AttendanceService;
import br.com.buildin.attendance.service.QueueService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO if max user reached
                Snackbar.make(view, "Número máximo de usuários atingido", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (savedInstanceState == null) {

//            final ArrayList<ActiveUser> testArray = new ArrayList<>(
//                    Arrays.asList(new ActiveUser("Junior 1", System.currentTimeMillis(), 1l),
//                            new ActiveUser("Junior 2", System.currentTimeMillis(), 2l),
//                            new ActiveUser("johny", System.currentTimeMillis(), 3l)));

            final List<ActiveUser> activeUsers = new ArrayList<>();
            QueueService service = ApiRetrofitClient.getClient().create(QueueService.class);

            Call<List<UserResponse>> call = service.listQueuedUsers();
            Callback<List<UserResponse>> callback = new Callback<List<UserResponse>>() {
                @Override
                public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                    if (response.body() != null)
                        for (UserResponse userResponse: response.body()) {
                            activeUsers.add(new ActiveUser(userResponse.getName(), System.currentTimeMillis(), userResponse.getId()));
                        }
                    final ListView listview = (ListView) findViewById(R.id.active_user_list);

                    final ActiveUserAdapter adapter = new ActiveUserAdapter(getApplicationContext(), activeUsers);

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
                }

                @Override
                public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                    //Handle failure
                }
            };
            call.enqueue(callback);






        }
    }
}
