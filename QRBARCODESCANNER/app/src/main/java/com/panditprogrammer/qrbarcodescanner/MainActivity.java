package com.panditprogrammer.qrbarcodescanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    //initialize global variables
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigning varable
        btn = findViewById(R.id.button);

        //onclick listener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initializing intent integrator
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);

                //set prompt message
                intentIntegrator.setPrompt("Press Volume Button for FlashLight");

                //set indicator sound
                intentIntegrator.setBeepEnabled(true);

                //locking orientation
                intentIntegrator.setOrientationLocked(true);

                //capturing acitvity
                intentIntegrator.setCaptureActivity(capture.class);

                //start scan
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        //checking condition
        if(intentResult.getContents() != null)
        {
            //alert if condition true
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            //set title
            builder.setTitle("Result");

            //set message
            builder.setMessage(intentResult.getContents());

            //set positive button
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss dialog
                   dialog.dismiss();
                }
            });
            //show alert dialog
            builder.show();

        }
        else
        {
            //when result content is null
            //display toast
            Toast.makeText(this, "Scanning failed!", Toast.LENGTH_SHORT).show();
        }
    }
}