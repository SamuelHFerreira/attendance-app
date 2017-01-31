package br.com.buildin.attendance.service;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by samuelferreira on 30/01/17.
 */

public class RestInterceptor implements Interceptor {
    private static final String TAG = "RestInterceptor";

    public Response intercept(Chain chain) throws IOException {
        Log.v(TAG, "receiving call: "+ chain.toString());
        return chain.proceed(chain.request());
    }
}
