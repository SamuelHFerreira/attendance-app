package br.com.buildin.attendance;

import android.content.Intent;
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

        final List<ActiveUser> activeUsers = new ArrayList<>();

        if (savedInstanceState == null) {

//            final ArrayList<ActiveUser> testArray = new ArrayList<>(
//                    Arrays.asList(new ActiveUser("Junior 1", System.currentTimeMillis(), 1l),
//                            new ActiveUser("Junior 2", System.currentTimeMillis(), 2l),
//                            new ActiveUser("johny", System.currentTimeMillis(), 3l)));

            final ListView listview = (ListView) findViewById(R.id.active_user_list);
            final ActiveUserAdapter adapter = new ActiveUserAdapter(getApplicationContext(), activeUsers);

            AttendanceService.instance(this).listQueueUsers(listview, activeUsers, adapter);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activeUsers.size() >= 10)
                    Snackbar.make(view, "Número máximo de usuários atingido", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                else {
                    Intent loginActivity = new Intent(DashboardActivity.this, LoginActivity.class);
                    startActivity(loginActivity);
                }
            }
        });
    }
}