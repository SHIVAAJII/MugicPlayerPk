package com.example.musicplayerfive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    TextView noMugicTextView;

    ArrayList<AudioModel> songsList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        noMugicTextView=(TextView) findViewById(R.id.no_songs_text);



        if (checkPermission()==false)
        {
            requestPermission();
            return;
        }


        String[] projection ={

                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION

        };


        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";





        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,selection,null, null);

        while ( cursor.moveToNext())
        {
            AudioModel songData = new AudioModel(cursor.getString(1),cursor.getString(0), cursor.getString(2));

            if (new File(songData.getPath()).exists())
            {
                songsList.add(songData);

            }

            if (songsList.size()==0)
            {
                noMugicTextView.setVisibility(View.VISIBLE);

            }
            else
            {
                //create recyclerView
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new MugicListAdapter(songsList,getApplicationContext()));

            }


        }




    }




    boolean checkPermission()
    {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else {
            Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
            requestPermission();
            return false;
        }
    }
    void requestPermission()
    {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
//        {
//            Toast.makeText(this, "READ PERMISSION IS REQUIRED, PLEASE ALLOW THE PERMISSION!", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
//        }





        String[] permissionStorage = {Manifest.permission.READ_EXTERNAL_STORAGE};
        int requestExternalStorage = 1;
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, permissionStorage, requestExternalStorage);
        }


    }


    @Override
    protected void onResume() {
        super.onResume();


        if (recyclerView != null)
        {
            recyclerView.setAdapter(new MugicListAdapter(songsList,getApplicationContext()));
        }

    }
}