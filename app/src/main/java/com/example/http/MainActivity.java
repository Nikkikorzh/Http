package com.example.http;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button button;

    OkHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String urlString = "https://publicobject.com/helloworld.txt";
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlString)
                .build();


        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread newThread = new Thread() {
                    @Override
                    public void run() {
                        try (Response response = client.newCall(request).execute()) {
                            if (!response.isSuccessful()) {
                                throw new IOException("Запрос к серверу не был успешен: " +
                                        response.code() + " " + response.message());
                            }
                            // пример получения конкретного заголовка ответа
                            Log.d("test",response.toString());
                            // вывод тела ответа
                            Log.d("test",response.body().string());
                        } catch (IOException e) {
                            Log.d("test","Ошибка подключения: " + e);
                        }

                    }
                };
                newThread.start();
            }
        });





    }
}