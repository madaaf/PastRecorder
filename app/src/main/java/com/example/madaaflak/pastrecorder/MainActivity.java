package com.example.madaaflak.pastrecorder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
  private Context context;
  // Storage Permissions
  private static final int REQUEST_EXTERNAL_STORAGE = 1;
  private static String[] PERMISSIONS_STORAGE = {
      Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
      Manifest.permission.RECORD_AUDIO
  };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final TextView tv = findViewById(R.id.btn);
    this.context = this;
    tv.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Log.e("SOEF ", "SOEF 1");
        Intent service = new Intent(MainActivity.this, ForegroundService.class);

        if (!ForegroundService.IS_SERVICE_RUNNING) {
          service.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
          ForegroundService.IS_SERVICE_RUNNING = true;
          tv.setText("Stop Service");
        } else {
          service.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
          ForegroundService.IS_SERVICE_RUNNING = false;
          tv.setText("Start Service");
        }

        // Check if we have write permission
        int permission =
            ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
            && permission != PackageManager.PERMISSION_GRANTED) {

          ActivityCompat.requestPermissions((Activity) context, PERMISSIONS_STORAGE, 1);
        } else {
          // startService(service); // TODO SOEF
          startAudioRecord();
        }

        verifyStoragePermissions((Activity) context);
      }
    });
  }

  private void startAudioRecord() {

  }

  public static void verifyStoragePermissions(Activity activity) {
    // Check if we have write permission
    int permission =
        ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    if (permission != PackageManager.PERMISSION_GRANTED) {
      // We don't have permission so prompt the user
      ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
    }
  }
}
