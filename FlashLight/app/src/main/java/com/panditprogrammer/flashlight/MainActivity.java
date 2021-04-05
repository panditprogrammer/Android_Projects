package com.panditprogrammer.flashlight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView toggleBtn;

    boolean hasCameraFlash = false;
    boolean flashOn = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleBtn = findViewById(R.id.imageView);

        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        toggleBtn.setOnClickListener(v -> {
            if(hasCameraFlash)
            {
                if (flashOn)
                {
                    flashOn = false;
                    toggleBtn.setImageResource(R.drawable.ic_poweroff);
                    try {
                        flashLightOff();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    flashOn = true;
                    toggleBtn.setImageResource(R.drawable.ic_poweron);
                    try {
                        flashLightOn();
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            else
            {
                Toast.makeText(MainActivity.this, "Flash Light Feature does't available on your device", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLightOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId,true);
        Toast.makeText(MainActivity.this, "FlashLight On", Toast.LENGTH_SHORT).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLightOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId,false);
        Toast.makeText(MainActivity.this, "FlashLight Off", Toast.LENGTH_SHORT).show();
    }
}