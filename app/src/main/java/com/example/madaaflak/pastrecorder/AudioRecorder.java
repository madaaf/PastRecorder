package com.example.madaaflak.pastrecorder;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;

/**
 * Created by madaaflak on 12/05/2018.
 */
public class AudioRecorder {

  final MediaRecorder recorder = new MediaRecorder();
  final String path;

  /**
   * Creates a new audio recording at the given path (relative to root of SD card).
   */
  public AudioRecorder(String path) {
    this.path = sanitizePath(path);
  }

  private String sanitizePath(String path) {
    if (!path.startsWith("/")) {
      path = "/" + path;
    }
    if (!path.contains(".")) {
      path += ".3gp";
    }
    return Environment.getExternalStorageDirectory().getAbsolutePath() + path;
  }

  /**
   * Starts a new recording.
   */
  public void start() throws IOException {
    Log.e("ON START ", " ok  ");
    String state = android.os.Environment.getExternalStorageState();
    if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
      Log.e("ON START ", " ok 1 ");
      throw new IOException("SD Card is not mounted.  It is " + state + ".");
    }

    // make sure the directory we plan to store the recording in exists
    File directory = new File(path).getParentFile();
    if (!directory.exists() && !directory.mkdirs()) {
      Log.e("ON START ", " ok 2 ");
      throw new IOException("Path to file could not be created.");
    }

    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    recorder.setOutputFile(path);
    recorder.prepare();
    recorder.start();
  }

  /**
   * Stops a recording that has been previously started.
   */
  public void stop() throws IOException {
    Log.e("ON START ", " ok 1 ");
    recorder.stop();
    recorder.release();
  }
}