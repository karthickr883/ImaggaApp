package com.example.karthick.imaggaapi;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {
    String baseUrl = "https://api.imagga.com/v2/";
    ArrayList<String> urls = new ArrayList<String>();
    TextView text1, text2, text3, text4;
    ImageView image;
    int count = 4;
    String[] arr = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        image = findViewById(R.id.fetchedImg);

        text1 = (TextView) findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);

        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("url");
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build();
        Uri uri = Uri.parse(bundle.getString("uri"));
        image.setImageURI(uri);

        imaggaInterface imaggaInterface = retrofit.create(com.example.karthick.imaggaapi.imaggaInterface.class);
        Call<Example> call1 = imaggaInterface.getRp(url);

        call1.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                Log.d("RESPONSE", response.body().toString() + " ");


                Toast.makeText(SecondActivity.this, String.valueOf(response.isSuccessful()), Toast.LENGTH_LONG).show();
                List<Tag> tags = response.body().getResult().getTags();
                for (int i = 0; i < 4; i++) {
                    arr[i] = tags.get(i).getTag().getEn();
                    Log.d("CHECKING", tags.get(i).getTag().getEn() + "\n");
                }
                text2.setText(arr[0]);
                text1.setText(arr[1]);
                text3.setText(arr[2]);
                text4.setText(arr[3]);

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });


        if (count == 0) {
            Toast.makeText(SecondActivity.this, "U are wrong exiting the game", Toast.LENGTH_LONG).show();
            finish();
        }

        //  Log.d("$ Strings", arr[0] + "HAAHA");

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
                builder.setMessage("Congrats u have won");
                builder.setPositiveButton("Start Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setNegativeButton("Exit the app ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory(Intent.CATEGORY_HOME);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SecondActivity.this, "Wrong", Toast.LENGTH_LONG).show();
                count--;
            }
        });
        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SecondActivity.this, "Wrong", Toast.LENGTH_LONG).show();
                count--;

            }
        });
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SecondActivity.this, "Wrong", Toast.LENGTH_LONG).show();
                count--;

            }
        });
    }
}
