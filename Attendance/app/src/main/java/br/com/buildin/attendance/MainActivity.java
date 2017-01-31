package br.com.buildin.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.jacksonandroidnetworking.JacksonParserFactory;

import br.com.buildin.attendance.service.RestInterceptor;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OkHttpClient okHttpClient = new OkHttpClient() .newBuilder()
                .addNetworkInterceptor(new RestInterceptor())
                .build();
        AndroidNetworking.initialize(getApplicationContext(), okHttpClient);
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
        defineFlow();
    }

    private void defineFlow() {
        Intent dashboard = new Intent(MainActivity.this, DashboardActivity.class);
        startActivity(dashboard);
//        Intent dashboard = new Intent(MainActivity.this, LoginActivity.class);
//        startActivity(dashboard);
    }
}
