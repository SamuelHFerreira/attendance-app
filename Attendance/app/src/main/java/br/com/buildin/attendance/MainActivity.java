package br.com.buildin.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineFlow();
    }

    private void defineFlow() {
//        Intent dashboard = new Intent(MainActivity.this, DashboardActivity.class);
//        startActivity(dashboard);
        Intent dashboard = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(dashboard);
    }
}
