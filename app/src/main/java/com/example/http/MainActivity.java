package com.example.http;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    Button button;

    OkHttpClient client;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.getButton);
        String urlString = "https://publicobject.com/helloworld.txt";
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlString)
                .build();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful()) {
                                throw new IOException("Запрос к серверу не был успешен: " +
                                        response.code() + " " + response.message());
                            }

                            // пример получения всех заголовков ответа
                            Headers responseHeaders = response.headers();
                            for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                                // вывод заголовков
                                Log.d("test",responseHeaders.name(i) + ": "
                                        + responseHeaders.value(i));
                            }
                            // вывод тела ответа
                            Log.d("test",responseBody.string());
                        }
                    }
                });
            }
        });
    }
}