package com.panditprogrammer.qrbarcodescanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {



    //initialize global variables
    Button scan,copy,open;
    TextView result;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assigning varable
        scan = findViewById(R.id.scan);
        copy = findViewById(R.id.copy);
        open = findViewById(R.id.open);
        result = findViewById(R.id.result);


        //onclick listener
        scan.setOnClickListener(new View.OnClickListener() {
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


        //copy result to the clipboard
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService((Context.CLIPBOARD_SERVICE));

                ClipData clip = ClipData.newPlainText("Result",result.getText().toString());

                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainActivity.this, "Result Copied", Toast.LENGTH_SHORT).show();


            }
        });


        //open in browser
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(result.getText().toString() != "")
                {
                    url = ("https://google.com/search?q="+result.getText().toString());

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }

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
            //set result to the result text view
            result.setText((intentResult.getContents()).toString());
        }
        else
        {
            //when result content is null
            //display toast
            Toast.makeText(this, "Scanning failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}