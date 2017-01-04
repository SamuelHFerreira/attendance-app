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

import br.com.buildin.attendance.adapter.ActiveUserAdapter;
import br.com.buildin.attendance.model.ActiveUser;

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

            final ArrayList<ActiveUser> testArray = new ArrayList<>(
                    Arrays.asList(new ActiveUser("Junior 1", System.currentTimeMillis()),
                                  new ActiveUser("Junior 2", System.currentTimeMillis()),
                                  new ActiveUser("johny", System.currentTimeMillis())));

            final ListView listview = (ListView) findViewById(R.id.active_user_list);

            final ActiveUserAdapter adapter = new ActiveUserAdapter(this, testArray);

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
                                    testArray.remove(item);
                                    adapter.notifyDataSetChanged();
                                    view.setAlpha(1);
                                }
                            });
                }
            });
        }
    }
}
