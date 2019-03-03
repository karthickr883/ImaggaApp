package com.example.karthick.imaggaapi;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button button;
    int count = 0;
    int imgn2 = 1;
    ImageView imageView;
    Button responseButton;
    String baseUrl = "https://api.imagga.com/v2/";
    String apiKey = "acc_f3fd5ad1ac46464";
    String apiSecret = "278d07e53f344acb57321650c8e0193e";
    String sampleUrl = "https://imagga.com/static/images/tagging/wind-farm-538576_640.jpg";
    String path;
    File file;
    MultipartBody.Part body;
    RequestBody name;
    LinearLayout layout;
    StorageReference mReference;
    String pass;
    FirebaseStorage storage;
    FirebaseDatabase database;
    DatabaseReference reference;
    Uri ImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.layout);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("url");
        mReference = storage.getReference().child("photos");
        count++;
//        button = findViewById(R.id.button);
        imageView = findViewById(R.id.gImage);
        responseButton = findViewById(R.id.responseButton);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 1) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    File picturedirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    String picturedirectorypath = picturedirectory.getPath();
                    //              button.setVisibility(View.INVISIBLE);

                    Uri data = Uri.parse(picturedirectorypath);
                    intent.setDataAndType(data, "image/*");
                    layout.setBackgroundColor(Color.parseColor("#2F3E47"));
                    startActivityForResult(intent, imgn2);
                    count++;
                }
            }
        });
        responseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build();

                imaggaInterface imaggaInterface = retrofit.create(com.example.karthick.imaggaapi.imaggaInterface.class);
                Call<Example> call = imaggaInterface.getResponse(apiKey + ":" + apiSecret, sampleUrl);
                Call<Example> call1 = imaggaInterface.getRp(sampleUrl);

                /*call1.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        Toast.makeText(MainActivity.this, String.valueOf(response.isSuccessful()), Toast.LENGTH_LONG).show();
                        List<Tag> tags = response.body().getResult().getTags();
                        for (int i = 0; i < tags.size(); i++) {
                            Log.d("CHECKING", tags.get(i).getTag().getEn() + "\n");
                        }

                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {

                    }
                });

                */

                Call<ImgResponse> call2 = imaggaInterface.getI(body);

                call2.enqueue(new Callback<ImgResponse>() {
                    @Override
                    public void onResponse(Call<ImgResponse> call, Response<ImgResponse> response) {
                        Toast.makeText(MainActivity.this, String.valueOf(response.isSuccessful()), Toast.LENGTH_LONG).show();
                        Log.e("Checking", String.valueOf(response.isSuccessful()));
                        if (response.isSuccessful()) {
                            String id = response.body().getResult().getUploadId();
                            Toast.makeText(MainActivity.this, id, Toast.LENGTH_LONG).show();
                        }

                        //Toast.makeText(MainActivity.this, )
                    }

                    @Override
                    public void onFailure(Call<ImgResponse> call, Throwable t) {

                    }
                });
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("url", pass);
                intent.putExtra("uri", ImageUri.toString());
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == imgn2) {
                Uri imgUri = data.getData();
                ImageUri = imgUri;
                String path1 = imgUri.getPath();
                File tfile = new File(path1);
                final StorageReference ref = mReference.child(imgUri.getLastPathSegment());
                final UploadTask uploadTask = ref.putFile(imgUri);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful())
                                    Log.i("problem", task.getException().toString());

                                return ref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri download = task.getResult();
                                    pass = download.toString();
                                    Log.e("URL CHECK 2", pass);
                                    reference.push().setValue(pass);

                                }
                            }
                        });

                    }
                });


                file = tfile;
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                body = fileToUpload;
                name = filename;


                path = path1;
                Toast.makeText(MainActivity.this, "HAHA " + path, Toast.LENGTH_SHORT).show();
                imageView.setImageURI(imgUri);

                imageView.setVisibility(View.VISIBLE);
                responseButton.setVisibility(View.VISIBLE);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
