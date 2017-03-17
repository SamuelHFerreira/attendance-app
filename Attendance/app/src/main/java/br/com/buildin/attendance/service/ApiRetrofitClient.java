package br.com.buildin.attendance.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by samuelferreira on 16/03/17.
 */
public class ApiRetrofitClient {
    private static final String DEFAULT_ENDPOINT = "http://horadavez.com.br:80";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(DEFAULT_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;
    }
}
